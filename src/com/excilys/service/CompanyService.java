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
	public List<Company> getList() {
		Connection connection = ConnectionManager.getConnection();
		List<Company> list = null;
		try {
			connection.setAutoCommit(false);
			log.info("Get list of companies");
			list = companyDAO.getList(connection);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public String getName(Long companyId) {
		Connection connection = ConnectionManager.getConnection();
		String name = null;
		try {
			connection.setAutoCommit(false);
			log.info("Get name of company");
			name = companyDAO.getName(connection, companyId);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return name;
	}

}
