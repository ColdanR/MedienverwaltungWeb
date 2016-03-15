package logic.genre;

import java.util.List;

import data.medien.Genre;
import database.genre.DBGenre;
import logic.DatenLogik;

public class GenreLogik extends DatenLogik<Genre> {
	private DBGenre dbAccess	=	new DBGenre();

	@Override
	public Genre create() {
		if (!checkObject()) {
			setObject(new Genre());
			return getObject();
		} else {
			addError("Bereits ein Genre geladen oder neu erstellt.");
			return null;
		}
	}

	@Override
	public boolean delete() {
		if (checkObject()) {
			boolean ret = dbAccess.delete(getObject().getId());
			if (!ret) {
				addErrors(dbAccess.getErrors());
			}
			return ret;
		} else {
			addError("Kein Objekt geladen!");
			return false;
		}
	}

	@Override
	public boolean write() {
		if (checkObject()) {
			boolean ret = dbAccess.write(getObject());
			if (!ret) {
				addErrors(dbAccess.getErrors());
			}
			return ret;
		} else {
			addError("Kein Objekt geladen!");
			return false;
		}
	}

	@Override
	public boolean load(int id) {
		if (!checkObject()) {
			Genre loaded = dbAccess.load(id);
			if (loaded == null) {
				addErrors(dbAccess.getErrors());
				return false;
			} else {
				setObject(loaded);
				return true;
			}
		} else {
			addError("Bereits ein Genre geladen oder neu erstellt.");
			return false;
		}
	}

	@Override
	public List<Genre> getAll() {
		List<Genre> ret = dbAccess.list();
		if (ret == null) {
			addErrors(dbAccess.getErrors());
		}
		return ret;
	}
	
	public List<Genre> getForMedium(int mediumId) {
		List<Genre> ret = dbAccess.getForMedium(mediumId);
		if (ret == null) {
			addErrors(dbAccess.getErrors());
		}
		return ret;
	}
}
