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
//		if (request.getParameter("idFormat") == null) {
//			FehlerDTO dto = new FehlerDTO();
//			dto.addError("Keine Format ID beim Aufruf zum L�schen des Formates gefunden.");
//			forward(request, response, dto, "404.jsp");
//		} else if (request.getParameter("idMedium") == null) {
//			FehlerDTO dto = new FehlerDTO();
//			dto.addError("Kein Medium Type beim Aufruf zum L�schen des Formates gefunden.");
//			forward(request, response, dto, "404.jsp");
//		} else if (request.getParameter("idMediumType") == null) {
//			FehlerDTO dto = new FehlerDTO();
//			dto.addError("Kein Medium Type beim Aufruf zum L�schen des Formates gefunden.");
//			forward(request, response, dto, "404.jsp");
//		} else if (request.getParameter("idFormatType") == null) {
//			FehlerDTO dto = new FehlerDTO();
//			dto.addError("Kein Format Type beim Aufruf zum L�schen des Formates gefunden.");
//			forward(request, response, dto, "404.jsp");
//		} else {
//			try {
//				int						idSpeicherFormat	=	Integer.parseInt(request.getParameter("idFormat"));
//				int						idMedium			=	Integer.parseInt(request.getParameter("idMedium"));
//				int						idMediumType		=	Integer.parseInt(request.getParameter("idMediumType"));
//				int						idFormatType		=	Integer.parseInt(request.getParameter("idFormatType"));
//				Mediengruppe			typeMedium			=	Mediengruppe.getElementFromId(idMediumType);
//				Format					typeFormat			=	Format.getElementFromId(idFormatType);
//				if (typeMedium == null) {
//					FehlerDTO dto = new FehlerDTO();
//					dto.addError("Medium Type konnte nicht bestimmt werden.");
//					forward(request, response, dto, "404.jsp");
//				} else if (typeFormat == null) {
//					FehlerDTO dto = new FehlerDTO();
//					dto.addError("Format Type konnte nicht bestimmt werden.");
//					forward(request, response, dto, "404.jsp");
//				} else {
//					if (request.getParameter("send") != null) {
//						String	selectedArtString	=	request.getParameter("speicherortArt");
//					} else {
//						SpeicherorteDTO	dto	=	new SpeicherorteDTO("Speicherort anlegen", 0, null, Arrays.asList(typeFormat.getAllowed()), idSpeicherFormat, idMedium, idMediumType, idFormatType);
//						forward(request, response, dto, "speicherortEingabe.jsp");
//					}
//				}
//				if (request.getParameter("send") != null) {
//					String	selectedArtString	=	request.getParameter("speicherortArt");
//					// Parameter übernehmen
//					String	formatTypeString	=	request.getParameter("format");
//					if (formatTypeString == null) {
//						FormateDTO	dto	=	new	AnalogFormateDTO("Format eingeben", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
//						dto.addError("Fehlender Parameter: FormatArt");
//						forward(request, response, dto, "speicherformatEingabe.jsp");
//					} else {
//						Format	formatType			=	Format.getElementFromId(Integer.parseInt(request.getParameter("format")));
//						if (formatType != null) {
//							List<String> errors = new ArrayList<>();
//							Formate format = logik.create(formatType);
//							if (format.getType() == Format.Digital) {
//								Digital digital = (Digital) format;
//								digital.setDateiformat(request.getParameter("dateiformat"));
//								digital.setQualitaet(request.getParameter("quali"));
//								DigitalValidator validator = new DigitalValidator();
//								if (validator.validate(digital) && logik.write()) {
//									String uri = "medium/" + type.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + mediumId;
//									redirect(request, response, uri);
//								} else {
//									validator.getErrors().stream().forEach(error -> {
//										errors.add(error);
//									});
//									logik.getErrors().stream().forEach(error -> {
//										errors.add(error);
//									});
//									DigitalFormateDTO	dto	=	new	DigitalFormateDTO("Format eingeben", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
//									errors.stream().forEach(error -> {
//										dto.addError(error);
//									});
//									dto.setSelectedFormat(formatType);
//									dto.setDateiformat(digital.getDateiformat());
//									dto.setQualitaet(digital.getQualitaet());
//									forward(request, response, dto, "speicherformatEingabe.jsp");
//								}
//							} else if (format.getType() == Format.Analog) {
//								Analog analog = (Analog) format;
//								AnalogValidator validator = new AnalogValidator();
//								if (validator.validate(analog) && logik.write()) {
//									String uri = "medium/" + type.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + mediumId;
//									redirect(request, response, uri);
//								} else {
//									validator.getErrors().stream().forEach(error -> {
//										errors.add(error);
//									});
//									logik.getErrors().stream().forEach(error -> {
//										errors.add(error);
//									});
//									AnalogFormateDTO	dto	=	new	AnalogFormateDTO("Format eingeben", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
//									errors.stream().forEach(error -> {
//										dto.addError(error);
//									});
//									dto.setSelectedFormat(formatType);
//									forward(request, response, dto, "speicherformatEingabe.jsp");
//								}
//							} else {
//								FormateDTO	dto	=	new	AnalogFormateDTO("Format eingeben", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
//								dto.addError("Unbekanntes Format");
//								forward(request, response, dto, "speicherformatEingabe.jsp");
//							}
//						} else {
//							FormateDTO	dto	=	new	AnalogFormateDTO("Format eingeben", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
//							dto.addError("FormatArt konnte nicht bestimmt werden.");
//							forward(request, response, dto, "speicherformatEingabe.jsp");
//						}
//					}
//				} else {
//					FormateDTO	dto	=	new	AnalogFormateDTO("Format eingeben", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
//					forward(request, response, dto, "speicherformatEingabe.jsp");
//				}
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//				FehlerDTO dto = new FehlerDTO();
//				dto.addError("Fehlerhafte ID beim Aufruf zum L�schen des Formates gefunden.");
//				forward(request, response, dto, "404.jsp");
//			}
//		}
	}

	private void bearbeiten(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ID beim Aufruf zum L�schen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMedium") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum L�schen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMediumType") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum L�schen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int 			id			=	Integer.parseInt(request.getParameter("id"));
				int				mediumId	=	Integer.parseInt(request.getParameter("idMedium"));
				Mediengruppe	type		=	Mediengruppe.getElementFromId(Integer.parseInt(request.getParameter("idMediumType")));
				FormateLogik	logik		=	new FormateLogik(mediumId);
				if (!logik.load(id)) {
					FehlerDTO	dto	=	new	FehlerDTO();
					dto.addError("Format konnte nicht geladen werden");
					forward(request, response, dto, "404.jsp");
				} else {
					// Parameter übernehmen
					String	formatTypeString	=	request.getParameter("format");
					if (formatTypeString == null) {
						FehlerDTO	dto	=	new	FehlerDTO();
						dto.addError("Formatart konnte nicht bestimmt werden");
						forward(request, response, dto, "404.jsp");
					} else {
						Format	formatType		=	Format.getElementFromId(Integer.parseInt(request.getParameter("format")));
						if (formatType != null) {
							Formate format = logik.getObject();
							if (request.getParameter("send") != null) {
								List<String> errors = new ArrayList<>();
								
								if (format.getType() == Format.Digital) {
									Digital digital = (Digital) format;
									digital.setDateiformat(request.getParameter("dateiformat"));
									digital.setQualitaet(request.getParameter("quali"));
									DigitalValidator validator = new DigitalValidator();
									if (validator.validate(digital) && logik.write()) {
										String uri = "medium/" + type.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + mediumId;
										redirect(request, response, uri);
									} else {
										validator.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										logik.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										DigitalFormateDTO	dto	=	new	DigitalFormateDTO("Format eingeben", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
										errors.stream().forEach(error -> {
											dto.addError(error);
										});
										dto.setSelectedFormat(formatType);
										dto.setDateiformat(digital.getDateiformat());
										dto.setQualitaet(digital.getQualitaet());
										forward(request, response, dto, "speicherformatEingabe.jsp");
									}
								} else if (format.getType() == Format.Analog) {
									Analog analog = (Analog) format;
									AnalogValidator validator = new AnalogValidator();
									if (validator.validate(analog) && logik.write()) {
										String uri = "medium/" + type.getURIPart() + "/" + Action.Details.getURIPart() + ".html?id=" + mediumId;
										redirect(request, response, uri);
									} else {
										validator.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										logik.getErrors().stream().forEach(error -> {
											errors.add(error);
										});
										AnalogFormateDTO	dto	=	new	AnalogFormateDTO("Format eingeben", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
										errors.stream().forEach(error -> {
											dto.addError(error);
										});
										dto.setSelectedFormat(formatType);
										forward(request, response, dto, "speicherformatEingabe.jsp");
									}
								} else {
									FehlerDTO	dto	=	new	FehlerDTO();
									dto.addError("Unbekanntes Format");
									forward(request, response, dto, "404.jsp");
								}
							} else {
								if (format.getType() == Format.Digital) {
									Digital				digital	=	(Digital) format;
									DigitalFormateDTO	dto		=	new	DigitalFormateDTO("Format editieren", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
									dto.setSelectedFormat(formatType);
									dto.setDateiformat(digital.getDateiformat());
									dto.setQualitaet(digital.getQualitaet());
									forward(request, response, dto, "speicherformatEingabe.jsp");
								} else if (format.getType() == Format.Analog) {
									AnalogFormateDTO	dto	=	new	AnalogFormateDTO("Format editieren", request.getContextPath() + "/formate/" + Action.Neuanlage.getURIPart() + ".html", mediumId, type.getId());
									dto.setSelectedFormat(formatType);
									forward(request, response, dto, "speicherformatEingabe.jsp");
								} else {
									FehlerDTO	dto	=	new	FehlerDTO();
									dto.addError("Unbekanntes Format");
									forward(request, response, dto, "404.jsp");
								}
							}
						} else {
							FehlerDTO	dto	=	new	FehlerDTO();
							dto.addError("FormatArt konnte nicht bestimmt werden.");
							forward(request, response, dto, "404.jsp");
						}
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID beim Aufruf zum L�schen des Formates gefunden.");
				forward(request, response, dto, "404.jsp");
			}
		}
	}

	private void loeschen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ID beim Aufruf zum L�schen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMedium") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum L�schen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else if (request.getParameter("idMediumType") == null) {
			FehlerDTO dto = new FehlerDTO();
			dto.addError("Keine ParentID beim Aufruf zum L�schen des Formates gefunden.");
			forward(request, response, dto, "404.jsp");
		} else {
			try {
				int 			id			=	Integer.parseInt(request.getParameter("id"));
				int				mediumId	=	Integer.parseInt(request.getParameter("idMedium"));
				Mediengruppe	type		=	Mediengruppe.getElementFromId(Integer.parseInt(request.getParameter("idMediumType")));
				FormateLogik	logik		=	new FormateLogik(mediumId);
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
			} catch (NumberFormatException e) {
				e.printStackTrace();
				FehlerDTO dto = new FehlerDTO();
				dto.addError("Fehlerhafte ID beim Aufruf zum L�schen des Formates gefunden.");
				forward(request, response, dto, "404.jsp");
			}
		}
	}
}
