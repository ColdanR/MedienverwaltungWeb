package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class DataBaseManager {
	private 	static	String		sqlConnect	=	"";
	private		static	String		username	=	"";
	private		static	String		password	=	"";
	protected	static	boolean		noErrors	=	false;
	
	/**
	 * Enumeration für die unterstützten Datenbanken
	 * @author User
	 *
	 */
	private enum SqlServers {
		MySQL ("com.mysql.jdbc.Driver", "jdbc:mysql://", "mysql"),
		FireBird("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql://", "firebird")
		;
		
		private	String	driver		=	"";
		private	String	connectPart	=	"";
		private	String	id			=	"";
		
		private SqlServers (String driver, String connectPart, String id) {
			this.driver			=	driver;
			this.connectPart	=	connectPart;
			this.id				=	id;
		}
		
		/**
		 * Klasse des Treibers
		 * @return
		 */
		public String getDriver() {
			return driver;
		}
		
		/**
		 * Prefix der Datenbankverbindung
		 * @return
		 */
		public String getconnectPart() {
			return connectPart;
		}
		
		/**
		 * Eindeutige Bezeichnung
		 * @return
		 */
		private String getId() {
			return id;
		}
		
		/**
		 * 
		 * @param id
		 * @return
		 */
		public static SqlServers getServerByName(String id) {
			for (SqlServers value : SqlServers.values()) {
				if (id.equals(value.getId())) {
					return value;
				}
			}
			return null;
		}
	}
	
	static {
		try {
			ResourceBundle	db		=	ResourceBundle.getBundle("database");
			SqlServers		server	=	SqlServers.getServerByName(db.getString("dbDriver"));
			sqlConnect				=	server.getconnectPart() + db.getString("dbHost") + "/" + db.getString("dbName");
			username				=	db.getString("dbUser");
			password				=	db.getString("dbPassword");
			Class.forName(server.getDriver());
			noErrors = true;
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (MissingResourceException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (LinkageError e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the Connection object
	 * @return the connection object
	 * @throws SQLException Connection refused
	 */
	protected final Connection getConnection () throws SQLException {
		Connection conn = DriverManager.getConnection(sqlConnect, username, password);
		return conn;
	}
}
