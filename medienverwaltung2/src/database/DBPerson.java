package database;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.Person;

/**
 * Database management Class for Person Object
 * @author <b>Bernd Schmidt</b> Funktionsk�pfe angelegt
 * @author <b>Andreas John</b> Implementierung der Funktionen
 *
 */
public class DBPerson extends DataBaseManager {

	public DBPerson() throws ClassNotFoundException {
		super();
	}
	
	/**
	 * Read content of a person into an object referred by the id
	 * @param id ID of the Person to load
	 * @return Person Object containing the data or null if not found or an exception has been thrown.
	 */
	public Person getPerson(int id) {
		Person ret = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String sql = "SELECT A.ID, A.NAME, A.NAME_1, A.KNAME FROM PERSON A WHERE A.ID = ?";
			
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			// Erster Index ist immer 1 bei SQL Befehlen!
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if (result.next()) {
				ret = new Person();
				ret.setId(result.getInt(1));
				ret.setNachname(result.getString(2));
				ret.setVorname(result.getString(3));
				ret.setKuenstlername(result.getString(4));
			} else {
				return null;
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
	
	public boolean writePerson(Person person){
		boolean ret = false;
		if (person == null) {
			return false;
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			if (person.getId() == 0) {
				String sql = "INSERT INTO PERSON (`NAME`, `NAME_1`, `KNAME`) VALUES (?, ?, ?)";
				conn = getConnection();
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, person.getNachname());
				stmt.setString(2, person.getVorname());
				stmt.setString(3, person.getKuenstlername());
				stmt.execute();
				result = stmt.getGeneratedKeys();
				if (result.next()) {
					person.setId(result.getInt(1));
					ret = true;
				}
			} else {
				String sql = "UPDATE PERSON SET `NAME` = ?, `NAME_1` = ?, `KNAME` = ? WHERE `ID` = ?";
				conn = getConnection();
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, person.getNachname());
				stmt.setString(2, person.getVorname());
				stmt.setString(3, person.getKuenstlername());
				stmt.setInt(4, person.getId());
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
	
	/**
	 * Returns a list of persons featuring a song
	 * @param songId the id of the song
	 * @return List of Person
	 */

	public boolean delete(Person object) { 
		boolean ret = false;
		int id;
		if (object == null)  {
			return false;
		}
		id = object.getId();
		
		if (id == 0) {
			return false;
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String sql = "DELETE FROM PERSON WHERE ID = ?";			
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,object.getId());
			stmt.execute();
			ret = true;
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
	
	public List<Person> getAll() {
		Person ret = null;
		List<Person> personlist = new ArrayList<Person>();
				
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			String sql = "SELECT A.ID, A.NAME, A.NAME_1, A.KNAME FROM PERSON A";
			
			conn = getConnection();
			stmt = conn.createStatement();
			
			result = stmt.executeQuery(sql);
			while (result.next()) {
				ret = new Person();
				ret.setId(result.getInt(1));
				ret.setNachname(result.getString(2));
				ret.setVorname(result.getString(3));
				ret.setKuenstlername(result.getString(4));
				personlist.add(ret);
			}
		} catch (SQLException e) {
			personlist = null;
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
		return personlist;
	}
}
