package com.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.company.om.Company;
import com.company.om.Computer;

public class ComputerDAO {
	// refaire les querys
	public static final String QUERY_GET_COMPUTERS = "SELECT * FROM `computer-database-db`.`computer`;";
	public static final String QUERY_ADD_COMPUTER = "INSERT INTO `computer-database-db`.`computer` (name,introduced,discontinued,company_id) values (?,?,?,?);";

	public List<Computer> getListComputers(Connection cn) {

		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		ResultSet rs = null;
		Statement stmt = null;

		try {
			stmt = cn.createStatement();
			rs = stmt.executeQuery(QUERY_GET_COMPUTERS);

			while (rs.next()) {
				Company company = new Company();
				Computer computer = new Computer();
				/* Formatteur de date */
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * On recupere l'id, le nom, les dates, ainsi que la companyId
				 * depuis l'objet result
				 */
				computer.setId(rs.getLong(1));
				computer.setName(rs.getString(2));

				if (rs.getString(3) != null) {
					computer.setIntroduced(formatter.parse(rs.getString(3)));
				} else
					computer.setIntroduced(null);
				if (rs.getString(4) != null) {
					computer.setDiscontinued(formatter.parse(rs.getString(4)));
				} else
					computer.setIntroduced(null);

				company.setId(rs.getLong(5));
				computer.setCompany(company);

				listComputers.add(computer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (cn != null)
					cn.close();
			} catch (SQLException e) {
			}
		}
		return listComputers;
	}

	public void addComputer(Computer computer, Connection cn) {

		ResultSet rs = null;
		PreparedStatement stmt = null;

		try {
			stmt = cn.prepareStatement(QUERY_ADD_COMPUTER);

			stmt.setString(1, computer.getName());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			stmt.setString(2, formatter.format(computer.getIntroduced()));
			stmt.setString(3, formatter.format(computer.getDiscontinued()));
			stmt.setLong(4, computer.getCompany().getId());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (cn != null)
					cn.close();
			} catch (SQLException e) {
			}
		}

	}

}
