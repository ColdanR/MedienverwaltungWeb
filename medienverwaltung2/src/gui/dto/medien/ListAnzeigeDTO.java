package gui.dto.medien;

import java.util.List;

import enums.Mediengruppe;
import gui.dto.BaseDTO;

public class ListAnzeigeDTO extends BaseDTO {
	public static class ListElementDTO {
		private int		id;
		private	String	bezeichnung;
		private	String	datum;
		private	String	genre;
		
		public ListElementDTO(int id, String bezeichnung, String datum, String genre) {
			super();
			this.id = id;
			this.bezeichnung = bezeichnung;
			this.datum = datum;
			this.genre = genre;
		}

		public int getId() {
			return id;
		}

		public String getBezeichnung() {
			return bezeichnung;
		}

		public String getDatum() {
			return datum;
		}

		public String getGenre() {
			return genre;
		}
	}
	
	private	String					baseURI;
	private	Mediengruppe			medium;
	private	List<ListElementDTO>	list;
	
	public ListAnzeigeDTO(String titel) {
		super(titel);
	}

	public String getBaseURI() {
		return baseURI;
	}

	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}

	public Mediengruppe getMedium() {
		return medium;
	}

	public void setMedium(Mediengruppe medium) {
		this.medium = medium;
	}

	public List<ListElementDTO> getList() {
		return list;
	}

	public void setList(List<ListElementDTO> list) {
		this.list = list;
	}
}
