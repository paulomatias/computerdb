package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.domain.Company;

public class CompanyDAO {
	/* Query */
	public static final String GET_ALL_COMPANIES = "SELECT id, name FROM `computer-database-db`.`company`;";
	public static final String GET_COMPANY_NAME = "SELECT id, name FROM `computer-database-db`.`company` WHERE id=?;";
	/* Singleton */
	private final static CompanyDAO instance = new CompanyDAO();

	private CompanyDAO() {
	}

	public static CompanyDAO getInstance() {
		return instance;
	}

	/* code to close connections */
	public void closeConnection(Connection connection, ResultSet resultSet,
			Statement statement) {
		try {
			if (resultSet != null)
				resultSet.close();
			if (resultSet != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
		}
	}

	public List<Company> getListCompanies() {
		Connection connection = ConnectionManager.getConnection();
		List<Company> listCompanies = new ArrayList<Company>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_ALL_COMPANIES);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company();
				company.setId(resultSet.getLong(1));
				company.setName(resultSet.getString(2));
				listCompanies.add(company);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, resultSet, statement);
		}
		return listCompanies;
	}

	public String getCompanyName(Long companyId) {
		Connection connection = ConnectionManager.getConnection();
		String name = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_COMPANY_NAME);
			statement.setLong(1, companyId);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				name = resultSet.getString(2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, resultSet, statement);
		}
		return name;
	}

}
