package gui.controller;

import java.io.IOException;
import java.time.LocalDate;
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

import data.formate.Digital;
import data.formate.Formate;
import data.medien.Bild;
import data.medien.Buch;
import data.medien.Film;
import data.medien.Genre;
import data.medien.Hoerbuch;
import data.medien.Medium;
import data.medien.Musik;
import data.medien.Spiel;
import data.medien.enums.BuchArt;
import data.medien.enums.FilmArt;
import data.medien.enums.HoerbuchArt;
import data.medien.validator.BildValidator;
import data.medien.validator.BuchValidator;
import data.medien.validator.FilmValidator;
import data.medien.validator.HoerbuchValidator;
import data.medien.validator.MusikValidator;
import data.medien.validator.SpielValidator;
import data.speicherorte.Dia;
import data.speicherorte.Kassette;
import data.speicherorte.Optisch;
import data.speicherorte.Schallplatte;
import data.speicherorte.Speicherort;
import enums.Action;
import enums.Mediengruppe;
import enums.SpeicherortArt;
import gui.Controller;
import gui.StaticElements;
import gui.dto.FehlerDTO;
import gui.dto.medien.BildDetailDTO;
import gui.dto.medien.BildEingabeDTO;
import gui.dto.medien.BuchDetailDTO;
import gui.dto.medien.BuchEingabeDTO;
import gui.dto.medien.FilmDetailDTO;
import gui.dto.medien.FilmEingabeDTO;
import gui.dto.medien.FormatDTO;
import gui.dto.medien.HoerbuchDetailDTO;
import gui.dto.medien.HoerbuchEingabeDTO;
import gui.dto.medien.ListAnzeigeDTO;
import gui.dto.medien.ListAnzeigeDTO.ListElementDTO;
import gui.dto.medien.MediumDetailDTO;
import gui.dto.medien.MusikDetailDTO;
import gui.dto.medien.MusikEingabeDTO;
import gui.dto.medien.SpeicherortDTO;
import gui.dto.medien.SpielDetailDTO;
import gui.dto.medien.SpielEingabeDTO;
import logic.MediumLogicFactory;
import logic.genre.GenreLogik;
import logic.medien.MediumLogik;

public class MedienController extends Controller {
	private static Pattern URI_PATTERN = Pattern.compile("/medium/(" + 
			Arrays.asList(Mediengruppe.values()).stream()
			.map(Mediengruppe::getURIPart)
			.collect(Collectors.joining("|")) +
			")/(" +
			Arrays.asList(Action.values()).stream()
			.filter(action -> {return action != Action.Loeschen;})
			.map(Action::getURIPart)
			.collect(Collectors.joining("|")) +
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
		LOGGER.debug("Medientyp: {} - Action: {}", medium.getBezeichnung(), action);
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
				dto.addError("Löschen konnte nicht durchgeführt werden: Fehlende ID beim Aufruf.");
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
					dto.addError("Löschen konnte nicht durchgeführt werden: Fehlende ID beim Aufruf.");
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
			erscheinung	=	medium.getErscheinungsdatum().format(StaticElements.FORMATTER);
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

	private void details(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) throws ServletException, IOException {
		MediumLogik<?>	logic		=	MediumLogicFactory.create(medium);
		List<String>	errors		=	new ArrayList<>();
		String			idString	=	request.getParameter("id");
		if (idString == null || idString.trim().length() == 0) {
			errors.add("Fehlerhafte ID der Daten");
			FehlerDTO dto = new FehlerDTO();
			errors.stream().forEach(error -> {
				dto.addError(error);
			});
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int id = Integer.parseInt(idString);
				if (logic.load(id)) {
					Object item = logic.getObject();
					switch (medium) {
					case Bild:
						if (item instanceof Bild) {
							Bild bild = (Bild) item;
							BildDetailDTO dto = new BildDetailDTO("Bild betrachten");
							dto.setBemerkung(bild.getBemerkungen());
							dto.setBezeichnung(bild.getTitel());
							dto.setDbId(bild.getDbId());
							dto.setDatum(bild.getErscheinungsdatum().format(StaticElements.FORMATTER));
							dto.setGenre(bild.getGenre().stream().map(Genre::getBezeichnung).collect(Collectors.joining(", ")));
							setFormate(dto, bild);
							forward(request, response, dto, "mediumDetails.jsp");
						} else {
							errors.add("Medium wurde nicht gefunden");
							FehlerDTO dto = new FehlerDTO();
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							forward(request, response, dto, "404.jsp");
						}
						break;
					case Buch:
						if (item instanceof Buch) {
							Buch buch = (Buch) item;
							BuchDetailDTO dto = new BuchDetailDTO("Buch betrachten");
							dto.setBemerkung(buch.getBemerkungen());
							dto.setBezeichnung(buch.getTitel());
							dto.setDbId(buch.getDbId());
							dto.setDatum(buch.getErscheinungsdatum().format(StaticElements.FORMATTER));
							dto.setGenre(buch.getGenre().stream().map(Genre::getBezeichnung).collect(Collectors.joining(", ")));
							dto.setArt(buch.getArt().getBezeichnung());
							dto.setAuflage(buch.getAuflage());
							dto.setSprache(buch.getSprache());
							setFormate(dto, buch);
							forward(request, response, dto, "mediumDetails.jsp");
						} else {
							errors.add("Medium wurde nicht gefunden");
							FehlerDTO dto = new FehlerDTO();
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							forward(request, response, dto, "404.jsp");
						}
						break;
					case Film:
						if (item instanceof Film) {
							Film film = (Film) item;
							FilmDetailDTO dto = new FilmDetailDTO("Film betrachten");
							dto.setBemerkung(film.getBemerkungen());
							dto.setBezeichnung(film.getTitel());
							dto.setDbId(film.getDbId());
							dto.setDatum(film.getErscheinungsdatum().format(StaticElements.FORMATTER));
							dto.setGenre(film.getGenre().stream().map(Genre::getBezeichnung).collect(Collectors.joining(", ")));
							dto.setArt(film.getArt().getBezeichnung());
							dto.setSprache(film.getSprache());
							setFormate(dto, film);
							forward(request, response, dto, "mediumDetails.jsp");
						} else {
							errors.add("Medium wurde nicht gefunden");
							FehlerDTO dto = new FehlerDTO();
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							forward(request, response, dto, "404.jsp");
						}
						break;
					case Hoerbuch:
						if (item instanceof Hoerbuch) {
							Hoerbuch hoerbuch = (Hoerbuch) item;
							HoerbuchDetailDTO dto = new HoerbuchDetailDTO("Hörbuch betrachten");
							dto.setBemerkung(hoerbuch.getBemerkungen());
							dto.setBezeichnung(hoerbuch.getTitel());
							dto.setDbId(hoerbuch.getDbId());
							dto.setDatum(hoerbuch.getErscheinungsdatum().format(StaticElements.FORMATTER));
							dto.setGenre(hoerbuch.getGenre().stream().map(Genre::getBezeichnung).collect(Collectors.joining(", ")));
							dto.setArt(hoerbuch.getArt().getBezeichnung());
							dto.setSprache(hoerbuch.getSprache());
							setFormate(dto, hoerbuch);
							forward(request, response, dto, "mediumDetails.jsp");
						} else {
							errors.add("Medium wurde nicht gefunden");
							FehlerDTO dto = new FehlerDTO();
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							forward(request, response, dto, "404.jsp");
						}
						break;
					case Musik:
						if (item instanceof Musik) {
							Musik musik = (Musik) item;
							MusikDetailDTO dto = new MusikDetailDTO("Musik betrachten");
							dto.setBemerkung(musik.getBemerkungen());
							dto.setBezeichnung(musik.getTitel());
							dto.setDbId(musik.getDbId());
							dto.setDatum(musik.getErscheinungsdatum().format(StaticElements.FORMATTER));
							dto.setGenre(musik.getGenre().stream().map(Genre::getBezeichnung).collect(Collectors.joining(", ")));
							dto.setLive(musik.isLive());
							setFormate(dto, musik);
							forward(request, response, dto, "mediumDetails.jsp");
						} else {
							errors.add("Medium wurde nicht gefunden");
							FehlerDTO dto = new FehlerDTO();
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							forward(request, response, dto, "404.jsp");
						}
						break;
					case Spiel:
						if (item instanceof Spiel) {
							Spiel spiel = (Spiel) item;
							SpielDetailDTO dto = new SpielDetailDTO("Spiel betrachten");
							dto.setBemerkung(spiel.getBemerkungen());
							dto.setBezeichnung(spiel.getTitel());
							dto.setDbId(spiel.getDbId());
							dto.setDatum(spiel.getErscheinungsdatum().format(StaticElements.FORMATTER));
							dto.setGenre(spiel.getGenre().stream().map(Genre::getBezeichnung).collect(Collectors.joining(", ")));
							dto.setBetriebssystem(spiel.getBetriebssystem());
							dto.setSprache(spiel.getSprache());
							setFormate(dto, spiel);
							forward(request, response, dto, "mediumDetails.jsp");
						} else {
							errors.add("Medium wurde nicht gefunden");
							FehlerDTO dto = new FehlerDTO();
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							forward(request, response, dto, "404.jsp");
						}
						break;
					}
				} else {
					errors.add("Medium wurde nicht gefunden");
					FehlerDTO dto = new FehlerDTO();
					errors.stream().forEach(error -> {
						dto.addError(error);
					});
					forward(request, response, dto, "404.jsp");
				}
			} catch (NumberFormatException e) {
				errors.add("Fehlerhafte ID der Daten");
				FehlerDTO dto = new FehlerDTO();
				errors.stream().forEach(error -> {
					dto.addError(error);
				});
				forward(request, response, dto, "404.jsp");
			}
		}
	}
	
	private void create(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) throws ServletException, IOException {
		MediumLogik<?>		logic		=	MediumLogicFactory.create(medium);
		List<String>		errors		=	new ArrayList<>();
		GenreLogik			genreLogik	=	new GenreLogik();
		List<Genre>			genreList	=	genreLogik.getAll();
		if (genreList == null) {
			genreLogik.getErrors().stream().forEach(error -> {
				errors.add(error);
			});
		}
		Object item = logic.create();
		if (item != null) {
			if (item instanceof Medium) {
				Medium mediumItem = (Medium) item;
				if (request.getParameter("send") != null) {
					// Parameter auslesen
					String		titel		=	request.getParameter("bezeichnung");
					String[]	genres		=	request.getParameterValues("genre");
					String		erscheinung	=	request.getParameter("erscheinungsjahr");
					String		bemerkungen	=	request.getParameter("bemerkung");
					List<Genre>	genre		=	new ArrayList<>();
					if (genres != null) {
						for (String element : genres) {
							GenreLogik genreLogikCheck = new GenreLogik();
							try {
								int idGenre = Integer.parseInt(element);
								if (genreLogikCheck.load(idGenre)) {
									genre.add(genreLogikCheck.getObject());
								} else {
									genreLogikCheck.getErrors().stream().forEach(error -> {
										errors.add(error);
									});
								}
							} catch (NumberFormatException e) {
								errors.add("Ausgewähltes Genre konnte nicht ermittelt werden.");
							}
						}
					}
					LocalDate	erscheinungsdatum	=	null;
					try {
						erscheinungsdatum = LocalDate.parse(erscheinung, StaticElements.FORMATTER);
					} catch (DateTimeParseException e) {
						errors.add("Bitte das Datum in folgendem Format eingeben: TT.MM.JJJJ");
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
							if (validator.validate(bild) && logic.write() && errors.size() == 0) {
								redirect(request, response, "medium/" + bild.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + bild.getDbId());
							} else {
								validator.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								logic.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								// DTO aus Objekt bestücken
								BildEingabeDTO	dto	=	new BildEingabeDTO("Bild anlegen");
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								dto.setBemerkung(bild.getBemerkungen());
								dto.setBezeichnung(bild.getTitel());
								dto.setDbId(bild.getDbId());
								if (bild.getErscheinungsdatum() == null) {
									dto.setDatum("");
								} else {
									dto.setDatum(bild.getErscheinungsdatum().format(StaticElements.FORMATTER));
								}
								dto.setGenreOptions(genreList);
								dto.setGenreSelected(bild.getGenre());
								forward(request, response, dto, "mediumEingabe.jsp");
							}
						} else {
							// DTO aus Object bestücken
							BildEingabeDTO	dto	=	new BildEingabeDTO("Bild anlegen");
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							dto.setBemerkung(bild.getBemerkungen());
							dto.setBezeichnung(bild.getTitel());
							dto.setDbId(bild.getDbId());
							if (bild.getErscheinungsdatum() == null) {
								dto.setDatum("");
							} else {
								dto.setDatum(bild.getErscheinungsdatum().format(StaticElements.FORMATTER));
							}
							dto.setGenreOptions(genreList);
							dto.setGenreSelected(bild.getGenre());
							forward(request, response, dto, "mediumEingabe.jsp");
						}
					} else {
						errors.add("Fehlerhaftes Casten der Daten");
						FehlerDTO dto = new FehlerDTO();
						errors.stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
					}
					break;
				case Buch:
					if (item instanceof Buch) {
						Buch buch = (Buch) item;
						if (request.getParameter("send") != null) {
							// Parameter auslesen
							String	art		=	request.getParameter("art");
							String	sprache	=	request.getParameter("sprache");
							String	auflage	=	request.getParameter("auflage");
							// Parameter auswerten
							BuchArt	buchArt		=	null;
							int		auflageInt	=	0;
							try {
								int artId = Integer.parseInt(art);
								auflageInt = Integer.parseInt(auflage);
								buchArt = BuchArt.getFromId(artId);
							} catch (NumberFormatException e) {
								errors.add("Fehlerhafte Buchart");
							}
							// Parameter binden
							buch.setArt(buchArt);
							buch.setAuflage(auflageInt);
							buch.setSprache(sprache);
							// Validieren und Speichern
							BuchValidator validator = new BuchValidator();
							if (validator.validate(buch) && logic.write() && errors.size() == 0) {
								redirect(request, response, "medium/" + buch.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + buch.getDbId());
							} else {
								validator.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								logic.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								// DTO aus Objekt bestücken
								BuchEingabeDTO	dto	=	new BuchEingabeDTO("Buch anlegen");
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								dto.setAuflage(buch.getAuflage());
								dto.setBemerkung(buch.getBemerkungen());
								dto.setBezeichnung(buch.getTitel());
								dto.setArtOptions(Arrays.asList(BuchArt.values()));
								dto.setArtSelected(buch.getArt());
								dto.setDbId(buch.getDbId());
								if (buch.getErscheinungsdatum() == null) {
									dto.setDatum("");
								} else {
									dto.setDatum(buch.getErscheinungsdatum().format(StaticElements.FORMATTER));
								}
								dto.setGenreOptions(genreList);
								dto.setGenreSelected(buch.getGenre());
								dto.setSprache(buch.getSprache());
								forward(request, response, dto, "mediumEingabe.jsp");
							}
						} else {
							// DTO aus Object bestücken
							BuchEingabeDTO	dto	=	new BuchEingabeDTO("Buch anlegen");
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							dto.setAuflage(buch.getAuflage());
							dto.setBemerkung(buch.getBemerkungen());
							dto.setBezeichnung(buch.getTitel());
							dto.setArtOptions(Arrays.asList(BuchArt.values()));
							dto.setArtSelected(buch.getArt());
							dto.setDbId(buch.getDbId());
							if (buch.getErscheinungsdatum() == null) {
								dto.setDatum("");
							} else {
								dto.setDatum(buch.getErscheinungsdatum().format(StaticElements.FORMATTER));
							}
							dto.setGenreOptions(genreList);
							dto.setGenreSelected(buch.getGenre());
							dto.setSprache(buch.getSprache());
							forward(request, response, dto, "mediumEingabe.jsp");
						}
					} else {
						errors.add("Fehlerhaftes Casten der Daten");
						FehlerDTO dto = new FehlerDTO();
						errors.stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
					}
					break;
				case Film:
					if (item instanceof Film) {
						Film film = (Film) item;
						if (request.getParameter("send") != null) {
							// Parameter auslesen
							String	art		=	request.getParameter("art");
							String	sprache	=	request.getParameter("sprache");
							// Parameter auswerten
							FilmArt	filmArt = null;
							try {
								int artId = Integer.parseInt(art);
								filmArt = FilmArt.getFromId(artId);
							} catch (NumberFormatException e) {
								errors.add("Fehlerhafte Filmart");
							}
							// Parameter binden
							film.setSprache(sprache);
							film.setArt(filmArt);
							// Validieren und Speichern
							FilmValidator validator = new FilmValidator();
							if (validator.validate(film) && logic.write() && errors.size() == 0) {
								redirect(request, response, "medium/" + film.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + film.getDbId());
							} else {
								validator.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								logic.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								// DTO aus Objekt bestücken
								FilmEingabeDTO dto = new FilmEingabeDTO("Film anlegen");
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								dto.setBemerkung(film.getBemerkungen());
								dto.setBezeichnung(film.getTitel());
								dto.setDbId(film.getDbId());
								if (film.getErscheinungsdatum() == null) {
									dto.setDatum("");
								} else {
									dto.setDatum(film.getErscheinungsdatum().format(StaticElements.FORMATTER));
								}
								dto.setArtOptions(Arrays.asList(FilmArt.values()));
								dto.setArtSelected(film.getArt());
								dto.setGenreOptions(genreList);
								dto.setGenreSelected(film.getGenre());
								dto.setSprache(film.getSprache());
								forward(request, response, dto, "mediumEingabe.jsp");
							}
						} else {
							// DTO aus Object bestücken
							FilmEingabeDTO dto = new FilmEingabeDTO("Film anlegen");
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							dto.setBemerkung(film.getBemerkungen());
							dto.setBezeichnung(film.getTitel());
							dto.setDbId(film.getDbId());
							if (film.getErscheinungsdatum() == null) {
								dto.setDatum("");
							} else {
								dto.setDatum(film.getErscheinungsdatum().format(StaticElements.FORMATTER));
							}
							dto.setArtOptions(Arrays.asList(FilmArt.values()));
							dto.setArtSelected(film.getArt());
							dto.setGenreOptions(genreList);
							dto.setGenreSelected(film.getGenre());
							dto.setSprache(film.getSprache());
							forward(request, response, dto, "mediumEingabe.jsp");
						}
					} else {
						errors.add("Fehlerhaftes Casten der Daten");
						FehlerDTO dto = new FehlerDTO();
						errors.stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
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
								errors.add("Fehlerhafte Hörbuchart");
							}
							// Parameter binden
							hoerbuch.setSprache(sprache);
							hoerbuch.setArt(hoerbuchArt);
							// Validieren und speichern
							HoerbuchValidator validator = new HoerbuchValidator();
							if (validator.validate(hoerbuch) && logic.write() && errors.size() == 0) {
								redirect(request, response, "medium/" + hoerbuch.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + hoerbuch.getDbId());
							} else {
								validator.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								logic.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								// DTO aus Objekt bestücken
								HoerbuchEingabeDTO dto = new HoerbuchEingabeDTO("Hörbuch anlegen");
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								dto.setBemerkung(hoerbuch.getBemerkungen());
								dto.setBezeichnung(hoerbuch.getTitel());
								dto.setDbId(hoerbuch.getDbId());
								if (hoerbuch.getErscheinungsdatum() == null) {
									dto.setDatum("");
								} else {
									dto.setDatum(hoerbuch.getErscheinungsdatum().format(StaticElements.FORMATTER));
								}
								dto.setGenreOptions(genreList);
								dto.setGenreSelected(hoerbuch.getGenre());
								dto.setArtOptions(Arrays.asList(HoerbuchArt.values()));
								dto.setArtSelected(hoerbuch.getArt());
								dto.setSprache(hoerbuch.getSprache());
								forward(request, response, dto, "mediumEingabe.jsp");
							}
						} else {
							// DTO aus Object bestücken
							HoerbuchEingabeDTO dto = new HoerbuchEingabeDTO("Hörbuch anlegen");
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							dto.setBemerkung(hoerbuch.getBemerkungen());
							dto.setBezeichnung(hoerbuch.getTitel());
							dto.setDbId(hoerbuch.getDbId());
							if (hoerbuch.getErscheinungsdatum() == null) {
								dto.setDatum("");
							} else {
								dto.setDatum(hoerbuch.getErscheinungsdatum().format(StaticElements.FORMATTER));
							}
							dto.setGenreOptions(genreList);
							dto.setGenreSelected(hoerbuch.getGenre());
							dto.setArtOptions(Arrays.asList(HoerbuchArt.values()));
							dto.setArtSelected(hoerbuch.getArt());
							dto.setSprache(hoerbuch.getSprache());
							forward(request, response, dto, "mediumEingabe.jsp");
						}
					} else {
						errors.add("Fehlerhaftes Casten der Daten");
						FehlerDTO dto = new FehlerDTO();
						errors.stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
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
							if (validator.validate(musik) && logic.write() && errors.size() == 0) {
								redirect(request, response, "medium/" + musik.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + musik.getDbId());
							} else {
								validator.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								logic.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								// DTO aus Objekt bestücken
								MusikEingabeDTO dto = new MusikEingabeDTO("Musiktitel anlegen");
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								dto.setBemerkung(musik.getBemerkungen());
								dto.setBezeichnung(musik.getTitel());
								dto.setDbId(musik.getDbId());
								if (musik.getErscheinungsdatum() == null) {
									dto.setDatum("");
								} else {
									dto.setDatum(musik.getErscheinungsdatum().format(StaticElements.FORMATTER));
								}
								dto.setGenreOptions(genreList);
								dto.setGenreSelected(musik.getGenre());
								dto.setLive(musik.isLive());
								forward(request, response, dto, "mediumEingabe.jsp");
							}
						} else {
							// DTO aus Object bestücken
							MusikEingabeDTO dto = new MusikEingabeDTO("Musiktitel anlegen");
							dto.setBemerkung(musik.getBemerkungen());
							dto.setBezeichnung(musik.getTitel());
							dto.setDbId(musik.getDbId());
							if (musik.getErscheinungsdatum() != null) {
								dto.setDatum(musik.getErscheinungsdatum().format(StaticElements.FORMATTER));
							} else {
								dto.setDatum("");
							}
							dto.setGenreOptions(genreList);
							dto.setGenreSelected(musik.getGenre());
							dto.setLive(musik.isLive());
							forward(request, response, dto, "mediumEingabe.jsp");
						}
					} else {
						errors.add("Fehlerhaftes Casten der Daten");
						FehlerDTO dto = new FehlerDTO();
						errors.stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
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
							if (validator.validate(spiel) && logic.write() && errors.size() == 0) {
								redirect(request, response, "medium/" + spiel.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + spiel.getDbId());
							} else {
								validator.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								logic.getErrors().stream().forEach(error -> {
									errors.add(error);
								});
								// DTO aus Objekt bestücken
								SpielEingabeDTO dto = new SpielEingabeDTO("Spiel anlegen");
								errors.forEach(error -> {
									dto.addError(error);
								});
								dto.setBemerkung(spiel.getBemerkungen());
								dto.setBetriebssystem(spiel.getBetriebssystem());
								dto.setBezeichnung(spiel.getTitel());
								dto.setDbId(spiel.getDbId());
								if (spiel.getErscheinungsdatum() == null) {
									dto.setDatum("");
								} else {
									dto.setDatum(spiel.getErscheinungsdatum().format(StaticElements.FORMATTER));
								}
								dto.setGenreOptions(genreList);
								dto.setGenreSelected(spiel.getGenre());
								dto.setSprache(spiel.getSprache());
								forward(request, response, dto, "mediumEingabe.jsp");
							}
						} else {
							// DTO aus Object bestücken
							SpielEingabeDTO dto = new SpielEingabeDTO("Spiel anlegen");
							errors.stream().forEach(error -> {
								dto.addError(error);
							});
							dto.setBemerkung(spiel.getBemerkungen());
							dto.setBetriebssystem(spiel.getBetriebssystem());
							dto.setBezeichnung(spiel.getTitel());
							dto.setDbId(spiel.getDbId());
							if (spiel.getErscheinungsdatum() == null) {
								dto.setDatum("");
							} else {
								dto.setDatum(spiel.getErscheinungsdatum().format(StaticElements.FORMATTER));
							}
							dto.setGenreOptions(genreList);
							dto.setGenreSelected(spiel.getGenre());
							dto.setSprache(spiel.getSprache());
							forward(request, response, dto, "mediumEingabe.jsp");
						}
					} else {
						errors.add("Fehlerhaftes Casten der Daten");
						FehlerDTO dto = new FehlerDTO();
						errors.stream().forEach(error -> {
							dto.addError(error);
						});
						forward(request, response, dto, "404.jsp");
					}
					break;
				}
			}
		} else {
			logic.getErrors().stream().forEach(error -> {
				errors.add(error);
			});
			FehlerDTO dto = new FehlerDTO();
			errors.stream().forEach(error -> {
				dto.addError(error);
			});
			forward(request, response, dto, "404.jsp");
		}
	}

	private void bearbeiten(HttpServletRequest request, HttpServletResponse response, Mediengruppe medium) throws ServletException, IOException {
		MediumLogik<?>		logic		=	MediumLogicFactory.create(medium);
		List<String>		errors		=	new ArrayList<>();
		String				idString	=	request.getParameter("id");
		GenreLogik			genreLogik	=	new GenreLogik();
		List<Genre>			genreList	=	genreLogik.getAll();
		if (genreList == null) {
			genreLogik.getErrors().stream().forEach(error -> {
				errors.add(error);
			});
		}
		if (idString == null || idString.trim().length() == 0) {
			errors.add("Keine ID gefunden!");
			FehlerDTO dto = new FehlerDTO();
			errors.stream().forEach(error -> {
				dto.addError(error);
			});
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int id = Integer.parseInt(idString);
				if (logic.load(id)) {
					Object item = logic.getObject();
					if (item instanceof Medium) {
						Medium mediumItem = (Medium) item;
						if (request.getParameter("send") != null) {
							// Parameter auslesen
							String		titel		=	request.getParameter("bezeichnung");
							String[]	genres		=	request.getParameterValues("genre");
							String		erscheinung	=	request.getParameter("erscheinungsjahr");
							String		bemerkungen	=	request.getParameter("bemerkung");
							List<Genre>	genre		=	new ArrayList<>();
							if (genres != null) {
								for (String element : genres) {
									GenreLogik genreLogikCheck = new GenreLogik();
									try {
										int idGenre = Integer.parseInt(element);
										if (genreLogikCheck.load(idGenre)) {
											genre.add(genreLogikCheck.getObject());
										} else {
											genreLogikCheck.getErrors().stream().forEach(error -> {
												errors.add(error);
											});
										}
									} catch (NumberFormatException e) {
										errors.add("Ausgewähltes Genre konnte nicht ermittelt werden.");
									}
								}
							}
							LocalDate	erscheinungsdatum	=	null;
							try {
								erscheinungsdatum = LocalDate.parse(erscheinung, StaticElements.FORMATTER);
							} catch (DateTimeParseException e) {
								errors.add("Bitte das Datum in folgendem Format eingeben: TT.MM.JJJJ");
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
									if (validator.validate(bild) && logic.write() && errors.size() == 0) {
										redirect(request, response, "medium/" + bild.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + bild.getDbId());
									} else {
										validator.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										logic.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										// DTO aus Objekt bestücken
										BildEingabeDTO	dto	=	new BildEingabeDTO("Bild editieren");
										errors.stream().forEach(error -> {
											dto.addError(error);
										});
										dto.setBemerkung(bild.getBemerkungen());
										dto.setBezeichnung(bild.getTitel());
										dto.setDbId(bild.getDbId());
										if (bild.getErscheinungsdatum() == null) {
											dto.setDatum("");
										} else {
											dto.setDatum(bild.getErscheinungsdatum().format(StaticElements.FORMATTER));
										}
										dto.setGenreOptions(genreList);
										dto.setGenreSelected(bild.getGenre());
										forward(request, response, dto, "mediumEingabe.jsp");
									}
								} else {
									// DTO aus Object bestücken
									BildEingabeDTO	dto	=	new BildEingabeDTO("Bild editieren");
									errors.stream().forEach(error -> {
										dto.addError(error);
									});
									dto.setBemerkung(bild.getBemerkungen());
									dto.setBezeichnung(bild.getTitel());
									dto.setDbId(bild.getDbId());
									if (bild.getErscheinungsdatum() == null) {
										dto.setDatum("");
									} else {
										dto.setDatum(bild.getErscheinungsdatum().format(StaticElements.FORMATTER));
									}
									dto.setGenreOptions(genreList);
									dto.setGenreSelected(bild.getGenre());
									forward(request, response, dto, "mediumEingabe.jsp");
								}
							} else {
								errors.add("Fehlerhaftes Casten der Daten");
								FehlerDTO dto = new FehlerDTO();
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								forward(request, response, dto, "404.jsp");
							}
							break;
						case Buch:
							if (item instanceof Buch) {
								Buch buch = (Buch) item;
								if (request.getParameter("send") != null) {
									// Parameter auslesen
									String	art		=	request.getParameter("art");
									String	sprache	=	request.getParameter("sprache");
									String	auflage	=	request.getParameter("auflage");
									// Parameter auswerten
									BuchArt	buchArt		=	null;
									int		auflageInt	=	0;
									try {
										int artId = Integer.parseInt(art);
										auflageInt = Integer.parseInt(auflage);
										buchArt = BuchArt.getFromId(artId);
									} catch (NumberFormatException e) {
										errors.add("Fehlerhafte Buchart");
									}
									// Parameter binden
									buch.setArt(buchArt);
									buch.setAuflage(auflageInt);
									buch.setSprache(sprache);
									// Validieren und Speichern
									BuchValidator validator = new BuchValidator();
									if (validator.validate(buch) && logic.write() && errors.size() == 0) {
										redirect(request, response, "medium/" + buch.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + buch.getDbId());
									} else {
										validator.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										logic.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										// DTO aus Objekt bestücken
										BuchEingabeDTO	dto	=	new BuchEingabeDTO("Buch editieren");
										errors.stream().forEach(error -> {
											dto.addError(error);
										});
										dto.setAuflage(buch.getAuflage());
										dto.setBemerkung(buch.getBemerkungen());
										dto.setBezeichnung(buch.getTitel());
										dto.setArtOptions(Arrays.asList(BuchArt.values()));
										dto.setArtSelected(buch.getArt());
										dto.setDbId(buch.getDbId());
										if (buch.getErscheinungsdatum() == null) {
											dto.setDatum("");
										} else {
											dto.setDatum(buch.getErscheinungsdatum().format(StaticElements.FORMATTER));
										}
										dto.setGenreOptions(genreList);
										dto.setGenreSelected(buch.getGenre());
										dto.setSprache(buch.getSprache());
										forward(request, response, dto, "mediumEingabe.jsp");
									}
								} else {
									// DTO aus Object bestücken
									BuchEingabeDTO	dto	=	new BuchEingabeDTO("Buch editieren");
									errors.stream().forEach(error -> {
										dto.addError(error);
									});
									dto.setAuflage(buch.getAuflage());
									dto.setBemerkung(buch.getBemerkungen());
									dto.setBezeichnung(buch.getTitel());
									dto.setArtOptions(Arrays.asList(BuchArt.values()));
									dto.setArtSelected(buch.getArt());
									dto.setDbId(buch.getDbId());
									if (buch.getErscheinungsdatum() == null) {
										dto.setDatum("");
									} else {
										dto.setDatum(buch.getErscheinungsdatum().format(StaticElements.FORMATTER));
									}
									dto.setGenreOptions(genreList);
									dto.setGenreSelected(buch.getGenre());
									dto.setSprache(buch.getSprache());
									forward(request, response, dto, "mediumEingabe.jsp");
								}
							} else {
								errors.add("Fehlerhaftes Casten der Daten");
								FehlerDTO dto = new FehlerDTO();
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								forward(request, response, dto, "404.jsp");
							}
							break;
						case Film:
							if (item instanceof Film) {
								Film film = (Film) item;
								if (request.getParameter("send") != null) {
									// Parameter auslesen
									String	art		=	request.getParameter("art");
									String	sprache	=	request.getParameter("sprache");
									// Parameter auswerten
									FilmArt	filmArt = null;
									try {
										int artId = Integer.parseInt(art);
										filmArt = FilmArt.getFromId(artId);
									} catch (NumberFormatException e) {
										errors.add("Fehlerhafte Filmart");
									}
									// Parameter binden
									film.setSprache(sprache);
									film.setArt(filmArt);
									// Validieren und Speichern
									FilmValidator validator = new FilmValidator();
									if (validator.validate(film) && logic.write() && errors.size() == 0) {
										redirect(request, response, "medium/" + film.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + film.getDbId());
									} else {
										validator.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										logic.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										// DTO aus Objekt bestücken
										FilmEingabeDTO dto = new FilmEingabeDTO("Film editieren");
										errors.stream().forEach(error -> {
											dto.addError(error);
										});
										dto.setBemerkung(film.getBemerkungen());
										dto.setBezeichnung(film.getTitel());
										dto.setDbId(film.getDbId());
										if (film.getErscheinungsdatum() == null) {
											dto.setDatum("");
										} else {
											dto.setDatum(film.getErscheinungsdatum().format(StaticElements.FORMATTER));
										}
										dto.setArtOptions(Arrays.asList(FilmArt.values()));
										dto.setArtSelected(film.getArt());
										dto.setGenreOptions(genreList);
										dto.setGenreSelected(film.getGenre());
										dto.setSprache(film.getSprache());
										forward(request, response, dto, "mediumEingabe.jsp");
									}
								} else {
									// DTO aus Object bestücken
									FilmEingabeDTO dto = new FilmEingabeDTO("Film editieren");
									errors.stream().forEach(error -> {
										dto.addError(error);
									});
									dto.setBemerkung(film.getBemerkungen());
									dto.setBezeichnung(film.getTitel());
									dto.setDbId(film.getDbId());
									if (film.getErscheinungsdatum() == null) {
										dto.setDatum("");
									} else {
										dto.setDatum(film.getErscheinungsdatum().format(StaticElements.FORMATTER));
									}
									dto.setArtOptions(Arrays.asList(FilmArt.values()));
									dto.setArtSelected(film.getArt());
									dto.setGenreOptions(genreList);
									dto.setGenreSelected(film.getGenre());
									dto.setSprache(film.getSprache());
									forward(request, response, dto, "mediumEingabe.jsp");
								}
							} else {
								errors.add("Fehlerhaftes Casten der Daten");
								FehlerDTO dto = new FehlerDTO();
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								forward(request, response, dto, "404.jsp");
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
										errors.add("Fehlerhafte Hörbuchart");
									}
									// Parameter binden
									hoerbuch.setSprache(sprache);
									hoerbuch.setArt(hoerbuchArt);
									// Validieren und speichern
									HoerbuchValidator validator = new HoerbuchValidator();
									if (validator.validate(hoerbuch) && logic.write() && errors.size() == 0) {
										redirect(request, response, "medium/" + hoerbuch.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + hoerbuch.getDbId());
									} else {
										validator.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										logic.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										// DTO aus Objekt bestücken
										HoerbuchEingabeDTO dto = new HoerbuchEingabeDTO("Hörbuch editieren");
										errors.stream().forEach(error -> {
											dto.addError(error);
										});
										dto.setBemerkung(hoerbuch.getBemerkungen());
										dto.setBezeichnung(hoerbuch.getTitel());
										dto.setDbId(hoerbuch.getDbId());
										if (hoerbuch.getErscheinungsdatum() == null) {
											dto.setDatum("");
										} else {
											dto.setDatum(hoerbuch.getErscheinungsdatum().format(StaticElements.FORMATTER));
										}
										dto.setGenreOptions(genreList);
										dto.setGenreSelected(hoerbuch.getGenre());
										dto.setArtOptions(Arrays.asList(HoerbuchArt.values()));
										dto.setArtSelected(hoerbuch.getArt());
										dto.setSprache(hoerbuch.getSprache());
										forward(request, response, dto, "mediumEingabe.jsp");
									}
								} else {
									// DTO aus Object bestücken
									HoerbuchEingabeDTO dto = new HoerbuchEingabeDTO("Hörbuch editieren");
									errors.stream().forEach(error -> {
										dto.addError(error);
									});
									dto.setBemerkung(hoerbuch.getBemerkungen());
									dto.setBezeichnung(hoerbuch.getTitel());
									dto.setDbId(hoerbuch.getDbId());
									if (hoerbuch.getErscheinungsdatum() == null) {
										dto.setDatum("");
									} else {
										dto.setDatum(hoerbuch.getErscheinungsdatum().format(StaticElements.FORMATTER));
									}
									dto.setGenreOptions(genreList);
									dto.setGenreSelected(hoerbuch.getGenre());
									dto.setArtOptions(Arrays.asList(HoerbuchArt.values()));
									dto.setArtSelected(hoerbuch.getArt());
									dto.setSprache(hoerbuch.getSprache());
									forward(request, response, dto, "mediumEingabe.jsp");
								}
							} else {
								errors.add("Fehlerhaftes Casten der Daten");
								FehlerDTO dto = new FehlerDTO();
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								forward(request, response, dto, "404.jsp");
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
									if (validator.validate(musik) && logic.write() && errors.size() == 0) {
										redirect(request, response, "medium/" + musik.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + musik.getDbId());
									} else {
										validator.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										logic.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										// DTO aus Objekt bestücken
										MusikEingabeDTO dto = new MusikEingabeDTO("Musiktitel editieren");
										errors.stream().forEach(error -> {
											dto.addError(error);
										});
										dto.setBemerkung(musik.getBemerkungen());
										dto.setBezeichnung(musik.getTitel());
										dto.setDbId(musik.getDbId());
										if (musik.getErscheinungsdatum() == null) {
											dto.setDatum("");
										} else {
											dto.setDatum(musik.getErscheinungsdatum().format(StaticElements.FORMATTER));
										}
										dto.setGenreOptions(genreList);
										dto.setGenreSelected(musik.getGenre());
										dto.setLive(musik.isLive());
										forward(request, response, dto, "mediumEingabe.jsp");
									}
								} else {
									// DTO aus Object bestücken
									MusikEingabeDTO dto = new MusikEingabeDTO("Musiktitel editieren");
									errors.stream().forEach(error -> {
										dto.addError(error);
									});
									dto.setBemerkung(musik.getBemerkungen());
									dto.setBezeichnung(musik.getTitel());
									dto.setDbId(musik.getDbId());
									if (musik.getErscheinungsdatum() == null) {
										dto.setDatum("");
									} else {
										dto.setDatum(musik.getErscheinungsdatum().format(StaticElements.FORMATTER));
									}
									dto.setGenreOptions(genreList);
									dto.setGenreSelected(musik.getGenre());
									dto.setLive(musik.isLive());
									forward(request, response, dto, "mediumEingabe.jsp");
								}
							} else {
								errors.add("Fehlerhaftes Casten der Daten");
								FehlerDTO dto = new FehlerDTO();
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								forward(request, response, dto, "404.jsp");
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
									if (validator.validate(spiel) && logic.write() && errors.size() == 0) {
										redirect(request, response, "medium/" + spiel.getType().getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + spiel.getDbId());
									} else {
										validator.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										logic.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										// DTO aus Objekt bestücken
										SpielEingabeDTO dto = new SpielEingabeDTO("Spiel editieren");
										errors.stream().forEach(error -> {
											dto.addError(error);
										});
										dto.setBemerkung(spiel.getBemerkungen());
										dto.setBetriebssystem(spiel.getBetriebssystem());
										dto.setBezeichnung(spiel.getTitel());
										dto.setDbId(spiel.getDbId());
										if (spiel.getErscheinungsdatum() == null) {
											dto.setDatum("");
										} else {
											dto.setDatum(spiel.getErscheinungsdatum().format(StaticElements.FORMATTER));
										}
										dto.setGenreOptions(genreList);
										dto.setGenreSelected(spiel.getGenre());
										dto.setSprache(spiel.getSprache());
										forward(request, response, dto, "mediumEingabe.jsp");
									}
								} else {
									// DTO aus Object bestücken
									SpielEingabeDTO dto = new SpielEingabeDTO("Spiel editieren");
									errors.stream().forEach(error -> {
										dto.addError(error);
									});
									dto.setBemerkung(spiel.getBemerkungen());
									dto.setBetriebssystem(spiel.getBetriebssystem());
									dto.setBezeichnung(spiel.getTitel());
									dto.setDbId(spiel.getDbId());
									if (spiel.getErscheinungsdatum() == null) {
										dto.setDatum("");
									} else {
										dto.setDatum(spiel.getErscheinungsdatum().format(StaticElements.FORMATTER));
									}
									dto.setGenreOptions(genreList);
									dto.setGenreSelected(spiel.getGenre());
									dto.setSprache(spiel.getSprache());
									forward(request, response, dto, "mediumEingabe.jsp");
								}
							} else {
								errors.add("Fehlerhaftes Casten der Daten");
								FehlerDTO dto = new FehlerDTO();
								errors.stream().forEach(error -> {
									dto.addError(error);
								});
								forward(request, response, dto, "404.jsp");
							}
							break;
						}
					}
				} else {
					errors.add("Unbekannte ID");
					FehlerDTO dto = new FehlerDTO();
					errors.stream().forEach(error -> {
						dto.addError(error);
					});
					forward(request, response, dto, "404.jsp");
				}
			} catch (NumberFormatException e) {
				errors.add("Fehlerhafte ID");
				FehlerDTO dto = new FehlerDTO();
				errors.stream().forEach(error -> {
					dto.addError(error);
				});
				forward(request, response, dto, "404.jsp");
			}
		}
	}
	
	private void setFormate(MediumDetailDTO dto, Medium object) {
		List<FormatDTO>	list	=	new ArrayList<>();
		for (Formate format : object.getFormate()) {
			String					bezeichnung		=	format.getType().getBezeichnung();
			List<SpeicherortDTO>	speicherorte	=	new ArrayList<>();
			switch (format.getType()) {
			case Digital:
				Digital digital = (Digital) format;
				bezeichnung = bezeichnung + ":   Dateiformat: " + digital.getDateiformat() + "   Qualität: " + digital.getQualitaet();
				break;
			default:
				break;
			}
			for (Speicherort speicherort : format.getSpeicherOrte()) {
				int				id			=	speicherort.getDbId();
				SpeicherortArt	type		=	speicherort.getType();
				String			art			=	"";
				String			bez			=	speicherort.getLagerOrt();
				String			bemerkung	=	speicherort.getBemerkung();
				String			zustand		=	"";
				switch (type) {
				case Buch:
					data.speicherorte.Buch	buch	=	(data.speicherorte.Buch) speicherort;
					art		=	buch.getArt().getBezeichnung();
					zustand	=	buch.getZustand();
					break;
				case Dia:
					Dia	dia	=	(Dia) speicherort;
					zustand	=	dia.getZustand();
					break;
				case Kassette:
					Kassette	kassette	=	(Kassette) speicherort;
					art		=	kassette.getArt().getBezeichnung();
					zustand	=	kassette.getZustand();
					break;
				case Optisch:
					Optisch	optisch	=	(Optisch) speicherort;
					art		=	optisch.getArt().getBezeichnung();
					zustand	=	optisch.getZustand();
					break;
				case Schallplatte:
					Schallplatte	schallplatte	=	(Schallplatte) speicherort;
					art		=	schallplatte.getArt().getBezeichnung();
					zustand	=	schallplatte.getZustand();
					break;
				default:
					break;
				}
				speicherorte.add(new SpeicherortDTO(id, type, art, bez, bemerkung, zustand));
			}
			FormatDTO formatDto = new FormatDTO(format.getDbId(), format.getType().getId(), bezeichnung, speicherorte);
			list.add(formatDto);
		}
		dto.setFormate(list);
	}
}