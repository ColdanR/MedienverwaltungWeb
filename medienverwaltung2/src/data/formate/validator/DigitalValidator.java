package data.formate.validator;

import data.formate.Digital;

public class DigitalValidator extends FormateValidator<Digital> {
	@Override
	public boolean validate(Digital object) {
		boolean ret = true;
		if (object.getDateiformat() == null || object.getDateiformat().trim().length() == 0) {
			addError("Dateiformat darf nicht leer sein.");
			ret = false;
		}
		if (object.getQualitaet() == null || object.getQualitaet().trim().length() == 0) {
			addError("Qualität darf nicht leer sein.");
			ret = false;
		}
		return ret;
	}
}