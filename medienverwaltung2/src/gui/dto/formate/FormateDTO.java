package gui.dto.formate;

import java.util.Arrays;
import java.util.List;

import enums.Format;
import gui.dto.BaseDTO;
import interfaces.SelectAble;

public abstract class FormateDTO extends BaseDTO {
	private	String				uri;
	private	SelectAble			selectedFormat;
	private	List<SelectAble>	selectFormatOptions	=	Arrays.asList(Format.values());
	private	int					idMedium;
	private	int					idMediumType;
	private	int					dbId				=	0;
	
	protected FormateDTO(String titel, String uri, int idMedium, int idMediumType) {
		super(titel);
		this.uri			=	uri;
		this.idMedium		=	idMedium;
		this.idMediumType	=	idMediumType;
		addJsFile("speicherformat.js");
	}
	
	public String getUri() {
		return uri;
	}
	public SelectAble getSelectedFormat() {
		return selectedFormat;
	}
	public void setSelectedFormat(SelectAble selectedFormat) {
		this.selectedFormat = selectedFormat;
	}
	public List<SelectAble> getSelectFormatOptions() {
		return selectFormatOptions;
	}
	public int getIdMedium() {
		return idMedium;
	}
	public int getIdMediumType() {
		return idMediumType;
	}

	public int getDbId() {
		return dbId;
	}
	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
}
