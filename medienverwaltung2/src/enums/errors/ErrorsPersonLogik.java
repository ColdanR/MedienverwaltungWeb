package enums.errors;


public enum ErrorsPersonLogik implements ErrorMessage {
	KeinNachname ("Kein Nachname übergeben."),
	KeinVorname ("Kein Vorname übergeben"),
	KeinKuenstlername("Kein Künstlername eingegeben")
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
