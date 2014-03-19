package com.excilys.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.ServiceManager;

@SuppressWarnings("serial")
public class AddComputerServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_NAME = "computerName";
	public static final String PARAM_INTRODUCED = "introduced";
	public static final String PARAM_DISCONTINUED = "discontinued";
	public static final String PARAM_COMPANY = "company";
	public static final String PARAM_PAGE = "page";
	public static final String ATT_LIST_COMPANIES = "listCompanies";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_MESSAGE = "message";
	public static final String ATT_CURRENT_PAGE = "currentPage";
	public static final String ATT_NBR_OF_PAGE = "nbrOfPages";
	public static final String VIEW_POST = "/DisplayServlet";
	public static final String VIEW_GET = "/WEB-INF/addComputer.jsp";
	public static final ServiceManager serviceManager = ServiceManager
			.getInstance();
	public static final SimpleDateFormat DateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final int recordsPerPage = DisplayServlet.recordsPerPage;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Get instance of services by serviceManager
		 */
		CompanyService companyService = serviceManager.getCompanyService();
		/*
		 * Prepare attributes
		 */
		List<Company> listCompanies = companyService.getList();
		/*
		 * Set attributes and VIEW
		 */
		request.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Get instance of services by serviceManager
		 */
		CompanyService companyService = serviceManager.getCompanyService();
		ComputerService computerService = serviceManager.getComputerService();
		Company company = new Company();
		Computer computer = new Computer();
		Date introducedDate = null;
		Date discontinuedDate = null;

		/*
		 * GetParameters
		 */
		String name = request.getParameter(PARAM_NAME);
		String introduced = request.getParameter(PARAM_INTRODUCED);
		String discontinued = request.getParameter(PARAM_DISCONTINUED);
		String companyId = request.getParameter(PARAM_COMPANY);

		/*
		 * Test to parse dates
		 */
		if (!introduced.equals("")) {
			try {
				introducedDate = DateFormatter.parse(introduced);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!discontinued.equals("")) {
			try {
				discontinuedDate = DateFormatter.parse(discontinued);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		/*
		 * Setting computer and add to db
		 */
		computer.setName(name);
		computer.setIntroduced(introducedDate);
		computer.setDiscontinued(discontinuedDate);
		company.setId(new Long(companyId));
		company.setName(companyService.getName(company.getId()));
		computer.setCompany(company);
		computerService.add(computer);

		/*
		 * Prepare attributes
		 */
		int page = 1;
		if (request.getParameter(PARAM_PAGE) != null) {
			page = Integer.parseInt(request.getParameter(PARAM_PAGE));
		}
		Long nbrComputers = computerService.count();
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0 / recordsPerPage);
		List<Company> listCompanies = companyService.getList();
		List<Computer> listComputers = computerService.getList(page,
				recordsPerPage);

		String message = "Computer added successfully !";

		/*
		 * Set attributes and VIEW
		 */
		request.setAttribute(ATT_CURRENT_PAGE, page);
		request.setAttribute(ATT_NBR_OF_PAGE, nbrOfPages);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_MESSAGE, message);
		request.getRequestDispatcher(VIEW_POST).forward(request, response);
	}
}
