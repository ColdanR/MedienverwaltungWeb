package data.speicherorte;

import data.speicherorte.enums.KassettenArt;
import enums.SpeicherortArt;

public class Kassette extends Speicherort {
	private	int				zustand;
	private	KassettenArt	art;
	
	public Kassette() {
		super(SpeicherortArt.Kassette);
	}
	
	public int getZustand() {
		return zustand;
	}
	public void setZustand(int zustand) {
		this.zustand = zustand;
	}
	public KassettenArt getArt() {
		return art;
	}
	public void setArt(KassettenArt art) {
		this.art = art;
	}
}
