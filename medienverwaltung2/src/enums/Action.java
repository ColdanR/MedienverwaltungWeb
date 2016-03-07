package enums;

import interfaces.URIPart;

public enum Action implements URIPart{
	Neuanlage("anlage"),
	Bearbeiten("editieren"),
	Details("details"),
	List("anzeigen");
	
	private	String	uriPart;
	
	private Action(String uriPart) {
		this.uriPart	=	uriPart;
	}
	
	@Override
	public String getURIPart() {
		return uriPart;
	}
	
	public static Action getFromURIPart(String uriPart) {
		for (Action element : values()) {
			if (element.getURIPart().equals(uriPart)) {
				return element;
			}
		}
		return null;
	}
}
