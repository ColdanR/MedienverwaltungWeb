package logic.speicherorte;

import data.speicherorte.Optisch;
import database.speicherorte.DBOptisch;

public class OptischLogik extends SpeicherorteLogik<Optisch> {
	public OptischLogik(int formatId) {
		super(new DBOptisch(), formatId);
	}
	
	@Override
	public Optisch create() {
		object = new Optisch();
		return object;
	}

}
