package enums;

import interfaces.SelectAble;

public enum SpeicherortArt implements SelectAble {
	Optisch("Optisch"),
	Schallplatte("Schallplatte"),
	Buch("Buch"),
	Kassette("Kassette"),
	Festplatte("Festplatte"),
	Dia("Dia");
	
	private	String	bezeichnung;
	private	int		id;
	
	private SpeicherortArt(String bezeichnung) {
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
	
	public static SpeicherortArt getElementFromId(int id) {
		for (SpeicherortArt element : SpeicherortArt.values()) {
			if (element.getId() == id) {
				return element;
			}
		}
		return null;
	}
}
