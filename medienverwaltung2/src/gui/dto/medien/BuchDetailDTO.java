package gui.dto.medien;

import enums.Mediengruppe;

public abstract class BuchDetailDTO extends MediumDetailDTO {
	private	String				sprache				=	"";
	private	String				art					= 	"";
	private	int					auflage				=	0;
	
	protected BuchDetailDTO(String titel, Mediengruppe medium) {
		super(titel, Mediengruppe.Buch);
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

	public int getAuflage() {
		return auflage;
	}

	public void setAuflage(int auflage) {
		this.auflage = auflage;
	}

}
