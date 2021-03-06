package gui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter(urlPatterns="/*", dispatcherTypes={DispatcherType.REQUEST, DispatcherType.ERROR})
public class Dispatcher implements Filter {
	private	static	ControllerFactory	FACTORY			=	ControllerFactory.getInstance();
	private	static	String				CONTROLLER_DIR	=	"gui/controller";
	private	static	Logger				LOGGER			=	LogManager.getLogger(Dispatcher.class);
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		LocalDateTime	start	=	LocalDateTime.now();
		arg0.setCharacterEncoding(StandardCharsets.UTF_8.name());
		arg1.setCharacterEncoding(StandardCharsets.UTF_8.name());
		try {
			HttpServletRequest	request		=	(HttpServletRequest) arg0;
			HttpServletResponse	response	=	(HttpServletResponse) arg1;
			Controller			handler		=	null;
			
			String	uri	=	request.getServletPath();
			if (request.getPathInfo() != null) {
				uri	+=	request.getPathInfo();
			}
			
			if (uri.startsWith("/css") || uri.startsWith("/js")) {
				LOGGER.debug("Direkte Verarbeitung der URI {}", uri);
				arg2.doFilter(arg0, arg1);
			} else {
				try {
					handler = FACTORY.getController(request);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
						| IllegalStateException e) {
					LOGGER.error("Fehler bei Zuholung des Controllers");
					LOGGER.catching(e);
				}
				if (handler != null) {
					LOGGER.debug("Rufe Controller {} auf", handler.getClass().getCanonicalName());
					handler.execute(request, response);
				} else {
					LOGGER.debug("Leite Request weiter.");
					arg2.doFilter(arg0, arg1);
				}
			}
		} catch (ClassCastException e) {
			LOGGER.warn("Fehlerhafter Cast f�r HttpServletRequest oder HttpServletResponse.");
			arg2.doFilter(arg0, arg1);
		}
		LOGGER.info("Dauer des Requests auf Server: {} ms", ChronoUnit.MILLIS.between(start, LocalDateTime.now()));
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		String	baseDir			=	config.getServletContext().getRealPath("/");
		Path	controllerPath	=	Paths.get(baseDir, "WEB-INF/classes", CONTROLLER_DIR);
		try {
			Files.list(controllerPath).parallel().forEach(line -> {
				String className = line.getFileName().toString();
				if (className.endsWith(".class") && !className.contains("$")) {
					className = CONTROLLER_DIR.replaceAll("/", ".") + "." + className.substring(0, className.indexOf(".class"));
					Controller handler = null;
					try {
						handler = (Controller) Class.forName(className).newInstance();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
						LOGGER.error("Erstellung des Controllers {} fehlgeschlagen! Teilweise sind Komponenten daher nicht verf�gbar!", line);
						LOGGER.catching(e);
					}
					if (handler != null) {
						FACTORY.register(handler);
					}
				}
			});
		} catch (IOException | SecurityException e) {
			LOGGER.error("Zugriff auf Controller Verzeichnis nicht m�glich. Bitte Zugriffsrechte und Namenskonventionen pr�fen!");
			LOGGER.catching(e);
		}
	}
}
