package enums;

import interfaces.SelectAble;

public enum SpeicherortArt implements SelectAble {
	Optisch		("Optisch",		0),
	Schallplatte("Schallplatte",1),
	Buch		("Buch",		2),
	Kassette	("Kassette",	3),
	Festplatte	("Festplatte",	4),
	Dia			("Dia",			5);
	
	private	String	bezeichnung;
	private	int		id;
	
	private SpeicherortArt(String bezeichnung, int id) {
		this.bezeichnung	=	bezeichnung;
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
	
	public static SpeicherortArt getElementFromId(int id) {
		for (SpeicherortArt element : SpeicherortArt.values()) {
			if (element.getId() == id) {
				return element;
			}
		}
		return null;
	}
}
