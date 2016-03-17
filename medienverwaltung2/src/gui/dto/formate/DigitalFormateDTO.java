package gui.dto.formate;

public class DigitalFormateDTO extends FormateDTO {
	private	String	qualitaet	=	"";
	private	String	dateiformat	=	"";

	public DigitalFormateDTO(String titel, String uri, int idMedium, int idMediumType) {
		super(titel, uri, idMedium, idMediumType);
	}
	
	public String getQualitaet() {
		return qualitaet;
	}

	public void setQualitaet(String qualitaet) {
		this.qualitaet = qualitaet;
	}

	public String getDateiformat() {
		return dateiformat;
	}

	public void setDateiformat(String dateiformat) {
		this.dateiformat = dateiformat;
	}
}
