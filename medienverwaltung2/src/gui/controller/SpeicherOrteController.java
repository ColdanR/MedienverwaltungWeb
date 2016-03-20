package gui.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.speicherorte.Buch;
import data.speicherorte.Dia;
import data.speicherorte.Festplatte;
import data.speicherorte.Kassette;
import data.speicherorte.Optisch;
import data.speicherorte.Schallplatte;
import data.speicherorte.Speicherort;
import data.speicherorte.enums.BuchArt;
import data.speicherorte.enums.KassettenArt;
import data.speicherorte.enums.OptischArt;
import data.speicherorte.enums.SchallplatteArt;
import data.speicherorte.validator.SpeicherOrteValidator;
import enums.Action;
import enums.Format;
import enums.Mediengruppe;
import enums.SpeicherortArt;
import gui.Controller;
import gui.dto.FehlerDTO;
import gui.dto.speicherorte.SpeicherorteDTO;
import logic.speicherorte.BuchLogik;
import logic.speicherorte.DiaLogik;
import logic.speicherorte.FestplatteLogik;
import logic.speicherorte.KassetteLogik;
import logic.speicherorte.OptischLogik;
import logic.speicherorte.SchallplatteLogik;
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
			dto.addError("Keine Format ID beim Aufruf zum Anlagen des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMedium") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Kein Medium ID beim Aufruf zum Anlagen des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMediumType") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Kein Medium Typ beim Aufruf zum Anlegen des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idFormatType") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Kein Format Typ beim Aufruf zum Anlegen des Speicherortes gefunden.");
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
					dto.addError("Medium Typ konnte nicht bestimmt werden.");
					forward(request, response, dto, "404.jsp");
				} else if (typeFormat == null) {
					FehlerDTO dto = new FehlerDTO();
					dto.addError("Format Typ konnte nicht bestimmt werden.");
					forward(request, response, dto, "404.jsp");
				} else {
					if (request.getParameter("send") != null) {
						SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
						String	bezeichnung	=	request.getParameter("lagerort");
						String	bemerkung	=	request.getParameter("bemerkung");
						String	zustand		=	request.getParameter("zustand");
						if (bemerkung == null) {
							bemerkung = "";
						}
						if (zustand == null) {
							zustand = "";
						}
						dto.setBemerkung(bemerkung);
						dto.setBezeichnung(bezeichnung);
						dto.setZustand(zustand);
						if (request.getParameter("speicherortArt") == null || request.getParameter("speicherortArt").trim().length() == 0) {
							dto.addError("Keine Art für den Speicherort angegeben.");
							forward(request, response, dto, "speicherortEingabe.jsp");
						} else {
							try {
								SpeicherortArt	speicherOrtArt = SpeicherortArt.getElementFromId(Integer.parseInt(request.getParameter("speicherortArt")));
								if (speicherOrtArt != null) {
									dto.setSelected(speicherOrtArt);
									SpeicherorteLogik<?>	logik	=	null;
									switch (speicherOrtArt) {
									case Buch:
										dto.setArtOptions(Arrays.asList(BuchArt.values()));
										if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
											dto.addError("Keine Art für das Buch ausgewählt.");
											forward(request, response, dto, "speicherortEingabe.jsp");
											return;
										} else {
											try {
												BuchArt	art	=	BuchArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
												if (art != null) {
													// Logik erzeugen und zuweisen
													logik = new BuchLogik(idSpeicherFormat);
													Buch buch = (Buch) logik.create();
													buch.setArt(art);
													buch.setBemerkung(bemerkung);
													buch.setLagerOrt(bezeichnung);
													buch.setZustand(zustand);
												} else {
													dto.addError("Ungültige Art für das Buch ausgewählt.");
													forward(request, response, dto, "speicherortEingabe.jsp");
													return;
												}
											} catch (NumberFormatException e) {
												dto.addError("Ungültige Art für das Buch ausgewählt.");
												forward(request, response, dto, "speicherortEingabe.jsp");
												return;
											}
										}
										break;
									case Dia:
										// Logik erzeugen und zuweisen
										logik = new DiaLogik(idSpeicherFormat);
										Dia	dia	=	(Dia) logik.create();
										dia.setBemerkung(bemerkung);
										dia.setLagerOrt(bezeichnung);
										dia.setZustand(zustand);
										break;
									case Festplatte:
										// Logik erzeugen und zuweisen
										logik = new FestplatteLogik(idSpeicherFormat);
										Festplatte festplatte = (Festplatte) logik.create();
										festplatte.setBemerkung(bemerkung);
										festplatte.setLagerOrt(bezeichnung);
										break;
									case Kassette:
										dto.setArtOptions(Arrays.asList(KassettenArt.values()));
										if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
											dto.addError("Keine Art für die Kassette ausgewählt.");
											forward(request, response, dto, "speicherortEingabe.jsp");
											return;
										} else {
											try {
												KassettenArt	art	=	KassettenArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
												if (art != null) {
													// Logik erzeugen und zuweisen
													logik = new KassetteLogik(idSpeicherFormat);
													Kassette kassette = (Kassette) logik.create();
													kassette.setArt(art);
													kassette.setBemerkung(bemerkung);
													kassette.setLagerOrt(bezeichnung);
													kassette.setZustand(zustand);
												} else {
													dto.addError("Ungültige Art für die Kassette ausgewählt.");
													forward(request, response, dto, "speicherortEingabe.jsp");
													return;
												}
											} catch (NumberFormatException e) {
												dto.addError("Ungültige Art für die Kassette ausgewählt.");
												forward(request, response, dto, "speicherortEingabe.jsp");
												return;
											}
										}
										break;
									case Optisch:
										dto.setArtOptions(Arrays.asList(OptischArt.values()));
										if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
											dto.addError("Keine Art für den optischen Datenträger ausgewählt.");
											forward(request, response, dto, "speicherortEingabe.jsp");
											return;
										} else {
											try {
												OptischArt	art	=	OptischArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
												if (art != null) {
													// Logik erzeugen und zuweisen
													logik = new OptischLogik(idSpeicherFormat);
													Optisch optisch = (Optisch) logik.create();
													optisch.setArt(art);
													optisch.setBemerkung(bemerkung);
													optisch.setLagerOrt(bezeichnung);
													optisch.setZustand(zustand);
												} else {
													dto.addError("Ungültige Art für den optischen Datenträger ausgewählt.");
													forward(request, response, dto, "speicherortEingabe.jsp");
													return;
												}
											} catch (NumberFormatException e) {
												dto.addError("Ungültige Art für den optischen Datenträger ausgewählt.");
												forward(request, response, dto, "speicherortEingabe.jsp");
												return;
											}
										}
										break;
									case Schallplatte:
										dto.setArtOptions(Arrays.asList(SchallplatteArt.values()));
										if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
											dto.addError("Keine Art für die Schallplatte ausgewählt.");
											forward(request, response, dto, "speicherortEingabe.jsp");
											return;
										} else {
											try {
												SchallplatteArt	art	=	SchallplatteArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
												if (art != null) {
													// Logik erzeugen und zuweisen
													logik = new SchallplatteLogik(idSpeicherFormat);
													Schallplatte schallplatte = (Schallplatte) logik.create();
													schallplatte.setArt(art);
													schallplatte.setBemerkung(bemerkung);
													schallplatte.setLagerOrt(bezeichnung);
													schallplatte.setZustand(zustand);
												} else {
													dto.addError("Ungültige Art für die Schallplatte ausgewählt.");
													forward(request, response, dto, "speicherortEingabe.jsp");
													return;
												}
											} catch (NumberFormatException e) {
												dto.addError("Ungültige Art für die Schallplatte ausgewählt.");
												forward(request, response, dto, "speicherortEingabe.jsp");
												return;
											}
										}
										break;
									default:
										FehlerDTO dtoFehler = new FehlerDTO();
										dtoFehler.addError("Unbekannte Speicherort Art");
										forward(request, response, dtoFehler, "404.jsp");
										return;
									}
									// Validator erzeugen
									SpeicherOrteValidator validator = new SpeicherOrteValidator();
									if (validator.validate(logik.getObject()) && logik.write()) {
										String uri = "medium/" + typeMedium.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + idMedium;
										redirect(request, response, uri);
									} else {
										dto.addErrors(validator.getErrors());
										dto.addErrors(logik.getErrors());
										forward(request, response, dto, "speicherortEingabe.jsp");
									}
								} else {
									dto.addError("Speicherort Art konnte nicht bestimmt werden.");
									forward(request, response, dto, "speicherortEingabe.jsp");
								}
							} catch (NumberFormatException e) {
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
				FehlerDTO dtoFehler = new FehlerDTO();
				dtoFehler.addError("Fehlerhafte ID beim Aufruf zum Löschen des Formates gefunden.");
				forward(request, response, dtoFehler, "404.jsp");
			}
		}
	}

	private void bearbeiten(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("idSpeicherortArt") == null || request.getParameter("idSpeicherortArt").trim().length() == 0) {
			FehlerDTO	dto	=	new FehlerDTO();
			dto.addError("Keine Art für den Speicherort angegeben.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("id") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ID beim Aufruf zum Bearbeiten des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idFormat") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine Format ID beim Aufruf zum Bearbeiten des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMedium") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Kein Medium ID beim Aufruf zum Bearbeiten des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMediumType") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Kein Medium Typ beim Aufruf zum Bearbeiten des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idFormatType") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Kein Format Typ beim Aufruf zum Bearbeiten des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int						id					=	Integer.parseInt(request.getParameter("id"));
				int						idSpeicherFormat	=	Integer.parseInt(request.getParameter("idFormat"));
				int						idMedium			=	Integer.parseInt(request.getParameter("idMedium"));
				int						idMediumType		=	Integer.parseInt(request.getParameter("idMediumType"));
				int						idFormatType		=	Integer.parseInt(request.getParameter("idFormatType"));
				Mediengruppe			typeMedium			=	Mediengruppe.getElementFromId(idMediumType);
				Format					typeFormat			=	Format.getElementFromId(idFormatType);
				SpeicherortArt			speicherOrtArt		=	SpeicherortArt.getElementFromId(Integer.parseInt(request.getParameter("idSpeicherortArt")));
				if (speicherOrtArt == null) {
					FehlerDTO dto = new FehlerDTO();
					dto.addError("Speicherort Typ konnte nicht bestimmt werden.");
					forward(request, response, dto, "404.jsp");
				} else if (typeMedium == null) {
					FehlerDTO dto = new FehlerDTO();
					dto.addError("Medium Typ konnte nicht bestimmt werden.");
					forward(request, response, dto, "404.jsp");
				} else if (typeFormat == null) {
					FehlerDTO dto = new FehlerDTO();
					dto.addError("Format Typ konnte nicht bestimmt werden.");
					forward(request, response, dto, "404.jsp");
				} else {
					SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort editieren", id, speicherOrtArt, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
					if (request.getParameter("send") != null) {
						try {
							String	bezeichnung	=	request.getParameter("lagerort");
							String	bemerkung	=	request.getParameter("bemerkung");
							String	zustand		=	request.getParameter("zustand");
							if (bemerkung == null) {
								bemerkung = "";
							}
							if (zustand == null) {
								zustand = "";
							}
							dto.setBemerkung(bemerkung);
							dto.setBezeichnung(bezeichnung);
							dto.setZustand(zustand);
							SpeicherorteLogik<?>	logik	=	null;
							switch (speicherOrtArt) {
							case Buch:
								dto.setArtOptions(Arrays.asList(BuchArt.values()));
								if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
									dto.addError("Keine Art für das Buch ausgewählt.");
									forward(request, response, dto, "speicherortEingabe.jsp");
									return;
								} else {
									try {
										BuchArt	art	=	BuchArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
										if (art != null) {
											// Logik erzeugen und zuweisen
											logik = new BuchLogik(idSpeicherFormat);
											if (logik.load(id)) {
												Buch buch = (Buch) logik.getObject();
												buch.setArt(art);
												buch.setBemerkung(bemerkung);
												buch.setLagerOrt(bezeichnung);
												buch.setZustand(zustand);
											} else {
												FehlerDTO dtoFehler = new FehlerDTO();
												dtoFehler.addErrors(logik.getErrors());
												forward(request, response, dtoFehler, "404.jsp");
												return;
											}
										} else {
											dto.addError("Ungültige Art für das Buch ausgewählt.");
											forward(request, response, dto, "speicherortEingabe.jsp");
											return;
										}
									} catch (NumberFormatException e) {
										dto.addError("Ungültige Art für das Buch ausgewählt.");
										forward(request, response, dto, "speicherortEingabe.jsp");
										return;
									}
								}
								break;
							case Dia:
								// Logik erzeugen und zuweisen
								logik = new DiaLogik(idSpeicherFormat);
								if (logik.load(id)) {
									Dia	dia	=	(Dia) logik.getObject();
									dia.setBemerkung(bemerkung);
									dia.setLagerOrt(bezeichnung);
									dia.setZustand(zustand);
								} else {
									FehlerDTO dtoFehler = new FehlerDTO();
									dtoFehler.addErrors(logik.getErrors());
									forward(request, response, dtoFehler, "404.jsp");
									return;
								}
								break;
							case Festplatte:
								// Logik erzeugen und zuweisen
								logik = new FestplatteLogik(idSpeicherFormat);
								if (logik.load(id)) {
									Festplatte festplatte = (Festplatte) logik.getObject();
									festplatte.setBemerkung(bemerkung);
									festplatte.setLagerOrt(bezeichnung);
								} else {
									FehlerDTO dtoFehler = new FehlerDTO();
									dtoFehler.addErrors(logik.getErrors());
									forward(request, response, dtoFehler, "404.jsp");
									return;
								}
								break;
							case Kassette:
								dto.setArtOptions(Arrays.asList(KassettenArt.values()));
								if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
									dto.addError("Keine Art für die Kassette ausgewählt.");
									forward(request, response, dto, "speicherortEingabe.jsp");
									return;
								} else {
									try {
										KassettenArt	art	=	KassettenArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
										if (art != null) {
											// Logik erzeugen und zuweisen
											logik = new KassetteLogik(idSpeicherFormat);
											if (logik.load(id)) {
												Kassette kassette = (Kassette) logik.getObject();
												kassette.setArt(art);
												kassette.setBemerkung(bemerkung);
												kassette.setLagerOrt(bezeichnung);
												kassette.setZustand(zustand);
											} else {
												FehlerDTO dtoFehler = new FehlerDTO();
												dtoFehler.addErrors(logik.getErrors());
												forward(request, response, dtoFehler, "404.jsp");
												return;
											}
										} else {
											dto.addError("Ungültige Art für die Kassette ausgewählt.");
											forward(request, response, dto, "speicherortEingabe.jsp");
											return;
										}
									} catch (NumberFormatException e) {
										dto.addError("Ungültige Art für die Kassette ausgewählt.");
										forward(request, response, dto, "speicherortEingabe.jsp");
										return;
									}
								}
								break;
							case Optisch:
								dto.setArtOptions(Arrays.asList(OptischArt.values()));
								if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
									dto.addError("Keine Art für den optischen Datenträger ausgewählt.");
									forward(request, response, dto, "speicherortEingabe.jsp");
									return;
								} else {
									try {
										OptischArt	art	=	OptischArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
										if (art != null) {
											// Logik erzeugen und zuweisen
											logik = new OptischLogik(idSpeicherFormat);
											if (logik.load(id)) {
												Optisch optisch = (Optisch) logik.getObject();
												optisch.setArt(art);
												optisch.setBemerkung(bemerkung);
												optisch.setLagerOrt(bezeichnung);
												optisch.setZustand(zustand);
											} else {
												FehlerDTO dtoFehler = new FehlerDTO();
												dtoFehler.addErrors(logik.getErrors());
												forward(request, response, dtoFehler, "404.jsp");
												return;
											}
										} else {
											dto.addError("Ungültige Art für den optischen Datenträger ausgewählt.");
											forward(request, response, dto, "speicherortEingabe.jsp");
											return;
										}
									} catch (NumberFormatException e) {
										dto.addError("Ungültige Art für den optischen Datenträger ausgewählt.");
										forward(request, response, dto, "speicherortEingabe.jsp");
										return;
									}
								}
								break;
							case Schallplatte:
								dto.setArtOptions(Arrays.asList(SchallplatteArt.values()));
								if (request.getParameter("art") == null || request.getParameter("art").trim().length() == 0) {
									dto.addError("Keine Art für die Schallplatte ausgewählt.");
									forward(request, response, dto, "speicherortEingabe.jsp");
									return;
								} else {
									try {
										SchallplatteArt	art	=	SchallplatteArt.getElementFromId(Integer.parseInt(request.getParameter("art")));
										if (art != null) {
											// Logik erzeugen und zuweisen
											logik = new SchallplatteLogik(idSpeicherFormat);
											if(logik.load(id)) {
												Schallplatte schallplatte = (Schallplatte) logik.getObject();
												schallplatte.setArt(art);
												schallplatte.setBemerkung(bemerkung);
												schallplatte.setLagerOrt(bezeichnung);
												schallplatte.setZustand(zustand);
											} else {
												FehlerDTO dtoFehler = new FehlerDTO();
												dtoFehler.addErrors(logik.getErrors());
												forward(request, response, dtoFehler, "404.jsp");
												return;
											}
										} else {
											dto.addError("Ungültige Art für die Schallplatte ausgewählt.");
											forward(request, response, dto, "speicherortEingabe.jsp");
											return;
										}
									} catch (NumberFormatException e) {
										dto.addError("Ungültige Art für die Schallplatte ausgewählt.");
										forward(request, response, dto, "speicherortEingabe.jsp");
										return;
									}
								}
								break;
							default:
								FehlerDTO dtoFehler = new FehlerDTO();
								dtoFehler.addError("Unbekannte Speicherort Art");
								forward(request, response, dtoFehler, "404.jsp");
								return;
							}
							// Validator erzeugen
							SpeicherOrteValidator validator = new SpeicherOrteValidator();
							if (validator.validate(logik.getObject()) && logik.write()) {
								String uri = "medium/" + typeMedium.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + idMedium;
								redirect(request, response, uri);
							} else {
								dto.addErrors(logik.getErrors());
								Speicherort	speicherort	=	logik.getObject();
								dto.setBemerkung(speicherort.getBemerkung());
								dto.setBezeichnung(speicherort.getLagerOrt());
								switch (speicherOrtArt) {
								case Buch:
									Buch buch = (Buch) logik.getObject();
									dto.setArtSelected(buch.getArt());
									dto.setArtOptions(Arrays.asList(BuchArt.values()));
									dto.setZustand(buch.getZustand());
									break;
								case Dia:
									Dia dia = (Dia) logik.getObject();
									dto.setZustand(dia.getZustand());
									break;
								case Festplatte:
									break;
								case Kassette:
									Kassette kassette = (Kassette) logik.getObject();
									dto.setArtSelected(kassette.getArt());
									dto.setArtOptions(Arrays.asList(KassettenArt.values()));
									dto.setZustand(kassette.getZustand());
									break;
								case Optisch:
									Optisch	optisch = (Optisch) logik.getObject();
									dto.setArtSelected(optisch.getArt());
									dto.setArtOptions(Arrays.asList(OptischArt.values()));
									dto.setZustand(optisch.getZustand());
									break;
								case Schallplatte:
									Schallplatte schallplatte = (Schallplatte) logik.getObject();
									dto.setArtSelected(schallplatte.getArt());
									dto.setArtOptions(Arrays.asList(SchallplatteArt.values()));
									dto.setZustand(schallplatte.getZustand());
									break;
								}
								forward(request, response, dto, "speicherortEingabe.jsp");
							}
						} catch (NumberFormatException e) {
							dto.addError("Speicherort Art konnte nicht bestimmt werden.");
							forward(request, response, dto, "speicherortEingabe.jsp");
						}
					} else {
						switch (speicherOrtArt) {
						case Buch:
							// Logik erzeugen und zuweisen
							BuchLogik logikBuch = new BuchLogik(idSpeicherFormat);
							if (logikBuch.load(id)) {
								Buch buch = logikBuch.getObject();
								dto.setBemerkung(buch.getBemerkung());
								dto.setBezeichnung(buch.getLagerOrt());
								dto.setArtSelected(buch.getArt());
								dto.setArtOptions(Arrays.asList(BuchArt.values()));
								dto.setZustand(buch.getZustand());
							} else {
								FehlerDTO dtoFehler = new FehlerDTO();
								dtoFehler.addErrors(logikBuch.getErrors());
								forward(request, response, dtoFehler, "404.jsp");
								return;
							}
							break;
						case Dia:
							// Logik erzeugen und zuweisen
							DiaLogik logikDia = new DiaLogik(idSpeicherFormat);
							if (logikDia.load(id)) {
								Dia dia = logikDia.getObject();
								dto.setBemerkung(dia.getBemerkung());
								dto.setBezeichnung(dia.getLagerOrt());
								dto.setZustand(dia.getZustand());
							} else {
								FehlerDTO dtoFehler = new FehlerDTO();
								dtoFehler.addErrors(logikDia.getErrors());
								forward(request, response, dtoFehler, "404.jsp");
								return;
							}
							break;
						case Festplatte:
							// Logik erzeugen und zuweisen
							FestplatteLogik logikFestplatte = new FestplatteLogik(idSpeicherFormat);
							if (logikFestplatte.load(id)) {
								Festplatte	item	=	logikFestplatte.getObject();
								dto.setBemerkung(item.getBemerkung());
								dto.setBezeichnung(item.getLagerOrt());
							} else {
								FehlerDTO dtoFehler = new FehlerDTO();
								dtoFehler.addErrors(logikFestplatte.getErrors());
								forward(request, response, dtoFehler, "404.jsp");
								return;
							}
							break;
						case Kassette:
							// Logik erzeugen und zuweisen
							KassetteLogik logikKassette = new KassetteLogik(idSpeicherFormat);
							if (logikKassette.load(id)) {
								Kassette item = logikKassette.getObject();
								dto.setBemerkung(item.getBemerkung());
								dto.setBezeichnung(item.getLagerOrt());
								dto.setArtSelected(item.getArt());
								dto.setArtOptions(Arrays.asList(KassettenArt.values()));
								dto.setZustand(item.getZustand());
							} else {
								FehlerDTO dtoFehler = new FehlerDTO();
								dtoFehler.addErrors(logikKassette.getErrors());
								forward(request, response, dtoFehler, "404.jsp");
								return;
							}
							break;
						case Optisch:
							// Logik erzeugen und zuweisen
							OptischLogik logikOptisch = new OptischLogik(idSpeicherFormat);
							if (logikOptisch.load(id)) {
								Optisch item = logikOptisch.getObject();
								dto.setBemerkung(item.getBemerkung());
								dto.setBezeichnung(item.getLagerOrt());
								dto.setArtSelected(item.getArt());
								dto.setArtOptions(Arrays.asList(OptischArt.values()));
								dto.setZustand(item.getZustand());
							} else {
								FehlerDTO dtoFehler = new FehlerDTO();
								dtoFehler.addErrors(logikOptisch.getErrors());
								forward(request, response, dtoFehler, "404.jsp");
								return;
							}
							break;
						case Schallplatte:
							SchallplatteLogik logikSchallplatte = new SchallplatteLogik(idSpeicherFormat);
							if(logikSchallplatte.load(id)) {
								Schallplatte item = logikSchallplatte.getObject();
								dto.setBemerkung(item.getBemerkung());
								dto.setBezeichnung(item.getLagerOrt());
								dto.setArtSelected(item.getArt());
								dto.setArtOptions(Arrays.asList(SchallplatteArt.values()));
								dto.setZustand(item.getZustand());
							} else {
								FehlerDTO dtoFehler = new FehlerDTO();
								dtoFehler.addErrors(logikSchallplatte.getErrors());
								forward(request, response, dtoFehler, "404.jsp");
								return;
							}
							break;
						}
						forward(request, response, dto, "speicherortEingabe.jsp");
					}
				}
			} catch (NumberFormatException e) {
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID beim Aufruf zum Löschen des Formates gefunden.");
				forward(request, response, dto, "404.jsp");
			}
		}
	}

	private void loeschen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("idSpeicherortArt") == null || request.getParameter("idSpeicherortArt").trim().length() == 0) {
			FehlerDTO	dto	=	new FehlerDTO();
			dto.addError("Keine Art für den Speicherort angegeben.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("id") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ID beim Aufruf zum Löschen des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMedium") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum Löschen des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMediumType") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum Löschen des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idFormat") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine Format ID beim Aufruf zum Löschen des Speicherortes gefunden.");
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int 					id					=	Integer.parseInt(request.getParameter("id"));
				int						mediumId			=	Integer.parseInt(request.getParameter("idMedium"));
				int						idSpeicherFormat	=	Integer.parseInt(request.getParameter("idFormat"));
				Mediengruppe			type				=	Mediengruppe.getElementFromId(Integer.parseInt(request.getParameter("idMediumType")));
				SpeicherortArt			speicherOrtArt		=	SpeicherortArt.getElementFromId(Integer.parseInt(request.getParameter("idSpeicherortArt")));
				SpeicherorteLogik<?>	logik				=	null;
				if (speicherOrtArt == null) {
					FehlerDTO dto = new FehlerDTO();
					dto.addError("Keine Speicherortart beim Aufruf zum Löschen des Speicherortes gefunden.");
					forward(request, response, dto, "404.jsp");
				} else if (type == null) {
					FehlerDTO dto = new FehlerDTO();
					dto.addError("Kein Medium Typ beim Aufruf zum Löschen des Speicherortes gefunden.");
					forward(request, response, dto, "404.jsp");
				} else {
					switch (speicherOrtArt) {
					case Buch:
						logik = new BuchLogik(idSpeicherFormat);
						break;
					case Dia:
						logik = new DiaLogik(idSpeicherFormat);
						break;
					case Festplatte:
						logik = new FestplatteLogik(idSpeicherFormat);
						break;
					case Kassette:
						logik = new KassetteLogik(idSpeicherFormat);
						break;
					case Optisch:
						logik = new OptischLogik(idSpeicherFormat);
						break;
					case Schallplatte:
						logik = new SchallplatteLogik(idSpeicherFormat);
						break;
					default:
						FehlerDTO dto = new FehlerDTO();
						dto.addError("Kein Speicherort Art beim Aufruf zum Löschen des Speicherortes gefunden.");
						forward(request, response, dto, "404.jsp");
						return;
					}
					if (logik.load(id)) {
						if (logik.delete()) {
							redirect(request, response, "medium/" + type.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + mediumId);
						} else {
							FehlerDTO dto = new FehlerDTO();
							logik.getErrors().stream().forEach(error -> {
								dto.addError(error);
							});
							forward(request, response, dto, "404.jsp");
						}
					} else {
						FehlerDTO dto = new FehlerDTO();
						logik.getErrors().stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
					}
				}
			} catch (NumberFormatException e) {
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID beim Aufruf zum Löschen des Formates gefunden.");
				forward(request, response, dto, "404.jsp");
			}
		}
	}
}
