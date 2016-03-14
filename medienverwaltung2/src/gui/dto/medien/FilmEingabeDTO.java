package gui.dto.medien;

import java.util.Arrays;
import java.util.List;

import data.medien.enums.FilmArt;
import enums.Mediengruppe;

public class FilmEingabeDTO extends MediumEingabeDTO {
	private	String				sprache				=	"";
	private	List<FilmArt>		filmartOptions		=	Arrays.asList(FilmArt.values());
	private	FilmArt				filmartSelected		=	null;
	
	public FilmEingabeDTO(String titel) {
		super(titel, Mediengruppe.Film);
		// TODO Auto-generated constructor stub
	}

	public String getSprache() {
		return sprache;
	}

	public void setSprache(String sprache) {
		this.sprache = sprache;
	}

	public List<FilmArt> getFilmartOptions() {
		return filmartOptions;
	}

	public void setFilmartOptions(List<FilmArt> filmartOptions) {
		this.filmartOptions = filmartOptions;
	}

	public FilmArt getFilmartSelected() {
		return filmartSelected;
	}

	public void setFilmartSelected(FilmArt filmartSelected) {
		this.filmartSelected = filmartSelected;
	}
}
