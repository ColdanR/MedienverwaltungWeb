package logic.speicherorte;

import data.formate.Formate;
import database.speicherorte.DBSpeicherOrteWrapper;
import logic.DatenLogik;

public abstract class SpeicherorteLogik<E extends Formate> extends DatenLogik<E> {
	@Override
	public boolean delete() {
		DBSpeicherOrteWrapper dbWrapper = new DBSpeicherOrteWrapper();
		if (!dbWrapper.delete(object.getDbId())) {
			
		}
		// TODO Auto-generated method stub
		return false;
	}
}
