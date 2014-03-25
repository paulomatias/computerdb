package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.domain.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ConnectionManager;
import com.excilys.persistence.DAOFactory;
import com.excilys.wrapper.Wrapper;

/* Singleton : enum will ensure that we really have a singleton (otherwise, a exploit can be done with the JVM to duplicate objects */
public enum CompanyService {
	INSTANCE;

	private CompanyService() {
	}

	public static CompanyService getInstance() {
		return INSTANCE;
	}

	private static DAOFactory daoFactory = DAOFactory.getInstance();
	private static CompanyDAO companyDAO = daoFactory.getCompanyDAO();
	static Logger log = LoggerFactory.getLogger(CompanyService.class);

	/*
	 * Return the wrapper to the addServlet, using transactions
	 */
	public Wrapper getAddComputerWrapper() {
		Connection connection = ConnectionManager.getConnection();
		List<Company> listCompanies = null;
		try {
			connection.setAutoCommit(false);
			log.info("Counting number of companies...");
			listCompanies = companyDAO.getList(connection);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Wrapper wrapper = Wrapper.builder().listCompanies(listCompanies)
				.build();
		return wrapper;
	}

}
