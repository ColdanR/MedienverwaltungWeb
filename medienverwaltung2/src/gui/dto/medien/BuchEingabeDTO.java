package gui.dto.medien;

import java.util.Arrays;
import java.util.List;

import data.medien.enums.BuchArt;
import enums.Mediengruppe;

public class BuchEingabeDTO extends MediumEingabeDTO {
	private	String				sprache			=	"";
	private	List<BuchArt>		artOptions		=	Arrays.asList(BuchArt.values());
	private BuchArt				artSelected		=	null;
	private	int					auflage			=	0;
	
	public BuchEingabeDTO(String titel) {
		super(titel, Mediengruppe.Buch);
	}

	public String getSprache() {
		return sprache;
	}

	public void setSprache(String sprache) {
		this.sprache = sprache;
	}

	public List<BuchArt> getArtOptions() {
		return artOptions;
	}

	public void setArtOptions(List<BuchArt> artOptions) {
		this.artOptions = artOptions;
	}

	public BuchArt getArtSelected() {
		return artSelected;
	}

	public void setArtSelected(BuchArt artSelected) {
		this.artSelected = artSelected;
	}

	public int getAuflage() {
		return auflage;
	}

	public void setAuflage(int auflage) {
		this.auflage = auflage;
	}

}
