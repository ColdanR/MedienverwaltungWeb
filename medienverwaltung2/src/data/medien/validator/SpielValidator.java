package data.medien.validator;

import data.medien.Spiel;

public class SpielValidator extends MediumValidator<Spiel> {
	@Override
	public boolean validate(Spiel object) {
		boolean ret = super.validate(object);
		if (object.getBetriebssystem() == null || object.getBetriebssystem().length() == 0) {
			addError("Kein Betriebssystem eingegeben.");
			ret = false;
		}
		if (object.getSprache() == null || object.getSprache().length() == 0) {
			addError("Keine Sprache eingegeben");
			ret = false;
		}
		return ret;
	}
}
