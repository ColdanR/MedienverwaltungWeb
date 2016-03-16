package logic.formate;

import java.util.List;

import data.formate.Analog;
import data.formate.Digital;
import data.formate.Formate;
import database.formate.DBFormate;
import enums.Format;
import logic.DatenLogik;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FormateLogik extends DatenLogik<Formate> {
	private	DBFormate	dbAccess	=	new DBFormate();
	private	int			mediumId	=	0;
	
	public FormateLogik(int mediumId) {
		this.mediumId = mediumId;
	}
	
	@Override
	public Formate create() {
		throw new NotImplementedException();
	}
	
	public Formate create(Format format) {
		if (object == null) {
			switch (format) {
			case Analog:
				setObject(new Analog());
				return object;
			case Digital:
				setObject(new Digital());
				return object;
			default:
				throw new NotImplementedException();
			}
		} else {
			addError("Element bereits geladen oder erzeugt.");
			return null;
		}
	}

	@Override
	public boolean delete() {
		if (object != null && object.getDbId() != 0) {
			if (dbAccess.delete(object.getDbId())) {
				return true;
			} else {
				addErrors(dbAccess.getErrors());
				return false;
			}
		} else {
			addError("Kein Element geladen oder nur temporäres Objekt gefunden.");
			return false;
		}
	}

	@Override
	public boolean write() {
		if (object != null) {
			if (dbAccess.write(object, mediumId)) {
				return true;
			} else {
				addErrors(dbAccess.getErrors());
				return false;
			}
		} else {
			addError("Kein Element geladen oder nur temporäres Objekt gefunden.");
			return false;
		}
	}

	@Override
	public boolean load(int id) {
		if (object == null) {
			if (dbAccess.write(object, mediumId)) {
				return true;
			} else {
				addErrors(dbAccess.getErrors());
				return false;
			}
		} else {
			addError("Element bereits geladen oder erzeugt.");
			return false;
		}
	}

	@Override
	public List<Formate> getAll() {
		throw new NotImplementedException();
	}
}
