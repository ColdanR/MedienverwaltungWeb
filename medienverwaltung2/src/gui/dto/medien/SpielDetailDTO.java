package gui.dto.medien;

import enums.Mediengruppe;

public abstract class SpielDetailDTO extends MediumDetailDTO {
	private	String				sprache				=	"";
	private	String				betriebssystem		=	"";
	
	protected SpielDetailDTO(String titel, Mediengruppe medium) {
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
