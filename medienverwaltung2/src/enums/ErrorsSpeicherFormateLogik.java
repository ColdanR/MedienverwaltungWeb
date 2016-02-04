package enums;

public enum ErrorsSpeicherFormateLogik implements ErrorMessage {
	NoObjectLoaded("Es ist kein Speicherformat geladen."),
	NoTitleIdSet("Es ist kein Titel zum Zuordnen bekannt."),
	UnableToDelete("Das Speicherformat konnte nicht gel\u00F6scht werden."),
	UnableToLoad("Das Speicherformat konnte nicht geladen werden."),
	UnableToWrite("Das Speicherformat konnte nicht geschrieben werden."),
	NoDatentraeger("Es wurde kein Datentr\u00E4ger eingegeben."),
	NoPfad("Es wurde kein Pfad eingegeben."),
	NoFormat("Es wurde kein Format eingegeben."),
	NoQualitaet("Es wurde keine Qualit\u00E4t eingegeben."),
	;
	private	String	errorMessage	=	"";

	private ErrorsSpeicherFormateLogik(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
