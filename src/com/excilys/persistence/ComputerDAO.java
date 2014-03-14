package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;

public class ComputerDAO {
	/* Query */
	public static final String GET_COMPUTERS = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id ;";
	public static final String ADD_COMPUTER = "INSERT INTO `computer-database-db`.`computer` (name,introduced,discontinued,company_id) values (?,?,?,?);";
	public static final String GET_COMPUTERS_BY_NAME = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=?;";
	public static final String DELETE_COMPUTER = "DELETE FROM `computer-database-db`.`computer` WHERE id=?;";
	public static final String UPDATE_COMPUTER = "UPDATE computer SET name =?,introduced=?,discontinued=?,company_id=?  WHERE id=?;";
	public static final String COUNT_COMPUTERS = "SELECT COUNT(*) FROM `computer-database-db`.computer;";
	public static final String GET_A_COMPUTER = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.id=? ;";
	public static final String GET_COMPUTERS_BY_NAME_AND_COMPANY_NAME = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=? AND company.name=?";
	public static final String GET_COMPUTERS_BY_COMPANY_NAME = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE company.name=?;";
	/* Singleton */
	private final static ComputerDAO instance = new ComputerDAO();

	private ComputerDAO() {
	}

	public static ComputerDAO getInstance() {
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

	/* functions */
	public List<Computer> getListComputers() {
		Connection connection = ConnectionManager.getConnection();
		List<Computer> listComputers = new ArrayList<Computer>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_COMPUTERS);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company();
				Computer computer = new Computer();

				computer.setId(resultSet.getLong(1));
				computer.setName(resultSet.getString(2));
				computer.setIntroduced(resultSet.getDate(3));
				computer.setDiscontinued(resultSet.getDate(4));
				company.setId(resultSet.getLong(5));
				company.setName(resultSet.getString(7));
				computer.setCompany(company);
				listComputers.add(computer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, resultSet, statement);
		}
		return listComputers;
	}

	public void addComputer(Computer computer) {

		Connection connection = ConnectionManager.getConnection();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(ADD_COMPUTER);

			statement.setString(1, computer.getName());
			if (computer.getIntroduced() == null) {
				statement.setDate(2, null);
			} else
				statement.setDate(2, new java.sql.Date(computer.getIntroduced()
						.getTime()));
			if (computer.getDiscontinued() == (null)) {
				statement.setDate(3, null);
			} else
				statement.setDate(3, new java.sql.Date(computer
						.getDiscontinued().getTime()));
			if (computer.getCompany().getId().equals((0L))) {
				statement.setString(4, null);
			} else
				statement.setLong(4, computer.getCompany().getId());

			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			this.closeConnection(connection, resultSet, statement);
		}

	}

	public List<Computer> getListComputersByName(String computerName) {
		Connection connection = ConnectionManager.getConnection();
		List<Computer> listComputers = new ArrayList<Computer>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_COMPUTERS_BY_NAME);
			statement.setString(1, computerName);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company();
				Computer computer = new Computer();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				computer.setId(resultSet.getLong(1));
				computer.setName(resultSet.getString(2));

				if (resultSet.getString(3) != null) {
					computer.setIntroduced(formatter.parse(resultSet
							.getString(3)));
				} else
					computer.setIntroduced(null);
				if (resultSet.getString(4) != null) {
					computer.setDiscontinued(formatter.parse(resultSet
							.getString(4)));
				} else
					computer.setIntroduced(null);

				company.setId(resultSet.getLong(5));
				company.setName(resultSet.getString(7));
				computer.setCompany(company);
				listComputers.add(computer);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			this.closeConnection(connection, resultSet, statement);
		}
		return listComputers;
	}

	public void deleteComputer(Computer computer) {
		Connection connection = ConnectionManager.getConnection();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(DELETE_COMPUTER);
			statement.setLong(1, computer.getId());
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			this.closeConnection(connection, resultSet, statement);
		}

	}

	public void editComputer(Computer computer) {

		Connection connection = ConnectionManager.getConnection();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(UPDATE_COMPUTER);

			statement.setString(1, computer.getName());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			statement.setString(2, formatter.format(computer.getIntroduced()));
			statement
					.setString(3, formatter.format(computer.getDiscontinued()));
			if (computer.getCompany().getId().equals(null)) {
				statement.setLong(4, computer.getCompany().getId());
			} else
				statement.setString(4, null);
			statement.setLong(5, computer.getId());

			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			this.closeConnection(connection, resultSet, statement);
		}

	}

	public Long countComputers() {
		Connection connection = ConnectionManager.getConnection();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Long nbrComputers = null;

		try {
			statement = connection.prepareStatement(COUNT_COMPUTERS);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nbrComputers = resultSet.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			this.closeConnection(connection, resultSet, statement);
		}
		return nbrComputers;
	}

	public Computer getAComputer(Long computerId) {
		Connection connection = ConnectionManager.getConnection();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Computer computer = null;

		try {
			statement = connection.prepareStatement(GET_A_COMPUTER);
			statement.setLong(1, computerId);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company();
				computer = new Computer();
				SimpleDateFormat formatter = new SimpleDateFormat("YY-MM-dd");

				computer.setId(resultSet.getLong(1));
				computer.setName(resultSet.getString(2));

				if (resultSet.getString(3) != null) {
					computer.setIntroduced(formatter.parse(resultSet
							.getString(3)));
				} else
					computer.setIntroduced(null);
				if (resultSet.getString(4) != null) {
					computer.setDiscontinued(formatter.parse(resultSet
							.getString(4)));
				} else
					computer.setIntroduced(null);

				company.setId(resultSet.getLong(5));
				company.setName(resultSet.getString(7));
				computer.setCompany(company);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, resultSet, statement);
		}
		return computer;
	}

	public List<Computer> getListComputersByNameAndCompanyName(
			String computerName, String computerCompanyName) {
		Connection connection = ConnectionManager.getConnection();
		List<Computer> listComputers = new ArrayList<Computer>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection
					.prepareStatement(GET_COMPUTERS_BY_NAME_AND_COMPANY_NAME);
			statement.setString(1, computerName);
			statement.setString(2, computerCompanyName);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company();
				Computer computer = new Computer();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				computer.setId(resultSet.getLong(1));
				computer.setName(resultSet.getString(2));

				if (resultSet.getString(3) != null) {
					computer.setIntroduced(formatter.parse(resultSet
							.getString(3)));
				} else
					computer.setIntroduced(null);
				if (resultSet.getString(4) != null) {
					computer.setDiscontinued(formatter.parse(resultSet
							.getString(4)));
				} else
					computer.setIntroduced(null);

				company.setId(resultSet.getLong(5));
				company.setName(resultSet.getString(7));
				computer.setCompany(company);
				listComputers.add(computer);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			this.closeConnection(connection, resultSet, statement);
		}
		return listComputers;

	}

	public List<Computer> getListComputersByCompanyName(
			String computerCompanyName) {
		Connection connection = ConnectionManager.getConnection();
		List<Computer> listComputers = new ArrayList<Computer>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection
					.prepareStatement(GET_COMPUTERS_BY_COMPANY_NAME);
			statement.setString(1, computerCompanyName);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company();
				Computer computer = new Computer();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				computer.setId(resultSet.getLong(1));
				computer.setName(resultSet.getString(2));

				if (resultSet.getString(3) != null) {
					computer.setIntroduced(formatter.parse(resultSet
							.getString(3)));
				} else
					computer.setIntroduced(null);
				if (resultSet.getString(4) != null) {
					computer.setDiscontinued(formatter.parse(resultSet
							.getString(4)));
				} else
					computer.setIntroduced(null);

				company.setId(resultSet.getLong(5));
				company.setName(resultSet.getString(7));
				computer.setCompany(company);
				listComputers.add(computer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listComputers;
	}
}
