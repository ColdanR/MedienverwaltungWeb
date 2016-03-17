package gui.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.speicherorte.enums.BuchArt;
import data.speicherorte.enums.KassettenArt;
import data.speicherorte.enums.OptischArt;
import data.speicherorte.enums.SchallplatteArt;
import enums.SpeicherortArt;
import gui.Controller;
import interfaces.SelectAble;

public class SpeicherOrtFormAJAX extends Controller {
	public class ZusatzDTO {
		private	List<SelectAble>	artOptions;
		private	SelectAble			artSelected;
		private	String				zustand;
		
		public ZusatzDTO(List<SelectAble> artOptions, SelectAble artSelected, String zustand) {
			super();
			this.artOptions = artOptions;
			this.artSelected = artSelected;
			this.zustand = zustand;
		}

		public List<SelectAble> getArtOptions() {
			return artOptions;
		}

		public SelectAble getArtSelected() {
			return artSelected;
		}

		public String getZustand() {
			return zustand;
		}
	}
	public SpeicherOrtFormAJAX() throws PatternSyntaxException {
		super("/ajax/speicherort");
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("art") != null && request.getParameter("art").trim().length() > 0) {
			try {
				int speicherOrtArt = Integer.parseInt(request.getParameter("art"));
				SpeicherortArt	art	=	SpeicherortArt.getElementFromId(speicherOrtArt);
				if (art != null) {
					List<SelectAble> arten = null;
					switch (art) {
					case Buch:
						arten = Arrays.asList(BuchArt.values());
						break;
					case Kassette:
						arten = Arrays.asList(KassettenArt.values());
						break;
					case Optisch:
						arten = Arrays.asList(OptischArt.values());
						break;
					case Schallplatte:
						arten = Arrays.asList(SchallplatteArt.values());
						break;
					default:
						break;
					}
					request.setAttribute("context", new ZusatzDTO(arten, null, ""));
					switch (art) {
					case Buch:
					case Kassette:
					case Optisch:
					case Schallplatte:
						request.getRequestDispatcher("/WEB-INF/classes/gui/jspFiles/fieldSpeicherortartArt.jsp").include(request, response);
						request.getRequestDispatcher("/WEB-INF/classes/gui/jspFiles/fieldSpeicherortartZustand.jsp").include(request, response);
						break;
					case Dia:
						request.getRequestDispatcher("/WEB-INF/classes/gui/jspFiles/fieldSpeicherortartZustand.jsp").include(request, response);
						break;
					case Festplatte:
					default:
						break;
					}
				}
			} catch (NumberFormatException e) {}
		}
		response.flushBuffer();
	}

}
