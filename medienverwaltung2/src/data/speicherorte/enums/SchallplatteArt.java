package data.speicherorte.enums;

import interfaces.SelectAble;

public enum SchallplatteArt implements SelectAble{
	Single	("Single",	1),
	EP		("EP",		2),
	LP		("LP",		3);
	
	private String 	bezeichnung;
	private int 	id;
	
	private SchallplatteArt (String bezeichnung, int id){
		this.bezeichnung = bezeichnung;
		this.id			 = id;
	}

	@Override
	public String getBezeichnung() {
		return bezeichnung;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public static SchallplatteArt getElementFromId (int id){
		for (SchallplatteArt element : values()){
			if(element.getId() == id){
				return element;
			}
		}
		return null;
	}
}
