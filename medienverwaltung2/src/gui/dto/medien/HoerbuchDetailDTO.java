package gui.dto.medien;

import enums.Mediengruppe;

public class HoerbuchDetailDTO extends MediumDetailDTO {
	private	String				sprache				=	"";
	private	String				art					=	"";
	
	public HoerbuchDetailDTO(String titel) {
		super(titel, Mediengruppe.Hoerbuch);
	}

	public String getSprache() {
		return sprache;
	}

	public void setSprache(String sprache) {
		this.sprache = sprache;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

}
