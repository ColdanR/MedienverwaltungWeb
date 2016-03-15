package gui.dto.medien;

import enums.Mediengruppe;

public class MusikDetailDTO extends MediumDetailDTO {
	private	boolean	live	=	false;
	
	public MusikDetailDTO(String titel) {
		super(titel, Mediengruppe.Musik);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

}
