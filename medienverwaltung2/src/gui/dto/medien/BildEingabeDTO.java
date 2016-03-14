package gui.dto.medien;

import enums.Mediengruppe;

public class BildEingabeDTO extends MediumEingabeDTO {
	public BildEingabeDTO(String titel) {
		super(titel, Mediengruppe.Bild);
	}
}
