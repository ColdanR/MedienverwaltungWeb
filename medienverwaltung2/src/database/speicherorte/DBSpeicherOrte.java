package database.speicherorte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.speicherorte.Speicherort;
import database.DataBaseManager;

public abstract class DBSpeicherOrte<E extends Speicherort> extends DataBaseManager {
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
	
	public	boolean	delete(int id) {
		Connection			conn	=	null;
		PreparedStatement	stmt	=	null;
		boolean				ret		=	true;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("DELETE FROM speicherorte WHERE speicherorte.id  = ?");
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			addError("Fehler beim Löschen des Speicherortes!");
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
	
	public	abstract	E		load(int id);
	public	abstract	boolean	write(E medium, int formatId);
	public	abstract	List<E>	listForFormatId(int formatId);
}
