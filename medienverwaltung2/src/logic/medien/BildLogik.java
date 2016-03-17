package logic.medien;

import data.medien.Bild;
import database.medien.DBBild;

public class BildLogik extends MediumLogik<Bild> {
	public BildLogik() {
		super(new DBBild());
	}

	@Override
	public Bild create() {
		object = new Bild();
		return object;
	}
}
