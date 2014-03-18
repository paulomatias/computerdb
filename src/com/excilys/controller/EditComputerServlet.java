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

@SuppressWarnings("serial")
public class EditComputerServlet extends HttpServlet {
	/* champ JSP, attribut de requête et vue */
	public static final String PARAM_COMPUTER_ID = "id";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_INTRODUCED = "introducedDate";
	public static final String PARAM_DISCONTINUED = "discontinuedDate";
	public static final String PARAM_COMPANY = "company";
	public static final String ATT_ID = "computerId";
	public static final String ATT_NAME = "name";
	public static final String ATT_INTRODUCED = "introduced";
	public static final String ATT_DISCONTINUED = "discontinued";
	public static final String ATT_COMPANY_NAME = "companyName";
	public static final String ATT_LIST_COMPANIES = "listCompanies";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_MESSAGE = "message";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_CURRENT_PAGE = "currentPage";
	public static final String ATT_NBR_OF_PAGE = "nbrOfPages";
	public static final int recordsPerPage = 25;
	public static final String VIEW_GET = "/WEB-INF/editComputer.jsp";
	public static final String VIEW_POST = "/WEB-INF/dashboard.jsp";
	public static final SimpleDateFormat DateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ComputerService computerService = ComputerService.getInstance();
		CompanyService companyService = CompanyService.getInstance();
		String computerId = request.getParameter(PARAM_COMPUTER_ID);
		Computer computer = computerService.get(Long.valueOf(computerId));

		Long id = computer.getId();
		String name = computer.getName();
		Date introduced = computer.getIntroduced();
		Date discontinued = computer.getDiscontinued();
		String companyName = computer.getCompany().getName();
		List<Company> listCompanies = companyService.getList();
		String introducedDate = DateFormatter.format(introduced);
		String discontinuedDate = DateFormatter.format(discontinued);
		request.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		request.setAttribute(ATT_ID, id);
		request.setAttribute(ATT_NAME, name);
		request.setAttribute(ATT_INTRODUCED, introducedDate);
		request.setAttribute(ATT_DISCONTINUED, discontinuedDate);
		request.setAttribute(ATT_COMPANY_NAME, companyName);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		ComputerService computerService = ComputerService.getInstance();
		Company company = new Company();
		Computer computer = new Computer();

		String computerId = request.getParameter(PARAM_COMPUTER_ID);
		String name = request.getParameter(PARAM_NAME);
		String introduced = request.getParameter(PARAM_INTRODUCED);
		String discontinued = request.getParameter(PARAM_DISCONTINUED);
		String companyId = request.getParameter(PARAM_COMPANY);

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
		computer.setName(name);
		computer.setIntroduced(introducedDate);
		computer.setDiscontinued(discontinuedDate);
		company.setId(new Long(companyId));
		computer.setCompany(company);
		computer.setId(Long.valueOf(computerId));

		computerService.edit(computer);
		List<Computer> listComputers = computerService.getList(page,
				recordsPerPage);

		Long nbrComputers = computerService.count();
		String message = "Computer edited successfully !";
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0 / recordsPerPage);

		request.setAttribute(ATT_NBR_OF_PAGE, nbrOfPages);
		request.setAttribute(ATT_CURRENT_PAGE, page);
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_MESSAGE, message);
		request.getRequestDispatcher(VIEW_POST).forward(request, response);

	}

}
