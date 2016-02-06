package enums.errors;

public enum ErrorsMusikLogik implements ErrorMessage {
	NoKuenstler("Kein Künstler übergeben."),
	NoTitel("Kein Titel vorhanden."),
	NoAlben("Kein Wert für die Alben erkannt."),
	NoMitwirkende("Kein Wert für die Mitwirkenden erkannt."),
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
