package gui.dto.medien;

import java.util.List;

public class FormatDTO {
	private	int						id;
	private	int						idFormatType;
	private	String					bezeichnung;
	private	List<SpeicherortDTO>	speicherorte;
	
	public FormatDTO(int id, int idFormatType, String bezeichnung, List<SpeicherortDTO> speicherorte) {
		super();
		this.id				=	id;
		this.idFormatType	=	idFormatType;
		this.bezeichnung	=	bezeichnung;
		this.speicherorte	=	speicherorte;
	}
	
	public int getId() {
		return id;
	}

	public int getIdFormatType() {
		return idFormatType;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public List<SpeicherortDTO> getSpeicherorte() {
		return speicherorte;
	}
}
