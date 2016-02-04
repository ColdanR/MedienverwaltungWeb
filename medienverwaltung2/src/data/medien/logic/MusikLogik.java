package data.medien.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import data.DatenLogik;
import data.Person;
import data.SpeicherFormatInterface;
import data.medien.Musik;
import database.DBMusik;
import enums.DatabaseErrors;
import enums.ErrorMessage;
import enums.ErrorsMusikLogik;

public class MusikLogik implements DatenLogik<Musik> {
	private	Musik				object	=	null;
	private	List<ErrorMessage>	errors	=	new ArrayList<ErrorMessage>();
	
	/**
	 * Creates a new Musik Element
	 * @return
	 */
	public boolean createNew(String titel, Person kuenstler, LocalDate ersterscheinung,
			List<SpeicherFormatInterface> speicherFormate, boolean live) {
		if (titel == null || titel.trim().length() == 0) {
			errors.add(ErrorsMusikLogik.NoTitel);
		}
		if (kuenstler == null) {
			errors.add(ErrorsMusikLogik.NoKuenstler);
		}
		if (speicherFormate == null) {
			errors.add(ErrorsMusikLogik.NoSpeicherworte);
		}
		if (errors.size() == 0) {
			object	=	new Musik();
			object.setTitel(titel);
			object.setErsterscheinung(ersterscheinung);
			object.setInterpret(kuenstler);
			object.setLive(live);
			object.setSpeicherformate(speicherFormate);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Läd ein Objekt mit der entsprechenden ID
	 * @param id
	 * @return true, wenn Objekt geladen wurde, false, wenn ein Fehler vorliegt.
	 */
	@Override
	public boolean loadObject(int id) {
		try {
			DBMusik dbLogic = getDBLogic();
			object = dbLogic.getForId(id);
			if (object == null) {
				errors.add(DatabaseErrors.UnableToRead);
				return false;
			} else {
				return true;
			}
		} catch (ClassNotFoundException e) {
			errors.add(DatabaseErrors.NoDBAvailable);
		}
		return false;
	}
	
	@Override
	public List<ErrorMessage> getErrors() {
		return errors;
	}

	/**
	 * Creates a new Musik Element
	 * @return
	 */
	public boolean editLoaded(String titel, Person kuenstler, LocalDate ersterscheinung,
			List<SpeicherFormatInterface> speicherFormate, boolean live) {
		if (object == null) {
			errors.add(ErrorsMusikLogik.NotLoaded);
		}
		if (titel == null || titel.trim().length() == 0) {
			errors.add(ErrorsMusikLogik.NoTitel);
		}
		if (kuenstler == null) {
			errors.add(ErrorsMusikLogik.NoKuenstler);
		}
		if (speicherFormate == null) {
			errors.add(ErrorsMusikLogik.NoSpeicherworte);
		}
		if (errors.size() == 0) {
			object.setTitel(titel);
			object.setErsterscheinung(ersterscheinung);
			object.setInterpret(kuenstler);
			object.setLive(live);
			object.setSpeicherformate(speicherFormate);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Musik getObject() {
		return object;
	}
	
	public void addSpeicherFormat (SpeicherFormatInterface speicherFormat) {
		object.getSpeicherformate().add(speicherFormat);
	}
	
	@Override
	public boolean delete() {
		try {
			DBMusik dbLogic = getDBLogic();
			if (!dbLogic.delete(object)) {
				errors.add(DatabaseErrors.UnableToDelete);
				return false;
			} else {
				return true;
			}
		} catch (ClassNotFoundException e) {
			errors.add(DatabaseErrors.NoDBAvailable);
		}
		return false;
	}
	
	/**
	 * Liefert eine Liste über alle Musiktitel zurück
	 * @return List&lt;Musik&gt; aller Titel
	 */
	@Override
	public List<Musik> getAll() {
		try {
			DBMusik dbLogic = getDBLogic();
			List<Musik> ret = dbLogic.getAll();
			if (ret == null) {
				errors.add(DatabaseErrors.UnableToRead);
				return null;
			} else {
				return ret;
			}
		} catch (ClassNotFoundException e) {
			errors.add(DatabaseErrors.NoDBAvailable);
		}
		return null;
	}

	private DBMusik getDBLogic() throws ClassNotFoundException {
		return new DBMusik();
	}
	
	@Override
	public boolean write() {
		if (object == null) {
			errors.add(ErrorsMusikLogik.NotLoaded);
			return false;
		}
		try {
			DBMusik dbLogic = getDBLogic();
			if (object.getId() == 0) {
				if (!dbLogic.writeAll(object)) {
					errors.add(DatabaseErrors.UnableToWrite);
					return false;
				} else {
					return true;
				}
			} else {
				if (!dbLogic.write(object)) {
					errors.add(DatabaseErrors.UnableToWrite);
					return false;
				} else {
					return true;
				}
			}
		} catch (ClassNotFoundException e) {
			errors.add(DatabaseErrors.NoDBAvailable);
		}
		return false;
	}
	
	public boolean writeAll() {
		try {
			DBMusik dbLogic = getDBLogic();
			if (!dbLogic.writeAll(object)) {
				errors.add(DatabaseErrors.UnableToWrite);
				return false;
			} else {
				return true;
			}
		} catch (ClassNotFoundException e) {
			errors.add(DatabaseErrors.NoDBAvailable);
		}
		return false;
	}

	@Override
	public void reset() {
		this.object = null;
		this.errors = new ArrayList<ErrorMessage>();
	}
}
