package logic;

import java.util.ArrayList;
import java.util.List;


/**
 * Interface zur Konsolidierung der Logikmethoden
 * @author Bernd Schmidt
 *
 * @param <E> Datentyp der Logik
 */
public abstract class DatenLogik<E> {
	private		List<String>	errors	=	new ArrayList<>();
	protected	E				object;
	
	public final void setObject(E object) {
		this.object = object;
	}
	
	public final E getObject() {
		return object;
	}
	
	protected final void addError(String error) {
		errors.add(error);
	}
	
	protected final void addErrors(List<String> errors) {
		errors.addAll(errors);
	}
	
	public final List<String> getErrors() {
		return errors;
	}
	
	protected final boolean checkObject() {
		if (object == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public	abstract	E		create();
	public	abstract	boolean	delete();
	public	abstract	boolean	write();
	public	abstract	boolean	load(int id);
	public	abstract	List<E>	getAll();
}