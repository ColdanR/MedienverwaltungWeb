package gui.dto.genre;

import gui.dto.BaseDTO;

public class GenreEditDTO extends BaseDTO {
	private	int		dbId;
	private	String	bezeichnung;

	public GenreEditDTO(String titel, int dbId, String bezeichnung) {
		super(titel);
		this.dbId			=	dbId;
		this.bezeichnung	=	bezeichnung;
	}

	public int getDbId() {
		return dbId;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

}
