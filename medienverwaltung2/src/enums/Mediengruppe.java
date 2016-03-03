package enums;

import interfaces.SelectAble;
import interfaces.URIPart;

public enum Mediengruppe implements SelectAble, URIPart {
	Musik	("Musik",	"musik"),
	Film	("Film",	"film"),
	Buch	("Buch",	"buch"),
	Hoerbuch("Hörbuch",	"hoerbuch"),
	Spiel	("Spiel",	"spiel"),
	Bild	("Bild",	"bild");

	private	String	bezeichnung;
	private	String	uriPart;
	private	int		id			=	ordinal();
	
	private Mediengruppe(String bezeichnung, String uriPart) {
		this.bezeichnung	=	bezeichnung;
		this.uriPart		=	uriPart;
	}

	@Override
	public String getBezeichnung() {
		return bezeichnung;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getURIPart() {
		return uriPart;
	}
	
	public static Mediengruppe getElementFromId(int id) {
		for (Mediengruppe element : values()) {
			if (element.getId() == id) {
				return element;
			}
		}
		return null;
	}
	
	public static Mediengruppe getElementFromUriPart(String uriPart) {
		for (Mediengruppe element : values()) {
			if (element.getURIPart().equals(uriPart)) {
				return element;
			}
		}
		return null;
	}
}
