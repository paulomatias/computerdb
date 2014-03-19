package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.domain.Company;

public class CompanyDAO {
	/* Query */
	public static final String GET_ALL = "SELECT id, name FROM `computer-database-db`.`company`;";
	public static final String GET_NAME = "SELECT id, name FROM `computer-database-db`.`company` WHERE id=?;";

	/*
	 * Logger
	 */
	static Logger log = LoggerFactory.getLogger(CompanyDAO.class.getName());

	/* Singleton */
	private final static CompanyDAO instance = new CompanyDAO();

	private CompanyDAO() {
	}

	public static CompanyDAO getInstance() {
		return instance;
	}

	public List<Company> getList(Connection connection) {
		List<Company> listCompanies = new ArrayList<Company>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = Company.builder().id(resultSet.getLong(1))
						.name(resultSet.getString(2)).build();
				listCompanies.add(company);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
			}
		}

		return listCompanies;
	}

	public String getName(Connection connection, Long companyId) {
		String name = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_NAME);
			statement.setLong(1, companyId);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				name = resultSet.getString(2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();

			} catch (SQLException e) {
			}
		}
		return name;
	}

}
