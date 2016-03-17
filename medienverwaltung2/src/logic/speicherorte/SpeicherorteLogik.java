package logic.speicherorte;

import java.util.List;

import data.speicherorte.Speicherort;
import database.speicherorte.DBSpeicherOrte;
import database.speicherorte.DBSpeicherOrteWrapper;
import logic.DatenLogik;

public abstract class SpeicherorteLogik<E extends Speicherort> extends DatenLogik<E> {
	private DBSpeicherOrte<E>	dbAccess;
	private	int					formatId;
	
	protected SpeicherorteLogik(DBSpeicherOrte<E> dbAccess, int formatId) {
		this.dbAccess	=	dbAccess;
		this.formatId	=	formatId;
	}

	@Override
	public final boolean write() {
		if (checkObject()) {
			if (dbAccess.write(object, formatId)) {
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
		throw new UnsupportedOperationException("Not available");
	}
	
	@Override
	public boolean delete() {
		DBSpeicherOrteWrapper dbWrapper = new DBSpeicherOrteWrapper();
		if (!dbWrapper.delete(object.getDbId())) {
			addErrors(dbWrapper.getErrors());
			return false;
		} else {
			return true;
		}
	}
}
