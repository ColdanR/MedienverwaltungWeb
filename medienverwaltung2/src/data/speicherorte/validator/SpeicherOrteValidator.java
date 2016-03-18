package data.speicherorte.validator;

import java.util.ArrayList;
import java.util.List;

import data.speicherorte.Buch;
import data.speicherorte.Kassette;
import data.speicherorte.Optisch;
import data.speicherorte.Schallplatte;
import data.speicherorte.Speicherort;
import interfaces.Validator;

public class SpeicherOrteValidator implements Validator<Speicherort> {
	private	List<String>	errors	=	new ArrayList<>();
	
	@Override
	public boolean validate(Speicherort object) {
		boolean ret = true;
		if (object.getLagerOrt() == null || object.getLagerOrt().trim().length() == 0) {
			ret = false;
			errors.add("Die Bezeichnung darf nicht leer sein.");
		}
		switch(object.getType()) {
		case Buch:
			Buch buch = (Buch) object;
			if (buch.getArt() == null) {
				ret = false;
				errors.add("Keine Art ausgew√§hlt!");
			}
			break;
		case Kassette:
			Kassette kassette = (Kassette) object;
			if (kassette.getArt() == null) {
				ret = false;
				errors.add("Keine Art ausgew‰hlt!");
			}
			break;
		case Optisch:
			Optisch optisch = (Optisch) object;
			if (optisch.getArt() == null) {
				ret = false;
				errors.add("Keine Art ausgew‰hlt!");
			}
			break;
		case Schallplatte:
			Schallplatte schallplatte = (Schallplatte) object;
			if (schallplatte.getArt() == null) {
				ret = false;
				errors.add("Keine Art ausgew‰hlt!");
			}
			break;
		default:
			break;
		}
		return ret;
	}
	
	public List<String> getErrors() {
		return errors;
	}
}
