package data.medien;

import enums.Mediengruppe;

public class Musik extends Medium {
	private	boolean	live;
	
	public Musik() {
		super(Mediengruppe.Musik);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
}
