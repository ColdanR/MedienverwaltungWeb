package gui.dto.medien;

import java.util.Arrays;
import java.util.List;

import data.medien.enums.HoerbuchArt;
import enums.Mediengruppe;

public class HoerbuchEingabeDTO extends MediumEingabeDTO {
	private	String				sprache				=	"";
	private	List<HoerbuchArt>	hoerbuchartOptions	=	Arrays.asList(HoerbuchArt.values());
	private HoerbuchArt			hoerbuchartSelected	=	null;
	
	public HoerbuchEingabeDTO(String titel) {
		super(titel, Mediengruppe.Hoerbuch);
	}

	public String getSprache() {
		return sprache;
	}

	public void setSprache(String sprache) {
		this.sprache = sprache;
	}

	public List<HoerbuchArt> getHoerbuchartOptions() {
		return hoerbuchartOptions;
	}

	public void setHoerbuchartOptions(List<HoerbuchArt> hoerbuchartOptions) {
		this.hoerbuchartOptions = hoerbuchartOptions;
	}

	public HoerbuchArt getHoerbuchartSelected() {
		return hoerbuchartSelected;
	}

	public void setHoerbuchartSelected(HoerbuchArt hoerbuchartSelected) {
		this.hoerbuchartSelected = hoerbuchartSelected;
	}

}
