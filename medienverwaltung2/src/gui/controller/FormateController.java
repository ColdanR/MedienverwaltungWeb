package gui.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import enums.Action;
import enums.Mediengruppe;
import gui.Controller;
import gui.dto.FehlerDTO;
import logic.formate.FormateLogik;

public class FormateController extends Controller {
	private static Pattern URI_PATTERN = Pattern.compile("/formate/(" +
			Arrays.asList(Action.values()).stream()
			.filter(action -> {return action != Action.Details && action != Action.List;})
			.map(Action::getURIPart)
			.collect(Collectors.joining("|")) +
			").html");
	private	static	Logger	LOGGER	=	LogManager.getLogger(FormateController.class);
	
	public FormateController() throws PatternSyntaxException {
		super(URI_PATTERN.pattern());
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String			uri		=	request.getServletPath();
		if (request.getPathInfo() != null) {
			uri += request.getPathInfo();
		}
		Matcher			matcher		=	URI_PATTERN.matcher(uri);
		matcher.matches();
		String			actionPart	=	matcher.group(1);
		Action			action		=	Action.getFromURIPart(actionPart);
		LOGGER.debug("Action: {}", action);
		switch (action) {
		case Bearbeiten:
			bearbeiten(request, response);
			break;
		case Loeschen:
			loeschen(request, response);
			break;
		case Neuanlage:
			neuanlage(request, response);
			break;
		default:
			break;
		}
	}

	private void neuanlage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("parentId") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("mediumTypeId") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int				mediumId	=	Integer.parseInt(request.getParameter("parentId"));
				Mediengruppe	type		=	Mediengruppe.getElementFromId(Integer.parseInt(request.getParameter("mediumTypeId")));
				FormateLogik	logik		=	new FormateLogik(mediumId);
				if (logik.load(id)) {
					// TODO
					if (logik.delete()) {
						redirect(request, response, "medium/" + type.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + mediumId);
					} else {
						FehlerDTO dto = new FehlerDTO();
						logik.getErrors().stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
					}
				} else {
					FehlerDTO dto = new FehlerDTO();
					logik.getErrors().stream().forEach(error -> {
						dto.addError(error);
					});
					forward(request, response, dto, "404.jsp");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID beim Aufruf zum Löschen des Formates gefunden.");
				forward(request, response, dto, "404.jsp");
			}
		}
	}

	private void bearbeiten(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ID beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("parentId") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("mediumTypeId") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int 			id			=	Integer.parseInt(request.getParameter("id"));
				int				mediumId	=	Integer.parseInt(request.getParameter("parentId"));
				Mediengruppe	type		=	Mediengruppe.getElementFromId(Integer.parseInt(request.getParameter("mediumTypeId")));
				FormateLogik	logik		=	new FormateLogik(mediumId);
				if (logik.load(id)) {
					// TODO
					if (logik.delete()) {
						redirect(request, response, "medium/" + type.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + mediumId);
					} else {
						FehlerDTO dto = new FehlerDTO();
						logik.getErrors().stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
					}
				} else {
					FehlerDTO dto = new FehlerDTO();
					logik.getErrors().stream().forEach(error -> {
						dto.addError(error);
					});
					forward(request, response, dto, "404.jsp");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID beim Aufruf zum Löschen des Formates gefunden.");
				forward(request, response, dto, "404.jsp");
			}
		}
	}

	private void loeschen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ID beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("parentId") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("mediumTypeId") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int 			id			=	Integer.parseInt(request.getParameter("id"));
				int				mediumId	=	Integer.parseInt(request.getParameter("parentId"));
				Mediengruppe	type		=	Mediengruppe.getElementFromId(Integer.parseInt(request.getParameter("mediumTypeId")));
				FormateLogik	logik		=	new FormateLogik(mediumId);
				if (logik.load(id)) {
					if (logik.delete()) {
						redirect(request, response, "medium/" + type.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + mediumId);
					} else {
						FehlerDTO dto = new FehlerDTO();
						logik.getErrors().stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
					}
				} else {
					FehlerDTO dto = new FehlerDTO();
					logik.getErrors().stream().forEach(error -> {
						dto.addError(error);
					});
					forward(request, response, dto, "404.jsp");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID beim Aufruf zum Löschen des Formates gefunden.");
				forward(request, response, dto, "404.jsp");
			}
		}
	}
}
