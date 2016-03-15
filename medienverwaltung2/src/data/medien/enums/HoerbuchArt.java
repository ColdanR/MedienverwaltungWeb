package data.medien.enums;

import interfaces.SelectAble;

public enum HoerbuchArt implements SelectAble {
	;
	private	int		id;
	private	String	bezeichnung;
	
	private HoerbuchArt(int id, String bezeichnung) {
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
	
	public static HoerbuchArt getFromId(int id) {
		for (HoerbuchArt art : HoerbuchArt.values()) {
			if (art.getId() == id) {
				return art;
			}
		}
		return null;
	}
}
