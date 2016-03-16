package enums;

import interfaces.SelectAble;
import interfaces.URIPart;

public enum Mediengruppe implements SelectAble, URIPart {
	Musik	("Musik",	"musik",	0),
	Film	("Film",	"film",		1),
	Buch	("Buch",	"buch",		2),
	Hoerbuch("Hörbuch",	"hoerbuch",	3),
	Spiel	("Spiel",	"spiel",	4),
	Bild	("Bild",	"bild",		5);

	private	String	bezeichnung;
	private	String	uriPart;
	private	int		id;
	
	private Mediengruppe(String bezeichnung, String uriPart, int id) {
		this.bezeichnung	=	bezeichnung;
		this.uriPart		=	uriPart;
		this.id				=	id;
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
