package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.domain.Wrapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.ServiceManager;
import com.excilys.transfert.ComputerDTO;

@SuppressWarnings("serial")
public class AddComputerServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_NAME = "computerName";
	public static final String PARAM_INTRODUCED = "introduced";
	public static final String PARAM_DISCONTINUED = "discontinued";
	public static final String PARAM_COMPANY = "company";
	public static final String PARAM_CURRENT_PAGE = "currentPage";
	public static final String ATT_WRAPPER = "wrapper";
	public static final String VIEW_POST = "/WEB-INF/dashboard.jsp";
	public static final String VIEW_GET = "/WEB-INF/addComputer.jsp";
	public static final ServiceManager serviceManager = ServiceManager
			.getInstance();

	public static final int recordsPercurrentPage = Wrapper.RECORDS_PER_PAGE;

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
		Wrapper computerWrapper = Wrapper.builder()
				.listCompanies(listCompanies).build();
		request.setAttribute(ATT_WRAPPER, computerWrapper);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * Get instance of services by serviceManager
		 */
		CompanyService companyService = serviceManager.getCompanyService();
		ComputerService computerService = serviceManager.getComputerService();

		/*
		 * GetParameters
		 */
		String name = request.getParameter(PARAM_NAME);
		String introduced = request.getParameter(PARAM_INTRODUCED);
		String discontinued = request.getParameter(PARAM_DISCONTINUED);
		String companyId = request.getParameter(PARAM_COMPANY);

		/*
		 * Setting computerDTO and add to db
		 */
		ComputerDTO computerDTO = ComputerDTO.builder().name(name)
				.introduced(introduced).discontinued(discontinued)
				.company(new Long(companyId)).build();
		computerService.add(computerDTO);

		/*
		 * Prepare attributes
		 */
		int currentPage = 1;
		if (request.getParameter(PARAM_CURRENT_PAGE) != null) {
			currentPage = Integer.parseInt(request
					.getParameter(PARAM_CURRENT_PAGE));
		}
		Long nbrComputers = computerService.count();
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ recordsPercurrentPage);
		List<Company> listCompanies = companyService.getList();
		List<Computer> listComputers = computerService.getList(null,
				currentPage, recordsPercurrentPage);

		String message = "Computer added successfully !";

		/*
		 * Set attributes and VIEW
		 */
		Wrapper wrapper = Wrapper.builder().currentPage(currentPage)
				.nbrOfPages(nbrOfPages).nbrComputers(nbrComputers)
				.listCompanies(listCompanies).listComputers(listComputers)
				.message(message).build();
		request.setAttribute(ATT_WRAPPER, wrapper);
		request.getRequestDispatcher(VIEW_POST).forward(request, response);
	}
}
