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
import com.excilys.transfert.ComputerDTO;
import com.excilys.transfert.MapperDTO;
import com.excilys.wrapper.Wrapper;

public class ComputerService {
	public static final Integer recordsPerPage = Wrapper.RECORDS_PER_PAGE;
	private final static ComputerService instance = new ComputerService();
	private static DAOFactory daoFactory = DAOFactory.getInstance();
	private static ComputerDAO computerDAO = daoFactory.getComputerDAO();
	private static CompanyDAO companyDAO = daoFactory.getCompanyDAO();
	static Logger log = LoggerFactory.getLogger(ComputerService.class);

	private ComputerService() {
	}

	public static ComputerService getInstance() {
		return instance;
	}

	public Wrapper getDashboardWrapper(int currentPage, String orderBy) {

		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			Long nbrComputers = computerDAO.count(connection);
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			List<Computer> listComputers = computerDAO.getList(connection,
					orderBy, currentPage, recordsPerPage);
			String message = "Welcome to your computer database !";
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).nbrComputers(nbrComputers)
					.listComputers(listComputers).message(message)
					.orderBy(orderBy).build();
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

	public Wrapper getAddComputerWrapper(int currentPage,
			ComputerDTO computerDTO) {
		Connection connection = ConnectionManager.getConnection();
		MapperDTO mapper = new MapperDTO();
		Wrapper wrapper = null;

		try {
			connection.setAutoCommit(false);
			Computer computer = mapper.toComputer(computerDTO);
			computerDAO.add(connection, computer);
			Long nbrComputers = computerDAO.count(connection);
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			List<Company> listCompanies = companyDAO.getList(connection);
			List<Computer> listComputers = computerDAO.getList(connection,
					null, currentPage, recordsPerPage);
			String message = "Computer added successfully !";
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).nbrComputers(nbrComputers)
					.listCompanies(listCompanies).listComputers(listComputers)
					.message(message).build();
			connection.commit();
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

	public Wrapper getDeleteComputerWrapper(int currentPage, String computerId) {
		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			Long nbrComputers;
			nbrComputers = computerDAO.count(connection);
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
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

	public Wrapper getEditComputerWrapper(String computerId) {

		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			List<Company> listCompanies;
			listCompanies = companyDAO.getList(connection);
			Computer computer = computerDAO.get(connection,
					Long.valueOf(computerId));
			wrapper = Wrapper.builder().computer(computer)
					.listCompanies(listCompanies).build();
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

	public Wrapper getEditComputerWrapperPost(int currentPage,
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
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
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

	public Wrapper getSelectComputerWrapperNoSearch(String orderBy,
			int currentPage, Integer recordsperpage2) {

		Connection connection = ConnectionManager.getConnection();
		Wrapper wrapper = null;
		try {
			connection.setAutoCommit(false);
			List<Computer> listComputers = computerDAO.getList(connection,
					orderBy, currentPage, recordsPerPage);
			Long nbrComputers = computerDAO.count(connection);
			String message = "Welcome to your computer database !";
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = Wrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).listComputers(listComputers)
					.nbrComputers(nbrComputers).orderBy(orderBy)
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

	public Wrapper getSelectComputerWrapperSearchComputer(String orderBy,
			Integer currentPage, Integer recordsperpage, String searchComputer) {
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
			String orderBy, Integer currentPage, Integer recordsperpage,
			String searchCompany, String searchComputer) {

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
			Integer currentPage, Integer recordsperpage, String searchCompany) {

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
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
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
