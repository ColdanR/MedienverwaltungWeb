package gui.dto;

import java.util.Arrays;
import java.util.List;

import data.medien.Genre;
import data.medien.enums.BuchArt;
import data.medien.enums.FilmArt;
import data.medien.enums.HoerbuchArt;

public class MediumAnlageDTO extends BaseDTO {
	private	int					dbId				=	0;
	private	String				bezeichnung			=	"";
	private	String				erscheinungsjahr	=	"";
	private	String				bemerkung			=	"";
	private	List<Genre>			genreOptions		=	null;
	private	Genre				genreSelected		=	null;
	
	private	String				sprache				=	"";
	private	List<BuchArt>		buchartOptions		=	Arrays.asList(BuchArt.values());
	private BuchArt				buchartSelected		=	null;
	private	String				auflage				=	"";
	private	List<FilmArt>		filmartOptions		=	Arrays.asList(FilmArt.values());
	private	FilmArt				filmartSelected		=	null;
	private	List<HoerbuchArt>	hoerbuchartOptions	=	Arrays.asList(HoerbuchArt.values());
	private HoerbuchArt			hoerbuchartSelected	=	null;
	private	String				betriebssystem		=	"";
	
	public MediumAnlageDTO(String titel) {
		super(titel);
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

	public Genre getGenreSelected() {
		return genreSelected;
	}

	public void setGenreSelected(Genre genreSelected) {
		this.genreSelected = genreSelected;
	}

	public String getSprache() {
		return sprache;
	}

	public void setSprache(String sprache) {
		this.sprache = sprache;
	}

	public List<BuchArt> getBuchartOptions() {
		return buchartOptions;
	}

	public BuchArt getBuchartSelected() {
		return buchartSelected;
	}

	public void setBuchartSelected(BuchArt buchartSelected) {
		this.buchartSelected = buchartSelected;
	}

	public String getAuflage() {
		return auflage;
	}

	public void setAuflage(String auflage) {
		this.auflage = auflage;
	}

	public List<FilmArt> getFilmartOptions() {
		return filmartOptions;
	}

	public FilmArt getFilmartSelected() {
		return filmartSelected;
	}

	public void setFilmartSelected(FilmArt filmartSelected) {
		this.filmartSelected = filmartSelected;
	}

	public List<HoerbuchArt> getHoerbuchartOptions() {
		return hoerbuchartOptions;
	}

	public HoerbuchArt getHoerbuchartSelected() {
		return hoerbuchartSelected;
	}

	public void setHoerbuchartSelected(HoerbuchArt hoerbuchartSelected) {
		this.hoerbuchartSelected = hoerbuchartSelected;
	}

	public String getBetriebssystem() {
		return betriebssystem;
	}

	public void setBetriebssystem(String betriebssystem) {
		this.betriebssystem = betriebssystem;
	}

	public int getDbId() {
		return dbId;
	}

	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
}
