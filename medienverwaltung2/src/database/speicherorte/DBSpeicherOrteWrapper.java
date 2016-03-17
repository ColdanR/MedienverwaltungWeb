package database.speicherorte;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import data.speicherorte.Buch;
import data.speicherorte.Dia;
import data.speicherorte.Festplatte;
import data.speicherorte.Kassette;
import data.speicherorte.Optisch;
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
		
		// Dia laden
		DBDia		dbDia		=	new DBDia();
		List<Dia>	tempDia	=	dbDia.listForFormatId(formatId);
		
		if (tempDia != null) {
			ret.addAll(tempDia);
		} else {
			errors.addAll(dbDia.getErrors());
		}
		
		// Festplatte laden
		DBFestplatte		dbFestplatte	=	new DBFestplatte();
		List<Festplatte>	tempFestplatte	=	dbFestplatte.listForFormatId(formatId);
		
		if (tempFestplatte != null) {
			ret.addAll(tempFestplatte);
		} else {
			errors.addAll(dbFestplatte.getErrors());
		}
		
		// Optisch laden
		DBOptisch		dbOptisch	=	new DBOptisch();
		List<Optisch>	tempOptisch	=	dbOptisch.listForFormatId(formatId);
		
		if (tempOptisch != null) {
			ret.addAll(tempOptisch);
		} else {
			errors.addAll(dbOptisch.getErrors());
		}
		if (errors.size() > 0) {
			return null;
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
	
	public boolean delete(int speicherortId) {
		// Kassetten laden
		DBKassette			dbKassette		=	new DBKassette();
		if (!dbKassette.delete(speicherortId)) {
			errors.addAll(dbKassette.getErrors());
		}
		
		// Buchformat laden
		DBBuchformat	dbBuch		=	new DBBuchformat();
		if (!dbBuch.delete(speicherortId)) {
			errors.addAll(dbBuch.getErrors());
		}
		
		// Schallplatten laden
		DBSchallplatte		dbSchallplatte		=	new DBSchallplatte();
		if (!dbSchallplatte.delete(speicherortId)) {
			errors.addAll(dbSchallplatte.getErrors());
		}
		
		// Dia laden
		DBDia		dbDia		=	new DBDia();
		if (!dbDia.delete(speicherortId)) {
			errors.addAll(dbDia.getErrors());
		}
		
		// Festplatte laden
		DBFestplatte		dbFestplatte	=	new DBFestplatte();
		if (!dbFestplatte.delete(speicherortId)) {
			errors.addAll(dbFestplatte.getErrors());
		}
		
		// Optisch laden
		DBOptisch		dbOptisch	=	new DBOptisch();
		if (!dbOptisch.delete(speicherortId)) {
			errors.addAll(dbOptisch.getErrors());
		}
		
		return errors.size() == 0;
	}
}
