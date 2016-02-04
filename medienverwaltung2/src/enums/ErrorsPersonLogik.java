package enums;


public enum ErrorsPersonLogik implements ErrorMessage {
	KeinNachname ("Kein Nachname \u00FCbergeben."),
	KeinVorname ("Kein Vorname \u00FCbergeben"),
	KeinKuenstlername("Kein K\u00FCnstlername eingegeben")
	;
	
	private String	errorMessage	=	"";
	
	private ErrorsPersonLogik(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

}
