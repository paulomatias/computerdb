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
public class SelectComputerServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_SEARCH_COMPUTER = "searchComputer";
	public static final String PARAM_SEARCH_COMANY = "searchCompany";
	public static final String PARAM_NAME = "computerName";
	public static final String PARAM_COMPANY_NAME = "computerCompanyName";
	public static final String PARAM_CURRENT_PAGE = "currentPage";
	public static final String PARAM_ORDER_BY = "orderBy";
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
		List<Computer> listComputers = null;
		String message = null;
		Long nbrComputers = null;

		/*
		 * GetParameters
		 */
		int currentPage = 1;
		if (request.getParameter(PARAM_CURRENT_PAGE) != null) {
			currentPage = Integer.parseInt(request
					.getParameter(PARAM_CURRENT_PAGE));
		}
		String orderBy = null;
		if (request.getParameter(PARAM_ORDER_BY) != null) {
			orderBy = request.getParameter(PARAM_ORDER_BY);
		}
		String searchComputer = request.getParameter(PARAM_SEARCH_COMPUTER);
		String searchCompany = request.getParameter(PARAM_SEARCH_COMANY);

		/*
		 * Tests ans set attributes
		 */

		if (searchComputer == null) {
			searchComputer = "";
		}
		if (searchCompany == null) {
			searchCompany = "";
		}
		if (searchComputer.equals("") && searchCompany.equals("")) {
			listComputers = computerService.getList(orderBy, currentPage,
					recordsPerPage);
			nbrComputers = computerService.count();
			message = "Welcome to your computer database !";
		} else if (!searchComputer.equals("") && searchCompany.equals("")) {
			listComputers = computerService.getListByName(searchComputer,
					orderBy, currentPage, recordsPerPage);
			nbrComputers = computerService.countByName(searchComputer);
			message = "Computer(s) selected successfully !";
		} else if ((!searchComputer.equals("") && !searchCompany.equals(""))) {
			listComputers = computerService.getListByNameAndCompanyName(
					searchComputer, searchCompany, orderBy, currentPage,
					recordsPerPage);
			nbrComputers = computerService.countByNameAndCompanyName(
					searchComputer, searchCompany);
			message = "Computer(s) selected successfully !";
		} else if (searchComputer.equals("") && !searchCompany.equals("")) {
			listComputers = computerService.getListByCompanyName(searchCompany,
					orderBy, currentPage, recordsPerPage);
			nbrComputers = computerService.countByCompanyName(searchCompany);
			message = "Computer(s) selected successfully !";
		}
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0 / recordsPerPage);

		/*
		 * Set attributes and VIEW
		 */
		Wrapper wrapper = Wrapper.builder().currentPage(currentPage)
				.nbrOfPages(nbrOfPages).searchComputer(searchComputer)
				.searchCompany(searchCompany).listComputers(listComputers)
				.nbrComputers(nbrComputers).orderBy(orderBy).message(message)
				.build();
		request.setAttribute(ATT_WRAPPER, wrapper);
		this.getServletContext().getRequestDispatcher(VIEW)
				.forward(request, response);

	}
}
