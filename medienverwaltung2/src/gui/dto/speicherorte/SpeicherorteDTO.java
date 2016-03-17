package gui.dto.speicherorte;

import java.util.List;

import enums.Format;
import enums.SpeicherortArt;
import gui.dto.BaseDTO;

public class SpeicherorteDTO extends BaseDTO {
	private	int						dbId;
	private	SpeicherortArt			selected;
	private	List<SpeicherortArt>	selectOptions;
	private	int						idSpeicherFormat;
	private	int						idMedium;
	private	int						
	
	public SpeicherorteDTO(String titel) {
		super(titel);
	}

}
