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
public class DashboardServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_SEARCH_COMPUTER = "searchComputer";
	public static final String PARAM_SEARCH_COMANY = "searchCompany";
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
		 * Get parameters
		 */
		String searchComputer = request.getParameter(PARAM_SEARCH_COMPUTER);
		String searchCompany = request.getParameter(PARAM_SEARCH_COMANY);
		Integer currentPage = 1;
		if (request.getParameter(PARAM_CURRENTPAGE) != null) {
			currentPage = Integer.parseInt(request
					.getParameter(PARAM_CURRENTPAGE));
		}
		String orderBy = null;
		if (request.getParameter(PARAM_ORDER_BY) != null) {
			orderBy = request.getParameter(PARAM_ORDER_BY);
		}

		/*
		 * Get the wrapper to return to the JSP. All functions necessary are
		 * done in the service package.
		 */
		Wrapper wrapper = null;
		if (searchComputer == null) {
			searchComputer = "";
		}
		if (searchCompany == null) {
			searchCompany = "";
		}
		if (searchComputer.equals("") && searchCompany.equals("")) {
			wrapper = computerService.getDashboardWrapper(currentPage, orderBy);
		} else if (!searchComputer.equals("") && searchCompany.equals("")) {
			wrapper = computerService.getSelectComputerWrapperSearchComputer(
					orderBy, currentPage, searchComputer);
		} else if ((!searchComputer.equals("") && !searchCompany.equals(""))) {
			wrapper = computerService
					.getSelectComputerWrapperSearchCompanySearchComputer(
							orderBy, currentPage, searchCompany, searchComputer);
		} else if (searchComputer.equals("") && !searchCompany.equals("")) {
			wrapper = computerService.getSelectComputerWrapperSearchCompany(
					orderBy, currentPage, searchCompany);
		}

		request.setAttribute(ATT_WRAPPER, wrapper);
		request.getRequestDispatcher(VIEW).forward(request, response);

	}
}
