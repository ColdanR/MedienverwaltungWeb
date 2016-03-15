package gui.dto.medien;

import enums.Mediengruppe;

public abstract class MusikDetailDTO extends MediumDetailDTO {
	private	boolean	live	=	false;
	
	protected MusikDetailDTO(String titel, Mediengruppe medium) {
		super(titel, Mediengruppe.Musik);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

}
