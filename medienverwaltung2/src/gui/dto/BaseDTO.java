package gui.dto;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDTO {
	private	String			titel;
	private	List<String>	jsFiles		=	new ArrayList<>();
	private	List<String>	cssFiles	=	new ArrayList<>();
	private	List<String>	errors		=	new ArrayList<>();
	
	protected BaseDTO(String titel) {
		this.titel = titel;
	}
	
	public String getTitel() {
		return titel;
	}
	public List<String> getJsFiles() {
		return jsFiles;
	}
	public void addJsFile(String jsFile) {
		this.jsFiles.add(jsFile);
	}
	public List<String> getCssFiles() {
		return cssFiles;
	}
	public void addCssFile(String cssFile) {
		this.cssFiles.add(cssFile);
	}
	public List<String> getErrors() {
		return errors;
	}
	public void addError(String error) {
		this.errors.add(error);
	}
}
