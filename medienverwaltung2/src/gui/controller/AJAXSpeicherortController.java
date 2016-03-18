package gui.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				if (art != null) {
					switch(art) {
					case Buch:
						break;
					case Dia:
						break;
					case Festplatte:
						break;
					case Kassette:
						break;
					case Optisch:
						break;
					case Schallplatte:
						break;
					default:
						break;
					}
					switch(art) {
					case Buch:
						break;
					case Dia:
						break;
					case Festplatte:
						break;
					case Kassette:
						break;
					case Optisch:
						break;
					case Schallplatte:
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
