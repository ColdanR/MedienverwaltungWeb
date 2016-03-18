package logic.speicherorte;

import data.speicherorte.Dia;
import database.speicherorte.DBDia;

public class DiaLogik extends SpeicherorteLogik<Dia> {

	public DiaLogik(int formatId) {
		super(new DBDia(), formatId);
	}

	@Override
	public Dia create() {
		object = new Dia();
		return object;
	}

}
