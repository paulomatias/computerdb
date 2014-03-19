package com.excilys.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOFactory {
	/*
	 * DAOFactory + ConnectionManager + Class.forname();
	 */
	private final static DAOFactory instance = new DAOFactory();

	/*
	 * Logger
	 */
	static Logger log = LoggerFactory.getLogger(DAOFactory.class.getName());

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return instance;
	}

	public ComputerDAO getComputerDAO() {
		return ComputerDAO.getInstance();
	}

	public CompanyDAO getCompanyDAO() {
		return CompanyDAO.getInstance();
	}
}