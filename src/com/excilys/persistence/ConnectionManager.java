package com.excilys.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionManager {
	public final static String URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	public final static String USER = "jee-cdb";
	public final static String PASSWORD = "password";
	private final static ConnectionManager instance = new ConnectionManager();

	private ConnectionManager() {

	}

	public static ConnectionManager getInstance() {
		return instance;
	}

	private static BoneCP connectionPool = null;
	private static Connection connection = null;

	public static void initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(URL);
			config.setUsername(USER);
			config.setPassword(PASSWORD);
			connectionPool = new BoneCP(config);
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (connection == null) {
			initialize();
		} else
			try {
				connection = connectionPool.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // fetch a connection
		return connection;
	}
}
