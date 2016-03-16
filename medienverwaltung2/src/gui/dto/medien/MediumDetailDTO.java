package gui.dto.medien;

import enums.Mediengruppe;
import gui.dto.BaseDTO;

public abstract class MediumDetailDTO extends BaseDTO {
	private	int					dbId				=	0;
	private	Mediengruppe		medium				=	null;
	private	String				bezeichnung			=	"";
	private	String				erscheinungsjahr	=	"";
	private	String				bemerkung			=	"";
	private	String				genre				=	"";

	protected MediumDetailDTO(String titel, Mediengruppe medium) {
		super(titel);
		this.setMedium(medium);
		addJsFile("transition.js");
		addJsFile("collapse.js");
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

	public void setMedium(Mediengruppe medium) {
		this.medium = medium;
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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}


}
