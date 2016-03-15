package gui.dto.medien;

import enums.Mediengruppe;

public abstract class BildDetailDTO extends MediumDetailDTO {

	protected BildDetailDTO(String titel, Mediengruppe medium) {
		super(titel, Mediengruppe.Bild);
	}

}
