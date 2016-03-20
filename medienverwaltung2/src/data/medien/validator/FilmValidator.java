package data.medien.validator;

import data.medien.Film;

public class FilmValidator extends MediumValidator<Film> {
	@Override
	public boolean validate(Film object) {
		boolean ret = super.validate(object);
		if (object.getArt() == null) {
			addError("Keine Filmart ausgewählt.");
			ret = false;
		}
		if (object.getSprache() == null || object.getSprache().length() == 0) {
			addError("Keine Sprache eingegeben");
			ret = false;
		}
		return ret;
	}
}
