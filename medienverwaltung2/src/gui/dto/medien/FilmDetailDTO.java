package gui.dto.medien;

import enums.Mediengruppe;

public abstract class FilmDetailDTO extends MediumDetailDTO {
	private	String				sprache				=	"";
	private	String				art					=	"";
	protected FilmDetailDTO(String titel, Mediengruppe medium) {
		super(titel, Mediengruppe.Film);
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
