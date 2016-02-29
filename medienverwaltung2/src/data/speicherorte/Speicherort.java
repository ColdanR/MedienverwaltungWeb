package data.speicherorte;

public abstract class Speicherort {
	private	String	lagerOrt;
	private	String	bemerkung;
	
	public String getLagerOrt() {
		return lagerOrt;
	}
	public void setLagerOrt(String lagerOrt) {
		this.lagerOrt = lagerOrt;
	}
	public String getBemerkung() {
		return bemerkung;
	}
	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}
}
