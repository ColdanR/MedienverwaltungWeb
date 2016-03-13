package gui.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import enums.Action;
import enums.Mediengruppe;
import gui.Controller;
import gui.dto.ShowParameterDTO;
import logic.MediumLogicFactory;
import logic.medien.MediumLogik;

public class MedienController extends Controller {
	private static Pattern URI_PATTERN = Pattern.compile("/medium/(" + 
			Arrays.asList(Mediengruppe.values()).stream().map(Mediengruppe::getURIPart).collect(Collectors.joining("|")) +
			")/(" +
			Arrays.asList(Action.values()).stream().map(Action::getURIPart).collect(Collectors.joining("|")) +
			").html");
	private	static	Logger	LOGGER	=	LogManager.getLogger(MedienController.class);
	
	public MedienController() {
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
		String			mediumPart	=	matcher.group(1);
		String			actionPart	=	matcher.group(2);
		Mediengruppe	medium		=	Mediengruppe.getElementFromUriPart(mediumPart);
		Action			action		=	Action.getFromURIPart(actionPart);
		LOGGER.debug("Medientype: {} - Action: {}", medium.getBezeichnung(), action);
		switch (action) {
		case Bearbeiten:
			bearbeiten(request, response, medium);
			break;
		case Details:
			details(request, response, medium);
			break;
		case List:
			list(request, response, medium);
			break;
		case Neuanlage:
			create(request, response, medium);
			break;
		default:
			break;
		}
		
		
		ShowParameterDTO	dto				=	new ShowParameterDTO(request);
		forward(request, response, dto, "/showRequest.jsp");
	}

	private void create(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) {
		// TODO Auto-generated method stub
		// Leeres DTO erzeugen oder Parameter übernehmen DTO
		// Schreiben, usw. kontrollieren
	}

	private void list(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) {
		// Medium löschen?
		if (request.getParameter("send") != null) {
			String idString = request.getParameter("id");
			if (idString == null || idString.trim().length() == 0) {
				
			}
		}
		MediumLogik<?> logic = MediumLogicFactory.create(medium);
		List<?> list = logic.getAll();
		if (list == null) {
			// Fehler beim Laden
		} else {
			// Liste zu DTO
		}
		// Liste anzeigen, Medium löschen
	}

	private void details(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) {
		// TODO Auto-generated method stub
		// Laden und ins DTO schreiben
	}

	private void bearbeiten(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) {
		// TODO Auto-generated method stub
		
	}
}