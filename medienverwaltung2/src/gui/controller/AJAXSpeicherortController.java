package gui.controller;

import java.io.IOException;
import java.util.ArrayList;
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

public class AJAXSpeicherortController extends Controller {
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

	public AJAXSpeicherortController() throws PatternSyntaxException {
		super("/ajax/speicherort");
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("art") != null && request.getParameter("art").trim().length() > 0) {
			try {
				SpeicherortArt art = SpeicherortArt.getElementFromId(Integer.parseInt(request.getParameter("art").trim()));
				List<SelectAble>	options		=	new ArrayList<>();
				SelectAble			selected	=	null;
				String				zustand		=	"";
				if (art != null) {
					switch(art) {
					case Kassette:
						options = Arrays.asList(KassettenArt.values());
						break;
					case Schallplatte:
						options = Arrays.asList(SchallplatteArt.values());
						break;
					case Buch:
						options = Arrays.asList(BuchArt.values());
						break;
					case Optisch:
						options = Arrays.asList(OptischArt.values());
						break;
					default:
						break;
					}
					request.setAttribute("context", new ZusatzDTO(options, selected, zustand));
					switch(art) {
					case Kassette:
					case Schallplatte:
					case Buch:
					case Optisch:
						request.getRequestDispatcher("/WEB-INF/classes/gui/jspFiles/fieldSpeicherortartArt.jsp").include(request, response);
						request.getRequestDispatcher("/WEB-INF/classes/gui/jspFiles/fieldSpeicherortartZustand.jsp").include(request, response);
						break;
					case Dia:
						request.getRequestDispatcher("/WEB-INF/classes/gui/jspFiles/fieldSpeicherortartZustand.jsp").include(request, response);
						break;
					default:
						break;
					}
				} else {
					response.getWriter().write("Keine legale Art übermittelt.");
				}
			} catch (NumberFormatException e) {
				response.getWriter().write("Keine legale Art übermittelt.");
			}
		} else {
			response.getWriter().write("Keine legale Art übermittelt.");
		}
		response.flushBuffer();
	}

}
