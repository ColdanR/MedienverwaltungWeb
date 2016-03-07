package gui.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gui.Controller;
import gui.dto.IndexDTO;

public class IndexController extends Controller {
	public IndexController() {
		super("/(index\\.html)?");
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IndexDTO dto = new IndexDTO();
		forward(request, response, dto, "/index.jsp");
	}
}
