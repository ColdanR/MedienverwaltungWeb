package data.medien;

import enums.Mediengruppe;

public class Buch extends Medium {
	private	Buch	fortsetzung;
	private	String	sprache;
	private	BuchArt	art;
	private	int		auflage;
	
	public Buch() {
		super(Mediengruppe.Buch);
	}

	public Buch getFortsetzung() {
		return fortsetzung;
	}
	public void setFortsetzung(Buch fortsetzung) {
		this.fortsetzung = fortsetzung;
	}
	public String getSprache() {
		return sprache;
	}
	public void setSprache(String sprache) {
		this.sprache = sprache;
	}
	public BuchArt getArt() {
		return art;
	}
	public void setArt(BuchArt art) {
		this.art = art;
	}
	public int getAuflage() {
		return auflage;
	}
	public void setAuflage(int auflage) {
		this.auflage = auflage;
	}
}
