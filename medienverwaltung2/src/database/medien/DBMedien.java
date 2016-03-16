package database.medien;

import java.util.ArrayList;
import java.util.List;

import data.formate.Formate;
import data.medien.Medium;
import database.DataBaseManager;
import database.formate.DBFormate;

public abstract class DBMedien<E extends Medium> extends DataBaseManager {
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
	
	protected final boolean loadFormate(Medium medium) {
		if (medium == null) {
			return false;
		}
		DBFormate dbFormate = new DBFormate();
		List<Formate> formate = dbFormate.loadForMedium(medium.getDbId());
		if (formate == null) {
			dbFormate.getErrors().stream().forEach(error -> {
				addError(error);
			});
			return false;
		} else {
			for (Formate format : formate) {
				medium.addFormat(format);
			}
			return true;
		}
	}
	
	public	abstract	E		load(int id);
	public	abstract	boolean	write(E medium);
	public	abstract	boolean	delete(int id);
	public	abstract	List<E>	list();
}
