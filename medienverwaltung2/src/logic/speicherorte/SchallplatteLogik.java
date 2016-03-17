package logic.speicherorte;

import data.speicherorte.Schallplatte;
import database.speicherorte.DBSchallplatte;

public class SchallplatteLogik extends SpeicherorteLogik<Schallplatte> {

	public SchallplatteLogik( int formatId) {
		super(new DBSchallplatte(), formatId);
	}

	@Override
	public Schallplatte create() {
		object = new Schallplatte();
		return object;
	}

}
