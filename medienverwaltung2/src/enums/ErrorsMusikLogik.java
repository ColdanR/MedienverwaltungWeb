package enums;

public enum ErrorsMusikLogik implements ErrorMessage {
	NoKuenstler("Kein K\u00FCnstler \u00FCbergeben."),
	NoTitel("Kein Titel vorhanden."),
	NoAlben("Kein Wert f\u00FCr die Alben erkannt."),
	NoMitwirkende("Kein Wert f\u00FCr die Mitwirkenden erkannt."),
	NoSpeicherworte("Keine Formate erkannt."),
	NotLoaded("Kein Titel geladen.");
	
	private	String	errorMessage	=	"";

	private ErrorsMusikLogik(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

}
