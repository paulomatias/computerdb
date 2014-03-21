package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.domain.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionManager;
import com.excilys.persistence.DAOFactory;
import com.excilys.transfert.ComputerDTO;
import com.excilys.transfert.MapperDTO;

public class ComputerService {
	private final static ComputerService instance = new ComputerService();
	private static DAOFactory daoFactory = DAOFactory.getInstance();
	private static ComputerDAO computerDAO = daoFactory.getComputerDAO();
	static Logger log = LoggerFactory.getLogger(ComputerService.class);

	private ComputerService() {
	}

	public static ComputerService getInstance() {
		return instance;
	}

	public List<Computer> getList() {

		Connection connection = ConnectionManager.getConnection();
		List<Computer> list = null;
		try {
			connection.setAutoCommit(false);
			log.info("Get list of computers");
			list = computerDAO.getList(connection, null);
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

	public List<Computer> getList(String orderBy, int page, int recordsPerPage) {
		Connection connection = ConnectionManager.getConnection();
		List<Computer> list = null;
		try {
			connection.setAutoCommit(false);
			log.info("Get list of computers with pagination");
			list = computerDAO.getList(connection, orderBy, page,
					recordsPerPage);
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

	public void add(ComputerDTO computerDTO) {
		Connection connection = ConnectionManager.getConnection();
		try {
			connection.setAutoCommit(false);
			log.info("Adding computer...");
			MapperDTO mapper = new MapperDTO();
			Computer computer = null;
			try {
				computer = mapper.toComputer(computerDTO);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			computerDAO.add(connection, computer);
			connection.commit();
			log.info("Computer added : " + computer);
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
	}

	public List<Computer> getListByName(String computerName, String orderBy,
			int page, int recordsPerPage) {
		List<Computer> list = null;
		Connection connection = ConnectionManager.getConnection();
		try {
			connection.setAutoCommit(false);
			log.info("Get list of computers by computer name with pagination");
			list = computerDAO.getListByName(connection, computerName, orderBy,
					page, recordsPerPage);
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

	public void delete(Computer computer) {

		Connection connection = ConnectionManager.getConnection();
		try {
			connection.setAutoCommit(false);
			log.info("Deleting computer...");
			computerDAO.delete(connection, computer);
			connection.commit();
			log.info("computer with id n°" + computer.getId() + " deleted : "
					+ computer);
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
	}

	public void edit(ComputerDTO computerDTO) {
		Connection connection = ConnectionManager.getConnection();
		try {
			connection.setAutoCommit(false);
			log.info("Editing computer...");
			MapperDTO mapper = new MapperDTO();
			Computer computer = null;
			try {
				computer = mapper.toComputer(computerDTO);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			computerDAO.edit(connection, computer);
			connection.commit();
			log.info("Computer with id n°" + computer.getId() + " edited : "
					+ computer);
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
	}

	public Long count() {

		Connection connection = ConnectionManager.getConnection();
		Long count = null;
		try {
			connection.setAutoCommit(false);
			log.info("Counting all computers...");
			count = computerDAO.count(connection);
			connection.commit();
			log.info(count + " computer(s) found");
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
		return count;
	}

	public Computer get(Long computerId) {
		Connection connection = ConnectionManager.getConnection();
		Computer computer = null;
		try {
			connection.setAutoCommit(false);
			log.info("Get a computer with id");
			try {
				computer = computerDAO.get(connection, computerId);
			} catch (ParseException e) {
				e.printStackTrace();
			}
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
		return computer;
	}

	public List<Computer> getListByNameAndCompanyName(String computerName,
			String computerCompanyName, String orderBy, int page,
			int recordsPerPage) {

		Connection connection = ConnectionManager.getConnection();
		List<Computer> list = null;
		try {
			connection.setAutoCommit(false);
			log.info("Get list by computer name and company name with pagination");
			list = computerDAO.getListByNameAndCompanyName(connection,
					computerName, computerCompanyName, orderBy, page,
					recordsPerPage);
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

	public List<Computer> getListByCompanyName(String computerCompanyName,
			String orderBy, int page, int recordsPerPage) {

		Connection connection = ConnectionManager.getConnection();
		List<Computer> list = null;
		try {
			connection.setAutoCommit(false);
			log.info("Get list of computers by company name with pagination");
			list = computerDAO.getListByCompanyName(connection,
					computerCompanyName, orderBy, page, recordsPerPage);
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

	public Long countByName(String name) {

		Connection connection = ConnectionManager.getConnection();
		Long count = null;
		try {
			connection.setAutoCommit(false);
			log.info("Counting computers by name...");
			count = computerDAO.countByName(connection, name);
			connection.commit();
			log.info(count + " computer(s) found");
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
		return count;
	}

	public Long countByNameAndCompanyName(String name, String companyName) {

		Connection connection = ConnectionManager.getConnection();
		Long count = null;
		try {
			connection.setAutoCommit(false);
			log.info("Counting computers by name and company name...");
			count = computerDAO.countByNameAndCompanyName(connection, name,
					companyName);
			connection.commit();
			log.info(count + " computer(s) found");
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
		return count;
	}

	public Long countByCompanyName(String companyName) {

		Connection connection = ConnectionManager.getConnection();
		Long count = null;
		try {
			connection.setAutoCommit(false);
			log.info("Counting by company  name...");
			count = computerDAO.countByCompanyName(connection, companyName);
			connection.commit();
			log.info(count + " computer(s) found");
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
		return count;
	}
}
