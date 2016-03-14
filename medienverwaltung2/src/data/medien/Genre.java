package data.medien;

public class Genre {
	private int		dbId;
	private	String	bezeichnung;
	
	public int getDbId() {
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
			return dbId == ((Genre) obj).getDbId();
		}
		return super.equals(obj);
	}
}
