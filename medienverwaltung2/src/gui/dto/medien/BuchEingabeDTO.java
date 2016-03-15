package gui.dto.medien;

import java.util.Arrays;
import java.util.List;

import data.medien.enums.BuchArt;
import enums.Mediengruppe;

public class BuchEingabeDTO extends MediumEingabeDTO {
	private	String				sprache				=	"";
	private	List<BuchArt>		buchartOptions		=	Arrays.asList(BuchArt.values());
	private BuchArt				buchartSelected		=	null;
	private	int					auflage				=	0;
	
	public BuchEingabeDTO(String titel) {
		super(titel, Mediengruppe.Buch);
	}

	public String getSprache() {
		return sprache;
	}

	public void setSprache(String sprache) {
		this.sprache = sprache;
	}

	public List<BuchArt> getBuchartOptions() {
		return buchartOptions;
	}

	public void setBuchartOptions(List<BuchArt> buchartOptions) {
		this.buchartOptions = buchartOptions;
	}

	public BuchArt getBuchartSelected() {
		return buchartSelected;
	}

	public void setBuchartSelected(BuchArt buchartSelected) {
		this.buchartSelected = buchartSelected;
	}

	public int getAuflage() {
		return auflage;
	}

	public void setAuflage(int auflage) {
		this.auflage = auflage;
	}

}
