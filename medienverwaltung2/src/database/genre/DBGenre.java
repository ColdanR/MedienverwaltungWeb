package database.genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.medien.Genre;
import database.DataBaseManager;

public class DBGenre extends DataBaseManager {
private	List<String>	errors	=	new ArrayList<>();
	
	protected final void addError(String error) {
		errors.add(error);
	}
	
	public final List<String> getErrors() {
		List<String> ret = new ArrayList<>();
		errors.stream().forEach(error -> {
			ret.add(error);
		});
		return ret;
	}
	
	public Genre load(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		Genre				ret		=	null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT bezeichnung FROM genre WHERE genre.id = ?");
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if (result.next() && result.isLast()) {
				ret = new Genre();
				ret.setDbId(id);
				ret.setBezeichnung(result.getString(1));
			}
		} catch (SQLException e) {
			addError("Fehler beim Laden des Genres!");
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return ret;
	}
	
	public boolean write(Genre genre) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		boolean				ret		=	true;
		try {
			conn = getConnection();
			if (genre.getId() != 0) {
				// UPDATE
				stmt = conn.prepareStatement("UPDATE genre SET bezeichnung = ? WHERE genre.id = ?");
				stmt.setString(1, genre.getBezeichnung());
				stmt.setInt(2, genre.getId());
				stmt.execute();
			} else {
				// INSERT
				stmt = conn.prepareStatement("INSERT INTO genre (bezeichnung) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, genre.getBezeichnung());
				stmt.execute();
				result = stmt.getGeneratedKeys();
				result.next();
				genre.setDbId(result.getInt(1));
			}
		} catch (SQLException e) {
			addError("Fehler beim Schreiben des Genres!");
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
	
	public boolean delete(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		boolean				ret		=	true;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("DELETE FROM genre WHERE genre.id = ?");
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1451) {
				addError("Das Genre ist mindestens noch einem Medium zugeordnet. Bitte dort zuerst löschen.");
			} else {
				addError("Fehler beim Löschen des Genres!");
			}
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
	
	public List<Genre> list() {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		List<Genre>			ret		=	new ArrayList<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT genre.id, bezeichnung FROM genre");
			result = stmt.executeQuery();
			while (result.next()) {
				Genre element = new Genre();
				element.setDbId(result.getInt(1));
				element.setBezeichnung(result.getString(2));
				ret.add(element);
			}
		} catch (SQLException e) {
			addError("Fehler beim Laden der Genres!");
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
	
	public List<Genre> getForMedium(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		List<Genre>			ret		=	new ArrayList<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT genre.id, genre.bezeichnung FROM genre "
					+ "INNER JOIN mediabaseGenre mbg ON genre.id = mbg.genre_id "
					+ "WHERE mbg.mediabase_id = ?");
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			while (result.next()) {
				Genre element = new Genre();
				element.setDbId(result.getInt(1));
				element.setBezeichnung(result.getString(2));
				ret.add(element);
			}
		} catch (SQLException e) {
			addError("Fehler beim Laden der Genres!");
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