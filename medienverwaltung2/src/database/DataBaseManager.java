package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class DataBaseManager {
	/**
	 * Enumeration für die unterstützten Datenbanken
	 * @author User
	 *
	 */
	private enum SqlServers {
		MySQL ("jdbc:mysql://", "mysql"),
		FireBird("jdbc:firebirdsql://", "firebird")
		;
		
		private	String	connectPart	=	"";
		private	String	id			=	"";
		
		private SqlServers (String connectPart, String id) {
			this.connectPart	=	connectPart;
			this.id				=	id;
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
	
	private 	static	String		sqlConnect	=	"";
	private		static	String		username	=	"";
	private		static	String		password	=	"";
	protected	static	boolean		noErrors	=	false;
	
	static {
		try {
			ResourceBundle	db		=	ResourceBundle.getBundle("database.database");
			SqlServers		server	=	SqlServers.getServerByName(db.getString("dbDriver"));
			if (server != null) {
				sqlConnect				=	server.getconnectPart() + db.getString("dbHost") + "/" + db.getString("dbName");
				username				=	db.getString("dbUser");
				password				=	db.getString("dbPassword");
				noErrors = true;
			}
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
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
