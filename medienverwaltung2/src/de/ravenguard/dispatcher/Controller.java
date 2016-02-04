package de.ravenguard.dispatcher;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller {
	private	Pattern	pattern;
	
	public Controller() {
	}
	
	protected Controller (String pattern) throws PatternSyntaxException {
		this.pattern = Pattern.compile(pattern);
	}
	
	public final boolean canHandle(String uri) {
		return pattern.matcher(uri).matches();
	}
	
	public final Pattern getPattern() {
		return pattern;
	}
	
	public	abstract	void	execute(HttpServletRequest request, HttpServletResponse response);
}