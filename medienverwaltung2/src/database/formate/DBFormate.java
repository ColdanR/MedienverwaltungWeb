package database.formate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.formate.Analog;
import data.formate.Digital;
import data.formate.Formate;
import database.DataBaseManager;

public class DBFormate extends DataBaseManager {
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
	
	public	Formate			load(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		Formate				format	=	null;
		try {
			conn	=	getConnection();
			stmt	=	conn.prepareStatement("SELECT idSpeicherformat, idformat_analog, idformat_digital, "
					+ "dateiformat, qualitaet "
					+ "FROM speicherformat "
					+ "LEFT JOIN format_analog ON idSpeicherformat = format_analog.speicherformat_fk "
					+ "LEFT JOIN format_digital ON idSpeicherformat = format_digital.speicherformat_fk "
					+ "WHERE idSpeicherformat = ?");
			stmt.setInt(1, id);
			result	=	stmt.executeQuery();
			if (result.next() && result.isLast()) {
				if (result.getObject(2) != null) {
					Analog	element	=	new Analog();
					element.setDbId(result.getInt(1));
					format = element;
				} else if (result.getObject(3) != null) {
					Digital element = new Digital();
					element.setDateiformat(result.getString(4));
					element.setDbId(result.getInt(1));
					element.setQualitaet(result.getString(5));
				} else {
					addError("Unbekanntes Format!");
				}
			} else {
				addError("Format wurde nicht gefunden.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Laden des Formates mit der ID " + id);
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
		return format;
	}
	
	public	List<Formate>	loadForMedium(int idMedium) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		List<Formate>		formate	=	new ArrayList<>();
		boolean				noError	=	true;
		try {
			conn	=	getConnection();
			stmt	=	conn.prepareStatement("SELECT idSpeicherformat FROM speicherformat "
					+ "WHERE mediabase_fk = ?");
			stmt.setInt(1, idMedium);
			result	=	stmt.executeQuery();
			while (result.next() && noError) {
				int idSpeicherOrt = result.getInt(1);
				Formate format = load(idSpeicherOrt);
				if (format != null) {
					formate.add(format);
				} else {
					noError = false;
					formate = null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Laden der Formate mit der ID " + idMedium);
			formate = null;
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
		return formate;
	}
	
	public	boolean			write(Formate format, int idMedium) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		ResultSet			result	=	null;
		boolean				noError	=	true;
		try {
			conn	=	getConnection();
			conn.setAutoCommit(false);
			if (format.getDbId() == 0) {
				// INSERT
				stmt = conn.prepareStatement("INSERT INTO speicherformat (mediabase_fk) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, idMedium);
				stmt.execute();
				result = stmt.getGeneratedKeys();
				if (result.next()) {
					format.setDbId(result.getInt(1));
				}
				result.close();
				result = null;
				stmt.close();
				stmt = null;
				switch (format.getType()) {
				case Analog:
					break;
				case Digital:
					Digital digital = (Digital) format;
					stmt = conn.prepareStatement("INSERT INTO format_digital "
							+ "(speicherformat_fk, dateiformat, qualitaet) VALUES (?, ?, ?)");
					stmt.setInt(1, digital.getDbId());
					stmt.setString(2, digital.getDateiformat());
					stmt.setString(3, digital.getQualitaet());
					stmt.execute();
					stmt.close();
					stmt = null;
					break;
				}
			} else {
				// UPDATE
				switch (format.getType()) {
				case Analog:
					break;
				case Digital:
					Digital digital = (Digital) format;
					stmt = conn.prepareStatement("UPDATE format_digital SET dateiformat = ?, qualitaet = ? WHERE speicherformat_fk = ?");
					stmt.setString(1, digital.getDateiformat());
					stmt.setString(2, digital.getQualitaet());
					stmt.setInt(3, digital.getDbId());
					stmt.execute();
					stmt.close();
					stmt = null;
					break;
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Schreiben der Formate mit der ID " + format.getDbId());
			noError = false;
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
		return noError;
	}
	
	public	boolean			delete(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		boolean				ret		=	true;
		try {
			conn	=	getConnection();
			stmt	=	conn.prepareStatement("DELETE FROM speicherformat WHERE idSpeicherformat = ?");
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Löschen des Formates mit der ID " + id);
			ret = false;
		} finally {
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
}