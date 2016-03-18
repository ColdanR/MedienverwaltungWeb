package data.medien.validator;

import data.medien.Buch;

public class BuchValidator extends MediumValidator<Buch> {
	@Override
	public boolean validate(Buch object) {
		boolean ret = super.validate(object);
		if (object.getArt() == null) {
			addError("Keine Buchart ausgewählt.");
			ret = false;
		}
		if (object.getSprache() == null || object.getSprache().length() == 0) {
			addError("Keine Sprache eingegeben");
			ret = false;
		}
		return ret;
	}
}
