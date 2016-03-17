package logic.medien;

import data.medien.Hoerbuch;
import database.medien.DBHoerbuch;

public class HoerbuchLogik extends MediumLogik<Hoerbuch> {

	public HoerbuchLogik() {
		super(new DBHoerbuch());
	}

	@Override
	public Hoerbuch create() {
		object = new Hoerbuch();
		return object;
	}

}
