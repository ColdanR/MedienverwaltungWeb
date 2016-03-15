package gui;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gui.dto.BaseDTO;

public abstract class Controller {
	private	Pattern	pattern;
	
	protected Controller(String pattern) throws PatternSyntaxException {
		this.pattern = Pattern.compile(pattern);
	}
	
	public final boolean canHandle(String uri) {
		return pattern.matcher(uri).matches();
	}
	
	public final Pattern getPattern() {
		return pattern;
	}
	
	public	abstract	void	execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	protected void forward(HttpServletRequest request, HttpServletResponse response, BaseDTO dto, String filePath) throws ServletException, IOException {
		request.setAttribute("context", dto);
		request.getRequestDispatcher("/WEB-INF/classes/gui/jspFiles" + filePath).forward(request, response);
	}
	
	protected void redirect (HttpServletRequest request, HttpServletResponse response, String uri) {
		String server = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/" + uri;
		response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		response.setHeader("location", server);
	}
}