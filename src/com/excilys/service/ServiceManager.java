package com.excilys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceManager {

	private final static ServiceManager instance = new ServiceManager();
	/*
	 * Logger
	 */
	static Logger log = LoggerFactory.getLogger(ServiceManager.class.getName());

	private ServiceManager() {
	}

	public static ServiceManager getInstance() {
		return instance;
	}

	public ComputerService getComputerService() {
		return ComputerService.getInstance();
	}

	public CompanyService getCompanyService() {
		return CompanyService.getInstance();
	}

}
