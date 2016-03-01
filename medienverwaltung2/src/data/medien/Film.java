package data.medien;

import enums.Mediengruppe;

public class Film extends Medium {
	private	Film	fortsetzung;
	private	String	sprache;
	private	FilmArt	art;
	
	public Film() {
		super(Mediengruppe.Film);
	}

	public Film getFortsetzung() {
		return fortsetzung;
	}
	public void setFortsetzung(Film fortsetzung) {
		this.fortsetzung = fortsetzung;
	}
	public String getSprache() {
		return sprache;
	}
	public void setSprache(String sprache) {
		this.sprache = sprache;
	}
	public FilmArt getArt() {
		return art;
	}
	public void setArt(FilmArt art) {
		this.art = art;
	}
}
