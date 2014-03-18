package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayServlet extends HttpServlet {

	public static final String ID = "computerId";
	public static final String NAME = "computerName";
	public static final String INTRODUCED = "introduced";
	public static final String DISCONTINUED = "discontinued";
	public static final String COMPANY_NAME = "companyName";
	public static final String LIST_COMPANIES = "listCompanies";
	public static final String LIST_COMPUTERS = "listComputers";
	public static final String NBR_COMPUTERS = "nbrComputers";
	public static final String SEARCH_COMPUTER = "searchComputer";
	public static final String SEARCH_COMPANY = "searchCompany";
	public static final String MESSAGE = "message";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String NBR_OF_PAGE = "nbrOfPages";
	public static final String VIEW = "/WEB-INF/dashboard.jsp";
	public static final int recordsPerPage = 25;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute(ID, request.getAttribute(ID));
		request.setAttribute(NAME, request.getAttribute(NAME));
		request.setAttribute(INTRODUCED, request.getAttribute(INTRODUCED));
		request.setAttribute(DISCONTINUED, request.getAttribute(DISCONTINUED));
		request.setAttribute(COMPANY_NAME, request.getAttribute(COMPANY_NAME));
		request.setAttribute(LIST_COMPANIES,
				request.getAttribute(LIST_COMPANIES));
		request.setAttribute(LIST_COMPUTERS,
				request.getAttribute(LIST_COMPUTERS));
		request.setAttribute(NBR_COMPUTERS, request.getAttribute(NBR_COMPUTERS));
		request.setAttribute(SEARCH_COMPUTER,
				request.getAttribute(SEARCH_COMPUTER));
		request.setAttribute(SEARCH_COMPANY,
				request.getAttribute(SEARCH_COMPANY));
		request.setAttribute(MESSAGE, request.getAttribute(MESSAGE));
		request.setAttribute(CURRENT_PAGE, request.getAttribute(CURRENT_PAGE));
		request.setAttribute(NBR_OF_PAGE, request.getAttribute(NBR_OF_PAGE));
		request.getRequestDispatcher(VIEW).forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute(ID, request.getAttribute(ID));
		request.setAttribute(NAME, request.getAttribute(NAME));
		request.setAttribute(INTRODUCED, request.getAttribute(INTRODUCED));
		request.setAttribute(DISCONTINUED, request.getAttribute(DISCONTINUED));
		request.setAttribute(COMPANY_NAME, request.getAttribute(COMPANY_NAME));
		request.setAttribute(LIST_COMPANIES,
				request.getAttribute(LIST_COMPANIES));
		request.setAttribute(LIST_COMPUTERS,
				request.getAttribute(LIST_COMPUTERS));
		request.setAttribute(NBR_COMPUTERS, request.getAttribute(NBR_COMPUTERS));
		request.setAttribute(SEARCH_COMPUTER,
				request.getAttribute(SEARCH_COMPUTER));
		request.setAttribute(SEARCH_COMPANY,
				request.getAttribute(SEARCH_COMPANY));
		request.setAttribute(MESSAGE, request.getAttribute(MESSAGE));
		request.setAttribute(CURRENT_PAGE, request.getAttribute(CURRENT_PAGE));
		request.setAttribute(NBR_OF_PAGE, request.getAttribute(NBR_OF_PAGE));
		request.getRequestDispatcher(VIEW).forward(request, response);

	}
}
