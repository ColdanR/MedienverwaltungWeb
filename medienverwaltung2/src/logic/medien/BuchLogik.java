package logic.medien;

import data.medien.Buch;

public class BuchLogik extends MediumLogik<Buch> {
	public BuchLogik() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Buch create() {
		return new Buch();
	}
}
