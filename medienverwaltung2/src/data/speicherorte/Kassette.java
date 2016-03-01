package data.speicherorte;

import enums.SpeicherortArt;

public class Kassette extends Speicherort {
	private	int		zustand;
	
	public Kassette() {
		super(SpeicherortArt.Kassette);
	}

	public int getZustand() {
		return zustand;
	}
	public void setZustand(int zustand) {
		this.zustand = zustand;
	}
}
