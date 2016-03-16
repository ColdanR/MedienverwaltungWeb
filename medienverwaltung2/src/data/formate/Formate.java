package data.formate;

import java.util.ArrayList;
import java.util.List;

import data.speicherorte.Speicherort;
import enums.Format;

public abstract class Formate {
	private	int					dbId;
	private	Format				type;
	private	List<Speicherort>	speicherOrte	=	new ArrayList<>();

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
	public void addSpeicherOrt(Speicherort speicherort) {
		this.speicherOrte.add(speicherort);
	}
	public void setSpeicherOrt(List<Speicherort> list) {
		this.speicherOrte.addAll(list);
	}
	public Format getType() {
		return type;
	}
}
