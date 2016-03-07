package data.speicherorte;

import data.speicherorte.enums.BuchArt;
import enums.SpeicherortArt;

public class Buch extends Speicherort {
	private	int		zustand;
	private	BuchArt	art;
	
	public Buch() {
		super(SpeicherortArt.Buch);
	}

	public int getZustand() {
		return zustand;
	}
	public void setZustand(int zustand) {
		this.zustand = zustand;
	}
	public BuchArt getArt() {
		return art;
	}
	public void setArt(BuchArt art) {
		this.art = art;
	}
}
