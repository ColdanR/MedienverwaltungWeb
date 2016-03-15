package data.medien.enums;

import interfaces.SelectAble;

public enum HoerbuchArt implements SelectAble {
	Hoerspiel		(1,"H�rspiel"),
	Lesung			(2,"Lesung"),
	szenischeLesung	(3,"szenische Lesung"),
	SachHoerbuch	(4,"Sach-H�rbuch"),
	LehrHoerbuch	(5,"Lehr-H�rbuch"),
	Audioguide		(6,"Audioguide"),
	Podcast			(7,"Podcast");
	
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
