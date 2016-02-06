package enums.errors;


public enum DatabaseErrors implements ErrorMessage {
	NoDBAvailable("Datenbank nicht verfügbar."),
	UnableToRead("Es konnte nicht aus der Datenbank gelesen werden."),
	UnableToDelete("Der Datensatz konnte nicht aus der Datenbank gelöscht werden."),
	UnableToDeletePerson("Der Datensatz konnte nicht aus der Datenbank gelöscht werden. Eventuell sind noch Titel mit der Person verknüpft."),
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
