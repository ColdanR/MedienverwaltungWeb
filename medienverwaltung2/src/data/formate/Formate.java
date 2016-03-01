package data.formate;

import java.util.List;

import data.speicherorte.Speicherort;
import enums.Format;

public abstract class Formate {
	private	int					dbId;
	private	Format				type;
	private	List<Speicherort>	speicherOrte;

	protected Formate(Format type) {
		this.type	=	type;
	}
	
	public int getDbId() {
		return dbId;
	}
	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
	public List<Speicherort> getSpeicherOrte() {
		return speicherOrte;
	}
	public void setSpeicherOrte(List<Speicherort> speicherOrte) {
		this.speicherOrte = speicherOrte;
	}
	public Format getType() {
		return type;
	}
}
