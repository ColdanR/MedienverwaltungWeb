package gui.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gui.Controller;
import gui.dto.FehlerDTO;

public class ErrorController extends Controller {
	private static Pattern URI_PATTERN = Pattern.compile("/(404|500).html");
	
	public ErrorController() throws PatternSyntaxException {
		super(URI_PATTERN.pattern());
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FehlerDTO	dto		=	new FehlerDTO();
		String		uri		=	request.getServletPath();
		if (request.getPathInfo() != null) {
			uri += request.getPathInfo();
		}
		Matcher		matcher	=	URI_PATTERN.matcher(uri);
		matcher.matches();
		String fehlerSeite = matcher.group(1);
		Throwable e = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		dto.addError(exceptionAsString);
		forward(request, response, dto, fehlerSeite + ".jsp");
	}
}
