package logic.medien;

import data.medien.Musik;

public class MusikLogik extends MediumLogik<Musik> {

	public MusikLogik() {
		// TODO
		super(null);
	}

	@Override
	public Musik create() {
		return new Musik();
	}

}
