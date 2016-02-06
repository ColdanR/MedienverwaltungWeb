package enums.errors;

public enum ErrorsMusikLogik implements ErrorMessage {
	NoKuenstler("Kein K�nstler �bergeben."),
	NoTitel("Kein Titel vorhanden."),
	NoAlben("Kein Wert f�r die Alben erkannt."),
	NoMitwirkende("Kein Wert f�r die Mitwirkenden erkannt."),
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
