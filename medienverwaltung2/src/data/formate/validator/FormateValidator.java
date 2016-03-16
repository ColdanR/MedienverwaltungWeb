package data.formate.validator;

import java.util.ArrayList;
import java.util.List;

import data.formate.Formate;
import interfaces.Validator;

public abstract class FormateValidator<E extends Formate> implements Validator<E> {
	private	List<String>	errors	=	new ArrayList<>();
	
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
