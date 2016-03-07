package gui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
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
			break;
		case Details:
			break;
		case List:
			break;
		case Neuanlage:
			break;
		default:
			break;
		}
		
		
		ShowParameterDTO	dto				=	new ShowParameterDTO();
		Enumeration<String> headerNames		=	request.getHeaderNames();
		Enumeration<String> attributeNames	=	request.getAttributeNames();
		Enumeration<String> parameterNames	=	request.getParameterNames();
		while (headerNames.hasMoreElements()) {
			String				header	=	headerNames.nextElement();
			Enumeration<String>	value	=	request.getHeaders(header);
			List<String>		list	=	new ArrayList<>();
			while (value.hasMoreElements()) {
				list.add(value.nextElement());
			}
			dto.addHeader(header, list.toArray(new String[0]));
		}
		while (attributeNames.hasMoreElements()) {
			String	header		=	attributeNames.nextElement();
			Object	attribute	=	request.getAttribute(header);
			dto.addAttribute(header, attribute);
		}
		while (parameterNames.hasMoreElements()) {
			String		header	=	parameterNames.nextElement();
			String[]	values	=	request.getParameterValues(header);
			dto.addParameter(header, values);
		}
		forward(request, response, dto, "/showRequest.jsp");
	}
}