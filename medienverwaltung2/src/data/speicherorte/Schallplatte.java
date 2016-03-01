package data.speicherorte;

import enums.SpeicherortArt;

public class Schallplatte extends Speicherort {
	private	int				zustand;
	private	SchallplatteArt	art;
	
	public Schallplatte() {
		super(SpeicherortArt.Schallplatte);
	}

	public int getZustand() {
		return zustand;
	}
	public void setZustand(int zustand) {
		this.zustand = zustand;
	}
	public SchallplatteArt getArt() {
		return art;
	}
	public void setArt(SchallplatteArt art) {
		this.art = art;
	}
}
