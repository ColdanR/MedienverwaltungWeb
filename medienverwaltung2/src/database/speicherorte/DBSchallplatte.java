package database.speicherorte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.speicherorte.Schallplatte;
import data.speicherorte.enums.SchallplatteArt;

public class DBSchallplatte extends DBSpeicherOrte<Schallplatte> {

	@Override
	public Schallplatte load(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		Schallplatte		ret		=	null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT s.bezeichnung, s.bemerkung, sp.zustand, sp.art "
					+ "FROM speicherort s "
					+ "INNER JOIN schallplatte sp ON s.id = sp.speicherort_id "
					+ "WHERE s.id = ?");
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if (result.next() && result.isLast()) {
				ret = new Schallplatte();
				ret.setDbId(id);
				ret.setLagerOrt(result.getString(1));
				ret.setBemerkung(result.getString(2));
				ret.setZustand(result.getString(3));
				ret.setArt(SchallplatteArt.getElementFromId(result.getInt(4)));
			} else {
				addError("Schallplatte mit der ID " + id + " nicht gefunden");
			}
			result.close();
			result = null;
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Laden der Schallplatte");
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
	public boolean write(Schallplatte medium, int formatId) {
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
				stmt = conn.prepareStatement("INSERT INTO schallplatte (speicherort_id, zustand, art) VALUES (?, ?, ?)");
				stmt.setInt(1, medium.getDbId());
				stmt.setString(2, medium.getZustand());
				stmt.setInt(3, medium.getArt().getId());
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
				stmt = conn.prepareStatement("UPDATE schallplatte SET zustand = ?, art = ? WHERE speicherort_id = ?");
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
			addError("Fehler beim Schreiben der Schallplatte");
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
	public List<Schallplatte> listForFormatId(int formatId) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		boolean				noError	=	true;
		List<Schallplatte>	ret		=	new ArrayList<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT speicherort.id FROM speicherort s"
					+ "INNER JOIN schallplatte sp ON s.id = sp.speicherort_id"
					+ "WHERE s.speicherformat_id = ?");
			stmt.setInt(1, formatId);
			result = stmt.executeQuery();
			while (result.next() && noError) {
				Schallplatte element = load(result.getInt(1));
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
		return ret;
	}

}
