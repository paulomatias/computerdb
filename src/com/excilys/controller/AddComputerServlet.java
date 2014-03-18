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
public class AddComputerServlet extends HttpServlet {
	/* champ JSP, attribut de requête et vue */
	public static final String PARAM_NAME = "computerName";
	public static final String PARAM_INTRODUCED = "introduced";
	public static final String PARAM_DISCONTINUED = "discontinued";
	public static final String PARAM_COMPANY = "company";
	public static final String ATT_LIST_COMPANIES = "listCompanies";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_MESSAGE = "message";
	public static final String ATT_CURRENT_PAGE = "currentPage";
	public static final String ATT_NBR_OF_PAGE = "nbrOfPages";
	public static final String VIEW_POST = "/DisplayServlet";
	public static final String VIEW_GET = "/WEB-INF/addComputer.jsp";

	public static final SimpleDateFormat DateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CompanyService companyService = CompanyService.getInstance();
		List<Company> listCompanies = companyService.getList();
		request.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CompanyService companyService = CompanyService.getInstance();
		ComputerService computerService = ComputerService.getInstance();
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		Company company = new Company();
		Computer computer = new Computer();

		String name = request.getParameter(PARAM_NAME);
		String introduced = request.getParameter(PARAM_INTRODUCED);
		String discontinued = request.getParameter(PARAM_DISCONTINUED);
		String companyId = request.getParameter(PARAM_COMPANY);

		Date introducedDate = null;
		Date discontinuedDate = null;
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
		company.setName(companyService.getName(company.getId()));
		computer.setCompany(company);
		computerService.add(computer);

		List<Company> listCompanies = companyService.getList();
		List<Computer> listComputers = computerService.getList(page,
				DisplayServlet.recordsPerPage);
		Long nbrComputers = computerService.count();
		String message = "Computer added successfully !";
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ DisplayServlet.recordsPerPage);

		/*
		 * Ajout du bean et du message à l'objet requête et envoie de la vue par
		 * forward
		 */
		request.setAttribute(ATT_NBR_OF_PAGE, nbrOfPages);
		request.setAttribute(ATT_CURRENT_PAGE, page);
		request.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_MESSAGE, message);
		request.getRequestDispatcher(VIEW_POST).forward(request, response);
	}
}
