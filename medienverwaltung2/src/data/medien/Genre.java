package data.medien;

import interfaces.SelectAble;

public class Genre implements SelectAble {
	private int		dbId;
	private	String	bezeichnung;
	
	public int getId() {
		return dbId;
	}
	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Genre) {
			return dbId == ((Genre) obj).getId();
		}
		return super.equals(obj);
	}
}
