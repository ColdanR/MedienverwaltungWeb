package logic.medien;

import data.medien.Buch;
import database.medien.DBBuch;

public class BuchLogik extends MediumLogik<Buch> {
	public BuchLogik() {
		super(new DBBuch());
	}

	@Override
	public Buch create() {
		object = new Buch();
		return object;
	}
}
