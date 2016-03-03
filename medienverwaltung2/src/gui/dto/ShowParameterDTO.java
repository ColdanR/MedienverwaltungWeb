package gui.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ShowParameterDTO extends BaseDTO {
	private	List<String[]>	header		=	new ArrayList<>();
	private	List<String[]>	parameter	=	new ArrayList<>();
	private	List<String[]>	attributes	=	new ArrayList<>();
	
	public ShowParameterDTO() {
		super("Parameter Auswertung");
	}

	public List<String[]> getHeader() {
		return header;
	}

	public List<String[]> getParameter() {
		return parameter;
	}

	public List<String[]> getAttributes() {
		return attributes;
	}
	
	public void addHeader(String name, String[] values) {
		String value = StringUtils.join(values, ", ");
		header.add(new String[]{name, value});
	}
	
	public void addParameter(String name, String[] values) {
		String value = StringUtils.join(values, ", ");
		parameter.add(new String[]{name, value});
	}
	
	public void addAttribute(String name, Object value) {
		attributes.add(new String[]{name, value.toString()});
	}
}
