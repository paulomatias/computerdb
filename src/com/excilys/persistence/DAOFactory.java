package com.excilys.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Singleton : enum will ensure that we really have a singleton (otherwise, a exploit can be done with the JVM to duplicate objects */
public enum DAOFactory {
	INSTANCE;

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	/*
	 * Logger
	 */
	static Logger log = LoggerFactory.getLogger(DAOFactory.class.getName());

	public ComputerDAO getComputerDAO() {
		return ComputerDAO.getInstance();
	}

	public CompanyDAO getCompanyDAO() {
		return CompanyDAO.getInstance();
	}
}