package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;

public class ComputerDAO {
	/* Query */
	public static final String GET_ALL = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id;";
	public static final String GET_ALL_PAGED = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id LIMIT ?,?;";
	public static final String ADD = "INSERT INTO `computer-database-db`.`computer` (name,introduced,discontinued,company_id) values (?,?,?,?);";
	public static final String GET_BY_NAME = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=? LIMIT ?,?;";
	public static final String COUNT_BY_NAME = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=?;";
	public static final String DELETE = "DELETE FROM `computer-database-db`.`computer` WHERE id=?;";
	public static final String UPDATE = "UPDATE computer SET name =?,introduced=?,discontinued=?,company_id=?  WHERE id=?;";
	public static final String COUNT = "SELECT COUNT(*) FROM `computer-database-db`.computer ;";
	public static final String GET = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.id=?;";
	public static final String GET_BY_NAME_AND_COMPANY_NAME = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=? AND company.name=? LIMIT ?,?";
	public static final String COUNT_BY_NAME_AND_COMPANY_NAME = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=? AND company.name=?";
	public static final String GET_BY_COMPANY_NAME = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE company.name=? LIMIT ?,?;";
	public static final String COUNT_BY_COMPANY_NAME = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE company.name=? ;";
	/* Singleton */
	private final static ComputerDAO instance = new ComputerDAO();

	private ComputerDAO() {
	}

	public static ComputerDAO getInstance() {
		return instance;
	}

	/* functions */
	public List<Computer> getList(Connection connection) {

		List<Computer> listComputers = new ArrayList<Computer>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_ALL);
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
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();

			} catch (SQLException e) {
			}
		}
		return listComputers;
	}

	public List<Computer> getList(Connection connection, int page,
			int recordsPerPage) {

		List<Computer> listComputers = new ArrayList<Computer>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_ALL_PAGED);
			statement.setInt(1, (page - 1) * recordsPerPage);
			statement.setInt(2, recordsPerPage);
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
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();

			} catch (SQLException e) {
			}
		}
		return listComputers;
	}

	public void add(Connection connection, Computer computer) {

		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(ADD);

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
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
			}
		}

	}

	public List<Computer> getListByName(Connection connection,
			String computerName, int page, int recordsPerPage) {

		List<Computer> listComputers = new ArrayList<Computer>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_BY_NAME);
			statement.setString(1, computerName);
			statement.setInt(2, (page - 1) * recordsPerPage);
			statement.setInt(3, recordsPerPage);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company();
				Computer computer = new Computer();

				computer.setId(resultSet.getLong(1));
				computer.setName(resultSet.getString(2));
				computer.setIntroduced(resultSet.getDate(3));
				computer.setDiscontinued(resultSet.getDate(4));
				computer.setIntroduced(null);

				company.setId(resultSet.getLong(5));
				company.setName(resultSet.getString(7));
				computer.setCompany(company);
				listComputers.add(computer);
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
		return listComputers;
	}

	public void delete(Connection connection, Computer computer) {
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(DELETE);
			statement.setLong(1, computer.getId());
			statement.executeUpdate();

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

	}

	public void edit(Connection connection, Computer computer) {

		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(UPDATE);

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
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();

			} catch (SQLException e) {
			}
		}

	}

	public Long count(Connection connection) {
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Long nbrComputers = null;

		try {
			statement = connection.prepareStatement(COUNT);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nbrComputers = resultSet.getLong(1);
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
		return nbrComputers;
	}

	public Computer get(Connection connection, Long computerId) {

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Computer computer = null;

		try {
			statement = connection.prepareStatement(GET);
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
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();

			} catch (SQLException e) {
			}
		}
		return computer;
	}

	public List<Computer> getListByNameAndCompanyName(Connection connection,
			String computerName, String computerCompanyName, int page,
			int recordsPerPage) {

		List<Computer> listComputers = new ArrayList<Computer>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection
					.prepareStatement(GET_BY_NAME_AND_COMPANY_NAME);
			statement.setString(1, computerName);
			statement.setString(2, computerCompanyName);
			statement.setInt(3, (page - 1) * recordsPerPage);
			statement.setInt(4, recordsPerPage);
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
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();

			} catch (SQLException e) {
			}
		}
		return listComputers;

	}

	public List<Computer> getListByCompanyName(Connection connection,
			String computerCompanyName, int page, int recordsPerPage) {

		List<Computer> listComputers = new ArrayList<Computer>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(GET_BY_COMPANY_NAME);
			statement.setString(1, computerCompanyName);
			statement.setInt(2, (page - 1) * recordsPerPage);
			statement.setInt(3, recordsPerPage);
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

	public Long countByName(Connection connection, String name) {
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Long nbrComputers = null;

		try {
			statement = connection.prepareStatement(COUNT_BY_NAME);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nbrComputers = resultSet.getLong(1);
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
		return nbrComputers;
	}

	public Long countByNameAndCompanyName(Connection connection, String name,
			String companyName) {
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Long nbrComputers = null;

		try {
			statement = connection
					.prepareStatement(COUNT_BY_NAME_AND_COMPANY_NAME);
			statement.setString(1, name);
			statement.setString(2, companyName);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nbrComputers = resultSet.getLong(1);
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
		return nbrComputers;
	}

	public Long countByCompanyName(Connection connection, String name) {
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Long nbrComputers = null;

		try {
			statement = connection.prepareStatement(COUNT_BY_COMPANY_NAME);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nbrComputers = resultSet.getLong(1);
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
		return nbrComputers;
	}
}
