package gui.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.medien.Bild;
import data.medien.Buch;
import data.medien.Film;
import data.medien.Genre;
import data.medien.Hoerbuch;
import data.medien.Medium;
import data.medien.Musik;
import data.medien.Spiel;
import data.medien.enums.HoerbuchArt;
import data.medien.validator.BildValidator;
import data.medien.validator.BuchValidator;
import data.medien.validator.FilmValidator;
import data.medien.validator.HoerbuchValidator;
import data.medien.validator.MusikValidator;
import data.medien.validator.SpielValidator;
import enums.Action;
import enums.Mediengruppe;
import gui.Controller;
import gui.StaticElements;
import gui.dto.BaseDTO;
import gui.dto.medien.ListAnzeigeDTO;
import gui.dto.medien.ListAnzeigeDTO.ListElementDTO;
import gui.dto.medien.MediumEingabeDTO;
import gui.dto.medien.ShowParameterDTO;
import logic.MediumLogicFactory;
import logic.genre.GenreLogik;
import logic.medien.MediumLogik;

public class MedienController extends Controller {
	private static Pattern URI_PATTERN = Pattern.compile("/medium/(" + 
			Arrays.asList(Mediengruppe.values()).stream().map(Mediengruppe::getURIPart).collect(Collectors.joining("|")) +
			")/(" +
			Arrays.asList(Action.values()).stream().map(Action::getURIPart).collect(Collectors.joining("|")) +
			").html");
	private	static	Logger	LOGGER	=	LogManager.getLogger(MedienController.class);
	
	public MedienController() {
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
		String			mediumPart	=	matcher.group(1);
		String			actionPart	=	matcher.group(2);
		Mediengruppe	medium		=	Mediengruppe.getElementFromUriPart(mediumPart);
		Action			action		=	Action.getFromURIPart(actionPart);
		LOGGER.debug("Medientype: {} - Action: {}", medium.getBezeichnung(), action);
		switch (action) {
		case Bearbeiten:
			bearbeiten(request, response, medium);
			break;
		case Details:
			details(request, response, medium);
			break;
		case List:
			list(request, response, medium);
			break;
		case Neuanlage:
			create(request, response, medium);
			break;
		default:
			break;
		}
		
		
		ShowParameterDTO	dto				=	new ShowParameterDTO(request);
		forward(request, response, dto, "/showRequest.jsp");
	}

	private void list(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) throws ServletException, IOException {
		ListAnzeigeDTO	dto		=	new ListAnzeigeDTO("Liste aller Elemente der Art " + medium.getBezeichnung());
		MediumLogik<?>	logic	=	MediumLogicFactory.create(medium);
		dto.setMedium(medium);
		dto.setBaseURI(request.getContextPath() + "/medium/" + medium.getURIPart() + "/");
		// Medium löschen?
		if (request.getParameter("act") != null && request.getParameter("act").equals("delete")) {
			String idString = request.getParameter("id");
			if (idString == null || idString.trim().length() == 0) {
				// Fehler: ID fehlt
				dto.addError("Löschen konnte nicht durchgeführt werden: Fehlernde ID beim Aufruf.");
			} else {
				try {
					int id = Integer.parseInt(idString);
					if (logic.load(id)) {
						if (!logic.delete()) {
							// Fehler DTO
							dto.addError("Fehler beim Löschen.");
							logic.getErrors().stream().forEach(data -> {
								dto.addError(data);
							});
						} else {
							// Mitteilung DTO
							dto.addError(medium.getBezeichnung() + " mit der ID " + id + " wurde gelöscht.");
						}
					} else {
						// Fehler DTO
						dto.addError("Kein Medium dieser ID gefunden.");
						logic.getErrors().stream().forEach(data -> {
							dto.addError(data);
						});
					}
				} catch (NumberFormatException e) {
					// Fehler: Falsche ID
					dto.addError("Löschen konnte nicht durchgeführt werden: Fehlernde ID beim Aufruf.");
				}
			}
		}
		
		List<?> list = logic.getAll();
		if (list == null) {
			// Fehler beim Laden
			dto.addError("Laden der Elemente fehlgeschlagen.");
			logic.getErrors().stream().forEach(data -> {
				dto.addError(data);
			});
		} else {
			// Liste zu DTO
			List<ListElementDTO> listDTO = new ArrayList<>();
			list.stream().forEach(data -> {
				try {
					listDTO.add(listElementToDTO(data, medium));
				} catch (IllegalArgumentException e) {
					LOGGER.error("Fehlerhafte Klasse für DTO Umwandlung! Medium: {} - Klasse: {}", medium.getBezeichnung(), data.getClass().getCanonicalName());
				}
			});
			dto.setList(listDTO);
		}
		forward(request, response, dto, "mediumAnzeige.jsp");
	}

	private ListElementDTO listElementToDTO(Object data, Mediengruppe mediumType) throws IllegalArgumentException {
		int		id			=	0;
		String	bezeichnung	=	null;
		String	erscheinung	=	null;
		String	genre		=	null;
		if (data instanceof Medium) {
			Medium 	medium	=	(Medium) data;
			id			=	medium.getDbId();
			bezeichnung	=	medium.getTitel();
			erscheinung	=	medium.getErscheinungsdatum().format(DateTimeFormatter.BASIC_ISO_DATE);
			genre		=	medium.getGenre().stream().map(Genre::getBezeichnung).collect(Collectors.joining(", "));
		} else {
			throw new IllegalArgumentException();
		}
		switch (mediumType) {
		case Bild:
			if (data instanceof Bild) {
				return new ListElementDTO(id, bezeichnung, erscheinung, genre);
			} else {
				throw new IllegalArgumentException();
			}
		case Buch:
			if (data instanceof Buch) {
				return new ListElementDTO(id, bezeichnung, erscheinung, genre);
			} else {
				throw new IllegalArgumentException();
			}
		case Film:
			if (data instanceof Film) {
				return new ListElementDTO(id, bezeichnung, erscheinung, genre);
			} else {
				throw new IllegalArgumentException();
			}
		case Hoerbuch:
			if (data instanceof Hoerbuch) {
				return new ListElementDTO(id, bezeichnung, erscheinung, genre);
			} else {
				throw new IllegalArgumentException();
			}
		case Musik:
			if (data instanceof Musik) {
				return new ListElementDTO(id, bezeichnung, erscheinung, genre);
			} else {
				throw new IllegalArgumentException();
			}
		case Spiel:
			if (data instanceof Spiel) {
				return new ListElementDTO(id, bezeichnung, erscheinung, genre);
			} else {
				throw new IllegalArgumentException();
			}
		default:
			throw new IllegalArgumentException();
		}
	}

	private void details(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) {
		MediumLogik<?>	logic		=	MediumLogicFactory.create(medium);
		BaseDTO			dto			=	null;
		String			idString	=	request.getParameter("id");
		if (idString == null || idString.trim().length() == 0) {
			// TODO Fehlende ID
		} else {
			try {
				int id = Integer.parseInt(idString);
				if (logic.load(id)) {
					Object item = logic.getObject();
					switch (medium) {
					case Bild:
						if (item instanceof Bild) {
							Bild bild = (Bild) item;
						} else {
							// TODO Something is wrong
						}
						break;
					case Buch:
						if (item instanceof Buch) {
							Buch buch = (Buch) item;
						} else {
							// TODO Something is wrong
						}
						break;
					case Film:
						if (item instanceof Film) {
							Film film = (Film) item;
						} else {
							// TODO Something is wrong
						}
						break;
					case Hoerbuch:
						if (item instanceof Hoerbuch) {
							Hoerbuch hoerbuch = (Hoerbuch) item;
						} else {
							// TODO Something is wrong
						}
						break;
					case Musik:
						if (item instanceof Musik) {
							Musik musik = (Musik) item;
						} else {
							// TODO Something is wrong
						}
						break;
					case Spiel:
						if (item instanceof Spiel) {
							Spiel spiel = (Spiel) item;
						} else {
							// TODO Something is wrong
						}
						break;
					default:
						// TODO Never ever
						break;
					}
				} else {
					// TODO Fehler - kann nicht laden
				}
			} catch (NumberFormatException e) {
				// TODO Fehlerhafte ID
			}
		}
	}
	
	private void create(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) {
		// TODO Auto-generated method stub
		// Leeres DTO erzeugen oder Parameter übernehmen DTO
		// Schreiben, usw. kontrollieren
	}

	private void bearbeiten(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) {
		MediumLogik<?>		logic		=	MediumLogicFactory.create(medium);
		String				idString	=	request.getParameter("id");
		if (idString == null || idString.trim().length() == 0) {
			// TODO Fehlende ID
		} else {
			try {
				int id = Integer.parseInt(idString);
				if (logic.load(id)) {
					Object item = logic.getObject();
					if (item instanceof Medium) {
						Medium mediumItem = (Medium) item;
						if (request.getParameter("send") != null) {
							// TODO Parameter auslesen und zuweisen
							String		titel		=	request.getParameter("bezeichnung");
							String[]	genres		=	request.getParameterValues("genre");
							String		erscheinung	=	request.getParameter("erscheinungsjahr");
							String		bemerkungen	=	request.getParameter("bemerkung");
							List<Genre>	genre		=	new ArrayList<>();
							if (genres != null) {
								for (String element : genres) {
									GenreLogik	genreLogik	=	new GenreLogik();
									try {
										int idGenre = Integer.parseInt(element);
										if (genreLogik.load(idGenre)) {
											genre.add(genreLogik.getObject());
										} else {
											// TODO Fehler
										}
									} catch (NumberFormatException e) {
										// TODO Fehler
									}
								}
							}
							LocalDate	erscheinungsdatum	=	null;
							try {
								erscheinungsdatum = LocalDate.parse(erscheinung, StaticElements.FORMATTER);
							} catch (DateTimeParseException e) {
								// TODO Fehler to DTO
							}
							mediumItem.setTitel(titel);
							mediumItem.setBemerkungen(bemerkungen);
							mediumItem.setGenre(genre);
							mediumItem.setErscheinungsdatum(erscheinungsdatum);
						}
						switch (medium) {
						case Bild:
							if (item instanceof Bild) {
								Bild bild = (Bild) item;
								if (request.getParameter("send") != null) {
									// Keine weiteren Parameter
									BildValidator validator = new BildValidator();
									if (validator.validate(bild) && logic.write()) {
										// TODO Weiterleitung wohin? Detailseite oder Liste?
									} else {
										// TODO DTO aus Objekt bestücken
										// TODO Weiterleitung Eingabeseite
									}
								} else {
									// TODO DTO aus Object bestücken
								}
							} else {
								// TODO Something is wrong
							}
							break;
						case Buch:
							if (item instanceof Buch) {
								Buch buch = (Buch) item;
								if (request.getParameter("send") != null) {
									// TODO Parameter auslesen und bild zuweisen
									String	art		=	request.getParameter("art");
									String	sprache	=	request.getParameter("sprache");
									String	auflage	=	request.getParameter("auflage");
									BuchValidator validator = new BuchValidator();
									if (validator.validate(buch) && logic.write()) {
										// TODO Weiterleitung wohin? Detailseite oder Liste?
									} else {
										// TODO DTO aus Objekt bestücken
										// TODO Weiterleitung Eingabeseite
									}
								} else {
									// TODO DTO aus Object bestücken
								}
							} else {
								// TODO Something is wrong
							}
							break;
						case Film:
							if (item instanceof Film) {
								Film film = (Film) item;
								if (request.getParameter("send") != null) {
									// TODO Parameter auslesen und bild zuweisen
									String	art		=	request.getParameter("art");
									String	sprache	=	request.getParameter("sprache");
									FilmValidator validator = new FilmValidator();
									if (validator.validate(film) && logic.write()) {
										// TODO Weiterleitung wohin? Detailseite oder Liste?
									} else {
										// TODO DTO aus Objekt bestücken
										// TODO Weiterleitung Eingabeseite
									}
								} else {
									// TODO DTO aus Object bestücken
								}
							} else {
								// TODO Something is wrong
							}
							break;
						case Hoerbuch:
							if (item instanceof Hoerbuch) {
								Hoerbuch hoerbuch = (Hoerbuch) item;
								if (request.getParameter("send") != null) {
									// Parameter auslesen
									String	art		=	request.getParameter("art");
									String	sprache	=	request.getParameter("sprache");
									// Parameter auswerten
									HoerbuchArt	hoerbuchArt = null;
									try {
										int artId = Integer.parseInt(art);
										hoerbuchArt = HoerbuchArt.getFromId(artId);
										
									} catch (NumberFormatException e) {
										// TODO Fehler
									}
									// Parameter binden
									hoerbuch.setSprache(sprache);
									hoerbuch.setArt(hoerbuchArt);
									// Validieren und speichern
									HoerbuchValidator validator = new HoerbuchValidator();
									if (validator.validate(hoerbuch) && logic.write()) {
										// TODO Weiterleitung wohin? Detailseite oder Liste?
									} else {
										// TODO DTO aus Objekt bestücken
										// TODO Weiterleitung Eingabeseite
									}
								} else {
									// TODO DTO aus Object bestücken
								}
							} else {
								// TODO Something is wrong
							}
							break;
						case Musik:
							if (item instanceof Musik) {
								Musik musik = (Musik) item;
								if (request.getParameter("send") != null) {
									// Parameter auslesen
									boolean live = request.getParameter("live") != null;
									// Parameter binden
									musik.setLive(live);
									// Validieren und Speichern
									MusikValidator validator = new MusikValidator();
									if (validator.validate(musik) && logic.write()) {
										// TODO Weiterleitung wohin? Detailseite oder Liste?
									} else {
										// TODO DTO aus Objekt bestücken
										// TODO Weiterleitung Eingabeseite
									}
								} else {
									// TODO DTO aus Object bestücken
								}
							} else {
								// TODO Something is wrong
							}
							break;
						case Spiel:
							if (item instanceof Spiel) {
								Spiel spiel = (Spiel) item;
								if (request.getParameter("send") != null) {
									// Parameter auslesen
									String	sprache	=	request.getParameter("sprache");
									String	betrieb	=	request.getParameter("betrieb");
									// Parameter binden
									spiel.setSprache(sprache);
									spiel.setBetriebssystem(betrieb);
									// Validieren und Speichern
									SpielValidator validator = new SpielValidator();
									if (validator.validate(spiel) && logic.write()) {
										// TODO Weiterleitung wohin? Detailseite oder Liste?
									} else {
										// TODO DTO aus Objekt bestücken
										// TODO Weiterleitung Eingabeseite
									}
								} else {
									// TODO DTO aus Object bestücken
								}
							} else {
								// TODO Something is wrong
							}
							break;
						default:
							// TODO Never ever
							break;
						}
					}
				} else {
					// TODO Fehler - kann nicht laden
				}
			} catch (NumberFormatException e) {
				// TODO Fehlerhafte ID
			}
		}
	}
}