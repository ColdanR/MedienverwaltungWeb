package data.speicherorte;

import enums.SpeicherortArt;

public abstract class Speicherort {
	private	String			lagerOrt;
	private	String			bemerkung;
	private	SpeicherortArt	type;
	
	protected Speicherort(SpeicherortArt type) {
		this.type	=	type;
	}
	
	public String getLagerOrt() {
		return lagerOrt;
	}
	public void setLagerOrt(String lagerOrt) {
		this.lagerOrt = lagerOrt;
	}
	public String getBemerkung() {
		return bemerkung;
	}
	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}
	public SpeicherortArt getType() {
		return type;
	}
}
