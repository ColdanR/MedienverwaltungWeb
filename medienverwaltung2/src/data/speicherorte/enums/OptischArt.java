package data.speicherorte.enums;
import interfaces.SelectAble;

public enum OptischArt implements SelectAble {
	MusikCD		("Musik CD",	1),
	DatenCD		("Daten CD",	2),
	VideoCD		("Video CD",	3),
	MusikDVD	("Musik DVD",	4),
	DatenDVD	("Daten DVD",	5),
	VideoDVD	("Video DVD",	6),
	BluRay		("BlueRay",		7);
	
	private String 	bezeichnung;
	private int 	id;
	
	private OptischArt (String bezeichnung, int id){
		this.bezeichnung = bezeichnung;
		this.id = id;
	}

	@Override
	public String getBezeichnung() {
		return bezeichnung;
	}

	@Override
	public int getId() {
		return id;
	}
	public static OptischArt getElementFromId (int id){
		for (OptischArt element : values()){
			if (element.getId() == id){
				return element;
			}
		}
		return null;
	}
}

