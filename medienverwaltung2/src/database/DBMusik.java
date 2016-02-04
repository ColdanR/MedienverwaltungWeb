package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.SpeicherFormatInterface;
import data.logic.PersonLogik;
import data.medien.Musik;

public class DBMusik extends DataBaseManager {

	public DBMusik() throws ClassNotFoundException {
		super();
	}
	
	public boolean write(Musik musik) {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = getConnection();
			if (musik.getId() == 0) {
				String sql = "INSERT INTO `TITEL` (`BEZEICHNUNG`, `PERSON_ID`, `ERSCHDATUM`, `LIVE`) VALUES (?, ?, ?, ?);";
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, musik.getTitel());
				stmt.setInt(2, musik.getInterpret().getId());
				stmt.setDate(3, Date.valueOf(musik.getErsterscheinung()));
				stmt.setBoolean(4, musik.isLive());
				stmt.execute();
				result = stmt.getGeneratedKeys();
				if (result.next()) {
					musik.setId(result.getInt(1));
					ret = true;
				}
			} else {
				String sql = "UPDATE `TITEL` SET `BEZEICHNUNG` = ?, `PERSON_ID` = ?, `ERSCHDATUM` = ?, `LIVE` = ? WHERE `ID` = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, musik.getTitel());
				stmt.setInt(2, musik.getInterpret().getId());
				stmt.setDate(3, Date.valueOf(musik.getErsterscheinung()));
				stmt.setBoolean(4, musik.isLive());
				stmt.setInt(5, musik.getId());
				stmt.execute();
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public boolean writeAll(Musik musik) {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			if (musik.getId() == 0) {
				String sql = "INSERT INTO `TITEL` (`BEZEICHNUNG`, `PERSON_ID`, `ERSCHDATUM`, `LIVE`) VALUES (?, ?, ?, ?);";
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, musik.getTitel());
				stmt.setInt(2, musik.getInterpret().getId());
				stmt.setDate(3, Date.valueOf(musik.getErsterscheinung()));
				stmt.setBoolean(4, musik.isLive());
				stmt.execute();
				result = stmt.getGeneratedKeys();
				if (result.next()) {
					musik.setId(result.getInt(1));
				}
				DBSpeicherFormat dbSpeicher = new DBSpeicherFormat();
				if (dbSpeicher.writeList(conn, musik.getSpeicherformate(), musik.getId())) {
					ret = true;
				} else {
					ret = false;
				}
			}
			if (ret) {
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public Musik getForId(int id) {
		Musik ret = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String sql = "SELECT `ID`, `BEZEICHNUNG`, `PERSON_ID`, `ERSCHDATUM`, `LIVE` FROM `TITEL` WHERE `ID` = ?";
		try {
			ret = new Musik();
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if (result.next()) {
				ret.setId(result.getInt(1));
				ret.setTitel(result.getString(2));
				ret.setErsterscheinung(result.getDate(4).toLocalDate());
				ret.setLive(result.getBoolean(5));
				
				PersonLogik logik = new PersonLogik();
				if (logik.loadObject(result.getInt(3))) {
					ret.setInterpret(logik.getObject());
					DBSpeicherFormat dbSpeicher = new DBSpeicherFormat();
					List<SpeicherFormatInterface> list = dbSpeicher.getSpeicherFormateForTitel(ret.getId());
					if (list != null) {
						ret.setSpeicherformate(list);
					} else {
						ret = null;
					}
				} else {
					ret = null;
				}
			} else {
				ret = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public boolean delete(Musik musik) {
		boolean ret = true;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM `TITEL` WHERE `ID` = ?";
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			DBSpeicherFormat dbformat = new DBSpeicherFormat();
			if (dbformat.deleteList(conn, musik.getSpeicherformate())) {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, musik.getId());
				stmt.execute();
				conn.commit();
				conn.setAutoCommit(true);
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			ret = false;
			e.printStackTrace();
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
	
	public List<Musik> getAll() {
		List<Musik> ret = new ArrayList<Musik>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String sql = "SELECT `ID`, `BEZEICHNUNG`, `PERSON_ID`, `ERSCHDATUM`, `LIVE` FROM `TITEL`";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			while (result.next() && ret != null) {
				Musik element = new Musik();
				element.setId(result.getInt(1));
				element.setTitel(result.getString(2));
				element.setErsterscheinung(result.getDate(4).toLocalDate());
				element.setLive(result.getBoolean(5));
				
				PersonLogik logik = new PersonLogik();
				if (logik.loadObject(result.getInt(3))) {
					element.setInterpret(logik.getObject());
				}
				
				DBSpeicherFormat dbSpeicher = new DBSpeicherFormat();
				List<SpeicherFormatInterface> list = dbSpeicher.getSpeicherFormateForTitel(element.getId());
				if (list != null) {
					element.setSpeicherformate(list);
				} else {
					ret = null;
				}
				if (ret != null) {
					ret.add(element);
				}
			}
		} catch (SQLException e) {
			ret = null;
			e.printStackTrace();
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
}
