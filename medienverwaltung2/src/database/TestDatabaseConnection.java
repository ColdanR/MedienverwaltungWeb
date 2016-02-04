/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Testklasse, um Verbindungsmöglichkeiten zur Datenbank zu testen
 * @author Bernd Schmidt
 *
 */
public class TestDatabaseConnection extends DataBaseManager {
	
	public static void main(String[] args) {
		TestDatabaseConnection test = new TestDatabaseConnection();
		System.out.println("Klasse aufgebaut.");
		try {
			Connection conn = test.getConnection();
			System.out.println("Verbindung aufgebaut.");
			conn.close();
			System.out.println("Verbindung abgebaut.");
		} catch (SQLException e) {
			System.err.println("Verbindung fehlgeschlagen!");
			System.err.println("MysqlError Code: " + e.getErrorCode() + " - " + e.getMessage());
		}
	}
}
