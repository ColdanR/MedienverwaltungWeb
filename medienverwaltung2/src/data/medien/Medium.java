package data.medien;

import java.time.LocalDate;
import java.util.List;

import data.formate.Formate;
import enums.Mediengruppe;

public abstract class Medium {
	private	Mediengruppe	type;
	private	String			titel;
	private	Genre			genre;
	private	LocalDate		erscheinungsdatum;
	private	String			bemerkungen;
	private	List<Formate>	formate;
	
	protected Medium(Mediengruppe type) {
		this.type	=	type;
	}
	
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public LocalDate getErscheinungsdatum() {
		return erscheinungsdatum;
	}
	public void setErscheinungsdatum(LocalDate erscheinungsdatum) {
		this.erscheinungsdatum = erscheinungsdatum;
	}
	public String getBemerkungen() {
		return bemerkungen;
	}
	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}
	public List<Formate> getFormate() {
		return formate;
	}
	public void setFormate(List<Formate> formate) {
		this.formate = formate;
	}
	public Mediengruppe getType() {
		return type;
	}
}
