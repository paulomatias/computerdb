package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.domain.Computer;
import com.excilys.service.ComputerService;

@SuppressWarnings("serial")
public class DeleteComputerServlet extends HttpServlet {
	/* champ JSP, attribut de requête et vue */
	public static final String PARAM_COMPUTER_ID = "id";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_MESSAGE = "message";
	public static final String ATT_CURRENT_PAGE = "currentPage";
	public static final String ATT_NBR_OF_PAGE = "nbrOfPages";
	public static final int recordsPerPage = 25;
	public static final String VIEW_GET = "/WEB-INF/dashboard.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		ComputerService computerService = ComputerService.getInstance();
		Computer computer = new Computer();

		String ComputerId = request.getParameter(PARAM_COMPUTER_ID);
		List<Computer> listComputers = computerService.getList();
		computer = computerService.get(Long.valueOf(ComputerId));
		computer.setId(Long.valueOf(ComputerId));
		computerService.delete(computer);
		listComputers = computerService.getList(page, recordsPerPage);
		Long nbrComputers = computerService.count();
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0 / recordsPerPage);
		String message = "Computer deleted successfully !";
		/*
		 * Ajout du bean et du message à l'objet requête et envoie de la vue par
		 * forward
		 */
		request.setAttribute(ATT_NBR_OF_PAGE, nbrOfPages);
		request.setAttribute(ATT_CURRENT_PAGE, page);
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_MESSAGE, message);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);

	}
}
