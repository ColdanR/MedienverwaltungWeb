package gui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.formate.Analog;
import data.formate.Digital;
import data.formate.Formate;
import data.formate.validator.AnalogValidator;
import data.formate.validator.DigitalValidator;
import data.speicherorte.Speicherort;
import data.speicherorte.enums.BuchArt;
import data.speicherorte.enums.KassettenArt;
import data.speicherorte.enums.OptischArt;
import data.speicherorte.enums.SchallplatteArt;
import enums.Action;
import enums.Format;
import enums.Mediengruppe;
import enums.SpeicherortArt;
import gui.Controller;
import gui.dto.FehlerDTO;
import gui.dto.formate.AnalogFormateDTO;
import gui.dto.formate.DigitalFormateDTO;
import gui.dto.formate.FormateDTO;
import gui.dto.speicherorte.SpeicherorteDTO;
import logic.formate.FormateLogik;
import logic.medien.BuchLogik;
import logic.speicherorte.SpeicherorteLogik;

public class SpeicherOrteController extends Controller {
	private static Pattern URI_PATTERN = Pattern.compile("/speicherorte/(" +
			Arrays.asList(Action.values()).stream()
			.filter(action -> {return action != Action.Details && action != Action.List;})
			.map(Action::getURIPart)
			.collect(Collectors.joining("|")) +
			").html");
	private	static	Logger	LOGGER	=	LogManager.getLogger(FormateController.class);
	
	public SpeicherOrteController() throws PatternSyntaxException {
		super(URI_PATTERN.pattern());
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String			uri		=	request.getServletPath();
		if (request.getPathInfo() != null) {
			uri += request.getPathInfo();
		}
		Matcher			matcher		=	URI_PATTERN.matcher(uri);
		matcher.matches();
		String			actionPart	=	matcher.group(1);
		Action			action		=	Action.getFromURIPart(actionPart);
		LOGGER.debug("Action: {}", action);
		switch (action) {
		case Bearbeiten:
			bearbeiten(request, response);
			break;
		case Loeschen:
			loeschen(request, response);
			break;
		case Neuanlage:
			neuanlage(request, response);
			break;
		default:
			break;
		}
	}

	private void neuanlage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("idFormat") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine Format ID beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMedium") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Kein Medium Type beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMediumType") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Kein Medium Type beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idFormatType") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Kein Format Type beim Aufruf zum Löschen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int						idSpeicherFormat	=	Integer.parseInt(request.getParameter("idFormat"));
				int						idMedium			=	Integer.parseInt(request.getParameter("idMedium"));
				int						idMediumType		=	Integer.parseInt(request.getParameter("idMediumType"));
				int						idFormatType		=	Integer.parseInt(request.getParameter("idFormatType"));
				Mediengruppe			typeMedium			=	Mediengruppe.getElementFromId(idMediumType);
				Format					typeFormat			=	Format.getElementFromId(idFormatType);
				if (typeMedium == null) {
					FehlerDTO dto = new FehlerDTO();
					dto.addError("Medium Type konnte nicht bestimmt werden.");
					forward(request, response, dto, "404.jsp");
				} else if (typeFormat == null) {
					FehlerDTO dto = new FehlerDTO();
					dto.addError("Format Type konnte nicht bestimmt werden.");
					forward(request, response, dto, "404.jsp");
				} else {
					if (request.getParameter("send") != null) {
						if (request.getParameter("speicherortArt") == null || request.getParameter("speicherortArt").trim().length() == 0) {
							SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
							dto.addError("Keine Art für den Speicherort angegeben.");
							forward(request, response, dto, "speicherortEingabe.jsp");
						} else {
							try {
								SpeicherortArt	speicherOrtArt = SpeicherortArt.getElementFromId(Integer.parseInt(request.getParameter("speicherortArt")));
								if (speicherOrtArt != null) {
									if (request.getParameter("bez") == null || request.getParameter("bez").trim().length() == 0) {
										SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
										dto.addError("Keine Bezeichnung für den Speicherort angegeben.");
										forward(request, response, dto, "speicherortEingabe.jsp");
									} else {
										String	bezeichnung	=	request.getParameter("bez");
										String	bemerkung	=	request.getParameter("bemerkung");
										if (bemerkung == null) {
											bemerkung = "";
										}
										SpeicherorteLogik<Speicherort>	logik	=	null;
										switch (speicherOrtArt) {
										case Buch:
											if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
												SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
												dto.addError("Keine Art für das Buch ausgewählt.");
												forward(request, response, dto, "speicherortEingabe.jsp");
											} else {
												try {
													BuchArt	art	=	BuchArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
													if (art != null) {
														String zustandBuch = request.getParameter("zustand");
														if (zustandBuch == null) {
															zustandBuch = "";
														}
														// Logik erzeugen und zuweisen
													} else {
														SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
														dto.addError("Ungültige Art für das Buch ausgewählt.");
														forward(request, response, dto, "speicherortEingabe.jsp");
													}
												} catch (NumberFormatException e) {
													SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
													dto.addError("Ungültige Art für das Buch ausgewählt.");
													forward(request, response, dto, "speicherortEingabe.jsp");
												}
											}
											break;
										case Dia:
											String zustandDia = request.getParameter("zustand");
											if (zustandDia == null) {
												zustandDia = "";
											}
											// Logik erzeugen und zuweisen
											break;
										case Festplatte:
											// Logik erzeugen und zuweisen
											break;
										case Kassette:
											if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
												SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
												dto.addError("Keine Art für das Buch ausgewählt.");
												forward(request, response, dto, "speicherortEingabe.jsp");
											} else {
												try {
													KassettenArt	art	=	KassettenArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
													if (art != null) {
														String zustandKassette = request.getParameter("zustand");
														if (zustandKassette == null) {
															zustandKassette = "";
														}
														// Logik erzeugen und zuweisen
													} else {
														SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
														dto.addError("Ungültige Art für das Buch ausgewählt.");
														forward(request, response, dto, "speicherortEingabe.jsp");
													}
												} catch (NumberFormatException e) {
													SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
													dto.addError("Ungültige Art für das Buch ausgewählt.");
													forward(request, response, dto, "speicherortEingabe.jsp");
												}
											}
											break;
										case Optisch:
											if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
												SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
												dto.addError("Keine Art für das Buch ausgewählt.");
												forward(request, response, dto, "speicherortEingabe.jsp");
											} else {
												try {
													OptischArt	art	=	OptischArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
													if (art != null) {
														String zustandOptisch = request.getParameter("zustand");
														if (zustandOptisch == null) {
															zustandOptisch = "";
														}
														// Logik erzeugen und zuweisen
													} else {
														SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
														dto.addError("Ungültige Art für das Buch ausgewählt.");
														forward(request, response, dto, "speicherortEingabe.jsp");
													}
												} catch (NumberFormatException e) {
													SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
													dto.addError("Ungültige Art für das Buch ausgewählt.");
													forward(request, response, dto, "speicherortEingabe.jsp");
												}
											}
											break;
										case Schallplatte:
											if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
												SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
												dto.addError("Keine Art für das Buch ausgewählt.");
												forward(request, response, dto, "speicherortEingabe.jsp");
											} else {
												try {
													SchallplatteArt	art	=	SchallplatteArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
													if (art != null) {
														String zustandSchallplatte = request.getParameter("zustand");
														if (zustandSchallplatte == null) {
															zustandSchallplatte = "";
														}
														// Logik erzeugen und zuweisen
													} else {
														SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
														dto.addError("Ungültige Art für das Buch ausgewählt.");
														forward(request, response, dto, "speicherortEingabe.jsp");
													}
												} catch (NumberFormatException e) {
													SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
													dto.addError("Ungültige Art für das Buch ausgewählt.");
													forward(request, response, dto, "speicherortEingabe.jsp");
												}
											}
											break;
										default:
											FehlerDTO dto = new FehlerDTO();
											dto.addError("Unbekannte Speicherort Art");
											forward(request, response, dto, "404.jsp");
											return;
										}
										// Validator erzeugen
										if (logik.write()) {
											// Redirect auf Detailseite
										} else {
											SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
											logik.getErrors().stream().forEach(error -> {
												dto.addError(error);;
											});
											forward(request, response, dto, "speicherortEingabe.jsp");
										}
									}
								} else {
									SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
									dto.addError("Speicherort Art konnte nicht bestimmt werden.");
									forward(request, response, dto, "speicherortEingabe.jsp");
								}
							} catch (NumberFormatException e) {
								SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
								dto.addError("Speicherort Art konnte nicht bestimmt werden.");
								forward(request, response, dto, "speicherortEingabe.jsp");
							}
						}
					} else {
						SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
						forward(request, response, dto, "speicherortEingabe.jsp");
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID beim Aufruf zum Löschen des Formates gefunden.");
				forward(request, response, dto, "404.jsp");
			}
		}
	}

	private void bearbeiten(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO
	}

	private void loeschen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO
	}
}
