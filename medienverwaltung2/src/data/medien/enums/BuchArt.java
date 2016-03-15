package data.medien.enums;

import interfaces.SelectAble;

public enum BuchArt implements SelectAble {
	
	Belletristik	(1,"Belletristik"),
	Literatur		(2,"Literatur"),
	Comic			(3,"Comic"),
	Manga			(4,"Manga"),
	Fachbuch		(5,"Fachbuch");
	
	private	int		id;
	private	String	bezeichnung;
	
	private BuchArt(int id, String bezeichnung) {
		this.id				=	id;
		this.bezeichnung	=	bezeichnung;
	}
	
	@Override
	public String getBezeichnung() {
		return bezeichnung;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public static BuchArt getFromId(int id) {
		for (BuchArt art : BuchArt.values()) {
			if (art.getId() == id) {
				return art;
			}
		}
		return null;
	}
}
