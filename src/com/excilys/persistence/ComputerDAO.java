package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;

public class ComputerDAO {
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"YY-MM-dd");
	/*
	 * Logger
	 */
	static Logger log = LoggerFactory.getLogger(ComputerDAO.class.getName());

	/* Singleton */
	private final static ComputerDAO instance = new ComputerDAO();

	private ComputerDAO() {
	}

	public static ComputerDAO getInstance() {
		return instance;
	}

	/* functions */
	public List<Computer> getList(Connection connection, String orderBy)
			throws SQLException {

		StringBuffer query = new StringBuffer(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else if (orderBy.equals("nameASC")) {
			orderBy = "computer.name ASC";
		} else if (orderBy.equals("nameDESC")) {
			orderBy = "computer.name DESC";
		} else if (orderBy.equals("introducedASC")) {
			orderBy = "computer.introduced ASC";
		} else if (orderBy.equals("introducedDESC")) {
			orderBy = "computer.introduced DESC";
		} else if (orderBy.equals("discontinuedASC")) {
			orderBy = "computer.discontinued ASC";
		} else if (orderBy.equals("discontinuedDESC")) {
			orderBy = "computer.discontinued DESC";
		} else if (orderBy.equals("companyASC")) {
			orderBy = "computer.company_id ASC";
		} else if (orderBy.equals("companyDESC")) {
			orderBy = "company.name DESC";
		}
		query.append(" ORDER BY ").append(orderBy);
		List<Computer> listComputers = new ArrayList<Computer>();
		PreparedStatement statement = connection.prepareStatement(query
				.toString());
		if (orderBy.equals(null)) {
			statement.setString(1, "comptuer.id ASC");
		} else
			statement.setString(1, orderBy);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2))
					.introduced(resultSet.getDate(3))
					.discontinued(resultSet.getDate(4)).company(company)
					.build();
			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return listComputers;
	}

	public List<Computer> getList(Connection connection, String orderBy,
			int page, int recordsPerPage) throws SQLException {

		StringBuffer query = new StringBuffer(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else if (orderBy.equals("nameASC")) {
			orderBy = "computer.name ASC";
		} else if (orderBy.equals("nameDESC")) {
			orderBy = "computer.name DESC";
		} else if (orderBy.equals("introducedASC")) {
			orderBy = "computer.introduced ASC";
		} else if (orderBy.equals("introducedDESC")) {
			orderBy = "computer.introduced DESC";
		} else if (orderBy.equals("discontinuedASC")) {
			orderBy = "computer.discontinued ASC";
		} else if (orderBy.equals("discontinuedDESC")) {
			orderBy = "computer.discontinued DESC";
		} else if (orderBy.equals("companyASC")) {
			orderBy = "computer.company_id ASC";
		} else if (orderBy.equals("companyDESC")) {
			orderBy = "company.name DESC";
		}
		query.append(" ORDER BY ").append(orderBy).append(" LIMIT ?,?;");
		List<Computer> listComputers = new ArrayList<Computer>();
		PreparedStatement statement = connection.prepareStatement(query
				.toString());

		statement.setInt(1, (page - 1) * recordsPerPage);
		statement.setInt(2, recordsPerPage);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2))
					.introduced(resultSet.getDate(3))
					.discontinued(resultSet.getDate(4)).company(company)
					.build();
			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return listComputers;
	}

	public void add(Connection connection, Computer computer)
			throws SQLException {

		String query = "INSERT INTO `computer-database-db`.`computer` (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, computer.getName());
		if (computer.getIntroduced() == null) {
			statement.setDate(2, null);
		} else
			statement.setDate(2, new java.sql.Date(computer.getIntroduced()
					.getTime()));
		if (computer.getDiscontinued() == (null)) {
			statement.setDate(3, null);
		} else
			statement.setDate(3, new java.sql.Date(computer.getDiscontinued()
					.getTime()));
		if (computer.getCompany().getId().equals((0L))) {
			statement.setString(4, null);
		} else
			statement.setLong(4, computer.getCompany().getId());
		statement.executeUpdate();
		if (statement != null)
			statement.close();
	}

	public List<Computer> getListByName(Connection connection,
			String computerName, String orderBy, int page, int recordsPerPage)
			throws SQLException {

		StringBuffer query = new StringBuffer(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=?");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else if (orderBy.equals("nameASC")) {
			orderBy = "computer.name ASC";
		} else if (orderBy.equals("nameDESC")) {
			orderBy = "computer.name DESC";
		} else if (orderBy.equals("introducedASC")) {
			orderBy = "computer.introduced ASC";
		} else if (orderBy.equals("introducedDESC")) {
			orderBy = "computer.introduced DESC";
		} else if (orderBy.equals("discontinuedASC")) {
			orderBy = "computer.discontinued ASC";
		} else if (orderBy.equals("discontinuedDESC")) {
			orderBy = "computer.discontinued DESC";
		} else if (orderBy.equals("companyASC")) {
			orderBy = "computer.company_id ASC";
		} else if (orderBy.equals("companyDESC")) {
			orderBy = "company.name DESC";
		}
		query.append(" ORDER BY ").append(orderBy).append(" LIMIT ?,?;");
		List<Computer> listComputers = new ArrayList<Computer>();
		PreparedStatement statement = connection.prepareStatement(query
				.toString());
		statement.setString(1, computerName);
		statement.setInt(2, (page - 1) * recordsPerPage);
		statement.setInt(3, recordsPerPage);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2))
					.introduced(resultSet.getDate(3))
					.discontinued(resultSet.getDate(4)).company(company)
					.build();
			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return listComputers;
	}

	public void delete(Connection connection, Computer computer)
			throws SQLException {

		String query = "DELETE FROM `computer-database-db`.`computer` WHERE id=?;";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setLong(1, computer.getId());
		statement.executeUpdate();
		if (statement != null)
			statement.close();
	}

	public void edit(Connection connection, Computer computer)
			throws SQLException {
		String query = "UPDATE computer SET name =?,introduced=?,discontinued=?,company_id=?  WHERE id=?;";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, computer.getName());
		statement.setDate(2, (java.sql.Date) (computer.getIntroduced()));
		statement.setDate(3, (java.sql.Date) (computer.getDiscontinued()));
		if (computer.getCompany().getId().equals(null)) {
			statement.setLong(4, computer.getCompany().getId());
		} else
			statement.setString(4, null);
		statement.setLong(5, computer.getId());

		statement.executeUpdate();
		if (statement != null)
			statement.close();
	}

	public Long count(Connection connection) throws SQLException {

		String query = "SELECT COUNT(*) FROM `computer-database-db`.computer ;";
		Long nbrComputers = null;
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			nbrComputers = resultSet.getLong(1);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return nbrComputers;
	}

	public Computer get(Connection connection, Long computerId)
			throws SQLException {

		String query = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.id=?;";
		Computer computer = null;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setLong(1, computerId);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Date introduced = null;
			Date discontinued = null;
			if (resultSet.getString(3) != null) {
				try {
					introduced = (FORMAT.parse(resultSet.getString(3)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else
				introduced = null;
			if (resultSet.getString(4) != null) {
				try {
					discontinued = FORMAT.parse(resultSet.getString(4));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else
				discontinued = null;

			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2)).introduced(introduced)
					.discontinued(discontinued).company(company).build();
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return computer;
	}

	public List<Computer> getListByNameAndCompanyName(Connection connection,
			String computerName, String computerCompanyName, String orderBy,
			int page, int recordsPerPage) throws SQLException {

		StringBuffer query = new StringBuffer(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=? AND company.name=?");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else if (orderBy.equals("nameASC")) {
			orderBy = "computer.name ASC";
		} else if (orderBy.equals("nameDESC")) {
			orderBy = "computer.name DESC";
		} else if (orderBy.equals("introducedASC")) {
			orderBy = "computer.introduced ASC";
		} else if (orderBy.equals("introducedDESC")) {
			orderBy = "computer.introduced DESC";
		} else if (orderBy.equals("discontinuedASC")) {
			orderBy = "computer.discontinued ASC";
		} else if (orderBy.equals("discontinuedDESC")) {
			orderBy = "computer.discontinued DESC";
		} else if (orderBy.equals("companyASC")) {
			orderBy = "computer.company_id ASC";
		} else if (orderBy.equals("companyDESC")) {
			orderBy = "company.name DESC";
		}
		query.append(" ORDER BY ").append(orderBy).append(" LIMIT ?,?;");
		List<Computer> listComputers = new ArrayList<Computer>();
		PreparedStatement statement = connection.prepareStatement(query
				.toString());
		statement.setString(1, computerName);
		statement.setString(2, computerCompanyName);
		statement.setInt(3, (page - 1) * recordsPerPage);
		statement.setInt(4, recordsPerPage);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2))
					.introduced(resultSet.getDate(3))
					.discontinued(resultSet.getDate(4)).company(company)
					.build();

			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return listComputers;

	}

	public List<Computer> getListByCompanyName(Connection connection,
			String computerCompanyName, String orderBy, int page,
			int recordsPerPage) throws SQLException {

		StringBuffer query = new StringBuffer(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE company.name=?");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else if (orderBy.equals("nameASC")) {
			orderBy = "computer.name ASC";
		} else if (orderBy.equals("nameDESC")) {
			orderBy = "computer.name DESC";
		} else if (orderBy.equals("introducedASC")) {
			orderBy = "computer.introduced ASC";
		} else if (orderBy.equals("introducedDESC")) {
			orderBy = "computer.introduced DESC";
		} else if (orderBy.equals("discontinuedASC")) {
			orderBy = "computer.discontinued ASC";
		} else if (orderBy.equals("discontinuedDESC")) {
			orderBy = "computer.discontinued DESC";
		} else if (orderBy.equals("companyASC")) {
			orderBy = "computer.company_id ASC";
		} else if (orderBy.equals("companyDESC")) {
			orderBy = "company.name DESC";
		}
		query.append(" ORDER BY ").append(orderBy).append(" LIMIT ?,?;");
		List<Computer> listComputers = new ArrayList<Computer>();
		PreparedStatement statement = connection.prepareStatement(query
				.toString());
		statement.setString(1, computerCompanyName);
		statement.setInt(2, (page - 1) * recordsPerPage);
		statement.setInt(3, recordsPerPage);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2))
					.introduced(resultSet.getDate(3))
					.discontinued(resultSet.getDate(4)).company(company)
					.build();
			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return listComputers;
	}

	public Long countByName(Connection connection, String name)
			throws SQLException {

		String query = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=?;";
		Long nbrComputers = null;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			nbrComputers = resultSet.getLong(1);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return nbrComputers;
	}

	public Long countByNameAndCompanyName(Connection connection, String name,
			String companyName) throws SQLException {

		String query = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=? AND company.name=?";
		Long nbrComputers = null;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, name);
		statement.setString(2, companyName);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			nbrComputers = resultSet.getLong(1);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return nbrComputers;
	}

	public Long countByCompanyName(Connection connection, String name)
			throws SQLException {

		String query = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE company.name=? ;";
		Long nbrComputers = null;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			nbrComputers = resultSet.getLong(1);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		return nbrComputers;
	}
}
