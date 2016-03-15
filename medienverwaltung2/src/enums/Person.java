package enums;

import interfaces.SelectAble;


public enum Person implements SelectAble{
	Interpret 				("Interpret",				1),
	MitwirkenderInterpret	("Mitwirkender Interpret",	2),
	Autor					("Autor",					3),
	CoAutor					("Co-Autor", 				4),
	Regisseur				("Regisseur",				5),
	Schauspieler			("Schauspieler", 			6),
	Programmierer			("Programmierer", 			7);
	
	private String 	bezeichnung;
	private int 	id;
	
	
	private Person (String bezeichnung, int id){
		this.bezeichnung 	= bezeichnung;
		this.id				= id;
		
	}


	@Override
	public String getBezeichnung() {
		return bezeichnung;
	}


	@Override
	public int getId() {
		return id;
	}
	
	public static Person getElementFromId(int id) {
		for (Person element : values()) {
			if (element.getId() == id) {
				return element;
			}
		}
		return null;
	}	
	
	
}
