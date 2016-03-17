package gui.dto.medien;

import enums.SpeicherortArt;

public class SpeicherortDTO {
	private	int				id;
	private	SpeicherortArt	type;
	private	String			art;
	private	String			bez;
	private	String			bemerkung;
	private	String			zustand;
	
	public SpeicherortDTO(int id, SpeicherortArt type, String art, String bez, String bemerkung, String zustand) {
		super();
		this.id = id;
		this.type = type;
		this.art = art;
		this.bez = bez;
		this.bemerkung = bemerkung;
		this.zustand = zustand;
	}

	public int getId() {
		return id;
	}

	public SpeicherortArt getType() {
		return type;
	}

	public String getArt() {
		return art;
	}

	public String getBez() {
		return bez;
	}

	public String getBemerkung() {
		return bemerkung;
	}

	public String getZustand() {
		return zustand;
	}
}
