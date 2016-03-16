package enums;

import interfaces.SelectAble;


public enum Person implements SelectAble{
	Interpret 				(1, "Interpret"),
	MitwirkenderInterpret	(2, "Mitwirkender Interpret"),
	Autor					(3, "Autor"),
	CoAutor					(4, "Co-Autor"),
	Sprecher				(5, "Sprecher"),
	Regisseur				(6, "Regisseur"),
	Schauspieler			(7, "Schauspieler"),
	Programmierer			(8, "Programmierer"),
	Fotograf				(9, "Fotograf"),
	Modell					(10,"Modell");
		
	private int 	id;
	private String 	bezeichnung;
	
	private Person (int id, String bezeichnung){
		this.id				= id;
		this.bezeichnung 	= bezeichnung;
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
