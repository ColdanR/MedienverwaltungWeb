package logic.medien;

import data.medien.Film;
import database.medien.DBFilm;

public class FilmLogik extends MediumLogik<Film> {

	public FilmLogik() {
		super(new DBFilm());
	}

	@Override
	public Film create() {
		object = new Film();
		return object;
	}
}
