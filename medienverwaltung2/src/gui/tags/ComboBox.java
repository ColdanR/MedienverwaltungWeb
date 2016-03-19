package gui.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import interfaces.SelectAble;

public class ComboBox extends TagSupport {
	private static final long serialVersionUID = 1L;
	private	static	final Logger	LOGGER	=	LogManager.getLogger(ComboBox.class);
	
	private	List<SelectAble>	selectOptions;
	private	List<SelectAble>	selectedList;
	private	SelectAble			selected;
	
	private	String				parameterName;
	private	String				title;
	private	String				className;
	private	boolean				multiple		=	false;
	
	private	boolean				readonly		=	false;
	private	boolean				noDefault		=	false;
	
	@Override
	public int doStartTag() throws JspException {
		if (selectOptions == null ||
				parameterName == null || parameterName.length() == 0) {
			throw new JspException("Fehlerhafte Parameter!");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<select name=\"").append(parameterName).append("\" id=\"").append(parameterName)
		.append("\"");
		if (className != null) {
			sb.append(" class=\"").append(className).append("\"");
		}
		if (title != null) {
			sb.append(" title=\"").append(title).append("\"");
		}
		if (multiple) {
			sb.append(" multiple");
		}
		if (readonly) {
			sb.append(" disabled");
		}
		sb.append(">").append(System.lineSeparator());
		if (!noDefault) {
			sb.append("<option value=\"-1\">Keine Auswahl</option>").append(System.lineSeparator());
		}
		for (SelectAble element : selectOptions) {
			sb.append("<option value=\"").append(element.getId()).append("\"");
			if (selected != null && selected.equals(element)) {
				sb.append(" selected");
			} else if (selectedList != null && selectedList.size() > 0 && selectedList.contains(element)) {
				sb.append(" selected");
			}
			sb.append(">").append(element.getBezeichnung())
			.append("</option>").append(System.lineSeparator());
		}
		sb.append("</select>").append(System.lineSeparator());
		JspWriter	writer = pageContext.getOut();
		try {
			writer.write(sb.toString());
		} catch (IOException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return SKIP_BODY;
	}
	
	public List<SelectAble> getSelectOptions() {
		return selectOptions;
	}
	public void setSelectOptions(List<SelectAble> selectOptions) {
		this.selectOptions = selectOptions;
	}
	public SelectAble getSelected() {
		return selected;
	}
	public void setSelected(SelectAble selected) {
		this.selected = selected;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public List<SelectAble> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<SelectAble> selectedList) {
		this.selectedList = selectedList;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isNoDefault() {
		return noDefault;
	}

	public void setNoDefault(boolean noDefault) {
		this.noDefault = noDefault;
	}
}
