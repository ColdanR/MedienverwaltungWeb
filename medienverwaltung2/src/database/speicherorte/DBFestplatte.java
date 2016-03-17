package database.speicherorte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.speicherorte.Festplatte;

public class DBFestplatte extends DBSpeicherOrte<Festplatte> {

	@Override
	public Festplatte load(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		Festplatte			ret		=	null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT s.bezeichnung, s.bemerkung "
					+ "FROM speicherort s "
					+ "INNER JOIN festplatte f ON s.id = f.speicherort_id "
					+ "WHERE s.id = ?");
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if (result.next() && result.isLast()) {
				ret = new Festplatte();
				ret.setDbId(id);
				ret.setLagerOrt(result.getString(1));
				ret.setBemerkung(result.getString(2));
			} else {
				addError("Festplatte mit der ID " + id + " nicht gefunden");
			}
			result.close();
			result = null;
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Laden der Festplatte");
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
	public boolean write(Festplatte medium, int formatId) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		boolean				ret		=	true;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			if (medium.getDbId() == 0) {
				stmt = conn.prepareStatement("INSERT INTO speicherort (bezeichnung, bemerkung, speicherformat_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
				stmt = conn.prepareStatement("INSERT INTO festplatte (speicherort_id) VALUES (?)");
				stmt.setInt(1, medium.getDbId());
				stmt.execute();
				stmt.close();
				stmt = null;
			} else {
				stmt = conn.prepareStatement("UPDATE speicherort SET bezeichnung = ?, bemerkung = ? WHERE speicherort.id = ?");
				stmt.setString(1, medium.getLagerOrt());
				stmt.setString(2, medium.getBemerkung());
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
			addError("Fehler beim Schreiben der Festplatte");
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
	public List<Festplatte> listForFormatId(int formatId) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		boolean				noError	=	true;
		List<Festplatte>			ret		=	new ArrayList<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT s.id FROM speicherort s"
					+ "INNER JOIN festplatte f ON s.id = f.speicherort_id"
					+ "WHERE s.speicherformat_id = ?");
			stmt.setInt(1, formatId);
			result = stmt.executeQuery();
			while (result.next() && noError) {
				Festplatte element = load(result.getInt(1));
				if (element == null) {
					noError = false;
				} else {
					ret.add(element);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Zuholen der ID Liste f�r SpeicherformatID " + formatId);
			ret = null;
		}
		return ret;
	}

}
