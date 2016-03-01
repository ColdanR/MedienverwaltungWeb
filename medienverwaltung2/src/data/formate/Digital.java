package data.formate;

import enums.Format;

public class Digital extends Formate {
	private	String	dateiformat;
	private	String	qualitaet;
	
	public Digital() {
		super(Format.Digital);
	}

	public String getDateiformat() {
		return dateiformat;
	}
	public void setDateiformat(String dateiformat) {
		this.dateiformat = dateiformat;
	}
	public String getQualitaet() {
		return qualitaet;
	}
	public void setQualitaet(String qualitaet) {
		this.qualitaet = qualitaet;
	}
}
