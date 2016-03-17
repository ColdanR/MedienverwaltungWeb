package gui.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.medien.Genre;
import enums.Action;
import gui.Controller;
import gui.dto.FehlerDTO;
import gui.dto.genre.GenreEditDTO;
import gui.dto.genre.GenreListDTO;
import logic.genre.GenreLogik;

public class GenreController extends Controller {
	private static Pattern URI_PATTERN = Pattern.compile("/genre/(" +
			Arrays.asList(Action.values()).stream()
			.filter(action -> {return action != Action.Details;})
			.map(Action::getURIPart)
			.collect(Collectors.joining("|")) +
			").html");

	public GenreController() throws PatternSyntaxException {
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
		case List:
			list(request, response);
			break;
		default:
			break;
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GenreLogik	logik	=	new GenreLogik();
		List<Genre>	list	=	logik.getAll();
		if (list == null) {
			FehlerDTO dto = new FehlerDTO();
			for (String error : logik.getErrors()) {
				dto.addError(error);
			}
			forward(request, response, dto, "404.jsp");
		} else {
			GenreListDTO	dto	=	new GenreListDTO(list);
			forward(request, response,dto, "genreAnzeige.jsp");
		}
	}

	private void neuanlage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("send") != null) {
			GenreLogik	logik	=	new GenreLogik();
			if (request.getParameter("bez") == null || request.getParameter("bez").trim().length() == 0) {
				GenreEditDTO	dto	=	new GenreEditDTO("Genre anlegen", 0, "");
				dto.addError("Bezeichnung darf nicht leer sein.");
				forward(request, response,dto, "genreEingabe.jsp");
			} else {
				Genre neuanlage = logik.create();
				neuanlage.setBezeichnung(request.getParameter("bez").trim());
				if (!logik.write()) {
					GenreEditDTO	dto	=	new GenreEditDTO("Genre anlegen", 0, request.getParameter("bez").trim());
					logik.getErrors().stream().forEach(error -> {
						dto.addError(error);
					});
					forward(request, response,dto, "genreEingabe.jsp");
				} else {
					redirect(request, response, "genre/" + Action.List.getURIPart() + ".html");
				}
			}
		} else {
			GenreEditDTO	dto	=	new GenreEditDTO("Genre anlegen", 0, "");
			forward(request, response,dto, "genreEingabe.jsp");
		}
	}

	private void loeschen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				GenreLogik	logik	=	new GenreLogik();
				if (!logik.load(id)) {
					FehlerDTO dto = new FehlerDTO();
					for (String error : logik.getErrors()) {
						dto.addError(error);
					}
					forward(request, response, dto, "404.jsp");
				} else {
					if (!logik.delete()) {
						FehlerDTO dto = new FehlerDTO();
						for (String error : logik.getErrors()) {
							dto.addError(error);
						}
						forward(request, response, dto, "404.jsp");
					} else {
						redirect(request, response, "genre/" + Action.List.getURIPart() + ".html");
					}
				}
			} catch (NumberFormatException e) {
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID");
				forward(request, response, dto, "404.jsp");
			}
		} else {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Fehlende ID");
			forward(request, response, dto, "404.jsp");
		}
	}

	private void bearbeiten(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				GenreLogik	logik	=	new GenreLogik();
				if (!logik.load(id)) {
					FehlerDTO dto = new FehlerDTO();
					for (String error : logik.getErrors()) {
						dto.addError(error);
					}
					forward(request, response, dto, "404.jsp");
				} else {
					if (request.getParameter("send") != null) {
						if (request.getParameter("bez") == null || request.getParameter("bez").trim().length() == 0) {
							GenreEditDTO	dto	=	new GenreEditDTO("Genre editieren", logik.getObject().getId(), "");
							dto.addError("Bezeichnung darf nicht leer sein.");
							forward(request, response,dto, "genreEingabe.jsp");
						} else {
							logik.getObject().setBezeichnung(request.getParameter("bez").trim());
							if (!logik.write()) {
								GenreEditDTO	dto	=	new GenreEditDTO("Genre editieren", logik.getObject().getId(), logik.getObject().getBezeichnung());
								for (String error : logik.getErrors()) {
									dto.addError(error);
								}
								forward(request, response,dto, "genreEingabe.jsp");
							} else {
								redirect(request, response, "genre/" + Action.List.getURIPart() + ".html");
							}
						}
					} else {
						GenreEditDTO	dto	=	new GenreEditDTO("Genre editieren", logik.getObject().getId(), logik.getObject().getBezeichnung());
						forward(request, response,dto, "genreEingabe.jsp");
					}
				}
			} catch (NumberFormatException e) {
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID");
				forward(request, response, dto, "404.jsp");
			}
		} else {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Fehlende ID");
			forward(request, response, dto, "404.jsp");
		}
	}
}
