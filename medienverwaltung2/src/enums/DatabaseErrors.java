package enums;


public enum DatabaseErrors implements ErrorMessage {
	NoDBAvailable("Datenbank nicht verf\u00FCgbar."),
	UnableToRead("Es konnte nicht aus der Datenbank gelesen werden."),
	UnableToDelete("Der Datensatz konnte nicht aus der Datenbank gel\u00F6scht werden."),
	UnableToDeletePerson("Der Datensatz konnte nicht aus der Datenbank gel\u00F6scht werden. Eventuell sind noch Titel mit der Person verkn\u00FCpft."),
	UnableToWrite("Das Schreiben in die Datenbank ist fehlgeschlagen."),
	UnknownDriver("Unbekannter Treiber in den Einstellungen verwendet.")
	;
	
	String errorMessage = "";
	
	private DatabaseErrors(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
