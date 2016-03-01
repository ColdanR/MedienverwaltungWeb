package data.medien;

import enums.Mediengruppe;

public class Hoerbuch extends Medium {
	private	Hoerbuch	fortsetzung;
	private	String		sprache;
	private	HoerbuchArt	art;
	
	public Hoerbuch() {
		super(Mediengruppe.Hoerbuch);
	}

	public Hoerbuch getFortsetzung() {
		return fortsetzung;
	}
	public void setFortsetzung(Hoerbuch fortsetzung) {
		this.fortsetzung = fortsetzung;
	}
	public String getSprache() {
		return sprache;
	}
	public void setSprache(String sprache) {
		this.sprache = sprache;
	}
	public HoerbuchArt getArt() {
		return art;
	}
	public void setArt(HoerbuchArt art) {
		this.art = art;
	}
}
