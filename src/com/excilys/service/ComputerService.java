package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionManager;
import com.excilys.persistence.DAOFactory;
import com.excilys.persistence.LogDAO;
import com.excilys.transfert.ComputerDTO;
import com.excilys.transfert.MapperDTO;
import com.excilys.wrapper.Wrapper;

/* Singleton : enum will ensure that we really have a singleton (otherwise, a exploit can be done with the JVM to duplicate objects */
public enum ComputerService {
	INSTANCE;

	private ComputerService() {
	}

	public static ComputerService getInstance() {
		return INSTANCE;
	}

	public static final Integer recordsPerPage = Wrapper.RECORDS_PER_PAGE;
	private static DAOFactory daoFactory = DAOFactory.getInstance();
	private static LogDAO logDAO = daoFactory.getLogDAO();
	private static ComputerDAO computerDAO = daoFactory.getComputerDAO();
	private static CompanyDAO companyDAO = daoFactory.getCompanyDAO();
	static Logger log = LoggerFactory.getLogger(ComputerService.class);

	/*
	 * Return the wrapper to the dahboardServlet, using transactions
	 */
	public Wrapper getDashboardWrapper(Integer currentPage, String orderBy) {

		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			log.info("Counting number of computers...");
			Long nbrComputers = computerDAO.count(connection);
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			log.info("Getting list of computers...");
			List<Computer> listComputers = computerDAO.getList(connection,
					orderBy, currentPage, recordsPerPage);
			String message = "Welcome to your computer database !";
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).nbrComputers(nbrComputers)
					.listComputers(listComputers).message(message)
					.orderBy(orderBy).build();
			connection.commit();
			log.info("Transactions successful");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wrapper;
	}

	/*
	 * Return the wrapper to the addServlet, using transactions
	 */
	public Wrapper getAddComputerWrapper(Integer currentPage,
			ComputerDTO computerDTO) {
		Connection connection = ConnectionManager.getConnection();
		MapperDTO mapper = new MapperDTO();
		Wrapper wrapper = null;
		System.out.println("1" + computerDTO);
		try {
			connection.setAutoCommit(false);
			log.info("Adding computer...");
			Computer computer = mapper.toComputer(computerDTO);
			computer.setId(computerDAO.add(connection, computer));
			log.info("Counting number of computers...");
			Long nbrComputers = computerDAO.count(connection);
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			log.info("Getting list of companies...");
			List<Company> listCompanies = companyDAO.getList(connection);
			log.info("Getting list of computers...");
			List<Computer> listComputers = computerDAO.getList(connection,
					null, currentPage, recordsPerPage);
			String message = "Computer added successfully !";
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).nbrComputers(nbrComputers)
					.listCompanies(listCompanies).listComputers(listComputers)
					.message(message).build();
			logDAO.setLog(connection, computer, "insert");
			connection.commit();
			log.info("Transaction successful");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wrapper;
	}

	/*
	 * Return the wrapper to the deleteServlet, using transactions
	 */
	public Wrapper getDeleteComputerWrapper(Integer currentPage,
			String computerId) {
		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			Long nbrComputers;
			nbrComputers = computerDAO.count(connection);
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			List<Computer> listComputers = computerDAO
					.getList(connection, null);
			Computer computer = Computer.builder().id(Long.valueOf(computerId))
					.build();
			computerDAO.delete(connection, computer);
			listComputers = computerDAO.getList(connection, null, currentPage,
					recordsPerPage);
			String message = "Computer deleted successfully !";
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrComputers(nbrComputers).nbrOfPages(nbrOfPages)
					.listComputers(listComputers).message(message).build();
			logDAO.setLog(connection, computer, "delete");
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wrapper;
	}

	/*
	 * Return the wrapper to the editServlet for the get method, using
	 * transactions
	 */
	public Wrapper getEditComputerWrapper(String computerId) {

		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			List<Company> listCompanies;
			listCompanies = companyDAO.getList(connection);
			Computer computer = computerDAO.get(connection,
					Long.valueOf(computerId));
			MapperDTO mapper = new MapperDTO();
			ComputerDTO computerDTO = mapper.toDTO(computer);
			wrapper = Wrapper.builder().computerDTO(computerDTO)
					.listCompanies(listCompanies).build();
			logDAO.setLog(connection, computer, "update");
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wrapper;
	}

	/*
	 * Return the wrapper to the editServlet for the post method, using
	 * transactions
	 */
	public Wrapper getEditComputerWrapperPost(Integer currentPage,
			ComputerDTO computerDTO) {

		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		MapperDTO mapper = new MapperDTO();

		Computer computer;
		try {
			connection.setAutoCommit(false);
			computer = mapper.toComputer(computerDTO);
			computerDAO.edit(connection, computer);
			Long nbrComputers = computerDAO.count(connection);
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			List<Computer> listComputers = computerDAO.getList(connection,
					null, currentPage, recordsPerPage);
			String message = "Computer edited successfully !";
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).listComputers(listComputers)
					.nbrComputers(nbrComputers).message(message).build();
			connection.commit();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return wrapper;
	}

	public Wrapper getSelectComputerWrapperSearchComputer(String orderBy,
			Integer currentPage, String searchComputer) {
		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			List<Computer> listComputers = computerDAO.getListByName(
					connection, searchComputer, orderBy, currentPage,
					recordsPerPage);
			Long nbrComputers = computerDAO.countByName(connection,
					searchComputer);
			String message = "Computer(s) selected successfully !";
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).listComputers(listComputers)
					.nbrComputers(nbrComputers).searchComputer(searchComputer)
					.orderBy(orderBy).message(message).build();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wrapper;
	}

	public Wrapper getSelectComputerWrapperSearchCompanySearchComputer(
			String orderBy, Integer currentPage, String searchCompany,
			String searchComputer) {

		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			System.out.println("oihreoh");
			List<Computer> listComputers = computerDAO
					.getListByNameAndCompanyName(connection, searchComputer,
							searchCompany, orderBy, currentPage, recordsPerPage);
			Long nbrComputers = computerDAO.countByNameAndCompanyName(
					connection, searchCompany, searchCompany);
			String message = "Computer(s) selected successfully !";
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).listComputers(listComputers)
					.nbrComputers(nbrComputers).searchComputer(searchComputer)
					.searchCompany(searchCompany).orderBy(orderBy)
					.message(message).build();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wrapper;
	}

	public Wrapper getSelectComputerWrapperSearchCompany(String orderBy,
			Integer currentPage, String searchCompany) {

		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			List<Computer> listComputers = computerDAO.getListByCompanyName(
					connection, searchCompany, orderBy, currentPage,
					recordsPerPage);
			Long nbrComputers = computerDAO.countByCompanyName(connection,
					searchCompany);
			String message = "Computer(s) selected successfully !";
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).listComputers(listComputers)
					.nbrComputers(nbrComputers).searchCompany(searchCompany)
					.orderBy(orderBy).message(message).build();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wrapper;
	}

}
