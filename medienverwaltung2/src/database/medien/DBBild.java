package database.medien;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.medien.Bild;
import data.medien.Genre;
import logic.genre.GenreLogik;

public class DBBild extends DBMedien<Bild> {

	@Override
	public Bild load(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		Bild				ret		=	null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT mb.id, mb.titel, mb.erscheinungsdatum, mb.bemerkung "
					+ "FROM mediabase mb "
					+ "INNER JOIN bild ON mb.id = bild.mediabase_id "
					+ "WHERE mb.id = ?");
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if (result.next() && result.isLast()) {
				ret = new Bild();
				ret.setDbId(result.getInt(1));
				ret.setTitel(result.getString(2));
				ret.setErscheinungsdatum(result.getDate(3).toLocalDate());
				ret.setBemerkungen(result.getString(4));
				
				GenreLogik genreLogik = new GenreLogik();
				List<Genre> genres = genreLogik.getForMedium(id);
				if (genres == null) {
					ret = null;
					genreLogik.getErrors().stream().forEach(error -> {
						addError(error);
					});
				} else {
					ret.setGenre(genres);
					if (!loadFormate(ret)) {
						ret = null;
					}
				}
			}
		} catch (SQLException e) {
			addError("Fehler beim Laden des Bildes!");
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return ret;
	}

	@Override
	public boolean write(Bild medium) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		boolean				ret		=	true;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			if (medium.getDbId() != 0) {
				// UPDATE
				stmt = conn.prepareStatement("UPDATE mediabase SET titel = ?, erscheinungsdatum = ?, bemerkung = ? WHERE mediabase.id = ?");
				stmt.setString(1, medium.getTitel());
				stmt.setDate(2, Date.valueOf(medium.getErscheinungsdatum()));
				stmt.setString(3, medium.getBemerkungen());
				stmt.setInt(4, medium.getDbId());
				stmt.execute();
				stmt.close();
				// Zusatztabelle updaten
				// nicht notwendig hier, da keine Auspr�gungen
				stmt = null;
			} else {
				// INSERT
				stmt = conn.prepareStatement("INSERT INTO mediabase (titel, erscheinungsdatum, bemerkung) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, medium.getTitel());
				stmt.setDate(2, Date.valueOf(medium.getErscheinungsdatum()));
				stmt.setString(3, medium.getBemerkungen());
				stmt.execute();
				result = stmt.getGeneratedKeys();
				result.next();
				medium.setDbId(result.getInt(1));
				result.close();
				result = null;
				stmt.close();
				// Zusatztabelle setzen
				stmt = conn.prepareStatement("INSERT INTO bild (mediabase_id) VALUES(?)");
				stmt.setInt(1, medium.getDbId());
				stmt.execute();
				stmt.close();
				stmt = null;
			}
			if (medium.getGenre() != null) {
				stmt = conn.prepareStatement("DELETE FROM mediabaseGenre WHERE mediabase_id = ?");
				stmt.setInt(1, medium.getDbId());
				stmt.execute();
				stmt.close();
				stmt = null;
				stmt = conn.prepareStatement("INSERT INTO mediabaseGenre (mediabase_id, genre_id) VALUES (?, ?)");
				for (Genre genre : medium.getGenre()) {
					stmt.setInt(1, medium.getDbId());
					stmt.setInt(2, genre.getId());
					stmt.execute();
				}
				stmt.close();
				stmt = null;
			}
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
			conn = null;
		} catch (SQLException e) {
			addError("Fehler beim Schreiben des Bildes!");
			ret = false;
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return ret;
	}

	@Override
	public boolean delete(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		boolean				ret		=	true;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("DELETE FROM mediabase WHERE mediabase.id = ?");
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			addError("Fehler beim Löschen des Bildes!");
			ret = false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return ret;
	}

	@Override
	public List<Bild> list() {
		List<Bild>			ret		=	new ArrayList<>();
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		try {
			boolean noError = true;
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT mediabase.id "
					+ "FROM mediabase INNER JOIN bild ON mediabase.id = bild.mediabase_id");
			result = stmt.executeQuery();
			while (result.next() && noError) {
				int id = result.getInt(1);
				Bild element = load(id);
				if (element != null) {
					ret.add(element);
				} else {
					noError = false;
				}
			}
		} catch (SQLException e) {
			addError("Fehler beim Laden der Bilderliste!");
			ret = null;
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return ret;
	}
}
