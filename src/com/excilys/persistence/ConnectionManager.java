package com.excilys.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionManager {

	private static BoneCP connectionPool;
	public final static String URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	public final static String USER = "jee-cdb";
	public final static String PASSWORD = "password";
	public final static String driverClass = "com.mysql.jdbc.Driver";

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
			config.setMaxConnectionsPerPartition(20);
			connectionPool = new BoneCP(config);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		if (connectionPool == null) {
			initialize();
		}
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
