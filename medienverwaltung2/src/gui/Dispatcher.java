package gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gui.controller.Controller;

public class Dispatcher implements Filter {
	private	static	String	BASE_DIR	=	null;
	private	static	String	CONFIG_FILE	=	"gui/urlhandlers.conf";
	private	static	Logger	LOGGER		=	LogManager.getLogger(Dispatcher.class);
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		try {
			HttpServletRequest	request		=	(HttpServletRequest) arg0;
			HttpServletResponse	response	=	(HttpServletResponse) arg1;
			ControllerFactory	factory		=	ControllerFactory.getInstance();
			Controller			handler		=	null;
			try {
				handler = factory.getController(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (handler != null) {
				handler.execute(request, response);
			} else {
				arg2.doFilter(arg0, arg1);
			}
		} catch (ClassCastException e) {
			LOGGER.error(e);
			arg2.doFilter(arg0, arg1);
		}
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		BASE_DIR = config.getServletContext().getRealPath("/");
		
		Path configFile = Paths.get(BASE_DIR, "WEB-INF/classes", CONFIG_FILE);
		try {
			Files.readAllLines(configFile).stream().forEach(data -> {
				Controller handler = null;
				try {
					handler = (Controller) Class.forName(data).newInstance();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					LOGGER.error(e);
				}
				if (handler != null) {
					ControllerFactory.getInstance().register(handler);
				}
			});
		} catch (IOException | SecurityException e) {
			LOGGER.error(e);
		}
	}
	
	public static String getBaseDir() {
		return BASE_DIR;
	}
}
