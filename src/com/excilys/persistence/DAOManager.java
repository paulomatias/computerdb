package com.excilys.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DAOManager {
	/*
	 * DAOFactory + ConnectionManager + Class.forname();
	 */
	private final static DAOManager instance = new DAOManager();
	private static BoneCP connectionPool = null;
	public final static String URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	public final static String USER = "jee-cdb";
	public final static String PASSWORD = "password";
	public final static String driverClass = "com.mysql.jdbc.Driver";
	/*
	 * Logger
	 */
	static Logger log = LoggerFactory.getLogger(DAOManager.class.getName());

	private DAOManager() {
	}

	public static DAOManager getInstance() {
		return instance;
	}

	public ComputerDAO getComputerDAO() {
		return ComputerDAO.getInstance();
	}

	public CompanyDAO getCompanyDAO() {
		return CompanyDAO.getInstance();
	}

	/*
	 * Connection Pool Manager
	 */
	public static void initialize() {
		try {
			Class.forName(driverClass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(URL);
			config.setUsername(USER);
			config.setPassword(PASSWORD);
			connectionPool = new BoneCP(config);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		if (connectionPool == null) {
			initialize();
		} else
			try {
				connection = connectionPool.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return connection;
	}
}