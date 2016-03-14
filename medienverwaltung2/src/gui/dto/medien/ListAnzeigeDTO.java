package gui.dto.medien;

import java.util.List;

import enums.Mediengruppe;
import gui.dto.BaseDTO;

public class ListAnzeigeDTO extends BaseDTO {
	public static class ListElementDTO {
		private int		id;
		private	String	bezeichnung;
		private	String	ersterscheinung;
		private	String	genre;
		private	String	person;
		
		public ListElementDTO(int id, String bezeichnung, String ersterscheinung, String genre, String person) {
			super();
			this.id = id;
			this.bezeichnung = bezeichnung;
			this.ersterscheinung = ersterscheinung;
			this.genre = genre;
			this.person = person;
		}

		public int getId() {
			return id;
		}

		public String getBezeichnung() {
			return bezeichnung;
		}

		public String getErsterscheinung() {
			return ersterscheinung;
		}

		public String getGenre() {
			return genre;
		}

		public String getPerson() {
			return person;
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
