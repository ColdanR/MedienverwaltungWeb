package data.medien;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import data.formate.Formate;
import enums.Mediengruppe;

public abstract class Medium {
	private	int				dbId;
	private	Mediengruppe	type;
	private	String			titel;
	private	List<Genre>		genre;
	private	LocalDate		erscheinungsdatum;
	private	String			bemerkungen;
	private	List<Formate>	formate				=	new ArrayList<>();
	
	protected Medium(Mediengruppe type) {
		this.type	=	type;
	}
	
	public int getDbId() {
		return dbId;
	}
	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public List<Genre> getGenre() {
		return genre;
	}
	public void setGenre(List<Genre> genre) {
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
	public void addFormat(Formate format) {
		this.formate.add(format);
	}
	public Mediengruppe getType() {
		return type;
	}
}
