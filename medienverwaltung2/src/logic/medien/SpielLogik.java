package logic.medien;

import data.medien.Spiel;
import database.medien.DBSpiel;

public class SpielLogik extends MediumLogik<Spiel> {
	public SpielLogik() {
		super(new DBSpiel());
	}

	@Override
	public Spiel create() {
		return new Spiel();
	}
}
