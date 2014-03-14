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
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;

@SuppressWarnings("serial")
public class AddComputerServlet extends HttpServlet {
	/* champ JSP, attribut de requête et vue */
	public static final String FIELD_NAME = "name";
	public static final String FIELD_INTRODUCED = "introducedDate";
	public static final String FIELD_DISCONTINUED = "discontinuedDate";
	public static final String FIELD_COMPANY = "company";
	public static final String ATT_LIST_COMPANIES = "listCompanies";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_MESSAGE = "message";
	public static final String VIEW_POST = "/WEB-INF/dashboard.jsp";
	public static final String VIEW_GET = "/WEB-INF/addComputer.jsp";
	public static final SimpleDateFormat DateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CompanyDAO companyDAO = CompanyDAO.getInstance();
		List<Company> listCompanies = companyDAO.getListCompanies();
		request.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CompanyDAO companyDAO = CompanyDAO.getInstance();
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		Company company = new Company();
		Computer computer = new Computer();

		String name = request.getParameter(FIELD_NAME);
		String introduced = request.getParameter(FIELD_INTRODUCED);
		String discontinued = request.getParameter(FIELD_DISCONTINUED);
		String companyId = request.getParameter(FIELD_COMPANY);

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
		company.setName(companyDAO.getCompanyName(company.getId()));
		computer.setCompany(company);
		computerDAO.addComputer(computer);

		List<Company> listCompanies = companyDAO.getListCompanies();
		List<Computer> listComputers = computerDAO.getListComputers();
		Long nbrComputers = computerDAO.countComputers();
		String message = "Computer added successfully !";

		/*
		 * Ajout du bean et du message à l'objet requête et envoie de la vue par
		 * forward
		 */
		request.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_MESSAGE, message);
		request.getRequestDispatcher(VIEW_POST).forward(request, response);
	}
}
