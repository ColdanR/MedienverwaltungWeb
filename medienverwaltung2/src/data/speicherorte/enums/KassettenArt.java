package data.speicherorte.enums;

import interfaces.SelectAble;


public enum KassettenArt implements SelectAble {
	MusikKassette	("Musikkassette", 1),
	VideoKassette	("Videokassette", 2);
		
	private String 	bezeichnung;
	private int 	id;
	
	private KassettenArt(String bezeichnung, int id) {
		this.id 		 = id;
		this.bezeichnung = bezeichnung;
	}
	
	
	@Override
	public String getBezeichnung() {
		return bezeichnung;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public static KassettenArt getElementFromId(int id){
		for (KassettenArt element : values()){
			if (element.getId() == id){
				return element;
			}
		}
		return null;
	}
	
	
}
