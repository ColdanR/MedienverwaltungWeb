package data.medien.validator;

import java.util.ArrayList;
import java.util.List;

import data.medien.Medium;
import interfaces.Validator;

public abstract class MediumValidator<E extends Medium> implements Validator<E> {
	private	List<String>	errors	=	new ArrayList<>();
	
	public boolean validate(E object) {
		boolean	ret	=	true;
		if (object.getTitel() == null || object.getTitel().length() == 0) {
			ret = false;
			addError("Kein Titel gesetzt.");
		}
		if (object.getGenre() == null || object.getGenre().size() == 0) {
			ret = false;
			addError("Kein Genre ausgewählt!");
		}
		if (object.getErscheinungsdatum() == null) {
			ret = false;
			addError("Kein Erscheinungsdatum eingegeben.");
		}
		return ret;
	}
	
	protected void addError(String error) {
		errors.add(error);
	}
	
	public List<String> getErrors() {
		List<String> ret = new ArrayList<>();
		errors.stream().forEach(data -> {
			ret.add(data);
		});
		return ret;
	}
}
