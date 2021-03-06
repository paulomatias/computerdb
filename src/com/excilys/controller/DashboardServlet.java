package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.WrapperMapper;
import com.excilys.service.ComputerService;
import com.excilys.service.ServiceManager;
import com.excilys.wrapper.ComputerWrapper;
import com.excilys.wrapper.DTOWrapper;

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
	public static final Integer recordsPercurrentPage = DTOWrapper.RECORDS_PER_PAGE;
	public static Logger logger = LoggerFactory
			.getLogger(DashboardServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("Enterring DashboardServlet doGet.");

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
		ComputerWrapper computerWrapper = null;
		if (searchComputer == null) {
			searchComputer = "";
		}
		if (searchCompany == null) {
			searchCompany = "";
		}
		if (searchComputer.equals("") && searchCompany.equals("")) {
			logger.debug("no search found");
			computerWrapper = computerService.dashboard(currentPage, orderBy);
		} else if (!searchComputer.equals("") && searchCompany.equals("")) {
			logger.debug("search company");
			computerWrapper = computerService.dashboardSearchComputer(orderBy,
					currentPage, searchComputer);
		} else if ((!searchComputer.equals("") && !searchCompany.equals(""))) {
			logger.debug("search computer and company");
			computerWrapper = computerService
					.dashboardSearchCompanySearchComputer(orderBy, currentPage,
							searchCompany, searchComputer);
		} else if (searchComputer.equals("") && !searchCompany.equals("")) {
			logger.debug("search comptuer");
			computerWrapper = computerService.dashboardSearchCompany(orderBy,
					currentPage, searchCompany);
		}
		WrapperMapper wrapperMapper = new WrapperMapper();
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
		DTOWrapper.builder().currentPage(currentPage)
				.searchCompany(searchCompany).searchComputer(searchComputer)
				.orderBy(orderBy).build();

		request.setAttribute(ATT_WRAPPER, dtoWrapper);
		logger.debug("Leaving DashboardServlet doGet.");
		request.getRequestDispatcher(VIEW).forward(request, response);

	}
}
