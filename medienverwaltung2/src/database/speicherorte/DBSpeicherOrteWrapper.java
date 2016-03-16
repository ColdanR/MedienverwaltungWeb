package database.speicherorte;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import data.speicherorte.Kassette;
import data.speicherorte.Speicherort;

public class DBSpeicherOrteWrapper {
	private	List<String>	errors	=	new ArrayList<>();
	
	public final List<String> getErrors() {
		List<String> ret = new ArrayList<>();
		errors.stream().forEach(error -> {
			ret.add(error);
		});
		return ret;
	}
	
	public List<Speicherort> getForId(int formatId) {
		List<Speicherort>	ret				=	new ArrayList<>();
		DBKassette			dbKassette		=	new DBKassette();
		List<Kassette>		tempKassette	=	dbKassette.listForFormatId(formatId);
		
		// Kassetten laden
		if (tempKassette == null) {
			ret.addAll(tempKassette);
		} else {
			errors.addAll(dbKassette.getErrors());
		}
		
		// Sortieren und Rückgabe
		ret.sort(new Comparator<Speicherort>() {
			@Override
			public int compare(Speicherort o1, Speicherort o2) {
				return o1.getLagerOrt().compareToIgnoreCase(o2.getLagerOrt());
			}
		});
		return ret;
	}
}
