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
public class DeleteComputerServlet extends HttpServlet {
	/* champ JSP, attribut de requête et vue */
	public static final String FIELD_COMPUTER_ID = "id";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_NBR_COMPUTERS = "nbrComputers";
	public static final String ATT_MESSAGE = "message";
	public static final String VIEW_GET = "/WEB-INF/dashboard.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		Computer computer = new Computer();

		String ComputerId = request.getParameter(FIELD_COMPUTER_ID);
		List<Computer> listComputers = computerDAO.getListComputers();

		computer = computerDAO.getAComputer(Long.valueOf(ComputerId));
		computer.setId(Long.valueOf(ComputerId));
		computerDAO.deleteComputer(computer);
		listComputers = computerDAO.getListComputers();
		Long nbrComputers = computerDAO.countComputers();
		String message = "Computer deleted successfully !";
		/*
		 * Ajout du bean et du message à l'objet requête et envoie de la vue par
		 * forward
		 */
		request.setAttribute(ATT_LIST_COMPUTERS, listComputers);
		request.setAttribute(ATT_NBR_COMPUTERS, nbrComputers);
		request.setAttribute(ATT_MESSAGE, message);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);

	}
}
