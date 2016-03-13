package logic.person;

import java.util.List;

import data.person.Person;
import database.DBPerson;
import enums.errors.DatabaseErrors;
import enums.errors.ErrorsPersonLogik;
import logic.DatenLogik;

public class PersonLogik extends DatenLogik<Person> {
	private	Person				object	=	null;

	public boolean createNew(String nachname, String vorname, String kuenstlername) {
		if (nachname == null) {
			addError(ErrorsPersonLogik.KeinNachname.getErrorMessage());
		}
		if (vorname == null) {
			addError(ErrorsPersonLogik.KeinVorname.getErrorMessage());
		}
		if (kuenstlername == null || kuenstlername.trim().length() == 0) {
			addError(ErrorsPersonLogik.KeinKuenstlername.getErrorMessage());
		}
		if (getErrors().size() > 0) {
			return false;
		}
		object = create();
		object.setKuenstlername(kuenstlername);
		object.setNachname(nachname);
		object.setVorname(vorname);
		return true;
	}
	
	public boolean editLoaded(String nachname, String vorname, String kuenstlername) {
		if (nachname == null) {
			addError(ErrorsPersonLogik.KeinNachname.getErrorMessage());
		}
		if (vorname == null) {
			addError(ErrorsPersonLogik.KeinVorname.getErrorMessage());
		}
		if (kuenstlername == null || kuenstlername.trim().length() == 0) {
			addError(ErrorsPersonLogik.KeinKuenstlername.getErrorMessage());
		}
		if (getErrors().size() > 0) {
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
				addError(DatabaseErrors.UnableToDeletePerson.getErrorMessage());
				return false;
			} else {
				return true;
			}
		} catch (ClassNotFoundException e) {
			addError(DatabaseErrors.NoDBAvailable.getErrorMessage());
		}
		return false;
	}

	@Override
	public boolean write() {
		try {
			DBPerson dbLogic = getDBLogic();
			if (!dbLogic.writePerson(object)) {
				addError(DatabaseErrors.UnableToWrite.getErrorMessage());
				return false;
			} else {
				return true;
			}
		} catch (ClassNotFoundException e) {
			addError(DatabaseErrors.NoDBAvailable.getErrorMessage());
		}
		return false;
	}

	@Override
	public boolean load(int id) {
		try {
			DBPerson dbLogic = getDBLogic();
			object = dbLogic.getPerson(id);
			if (object == null) {
				addError(DatabaseErrors.UnableToRead.getErrorMessage());
				return false;
			} else {
				return true;
			}
		} catch (ClassNotFoundException e) {
			addError(DatabaseErrors.NoDBAvailable.getErrorMessage());
		}
		return false;
	}

	@Override
	public List<Person> getAll() {
		try {
			DBPerson dbLogic = getDBLogic();
			List<Person> ret = dbLogic.getAll();
			if (ret == null) {
				addError(DatabaseErrors.UnableToRead.getErrorMessage());
				return null;
			} else {
				return ret;
			}
		} catch (ClassNotFoundException e) {
			addError(DatabaseErrors.NoDBAvailable.getErrorMessage());
		}
		return null;
	}
	
	private DBPerson getDBLogic() throws ClassNotFoundException {
		return new DBPerson();
	}

	@Override
	public Person create() {
		return new Person();
	}
}
