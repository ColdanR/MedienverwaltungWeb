package data;

import java.util.List;

import enums.ErrorMessage;

/**
 * Interface zur Konsolidierung der Logikmethoden
 * @author Bernd Schmidt
 *
 * @param <E> Datentyp der Logik
 */
public interface DatenLogik<E> {
	public boolean delete();
	public boolean write();
	public boolean loadObject(int id);
	public E getObject();
	public List<E> getAll();
	public List<ErrorMessage> getErrors();
	public void reset();
}