package enums.errors;


public enum ErrorsPersonLogik implements ErrorMessage {
	KeinNachname ("Kein Nachname �bergeben."),
	KeinVorname ("Kein Vorname �bergeben"),
	KeinKuenstlername("Kein K�nstlername eingegeben")
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
