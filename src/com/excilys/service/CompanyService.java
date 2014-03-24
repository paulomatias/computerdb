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

public class CompanyService {
	private final static CompanyService instance = new CompanyService();
	private static DAOFactory daoFactory = DAOFactory.getInstance();
	private static CompanyDAO companyDAO = daoFactory.getCompanyDAO();

	/*
	 * Logger
	 */
	static Logger log = LoggerFactory.getLogger(CompanyService.class.getName());

	/*
	 * Singleton
	 */
	private CompanyService() {
	}

	public static CompanyService getInstance() {
		return instance;
	}

	/*
	 * Service
	 */

	public Wrapper getAddComputerWrapper() {
		Connection connection = ConnectionManager.getConnection();
		List<Company> listCompanies = null;
		try {
			connection.setAutoCommit(false);
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
