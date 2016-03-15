package database.speicherorte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.speicherorte.Kassette;
import data.speicherorte.enums.KassettenArt;

public class DBKassette extends DBSpeicherOrte<Kassette> {
	@Override
	public Kassette load(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		Kassette			ret		=	null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT bez, bemerkung, zustand, art "
					+ "FROM MEDIASTORAGE "
					+ "INNER JOIN kassette ON mdstorage_id = idMediastorage "
					+ "WHERE idMediastorage = ?");
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if (result.next() && result.isLast()) {
				ret = new Kassette();
				ret.setDbId(id);
				ret.setLagerOrt(result.getString(1));
				ret.setBemerkung(result.getString(2));
				ret.setZustand(result.getString(3));
				ret.setArt(KassettenArt.getElementFromId(result.getInt(4)));
			} else {
				addError("Kassette mit der ID " + id + " nicht gefunden");
			}
			result.close();
			result = null;
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Laden der Kassette");
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	@Override
	public boolean write(Kassette medium, int formatId) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		boolean				ret		=	true;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			if (medium.getDbId() == 0) {
				stmt = conn.prepareStatement("INSERT INTO MEDIASTORAGE (bez, bemerkung, speicherformat_fk) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, medium.getLagerOrt());
				stmt.setString(2, medium.getBemerkung());
				stmt.setInt(3, formatId);
				stmt.execute();
				result = stmt.getGeneratedKeys();
				result.next();
				medium.setDbId(result.getInt(1));
				result.close();
				result = null;
				stmt.close();
				stmt = conn.prepareStatement("INSERT INTO KASSETTE (mdstorage_id, zustand, art) VALUES (?, ?, ?)");
				stmt.setInt(1, medium.getDbId());
				stmt.setString(2, medium.getZustand());
				stmt.setInt(3, medium.getArt().getId());
				stmt.execute();
				stmt.close();
				stmt = null;
			} else {
				stmt = conn.prepareStatement("UPDATE mediastorage SET bez = ?, bemerkung = ? WHERE idMediastorage = ?");
				stmt.setString(1, medium.getLagerOrt());
				stmt.setString(2, medium.getBemerkung());
				stmt.setInt(3, medium.getDbId());
				stmt.execute();
				stmt.close();
				stmt = conn.prepareStatement("UPDATE kassette SET zustand = ?, art = ? WHERE mdstorage_id = ?");
				stmt.setString(1, medium.getZustand());
				stmt.setInt(2, medium.getArt().getId());
				stmt.setInt(3, medium.getDbId());
				stmt.execute();
				stmt.close();
				stmt = null;
			}
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Schreiben der Kassette");
			ret = false;
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	@Override
	public List<Kassette> listForFormatId(int formatId) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		boolean				noError	=	true;
		List<Kassette>		ret		=	new ArrayList<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT idMediastorage FROM mediastorage "
					+ "INNER JOIN kassette ON kassette.mdstorage_id = mediastorage.idMediastorage "
					+ "WHERE speicherformat_fk = ?");
			stmt.setInt(1, formatId);
			result = stmt.executeQuery();
			while (result.next() && noError) {
				Kassette element = load(result.getInt(1));
				if (element == null) {
					noError = false;
				} else {
					ret.add(element);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Zuholen der ID Liste für SpeicherformatID " + formatId);
			ret = null;
		}
		return null;
	}
}
