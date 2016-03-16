package database.speicherorte;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import data.speicherorte.Buch;
import data.speicherorte.Kassette;
import data.speicherorte.Schallplatte;
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
		
		// Kassetten laden
		DBKassette			dbKassette		=	new DBKassette();
		List<Kassette>		tempKassette	=	dbKassette.listForFormatId(formatId);
		
		if (tempKassette != null) {
			ret.addAll(tempKassette);
		} else {
			errors.addAll(dbKassette.getErrors());
		}
		
		// Buchformat laden
		DBBuchformat	dbBuch		=	new DBBuchformat();
		List<Buch>		tempBuch	=	dbBuch.listForFormatId(formatId);
		
		if (tempBuch != null) {
			ret.addAll(tempBuch);
		} else {
			errors.addAll(dbBuch.getErrors());
		}
		
		// Schallplatten laden
		DBSchallplatte		dbSchallplatte		=	new DBSchallplatte();
		List<Schallplatte>	tempSchallplatte	=	dbSchallplatte.listForFormatId(formatId);
		
		if (tempSchallplatte != null) {
			ret.addAll(tempSchallplatte);
		} else {
			errors.addAll(dbSchallplatte.getErrors());
		}
		
		// Sortieren und R�ckgabe
		ret.sort(new Comparator<Speicherort>() {
			@Override
			public int compare(Speicherort o1, Speicherort o2) {
				return o1.getLagerOrt().compareToIgnoreCase(o2.getLagerOrt());
			}
		});
		return ret;
	}
}
