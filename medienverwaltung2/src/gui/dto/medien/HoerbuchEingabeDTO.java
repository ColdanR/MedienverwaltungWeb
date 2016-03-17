package gui.dto.medien;

import java.util.Arrays;
import java.util.List;

import data.medien.enums.HoerbuchArt;
import enums.Mediengruppe;

public class HoerbuchEingabeDTO extends MediumEingabeDTO {
	private	String				sprache		=	"";
	private	List<HoerbuchArt>	artOptions	=	Arrays.asList(HoerbuchArt.values());
	private HoerbuchArt			artSelected	=	null;
	
	public HoerbuchEingabeDTO(String titel) {
		super(titel, Mediengruppe.Hoerbuch);
	}

	public String getSprache() {
		return sprache;
	}

	public void setSprache(String sprache) {
		this.sprache = sprache;
	}

	public List<HoerbuchArt> getArtOptions() {
		return artOptions;
	}

	public void setArtOptions(List<HoerbuchArt> artOptions) {
		this.artOptions = artOptions;
	}

	public HoerbuchArt getArtSelected() {
		return artSelected;
	}

	public void setArtSelected(HoerbuchArt artSelected) {
		this.artSelected = artSelected;
	}
}
