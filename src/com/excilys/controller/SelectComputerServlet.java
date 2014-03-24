package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		Wrapper wrapper = null;
		if (searchComputer == null) {
			searchComputer = "";
		}
		if (searchCompany == null) {
			searchCompany = "";
		}
		if (searchComputer.equals("") && searchCompany.equals("")) {
			wrapper = computerService.getSelectComputerWrapperNoSearch(orderBy,
					currentPage, recordsPerPage);

		} else if (!searchComputer.equals("") && searchCompany.equals("")) {
			wrapper = computerService.getSelectComputerWrapperSearchComputer(
					orderBy, currentPage, recordsPerPage, searchComputer);

		} else if ((!searchComputer.equals("") && !searchCompany.equals(""))) {
			wrapper = computerService
					.getSelectComputerWrapperSearchCompanySearchComputer(
							orderBy, currentPage, recordsPerPage,
							searchCompany, searchComputer);

		} else if (searchComputer.equals("") && !searchCompany.equals("")) {
			wrapper = computerService.getSelectComputerWrapperSearchCompany(
					orderBy, currentPage, recordsPerPage, searchCompany);
		}

		/*
		 * Set attributes and VIEW
		 */

		request.setAttribute(ATT_WRAPPER, wrapper);
		this.getServletContext().getRequestDispatcher(VIEW)
				.forward(request, response);

	}
}
