package gui.dto.medien;

import enums.Mediengruppe;

public class FilmDetailDTO extends MediumDetailDTO {
	private	String				sprache				=	"";
	private	String				art					=	"";
	
	public FilmDetailDTO(String titel) {
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
