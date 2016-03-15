package data.speicherorte.enums;

import interfaces.SelectAble;

public enum BuchArt implements SelectAble {
	HardCover	("Hardcover",   1),
	SoftCover 	("Softcover",   2),
	Taschenbuch	("Taschenbuch",	3),
	Magazin 	("Magazin",		4),
	ebook  		("E-Book",		5);
	
	private int id;
	private String bezeichnung;
	
	private BuchArt(String bezeichnung, int id){
		this.bezeichnung = bezeichnung;
		this.id = id;
	}

	@Override
	public String getBezeichnung() {
		return bezeichnung;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public static BuchArt getElementFromId(int id) {
		for (BuchArt element : values()) {
			if (element.getId() == id) {
				return element;
			}
		}
		return null;
	}
}
