package data.sammlung;

import java.util.List;

import data.medien.Medium;

public class Sammlung {
	private	String			bezeichnung;
	private	List<Medium>	elemente;
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public List<Medium> getElemente() {
		return elemente;
	}
	public void setElemente(List<Medium> elemente) {
		this.elemente = elemente;
	}
}
