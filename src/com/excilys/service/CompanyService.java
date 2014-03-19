package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.domain.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.DAOManager;

public class CompanyService {
	private final static CompanyService instance = new CompanyService();
	private static DAOManager daoManager = DAOManager.getInstance();
	private static CompanyDAO companyDAO = daoManager.getCompanyDAO();

	/*
	 * Logger
	 */
	static Logger log = LoggerFactory.getLogger(CompanyService.class.getName());

	private CompanyService() {
	}

	public static CompanyService getInstance() {
		return instance;
	}

	public List<Company> getList() {
		Connection connection = DAOManager.getConnection();
		List<Company> list = companyDAO.getList(connection);
		try {
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	public String getName(Long companyId) {
		Connection connection = DAOManager.getConnection();
		String name = companyDAO.getName(connection, companyId);
		try {
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return name;
	}

}
