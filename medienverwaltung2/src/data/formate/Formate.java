package data.formate;

import java.util.List;

import data.speicherorte.Speicherort;

public abstract class Formate {
	private	List<Speicherort>	speicherOrte;

	public List<Speicherort> getSpeicherOrte() {
		return speicherOrte;
	}

	public void setSpeicherOrte(List<Speicherort> speicherOrte) {
		this.speicherOrte = speicherOrte;
	}
}
