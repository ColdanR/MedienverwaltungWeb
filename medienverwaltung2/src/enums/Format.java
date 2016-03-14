package enums;

import interfaces.SelectAble;

public enum Format implements SelectAble {
	Digital(new SpeicherortArt[]{SpeicherortArt.Optisch,
			SpeicherortArt.Festplatte}, "Digital", 0),
	Analog(new SpeicherortArt[]{SpeicherortArt.Optisch,
			SpeicherortArt.Schallplatte,
			SpeicherortArt.Buch,
			SpeicherortArt.Kassette,
			SpeicherortArt.Dia}, "Analog", 1);
	
	private	SpeicherortArt[]	allowed;
	private	String				bezeichnung;
	private	int					id;
	
	private Format(SpeicherortArt[] allowed, String bezeichnung, int id) {
		this.allowed		=	allowed;
		this.bezeichnung	=	bezeichnung;
		this.id				=	id;
	}
	
	public SpeicherortArt[] getAllowed() {
		return allowed;
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
