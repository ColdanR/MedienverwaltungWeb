package gui.dto.medien;

import enums.Mediengruppe;

public class MusikEingabeDTO extends MediumEingabeDTO {
	private	boolean	live	=	false;

	public MusikEingabeDTO(String titel) {
		super(titel, Mediengruppe.Musik);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

}
