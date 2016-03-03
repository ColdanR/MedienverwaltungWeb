package gui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gui.dto.ShowParameterDTO;

public class MedienController extends Controller {
	public MedienController() {
		super("/medium/(musik|film|buch|hoerbuch|spiel|bild)/(anlage|editieren|anzeigen|details).html");
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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