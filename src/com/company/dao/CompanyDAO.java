package com.company.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.company.om.Company;

public class CompanyDAO {
	public static final String QUERY_GET_COMPANIES = "SELECT id, name FROM `computer-database-db`.`company`;";
	public static final String QUERY_SELECT_COMPANY = "SELECT id, name FROM `computer-database-db`.`company` WHERE id =?;";

	public List<Company> getListCompanies(Connection cn) {

		ArrayList<Company> listCompanies = new ArrayList<Company>();
		ResultSet rs = null;
		Statement stmt = null;

		try {

			stmt = cn.createStatement();
			rs = stmt.executeQuery(QUERY_GET_COMPANIES);
			while (rs.next()) {
				Company company = new Company();
				company.setId(new Long(rs.getLong(1)));
				company.setName(rs.getString(2));

				listCompanies.add(company);
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

		return listCompanies;
	}

	/*
	 * @SuppressWarnings("null") public String getCompanyName(Connection cn,
	 * Company company) {
	 * 
	 * ResultSet rs = null; PreparedStatement stmt = null; String companyName =
	 * null;
	 * 
	 * try { stmt = cn.prepareStatement(QUERY_SELECT_COMPANY);
	 * 
	 * stmt.setLong(1, company.getId()); stmt.executeQuery();
	 * 
	 * if (rs.getString(2) != null) { companyName = "Pas de company"; } else
	 * companyName = "Pas de company";
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * } finally { try { if (rs != null) rs.close(); if (stmt != null)
	 * stmt.close(); if (cn != null) cn.close(); } catch (SQLException e) { } }
	 * 
	 * return companyName; }
	 */
}
