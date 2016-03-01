package data.speicherorte;

import enums.SpeicherortArt;

public class Dia extends Speicherort {
	private	int		zustand;
	
	public Dia() {
		super(SpeicherortArt.Dia);
	}

	public int getZustand() {
		return zustand;
	}
	public void setZustand(int zustand) {
		this.zustand = zustand;
	}
}
