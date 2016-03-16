package logic.medien;

import data.medien.Musik;
import database.medien.DBMusik;

public class MusikLogik extends MediumLogik<Musik> {

	public MusikLogik() {
		super(new DBMusik());
	}

	@Override
	public Musik create() {
		return new Musik();
	}

}
