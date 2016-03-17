package logic.speicherorte;

import data.speicherorte.Festplatte;
import database.speicherorte.DBFestplatte;

public class FestplatteLogik extends SpeicherorteLogik<Festplatte> {

	public FestplatteLogik(int idSpeicherFormat) {
		super(new DBFestplatte(), idSpeicherFormat);
	}

	@Override
	public Festplatte create() {
		object = new Festplatte();
		return object;
	}

}
