package logic.medien;

import java.util.List;

import data.medien.Medium;
import database.medien.DBMedien;
import logic.DatenLogik;

public abstract class MediumLogik<E extends Medium> extends DatenLogik<E> {
	private DBMedien<E> dbAccess;
	
	protected MediumLogik(DBMedien<E> dbAccess) {
		this.dbAccess	=	dbAccess;
	}

	@Override
	public final boolean delete() {
		if (checkObject()) {
			if (dbAccess.delete(object.getDbId())) {
				return true;
			} else {
				addErrors(dbAccess.getErrors());
				return false;
			}
		} else {
			addError("Kein Objekt geladen!");
			return false;
		}
	}

	@Override
	public final boolean write() {
		if (checkObject()) {
			if (dbAccess.write(object)) {
				return true;
			} else {
				addErrors(dbAccess.getErrors());
				return false;
			}
		} else {
			addError("Kein Objekt geladen!");
			return false;
		}
	}

	@Override
	public final boolean load(int id) {
		if (checkObject()) {
			addError("Objekt bereits geladen.");
			return false;
		} else {
			object = dbAccess.load(id);
			addErrors(dbAccess.getErrors());
			return getErrors().size() == 0;
		}
	}

	@Override
	public final List<E> getAll() {
		List<E> ret = dbAccess.list();
		addErrors(dbAccess.getErrors());
		return ret;
	}
}
