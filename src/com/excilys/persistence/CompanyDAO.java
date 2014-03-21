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

/* Singleton : enum will ensure that we really have a singleton (otherwise, a exploit can be done with the JVM to duplicate objects */
public enum CompanyDAO {
	INSTANCE;

	private CompanyDAO() {
	}

	public static CompanyDAO getInstance() {
		return INSTANCE;
	}

	/*
	 * Logger
	 */
	static Logger log = LoggerFactory.getLogger(CompanyDAO.class.getName());

	/*
	 * Functions
	 */
	public List<Company> getList(Connection connection) throws SQLException {

		String GET_ALL = "SELECT id, name FROM `computer-database-db`.`company`;";
		List<Company> listCompanies = new ArrayList<Company>();

		PreparedStatement statement = connection.prepareStatement(GET_ALL);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2)).build();
			listCompanies.add(company);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return listCompanies;
	}

	public String getName(Connection connection, Long companyId)
			throws SQLException {

		String GET_NAME = "SELECT id, name FROM `computer-database-db`.`company` WHERE id=?;";
		String name = null;
		PreparedStatement statement = connection.prepareStatement(GET_NAME);
		statement.setLong(1, companyId);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			name = resultSet.getString(2);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return name;
	}

}
