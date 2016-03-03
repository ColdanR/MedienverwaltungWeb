package enums;

import interfaces.SelectAble;

public enum Mediengruppe implements SelectAble {
	Musik("Musik"),
	Film("Film"),
	Buch("Buch"),
	Hoerbuch("Hörbuch"),
	Spiel("Spiel"),
	Bild("Bild");

	private	String	bezeichnung;
	private	int		id;
	
	private Mediengruppe(String bezeichnung) {
		this.bezeichnung	=	bezeichnung;
		this.id				=	this.ordinal();
	}

	@Override
	public String getBezeichnung() {
		return bezeichnung;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public static Mediengruppe getElementFromId(int id) {
		for (Mediengruppe element : values()) {
			if (element.getId() == id) {
				return element;
			}
		}
		return null;
	}
}
