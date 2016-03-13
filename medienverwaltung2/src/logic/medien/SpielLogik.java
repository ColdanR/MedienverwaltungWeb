package logic.medien;

import data.medien.Spiel;

public class SpielLogik extends MediumLogik<Spiel> {
	public SpielLogik() {
		// TODO
		super(null);
	}

	@Override
	public Spiel create() {
		return new Spiel();
	}
}
