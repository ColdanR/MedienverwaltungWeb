package gui.dto.speicherorte;

import java.util.List;

import enums.SpeicherortArt;
import gui.dto.BaseDTO;
import interfaces.SelectAble;

public class SpeicherorteDTO extends BaseDTO {
	private	int						dbId;
	private	SpeicherortArt			selected;
	private	List<SpeicherortArt>	selectOptions;
	private	int						idSpeicherFormat;
	private	int						idMedium;
	private	int						idMediumType;
	private	int						idFormatType;
	private	String					bezeichnung;
	private	String					bemerkung;
	private	String					zustand;
	private	SelectAble				selectedArt;
	private	List<SelectAble>		selectOptionArt;

	public SpeicherorteDTO(String titel, int dbId, SpeicherortArt selected, List<SpeicherortArt> selectOptions,
			int idSpeicherFormat, int idMedium, int idMediumType, int idFormatType) {
		super(titel);
		this.dbId				=	dbId;
		this.selected			=	selected;
		this.selectOptions		=	selectOptions;
		this.idSpeicherFormat	=	idSpeicherFormat;
		this.idMedium			=	idMedium;
		this.idMediumType		=	idMediumType;
		this.idFormatType		=	idFormatType;
	}

	public int getDbId() {
		return dbId;
	}

	public SpeicherortArt getSelected() {
		return selected;
	}

	public List<SpeicherortArt> getSelectOptions() {
		return selectOptions;
	}

	public int getIdSpeicherFormat() {
		return idSpeicherFormat;
	}

	public int getIdMedium() {
		return idMedium;
	}

	public int getIdMediumType() {
		return idMediumType;
	}

	public int getIdFormatType() {
		return idFormatType;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}

	public String getZustand() {
		return zustand;
	}

	public void setZustand(String zustand) {
		this.zustand = zustand;
	}

	public SelectAble getSelectedArt() {
		return selectedArt;
	}

	public void setSelectedArt(SelectAble selectedArt) {
		this.selectedArt = selectedArt;
	}

	public List<SelectAble> getSelectOptionArt() {
		return selectOptionArt;
	}

	public void setSelectOptionArt(List<SelectAble> selectOptionArt) {
		this.selectOptionArt = selectOptionArt;
	}
	
}
