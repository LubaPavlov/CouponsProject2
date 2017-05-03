package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.exceptions.DAOException;

//
//CompanyDBDAO class implements CompanyDAO interface.
//Providing methods to insert, update and select data from and to the DB.
//

public class CompanyDBDAO implements CompanyDAO {

	private static final String TABLE_NAME = "company";
	private Connection con = null;
	private String dbName = "coupon";

	@Override
	public void createCompany(Company company) throws DAOException {
		// 1. get a connection (from pool)
		try {
			con = getConnection();
			// 2. create sql insert
			PreparedStatement stat = con.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?)");
			stat.setLong(1, company.getCompId());
			stat.setString(2, company.getCompName());
			stat.setString(3, company.getPassword());
			stat.setString(4, company.getEmail());
			System.out.println("Executing: " + stat.toString());
			// stat.executeUpdate();
			int rowsInserted = stat.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new company " + company.getCompName() + " has been created successfully");
			} else
				System.out.println("An Error Has Occurred. Check if entered data is correct.");
			
		} catch (SQLException e) {
			// TODO: deal with exception
			System.out.println("Cannot create company : " + company.getCompName() + ". " + e.getMessage());
			
		} finally {
			// 3. release connection
			releaseConnection(con);
		}
	}

	@Override
	public void removeCompany(Company company) {
		/// 1. get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				// 2. create sql insert
				PreparedStatement stat = con
						// .prepareStatement("DELETE FROM " + TABLE_NAME + "
						// WHERE compId=?; DELETE FROM Company_Coupon WHERE
						// compId=?");
						.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE compId=?");
				stat.setLong(1, company.getCompId());
				// stat.setLong(2, company.getCompId());

				System.out.println("Executing: " + stat.toString());
				// stat.executeUpdate();
				int rowsDeleted = stat.executeUpdate();

				if (rowsDeleted > 0) {
					System.out.println("A company " + company.getCompName() + " has been deleted successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {
			// TODO: deal with exception
			System.out.println("Cannot remove company : " + company.getCompName() + ". " + e.getMessage());
		}
		finally {
			// 3. release connection
			releaseConnection(con);
		}
	}

	@Override
	public void updateCompany(Company company) {
		/// 1. get a connection (from pool)
		try {
			con = getConnection();
			// 2. create sql insert
			PreparedStatement stat = con
					.prepareStatement("UPDATE " + TABLE_NAME + " SET compName=?, password=? WHERE compName=?");
			stat.setString(1, company.getCompName());
			stat.setString(2, company.getPassword());
			stat.setString(3, company.getCompName());

			System.out.println("Executing: " + stat.toString());
			// stat.executeUpdate();
			int rowsUpdated = stat.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println(company + " has been updated successfully");
			} else
				System.out.println("An Error Has Occurred. Check if entered data is correct.");
			
		} catch (SQLException e) {

			System.out.println("Cannot update company : " + company.getCompName() + ". " + e.getMessage());
			
		} finally {
			// 3. release connection
			releaseConnection(con);
		}
	}

	@Override
	public Company getCompany(long compId) {
		Company company = new Company();
		// 1. get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE compId=? ");
				stat.setLong(1, compId);
				System.out.println("Executing: " + stat.toString());
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					company.setCompId(rows.getLong("compId"));
					company.setCompName(rows.getString("compName"));
					company.setPassword(rows.getString("password"));
				}
			}
		} catch (SQLException e) {

			System.out.println("Cannot found a company : " + compId + ". " + e.getMessage());
			
		}
		finally {
			// 3. release connection
			releaseConnection(con);
		}
		return company;
	}

	@Override
	public Collection<Company> getAllCompanies() {
		return null;

	}

	@Override
	public Collection<Coupon> getCoupons() {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean login(String compName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	private Connection getConnection() throws SQLException {
		// TODO: maybe we should catch the exception here
		// and close the program. It is too severe
		return DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, "root", "123123");
	}

	private void releaseConnection(Connection con) {
		// We currently are closing the connections. Later we
		// will move to a better solution using a connection pool
		// that Assaf will provide
		try {
			con.close();
		} catch (SQLException e) {
			// We don't care
			e.printStackTrace();
		}
	}
}
