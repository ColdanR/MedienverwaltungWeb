package logic.speicherorte;

import java.util.List;

import data.formate.Formate;
import data.speicherorte.Speicherort;
import database.speicherorte.DBSpeicherOrteWrapper;
import logic.DatenLogik;

public abstract class SpeicherorteLogik<E extends Speicherort> extends DatenLogik<E> {
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
	
	public List<Speicherort> getForId(int formatId) {
		DBSpeicherOrteWrapper	dbWrapper	=	new DBSpeicherOrteWrapper();
		List<Speicherort>		ret			=	dbWrapper.getForId(formatId);
		if (ret == null) {
			addErrors(dbWrapper.getErrors());
		}
		return ret;
	}
}
