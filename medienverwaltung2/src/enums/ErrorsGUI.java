package enums;

public enum ErrorsGUI implements ErrorMessage {
	NoSelection("Es wurde kein Element der Liste ausgew\u00E4hlt."),
	NoDateFormat("Das Datum entspricht nicht dem Format TT.MM.JJJJ"),
	NoDataAvailable("Keine Daten verf\u00FCgbar."),
	;

	private	String errorMessage;
	
	private ErrorsGUI(String error) {
		this.errorMessage = error;
	}
	
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

}
