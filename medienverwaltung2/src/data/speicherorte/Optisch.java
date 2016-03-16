package data.speicherorte;

import data.speicherorte.enums.OptischArt;
import enums.SpeicherortArt;

public class Optisch extends Speicherort {
	private	String		zustand;
	private	OptischArt	art;
	
	public Optisch() {
		super(SpeicherortArt.Optisch);
	}

	public String getZustand() {
		return zustand;
	}
	public void setZustand(String zustand) {
		this.zustand = zustand;
	}
	public OptischArt getArt() {
		return art;
	}
	public void setArt(OptischArt art) {
		this.art = art;
	}
}
