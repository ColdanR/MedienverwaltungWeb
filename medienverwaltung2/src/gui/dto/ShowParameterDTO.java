package gui.dto;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ShowParameterDTO extends BaseDTO {
	private	List<String[]>	header		=	new ArrayList<>();
	private	List<String[]>	parameter	=	new ArrayList<>();
	private	List<String[]>	attributes	=	new ArrayList<>();
	
	public ShowParameterDTO() {
		super("Parameter Auswertung");
	}
	
	public ShowParameterDTO(HttpServletRequest request) {
		super("Parameter Auswertung");
		Enumeration<String> headerNames		=	request.getHeaderNames();
		Enumeration<String> attributeNames	=	request.getAttributeNames();
		Enumeration<String> parameterNames	=	request.getParameterNames();
		while (headerNames.hasMoreElements()) {
			String				header	=	headerNames.nextElement();
			Enumeration<String>	value	=	request.getHeaders(header);
			List<String>		list	=	new ArrayList<>();
			while (value.hasMoreElements()) {
				list.add(value.nextElement());
			}
			addHeader(header, list.toArray(new String[0]));
		}
		while (attributeNames.hasMoreElements()) {
			String	header		=	attributeNames.nextElement();
			Object	attribute	=	request.getAttribute(header);
			addAttribute(header, attribute);
		}
		while (parameterNames.hasMoreElements()) {
			String		header	=	parameterNames.nextElement();
			String[]	values	=	request.getParameterValues(header);
			addParameter(header, values);
		}
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
