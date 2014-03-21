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
import com.excilys.wrapper.Wrapper;

@SuppressWarnings("serial")
public class DeleteComputerServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_COMPUTER_ID = "id";
	public static final String PARAM_CURRENT_PAGE = "currentPage";
	public static final String ATT_WRAPPER = "wrapper";
	public static final String VIEW = "/WEB-INF/dashboard.jsp";
	public static final ServiceManager serviceManager = ServiceManager
			.getInstance();
	public static final Integer recordsPerPage = Wrapper.RECORDS_PER_PAGE;

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
		int currentPage = 1;
		if (request.getParameter(PARAM_CURRENT_PAGE) != null) {
			currentPage = Integer.parseInt(request
					.getParameter(PARAM_CURRENT_PAGE));
		}
		Long nbrComputers = computerService.count();
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0 / recordsPerPage);
		List<Computer> listComputers = computerService.getList();
		Computer computer = Computer.builder().id(Long.valueOf(ComputerId))
				.build();
		// computer = computerService.get(Long.valueOf(ComputerId));
		computerService.delete(computer);
		listComputers = computerService.getList(null, currentPage,
				recordsPerPage);
		String message = "Computer deleted successfully !";
		/*
		 * Ajout du bean et du message à l'objet requête et envoie de la vue par
		 * forward
		 */
		Wrapper wrapper = Wrapper.builder().currentPage(currentPage)
				.nbrComputers(nbrComputers).nbrOfPages(nbrOfPages)
				.listComputers(listComputers).message(message).build();
		request.setAttribute(ATT_WRAPPER, wrapper);
		request.getRequestDispatcher(VIEW).forward(request, response);

	}
}
