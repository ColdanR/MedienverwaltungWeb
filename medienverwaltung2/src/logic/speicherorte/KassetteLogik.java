package logic.speicherorte;

import data.speicherorte.Kassette;
import database.speicherorte.DBKassette;

public class KassetteLogik extends SpeicherorteLogik<Kassette> {

	public KassetteLogik(int formatId) {
		super(new DBKassette(), formatId);
	}

	@Override
	public Kassette create() {
		object = new Kassette();
		return object;
	}

}
