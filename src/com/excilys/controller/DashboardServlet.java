package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.domain.Computer;
import com.excilys.domain.Wrapper;
import com.excilys.service.ComputerService;
import com.excilys.service.ServiceManager;

@SuppressWarnings("serial")
public class DashboardServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_CURRENTPAGE = "currentPage";
	public static final String PARAM_ORDER_BY = "orderBy";
	public static final String ATT_WRAPPER = "wrapper";
	public static final String VIEW = "/WEB-INF/dashboard.jsp";
	public static final ServiceManager serviceManager = ServiceManager
			.getInstance();
	public static final Integer recordsPercurrentPage = Wrapper.RECORDS_PER_PAGE;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Get instance of services by serviceManager
		 */
		ComputerService computerService = serviceManager.getComputerService();
		/*
		 * Prepare attributes
		 */
		int currentPage = 1;

		if (request.getParameter(PARAM_CURRENTPAGE) != null) {
			currentPage = Integer.parseInt(request
					.getParameter(PARAM_CURRENTPAGE));
		}
		String orderBy = null;
		if (request.getParameter(PARAM_ORDER_BY) != null) {
			orderBy = request.getParameter(PARAM_ORDER_BY);
		}
		Long nbrComputers = computerService.count();
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ recordsPercurrentPage);
		List<Computer> listComputers = computerService.getList(orderBy,
				currentPage, recordsPercurrentPage);
		String message = "Welcome to your computer database !";
		/*
		 * Set attributes and VIEW
		 */
		Wrapper wrapper = Wrapper.builder().currentPage(currentPage)
				.nbrOfPages(nbrOfPages).nbrComputers(nbrComputers)
				.listComputers(listComputers).message(message).orderBy(orderBy)
				.build();
		request.setAttribute(ATT_WRAPPER, wrapper);
		request.getRequestDispatcher(VIEW).forward(request, response);

	}
}
