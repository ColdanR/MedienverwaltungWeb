package logic.speicherorte;

import data.speicherorte.Buch;
import database.speicherorte.DBBuchformat;

public class BuchLogik extends SpeicherorteLogik<Buch> {

	public BuchLogik(int formatId) {
		super(new DBBuchformat(), formatId);
	}

	@Override
	public Buch create() {
		object = new Buch();
		return object;
	}
}
