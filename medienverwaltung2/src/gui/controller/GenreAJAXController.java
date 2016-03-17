package gui.controller;

import java.io.IOException;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.medien.Genre;
import gui.Controller;
import logic.genre.GenreLogik;

public class GenreAJAXController extends Controller {

	public GenreAJAXController() throws PatternSyntaxException {
		super("/ajax/addGenre");
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		if (request.getParameter("bez") == null || request.getParameter("bez").trim().length() == 0) {
			String ret = "{\"status\":1,\"error\":[\"Keine Bezeichnung uebermittelt.\"]}";
			response.getOutputStream().print(ret);
			response.flushBuffer();
		} else {
			GenreLogik logik = new GenreLogik();
			Genre objekt = logik.create();
			objekt.setBezeichnung(request.getParameter("bez").trim());
			if (!logik.write()) {
				String ret = "{\"status\":1,\"error\":[\"" + logik.getErrors().stream().collect(Collectors.joining("\",\"")) + "\"]}";
				response.getOutputStream().print(ret);
				response.flushBuffer();
			} else {
				String ret = "{\"status\":0,\"id\":" + objekt.getId() + "}";
				response.getOutputStream().print(ret);
				response.flushBuffer();
			}
		}
	}
}