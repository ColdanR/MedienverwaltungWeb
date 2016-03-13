package logic.medien;

import data.medien.Hoerbuch;

public class HoerbuchLogik extends MediumLogik<Hoerbuch> {

	public HoerbuchLogik() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Hoerbuch create() {
		return new Hoerbuch();
	}

}
