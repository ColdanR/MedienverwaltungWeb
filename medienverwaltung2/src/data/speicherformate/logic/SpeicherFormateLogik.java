/**
 * 
 */
package data.speicherformate.logic;

import java.util.ArrayList;
import java.util.List;

import data.DatenLogik;
import data.SpeicherFormatInterface;
import data.speicherformate.DigitalMusik;
import database.DBSpeicherFormat;
import enums.ErrorMessage;
import enums.ErrorsSpeicherFormateLogik;

/**
 * Klasse zur Abhandlung der Logik f�r Speicherformate
 * @author Bernd Schmidt
 *
 */
public class SpeicherFormateLogik implements DatenLogik<SpeicherFormatInterface> {
	private	SpeicherFormatInterface	object	=	null;
	private	int						titelId	=	0;
	private	List<ErrorMessage>		errors	=	new ArrayList<ErrorMessage>();
	
	public SpeicherFormateLogik() {
	}
	
	public boolean createDigitalMusik(String datentraeger, String pfad, String format, String qualitaet) {
		if (datentraeger == null || datentraeger.isEmpty()) {
			errors.add(ErrorsSpeicherFormateLogik.NoDatentraeger);
		}
		if (pfad == null || pfad.isEmpty()) {
			errors.add(ErrorsSpeicherFormateLogik.NoPfad);
		}
		if (format == null || format.isEmpty()) {
			errors.add(ErrorsSpeicherFormateLogik.NoFormat);
		}
		if (qualitaet == null || qualitaet.isEmpty()) {
			errors.add(ErrorsSpeicherFormateLogik.NoQualitaet);
		}
		if (errors.size() == 0) {
			DigitalMusik tempObject = new DigitalMusik();
			tempObject.setDatentraeger(datentraeger);
			tempObject.setFormat(format);
			tempObject.setPfad(pfad);
			tempObject.setQualitaet(qualitaet);
			object = tempObject;
		}
		return errors.size() == 0;
	}

	@Override
	public boolean delete() {
		DBSpeicherFormat	dbLogik	=	new DBSpeicherFormat();
		if (object == null) {
			errors.add(ErrorsSpeicherFormateLogik.NoObjectLoaded);
			return false;
		}
		if (!dbLogik.delete(object)) {
			errors.add(ErrorsSpeicherFormateLogik.UnableToDelete);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean write() {
		DBSpeicherFormat	dbLogik	=	new DBSpeicherFormat();
		if (object == null) {
			errors.add(ErrorsSpeicherFormateLogik.NoObjectLoaded);
		}
		if (titelId == 0) {
			errors.add(ErrorsSpeicherFormateLogik.NoTitleIdSet);
		}
		if (errors.size() == 0) {
			if (!dbLogik.write(object, titelId)) {
				errors.add(ErrorsSpeicherFormateLogik.UnableToWrite);
			}
		}
		return errors.size() == 0;
	}

	@Override
	public boolean loadObject(int id) {
		DBSpeicherFormat	dbLogik	=	new DBSpeicherFormat();
		object = dbLogik.getForId(id);
		if (object == null) {
			errors.add(ErrorsSpeicherFormateLogik.UnableToLoad);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public SpeicherFormatInterface getObject() {
		return object;
	}

	@Override
	public List<SpeicherFormatInterface> getAll() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Dieser Befehl wird nicht unterstützt!");
	}

	@Override
	public List<ErrorMessage> getErrors() {
		return errors;
	}

	public int getTitelId() {
		return titelId;
	}

	public void setTitelId(int titelId) {
		this.titelId = titelId;
	}

	@Override
	public void reset() {
		this.titelId = 0;
		this.errors = new ArrayList<ErrorMessage>();
		this.object = null;
	}
}
