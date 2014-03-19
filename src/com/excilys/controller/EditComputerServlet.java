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
public class EditComputerServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_COMPUTER_ID = "id";
	public static final String PARAM_NAME = "computerName";
	public static final String PARAM_INTRODUCED = "introduced";
	public static final String PARAM_DISCONTINUED = "discontinued";
	public static final String PARAM_COMPANY = "company";
	public static final String ATT_ID = "computerId";
	public static final String ATT_NAME = "computerName";
	public static final String ATT_INTRODUCED = "introduced";
	public static final String ATT_DISCONTINUED = "discontinued";
	public static final String ATT_COMPANY_NAME = "companyName";
	public static final String ATT_LIST_COMPANIES = "listCompanies";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_MESSAGE = "message";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_CURRENT_PAGE = "currentPage";
	public static final String ATT_NBR_OF_PAGE = "nbrOfPages";
	public static final String VIEW_GET = "/WEB-INF/editComputer.jsp";
	public static final String VIEW_POST = "/DisplayServlet";
	public static final SimpleDateFormat DateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final ServiceManager serviceManager = ServiceManager
			.getInstance();
	public static final int recordsPerPage = DisplayServlet.recordsPerPage;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Get instance of services by serviceManager
		 */
		ComputerService computerService = serviceManager.getComputerService();
		CompanyService companyService = serviceManager.getCompanyService();

		/*
		 * GetParameters
		 */
		String computerId = request.getParameter(PARAM_COMPUTER_ID);

		/*
		 * getting paramters of the computer to edit and prepare attributes
		 */
		Computer computer = computerService.get(Long.valueOf(computerId));
		Long id = computer.getId();
		String name = computer.getName();
		Date introduced = computer.getIntroduced();
		Date discontinued = computer.getDiscontinued();
		String companyName = computer.getCompany().getName();
		List<Company> listCompanies = companyService.getList();

		/*
		 * Test to format dates
		 */
		if (introduced != null) {
			String introducedDate = DateFormatter.format(introduced);
			request.setAttribute(ATT_INTRODUCED, introducedDate);
		}

		if (discontinued != null) {
			String discontinuedDate = DateFormatter.format(discontinued);
			request.setAttribute(ATT_DISCONTINUED, discontinuedDate);
		}

		/*
		 * Set attributes and VIEW
		 */
		request.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		request.setAttribute(ATT_ID, id);
		request.setAttribute(ATT_NAME, name);
		request.setAttribute(ATT_COMPANY_NAME, companyName);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Get instance of services by serviceManager
		 */
		ComputerService computerService = ComputerService.getInstance();

		/*
		 * GetParameters
		 */
		String computerId = request.getParameter(PARAM_COMPUTER_ID);
		String name = request.getParameter(PARAM_NAME);
		String introduced = request.getParameter(PARAM_INTRODUCED);
		String discontinued = request.getParameter(PARAM_DISCONTINUED);
		String companyId = request.getParameter(PARAM_COMPANY);

		/*
		 * Test to parse dates
		 */
		Date introducedDate = new Date();
		Date discontinuedDate = new Date();
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
		 * Setting computer and edit the db
		 */
		Company company = Company.builder().id(new Long(companyId)).build();

		Computer computer = Computer.builder().id(Long.valueOf(computerId))
				.name(name).introduced(introducedDate)
				.discontinued(discontinuedDate).company(company).build();
		computerService.edit(computer);

		/*
		 * Prepare attributes
		 */
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		Long nbrComputers = computerService.count();
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ DisplayServlet.recordsPerPage);
		List<Computer> listComputers = computerService.getList(page,
				DisplayServlet.recordsPerPage);
		String message = "Computer edited successfully !";

		/*
		 * Set attributes and VIEW
		 */
		request.setAttribute(ATT_NBR_OF_PAGE, nbrOfPages);
		request.setAttribute(ATT_CURRENT_PAGE, page);
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_MESSAGE, message);
		request.getRequestDispatcher(VIEW_POST).forward(request, response);

	}

}
