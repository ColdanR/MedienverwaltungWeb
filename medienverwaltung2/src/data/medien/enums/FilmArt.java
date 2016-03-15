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
	
	public static FilmArt getFromId(int id) {
		for (FilmArt art : FilmArt.values()) {
			if (art.getId() == id) {
				return art;
			}
		}
		return null;
	}
}
