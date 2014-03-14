package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String USER = "jee-cdb";
	private static final String PASSWORD = "password";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			System.out.println("Probleme dans le ConnectionManager.");
			e.printStackTrace();
		}
		return conn;
	}
}