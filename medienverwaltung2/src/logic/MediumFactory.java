package logic;

import data.medien.Bild;
import data.medien.Buch;
import data.medien.Film;
import data.medien.Hoerbuch;
import data.medien.Medium;
import data.medien.Musik;
import data.medien.Spiel;
import enums.Mediengruppe;

public class MediumFactory {
	public static Medium load(int id) {
		return null;
	}
	
	public static Medium create(Mediengruppe type) throws IllegalArgumentException {
		switch(type) {
		case Bild:
			return new Bild();
		case Buch:
			return new Buch();
		case Film:
			return new Film();
		case Hoerbuch:
			return new Hoerbuch();
		case Musik:
			return new Musik();
		case Spiel:
			return new Spiel();
		default:
			throw new IllegalArgumentException("Nicht implementierter Type " + type.getBezeichnung());
		}
	}
	
	public static void delete(Medium medium) {
		
	}
	
	public static void write(Medium medium) {
		
	}
}
