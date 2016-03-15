package data.medien.enums;

import interfaces.SelectAble;

public enum FilmArt implements SelectAble {
	;
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
}
