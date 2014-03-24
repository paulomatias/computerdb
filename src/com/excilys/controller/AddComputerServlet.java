package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.ServiceManager;
import com.excilys.transfert.ComputerDTO;
import com.excilys.validator.Validator;
import com.excilys.wrapper.Wrapper;

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
		 * Get the wrapper to return to the JSP. All functions necessary are
		 * done in the service package.
		 */
		Wrapper wrapper = companyService.getAddComputerWrapper();

		/*
		 * Set attributes and VIEW
		 */
		request.setAttribute(ATT_WRAPPER, wrapper);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * Get instance of services by serviceManager
		 */
		ComputerService computerService = serviceManager.getComputerService();

		/*
		 * GetParameters
		 */
		String name = request.getParameter(PARAM_NAME);
		String introduced = request.getParameter(PARAM_INTRODUCED);
		String discontinued = request.getParameter(PARAM_DISCONTINUED);
		String companyId = request.getParameter(PARAM_COMPANY);

		/*
		 * Setting computerDTO
		 */
		ComputerDTO computerDTO = ComputerDTO.builder().name(name)
				.introduced(introduced).discontinued(discontinued)
				.company(new Long(companyId)).build();

		/*
		 * Get the wrapper to return to the JSP. All functions necessary are
		 * done in the service package.
		 */
		Integer currentPage = 1;
		if (request.getParameter(PARAM_CURRENT_PAGE) != null) {
			currentPage = Integer.parseInt(request
					.getParameter(PARAM_CURRENT_PAGE));
		}

		Validator validator = new Validator();
		if (validator.getValidation(computerDTO).equals(0)) {
			/*
			 * Get the wrapper to return to the JSP. All functions necessary are
			 * done in the service package.
			 */
			Wrapper wrapper = computerService.getAddComputerWrapper(
					currentPage, computerDTO);

			/*
			 * Set attributes and VIEW
			 */
			request.setAttribute(ATT_WRAPPER, wrapper);
			request.getRequestDispatcher(VIEW_POST).forward(request, response);
		}
		if (validator.getValidation(computerDTO).equals(1)) {

		}
		if (validator.getValidation(computerDTO).equals(2)) {

		}
		if (validator.getValidation(computerDTO).equals(3)) {

		}
	}
}
