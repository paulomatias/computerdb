package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.domain.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.DAOManager;

public class ComputerService {
	private final static ComputerService instance = new ComputerService();
	private static ComputerDAO computerDAO = ComputerDAO.getInstance();

	private ComputerService() {
	}

	public static ComputerService getInstance() {
		return instance;
	}

	public List<Computer> getList() {
		Connection connection = DAOManager.getConnection();

		List<Computer> list = computerDAO.getList(connection);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Computer> getList(int page, int recordsPerPage) {
		Connection connection = DAOManager.getConnection();
		List<Computer> list = computerDAO.getList(connection, page,
				recordsPerPage);

		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public void add(Computer computer) {
		Connection connection = DAOManager.getConnection();
		computerDAO.add(connection, computer);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Computer> getListByName(String computerName, int page,
			int recordsPerPage) {
		Connection connection = DAOManager.getConnection();
		List<Computer> list = computerDAO.getListByName(connection,
				computerName, page, recordsPerPage);

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void delete(Computer computer) {
		Connection connection = DAOManager.getConnection();
		computerDAO.delete(connection, computer);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void edit(Computer computer) {
		Connection connection = DAOManager.getConnection();
		computerDAO.edit(connection, computer);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Long count() {
		Connection connection = DAOManager.getConnection();
		Long count = computerDAO.count(connection);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Computer get(Long computerId) {
		Connection connection = DAOManager.getConnection();
		Computer c = computerDAO.get(connection, computerId);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public List<Computer> getListByNameAndCompanyName(String computerName,
			String computerCompanyName, int page, int recordsPerPage) {
		Connection connection = DAOManager.getConnection();
		List<Computer> list = computerDAO.getListByNameAndCompanyName(
				connection, computerName, computerCompanyName, page,
				recordsPerPage);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Computer> getListByCompanyName(String computerCompanyName,
			int page, int recordsPerPage) {
		Connection connection = DAOManager.getConnection();
		List<Computer> list = computerDAO.getListByCompanyName(connection,
				computerCompanyName, page, recordsPerPage);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Long countByName(String name) {
		Connection connection = DAOManager.getConnection();
		Long l = computerDAO.countByName(connection, name);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	public Long countByNameAndCompanyName(String name, String companyName) {
		Connection connection = DAOManager.getConnection();
		Long l = computerDAO.countByNameAndCompanyName(connection, name,
				companyName);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	public Long countByCompanyName(String companyName) {
		Connection connection = DAOManager.getConnection();
		Long l = computerDAO.countByCompanyName(connection, companyName);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

}
