package data.logic;

import java.util.ArrayList;
import java.util.List;

import data.DatenLogik;
import data.Person;
import database.DBPerson;
import enums.DatabaseErrors;
import enums.ErrorMessage;
import enums.ErrorsPersonLogik;

public class PersonLogik implements DatenLogik<Person> {
	private	Person				object	=	null;
	private	List<ErrorMessage>	errors	=	new ArrayList<ErrorMessage>();

	public boolean createNew(String nachname, String vorname, String kuenstlername) {
		if (nachname == null) {
			errors.add(ErrorsPersonLogik.KeinNachname);
		}
		if (vorname == null) {
			errors.add(ErrorsPersonLogik.KeinVorname);
		}
		if (kuenstlername == null || kuenstlername.trim().length() == 0) {
			errors.add(ErrorsPersonLogik.KeinKuenstlername);
		}
		if (errors.size() > 0) {
			return false;
		}
		object = new Person();
		object.setKuenstlername(kuenstlername);
		object.setNachname(nachname);
		object.setVorname(vorname);
		return true;
	}
	
	public boolean editLoaded(String nachname, String vorname, String kuenstlername) {
		if (nachname == null) {
			errors.add(ErrorsPersonLogik.KeinNachname);
		}
		if (vorname == null) {
			errors.add(ErrorsPersonLogik.KeinVorname);
		}
		if (kuenstlername == null || kuenstlername.trim().length() == 0) {
			errors.add(ErrorsPersonLogik.KeinKuenstlername);
		}
		if (errors.size() > 0) {
			return false;
		}
		object.setKuenstlername(kuenstlername);
		object.setNachname(nachname);
		object.setVorname(vorname);
		return true;
	}
	
	@Override
	public boolean delete() {
		try {
			DBPerson dbLogic = getDBLogic();
			if (!dbLogic.delete(object)) {
				errors.add(DatabaseErrors.UnableToDeletePerson);
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
	public boolean write() {
		try {
			DBPerson dbLogic = getDBLogic();
			if (!dbLogic.writePerson(object)) {
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
	public boolean loadObject(int id) {
		try {
			DBPerson dbLogic = getDBLogic();
			object = dbLogic.getPerson(id);
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
	public Person getObject() {
		return object;
	}

	@Override
	public List<Person> getAll() {
		try {
			DBPerson dbLogic = getDBLogic();
			List<Person> ret = dbLogic.getAll();
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

	@Override
	public List<ErrorMessage> getErrors() {
		return errors;
	}
	
	private DBPerson getDBLogic() throws ClassNotFoundException {
		return new DBPerson();
	}

	@Override
	public void reset() {
		this.object = null;
		this.errors = new ArrayList<ErrorMessage>();
	}
}
