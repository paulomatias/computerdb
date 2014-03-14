package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.domain.Computer;
import com.excilys.persistence.ComputerDAO;

@SuppressWarnings("serial")
public class SelectComputerServlet extends HttpServlet {
	/* champ JSP, attribut de requêteet vue */
	public static final String FIELD_SEARCH_COMPUTER = "searchComputer";
	public static final String FIELD_SEARCH_COMANY = "searchCompany";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_MESSAGE = "message";
	public static final String VIEW = "/WEB-INF/dashboard.jsp";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* recueration de l'instance de computerDAO en cours */
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		List<Computer> listComputers = null;
		String message = null;
		Long nbrComputers = null;
		String computerName = request.getParameter(FIELD_SEARCH_COMPUTER);
		String computerCompanyName = request.getParameter(FIELD_SEARCH_COMANY);

		if (computerName.equals("") && computerCompanyName.equals("")) {
			listComputers = computerDAO.getListComputers();
			nbrComputers = computerDAO.countComputers();
			message = "dashboard";
		} else if (!computerName.equals("") && computerCompanyName.equals("")) {
			listComputers = computerDAO.getListComputersByName(computerName);
			nbrComputers = new Long(listComputers.size());
			message = "ComputerSelected";
		} else if (!computerName.equals("") && !computerCompanyName.equals("")) {
			listComputers = computerDAO.getListComputersByNameAndCompanyName(
					computerName, computerCompanyName);
			nbrComputers = new Long(listComputers.size());
			message = "ComputerSelected";
		} else if (computerName.equals("") && !computerCompanyName.equals("")) {
			listComputers = computerDAO
					.getListComputersByCompanyName(computerCompanyName);
			nbrComputers = new Long(listComputers.size());
			message = "Computer(s) selected successfully !";
		}
		/*
		 * Ajout du bean et du message à l'objet requête et envoie de la vue par
		 * forward
		 */
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_MESSAGE, message);

		this.getServletContext().getRequestDispatcher(VIEW)
				.forward(request, response);

	}
}
