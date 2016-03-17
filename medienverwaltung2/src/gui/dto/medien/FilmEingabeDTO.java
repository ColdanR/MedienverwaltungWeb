package gui.dto.medien;

import java.util.Arrays;
import java.util.List;

import data.medien.enums.FilmArt;
import enums.Mediengruppe;

public class FilmEingabeDTO extends MediumEingabeDTO {
	private	String				sprache			=	"";
	private	List<FilmArt>		artOptions		=	Arrays.asList(FilmArt.values());
	private	FilmArt				artSelected		=	null;
	
	public FilmEingabeDTO(String titel) {
		super(titel, Mediengruppe.Film);
		
	}

	public String getSprache() {
		return sprache;
	}

	public void setSprache(String sprache) {
		this.sprache = sprache;
	}

	public List<FilmArt> getArtOptions() {
		return artOptions;
	}

	public void setArtOptions(List<FilmArt> artOptions) {
		this.artOptions = artOptions;
	}

	public FilmArt getArtSelected() {
		return artSelected;
	}

	public void setArtSelected(FilmArt artSelected) {
		this.artSelected = artSelected;
	}
}
