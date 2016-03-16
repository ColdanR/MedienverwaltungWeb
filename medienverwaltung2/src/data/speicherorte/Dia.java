package data.speicherorte;

import enums.SpeicherortArt;

public class Dia extends Speicherort {
	private	String		zustand;
	
	public Dia() {
		super(SpeicherortArt.Dia);
	}

	public String getZustand() {
		return zustand;
	}
	public void setZustand(String zustand) {
		this.zustand = zustand;
	}
}
