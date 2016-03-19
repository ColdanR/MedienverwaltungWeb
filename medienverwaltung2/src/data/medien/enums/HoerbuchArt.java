package data.medien.enums;

import interfaces.SelectAble;

public enum HoerbuchArt implements SelectAble {
	Hoerspiel		(1,"Hörspiel"),
	Lesung			(2,"Lesung"),
	szenischeLesung	(3,"szenische Lesung"),
	SachHoerbuch	(4,"Sach-Hörbuch"),
	LehrHoerbuch	(5,"Lehr-Hörbuch"),
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
