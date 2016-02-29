package gui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import gui.controller.Controller;

public class ControllerFactory {

	private static	final	Map<Pattern, String>	MAP			=	Collections.synchronizedMap(new HashMap<Pattern, String>());
	private	static			ControllerFactory		INSTANCE	=	new	ControllerFactory();
	
	private ControllerFactory() {
		super();
	}
	
	public static ControllerFactory getInstance() {
		return INSTANCE;
	}
	
	public void register(Controller handler) {
		MAP.put(handler.getPattern(), handler.getClass().getCanonicalName());
	}

	public Controller getController(HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalStateException {
		String uri = request.getServletPath();
		if (request.getPathInfo() != null) {
			uri += request.getPathInfo();
		}
		final String uriFinal = uri;
		Stream<Pattern> patternStream = MAP.keySet().parallelStream().filter(data -> {
			return data.matcher(uriFinal).matches();
		});
		if (patternStream.count() == 0) {
			return null;
		} else if (patternStream.count() > 1) {
			throw new IllegalStateException("Multiple Controller for " + uri);
		} else {
			Pattern found = patternStream.iterator().next();
			return (Controller) Class.forName(MAP.get(found)).newInstance();
		}
	}
}
