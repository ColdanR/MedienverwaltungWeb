package logic;

import enums.Mediengruppe;
import logic.medien.BildLogik;
import logic.medien.BuchLogik;
import logic.medien.FilmLogik;
import logic.medien.HoerbuchLogik;
import logic.medien.MediumLogik;
import logic.medien.MusikLogik;
import logic.medien.SpielLogik;

public class MediumLogicFactory {
	public static MediumLogik<?> create(Mediengruppe type) throws IllegalArgumentException {
		switch(type) {
		case Bild:
			return new BildLogik();
		case Buch:
			return new BuchLogik();
		case Film:
			return new FilmLogik();
		case Hoerbuch:
			return new HoerbuchLogik();
		case Musik:
			return new MusikLogik();
		case Spiel:
			return new SpielLogik();
		default:
			throw new IllegalArgumentException("Nicht implementierter Type " + type.getBezeichnung());
		}
	}
}
