package gui.dto.medien;

import enums.Mediengruppe;

public class SpielEingabeDTO extends MediumEingabeDTO {
	private	String				sprache				=	"";
	private	String				betriebssystem		=	"";

	public SpielEingabeDTO(String titel) {
		super(titel, Mediengruppe.Spiel);
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
