package gui.dto.medien;

import enums.Mediengruppe;

public class BildDetailDTO extends MediumDetailDTO {
	public BildDetailDTO(String titel) {
		super(titel, Mediengruppe.Bild);
	}
}
