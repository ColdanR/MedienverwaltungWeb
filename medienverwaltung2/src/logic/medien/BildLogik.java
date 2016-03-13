package logic.medien;

import data.medien.Bild;

public class BildLogik extends MediumLogik<Bild> {
	public BildLogik() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bild create() {
		return new Bild();
	}
}
