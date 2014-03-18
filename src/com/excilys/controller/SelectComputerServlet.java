package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.domain.Computer;
import com.excilys.service.ComputerService;

@SuppressWarnings("serial")
public class SelectComputerServlet extends HttpServlet {
	/* champ JSP, attribut de requêteet vue */
	public static final String PARAM_SEARCH_COMPUTER = "searchComputer";
	public static final String PARAM_SEARCH_COMANY = "searchCompany";
	public static final String PARAM_NAME = "computerName";
	public static final String PARAM_COMPANY_NAME = "computerCompanyName";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_SEARCH_COMPUTER = "searchComputer";
	public static final String ATT_SEARCH_COMPANY = "searchCompany";
	public static final String ATT_MESSAGE = "message";
	public static final String ATT_CURRENT_PAGE = "currentPage";
	public static final String ATT_NBR_OF_PAGE = "nbrOfPages";
	public static final String VIEW = "/WEB-INF/dashboard.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1;
		int search = 0;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		if (request.getParameter("search") != null) {
			search = Integer.parseInt(request.getParameter("search"));
		}
		ComputerService computerService = ComputerService.getInstance();
		List<Computer> listComputers = null;
		String message = null;
		Long nbrComputers = null;

		String computerName = request.getParameter(PARAM_SEARCH_COMPUTER);
		String computerCompanyName = request.getParameter(PARAM_SEARCH_COMANY);

		if ((computerName.equals("") && computerCompanyName.equals(""))
				|| search == 1) {
			search = 1;
			listComputers = computerService.getList(page,
					DisplayServlet.recordsPerPage);
			nbrComputers = computerService.count();
			message = "Welcome to your computer database !";

		} else if ((!computerName.equals("") && computerCompanyName.equals(""))
				|| search == 2) {
			search = 2;
			listComputers = computerService.getListByName(computerName, page,
					DisplayServlet.recordsPerPage);
			nbrComputers = computerService.countByName(computerName);
			message = "Computer(s) selected successfully !";
		} else if ((!computerName.equals("") && !computerCompanyName.equals(""))
				|| search == 3) {
			search = 3;
			listComputers = computerService.getListByNameAndCompanyName(
					computerName, computerCompanyName, page,
					DisplayServlet.recordsPerPage);
			nbrComputers = computerService.countByNameAndCompanyName(
					computerName, computerCompanyName);
			message = "Computer(s) selected successfully !";
		} else if ((computerName.equals("") && !computerCompanyName.equals(""))
				|| search == 4) {
			search = 4;
			listComputers = computerService.getListByCompanyName(
					computerCompanyName, page, DisplayServlet.recordsPerPage);
			nbrComputers = computerService
					.countByCompanyName(computerCompanyName);
			message = "Computer(s) selected successfully !";
		}

		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ DisplayServlet.recordsPerPage);
		/*
		 * Ajout du bean et du message à l'objet requête et envoie de la vue par
		 * forward
		 */
		request.setAttribute(PARAM_NAME, computerName);
		request.setAttribute(PARAM_COMPANY_NAME, computerCompanyName);
		request.setAttribute(ATT_NBR_OF_PAGE, nbrOfPages);
		request.setAttribute(ATT_CURRENT_PAGE, page);
		request.setAttribute(ATT_SEARCH_COMPUTER, computerName);
		request.setAttribute(ATT_SEARCH_COMPANY, computerCompanyName);
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_MESSAGE, message);
		this.getServletContext().getRequestDispatcher(VIEW)
				.forward(request, response);

	}
}
