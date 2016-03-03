package gui;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gui.controller.Controller;

public class ControllerFactory {

	private static	final	Map<Pattern, String>	MAP			=	Collections.synchronizedMap(new HashMap<Pattern, String>());
	private	static			ControllerFactory		INSTANCE	=	new	ControllerFactory();
	private	static	final	Logger					LOGGER		=	LogManager.getLogger(ControllerFactory.class);
	
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
		LOGGER.debug("Verarbeite Request URI: {}", uri);
		final	String			uriFinal		=	uri;
				List<Pattern>	patternList	=	MAP.keySet()
						.parallelStream()
						.filter(data -> {return data.matcher(uriFinal).matches();})
						.collect(Collectors.toList());
		if (patternList.size() == 0) {
			LOGGER.debug("Kein Controller gefunden.");
			return null;
		} else if (patternList.size() > 1) {
			LOGGER.debug("Mehrere Controller gefunden! Controller Klassen: {}", StringUtils.join(patternList.parallelStream().map(data -> {
				return MAP.get(data);
			}).filter(data -> {
				return data != null;
			}).collect(Collectors.toList()), ", "));
			throw new IllegalStateException("Multiple Controller for " + uri);
		} else {
			Pattern found = patternList.get(0);
			LOGGER.debug("Controller gefunden: {}", MAP.get(found));
			return (Controller) Class.forName(MAP.get(found)).newInstance();
		}
	}
}
