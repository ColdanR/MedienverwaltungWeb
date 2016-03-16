package gui.dto.medien;

import java.util.List;

import data.medien.Genre;
import enums.Mediengruppe;
import gui.dto.BaseDTO;

public abstract class MediumEingabeDTO extends BaseDTO {
	private	int					dbId				=	0;
	private	Mediengruppe		medium				=	null;
	private	String				bezeichnung			=	"";
	private	String				erscheinungsjahr	=	"";
	private	String				bemerkung			=	"";
	private	List<Genre>			genreOptions		=	null;
	private	List<Genre>			genreSelected		=	null;
	
	protected MediumEingabeDTO(String titel, Mediengruppe medium) {
		super(titel);
		this.medium = medium;
		addJsFile("bootstrap-datetimepicker.min.js");
		addCssFile("bootstrap-datetimepicker.min.css");
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getErscheinungsjahr() {
		return erscheinungsjahr;
	}

	public void setErscheinungsjahr(String erscheinungsjahr) {
		this.erscheinungsjahr = erscheinungsjahr;
	}

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}

	public List<Genre> getGenreOptions() {
		return genreOptions;
	}

	public void setGenreOptions(List<Genre> genreOptions) {
		this.genreOptions = genreOptions;
	}

	public List<Genre> getGenreSelected() {
		return genreSelected;
	}

	public void setGenreSelected(List<Genre> genreSelected) {
		this.genreSelected = genreSelected;
	}

	public int getDbId() {
		return dbId;
	}

	public void setDbId(int dbId) {
		this.dbId = dbId;
	}

	public Mediengruppe getMedium() {
		return medium;
	}
}
