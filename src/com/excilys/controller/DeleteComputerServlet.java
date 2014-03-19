package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.domain.Computer;
import com.excilys.service.ComputerService;
import com.excilys.service.ServiceManager;

@SuppressWarnings("serial")
public class DeleteComputerServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_COMPUTER_ID = "id";
	public static final String PARAM_PAGE = "page";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_MESSAGE = "message";
	public static final String ATT_CURRENT_PAGE = "currentPage";
	public static final String ATT_NBR_OF_PAGE = "nbrOfPages";
	public static final String VIEW = "/DisplayServlet";
	public static final ServiceManager serviceManager = ServiceManager
			.getInstance();
	public static final int recordsPerPage = DisplayServlet.recordsPerPage;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Get instance of services by serviceManager
		 */
		ComputerService computerService = serviceManager.getComputerService();
		/*
		 * GetParameters
		 */
		String ComputerId = request.getParameter(PARAM_COMPUTER_ID);
		/*
		 * Prepare attributes
		 */
		int page = 1;
		if (request.getParameter(PARAM_PAGE) != null) {
			page = Integer.parseInt(request.getParameter(PARAM_PAGE));
		}
		Long nbrComputers = computerService.count();
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0 / recordsPerPage);
		List<Computer> listComputers = computerService.getList();
		Computer computer = Computer.builder().id(Long.valueOf(ComputerId))
				.build();
		// computer = computerService.get(Long.valueOf(ComputerId));
		computerService.delete(computer);
		listComputers = computerService.getList(page,
				DisplayServlet.recordsPerPage);
		String message = "Computer deleted successfully !";
		/*
		 * Ajout du bean et du message à l'objet requête et envoie de la vue par
		 * forward
		 */
		request.setAttribute(ATT_CURRENT_PAGE, page);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_NBR_OF_PAGE, nbrOfPages);
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_MESSAGE, message);
		request.getRequestDispatcher(VIEW).forward(request, response);

	}
}
