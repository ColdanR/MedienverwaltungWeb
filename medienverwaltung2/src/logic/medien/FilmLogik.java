package logic.medien;

import data.medien.Film;

public class FilmLogik extends MediumLogik<Film> {

	public FilmLogik() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Film create() {
		return new Film();
	}
}
