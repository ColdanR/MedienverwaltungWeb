package gui.dto.formate;

import enums.Format;

public class AnalogFormateDTO extends FormateDTO {
	public AnalogFormateDTO(String titel, String uri, int idMedium, int idMediumType) {
		super(titel, uri, idMedium, idMediumType);
		this.setSelectedFormat(Format.Analog);
	}
}
