package database.medien;

import java.util.ArrayList;
import java.util.List;

import database.DataBaseManager;

public abstract class DBMedien<E> extends DataBaseManager {
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
	
	public	abstract	E		load(int id);
	public	abstract	boolean	write(E medium);
	public	abstract	boolean	delete(int id);
	public	abstract	List<E>	list();
}
