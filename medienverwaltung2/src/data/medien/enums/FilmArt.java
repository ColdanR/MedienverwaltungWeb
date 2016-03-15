package data.medien.enums;

import interfaces.SelectAble;

public enum FilmArt implements SelectAble {
	Spielfilm	(1,"Spielfilm"),
	Doku		(2,"Dokumentarfilm"),
	Serie		(3,"Serie"),
	Trilogie	(4,"Trilogie");
	
	private	int		id;
	private	String	bezeichnung;
	
	private FilmArt(int id, String bezeichnung) {
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
	
	public static FilmArt getFromId(int id) {
		for (FilmArt art : FilmArt.values()) {
			if (art.getId() == id) {
				return art;
			}
		}
		return null;
	}
}
