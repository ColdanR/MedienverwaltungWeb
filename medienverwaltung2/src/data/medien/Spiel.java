package data.medien;

import enums.Mediengruppe;

public class Spiel extends Medium {
	private	Spiel		fortsetzung;
	private	String		sprache;
	private	String		betriebssystem;
	
	public Spiel() {
		super(Mediengruppe.Spiel);
	}

	public Spiel getFortsetzung() {
		return fortsetzung;
	}
	public void setFortsetzung(Spiel fortsetzung) {
		this.fortsetzung = fortsetzung;
	}
	public String getSprache() {
		return sprache;
	}
	public void setSprache(String sprache) {
		this.sprache = sprache;
	}
	public String getBetriebssystem() {
		return betriebssystem;
	}
	public void setBetriebssystem(String betriebssystem) {
		this.betriebssystem = betriebssystem;
	}
}
