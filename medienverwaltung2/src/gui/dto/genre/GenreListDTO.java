package gui.dto.genre;

import java.util.List;

import data.medien.Genre;
import gui.dto.BaseDTO;

public class GenreListDTO extends BaseDTO {
	private	List<Genre>	list;

	public GenreListDTO(List<Genre> list) {
		super("Alle Genres");
		this.list = list;
	}

	public List<Genre> getList() {
		return list;
	}
}
