package com.company.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.company.dao.CompanyDAO;
import com.company.dao.ComputerDAO;
import com.company.om.Company;
import com.company.om.Computer;

@SuppressWarnings("serial")
public class AddComputerServlet extends HttpServlet {

	/* Constantes des différents champs */
	public static final String FIELD_NAME = "name";
	public static final String FIELD_INTRODUCED = "introducedDate";
	public static final String FIELD_DISCONTINUED = "discontinuedDate";
	public static final String FIELD_COMPANY = "company";
	/* Constantes des différents attributs */
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	/* Constante de la vue renvoyée */
	public static final String VEW = "/dashboard.jsp";

	/* Méthde post */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Creation de l'initial context et de la datasource */
		Context ctx;
		try {
			ctx = new InitialContext();

			DataSource ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/computer-database-db");

			/* Creation du dao, de la company du computer */
			ComputerDAO computerDAO = new ComputerDAO();
			CompanyDAO companyDAO = new CompanyDAO();
			Company company = new Company();
			Computer computer = new Computer();

			/* Recupération de la liste de computers de de companies */
			ArrayList<Computer> listComputers = new ArrayList<Computer>();
			listComputers = (ArrayList<Computer>) computerDAO
					.getListComputers(ds.getConnection());
			ArrayList<Company> listCompanies = new ArrayList<Company>();
			listCompanies = (ArrayList<Company>) companyDAO.getListCompanies(ds
					.getConnection());

			/* Recuperation des parametres de la JSP */
			String name = request.getParameter(FIELD_NAME);
			String introduced = request.getParameter(FIELD_INTRODUCED);
			String discontinued = request.getParameter(FIELD_DISCONTINUED);
			String companyId = request.getParameter(FIELD_COMPANY);

			/* Partie du code pour obtenir des dates à partir du String recupere */

			Date introducedDate = new Date();
			Date discontinuedDate = new Date();
			SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				introducedDate = DateFormatter.parse(introduced);
				discontinuedDate = DateFormatter.parse(discontinued);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			computer.setName(name);
			computer.setIntroduced(introducedDate);
			computer.setDiscontinued(discontinuedDate);
			company.setId(new Long(companyId));
			computer.setCompany(company);

			listCompanies = (ArrayList<Company>) companyDAO.getListCompanies(ds
					.getConnection());
			System.out.println(listCompanies);
			company.setName(listCompanies.get(Integer.parseInt(companyId))
					.getName());
			System.out.println(computer.getCompany().getName());

			computerDAO.addComputer(computer, ds.getConnection());
			listComputers = (ArrayList<Computer>) computerDAO
					.getListComputers(ds.getConnection());

			System.out.println(listComputers);

			/* Ajout du bean et du message à l'objet requête */
			request.setAttribute(ATT_LIST_COMPUTERS, listComputers);

		} catch (NamingException e) {
			System.out.println("exceptioncatch1 addComputerServet");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("exceptioncatch2 addComputerServet");
			e.printStackTrace();

		}
		/* Envoie de la vue vers VEW apr forward */
		this.getServletContext().getRequestDispatcher(VEW)
				.forward(request, response);

	}
}
