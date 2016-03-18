package data.medien.validator;

import data.medien.Hoerbuch;

public class HoerbuchValidator extends MediumValidator<Hoerbuch> {
	@Override
	public boolean validate(Hoerbuch object) {
		boolean ret = super.validate(object);
		if (object.getArt() == null) {
			addError("Keine Hörbuchart ausgewählt.");
			ret = false;
		}
		if (object.getSprache() == null || object.getSprache().length() == 0) {
			addError("Keine Sprache eingegeben");
			ret = false;
		}
		return ret;
	}
}
