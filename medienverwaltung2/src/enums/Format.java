package enums;

public enum Format {
	Digital(new SpeicherortArt[]{SpeicherortArt.Optisch,
			SpeicherortArt.Festplatte}),
	Analog(new SpeicherortArt[]{SpeicherortArt.Optisch,
			SpeicherortArt.Schallplatte,
			SpeicherortArt.Buch,
			SpeicherortArt.Kassette,
			SpeicherortArt.Dia});
	
	private	SpeicherortArt[]	allowed;
	
	private Format(SpeicherortArt[] allowed) {
		this.allowed	=	allowed;
	}
	
	public SpeicherortArt[] getAllowed() {
		return allowed;
	}
}
