package data.speicherformate;

import data.SpeicherFormatInterface;
import enums.Speicherformat;

public class DigitalMusik implements SpeicherFormatInterface {
	private	int				id				=	0;
	private	String			datentraeger	=	"";
	private	String			pfad			=	"";
	private	String			format			=	"";
	private	String			qualitaet		=	"";
	private Speicherformat	type			=	Speicherformat.DigitalMusik;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the datentraeger
	 */
	public String getDatentraeger() {
		return datentraeger;
	}
	/**
	 * @param datentraeger the datentraeger to set
	 */
	public void setDatentraeger(String datentraeger) {
		this.datentraeger = datentraeger;
	}
	/**
	 * @return the pfad
	 */
	public String getPfad() {
		return pfad;
	}
	/**
	 * @param pfad the pfad to set
	 */
	public void setPfad(String pfad) {
		this.pfad = pfad;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the qualitaet
	 */
	public String getQualitaet() {
		return qualitaet;
	}
	/**
	 * @param qualitaet the qualitaet to set
	 */
	public void setQualitaet(String qualitaet) {
		this.qualitaet = qualitaet;
	}

	@Override
	public Speicherformat getType() {
		return type;
	}
}
