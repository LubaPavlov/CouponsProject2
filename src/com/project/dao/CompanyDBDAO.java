package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.exceptions.DAOException;
import com.project.main.CouponSystem;

//
//CompanyDBDAO class implements CompanyDAO interface.
//Providing methods to insert, update and select data from and to the Data Base.
//

public class CompanyDBDAO implements CompanyDAO {

	private static final String TABLE_NAME = "company";
	private Connection con = null;

	@Override
	public void createCompany(Company company) throws DAOException {
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			// 2. Create SQL INSERT
			PreparedStatement stat = con.prepareStatement(
					"INSERT INTO " + TABLE_NAME + "(compName, password, email)" + " VALUES (?, ?, ?)");
			stat.setString(1, company.getCompName());
			stat.setString(2, company.getPassword());
			stat.setString(3, company.getEmail());
			System.out.println("Executing: " + stat.toString());
			int rowsInserted = stat.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("A new company " + company.getCompName() + " has been created successfully");
			} else
				System.out.println("An Error Has Occurred. Check if entered data is correct.");

		} catch (SQLException e) {

			System.out.println("Cannot create company : " + company.getCompName() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
	}

	@Override
	public void removeCompany(Company company) {
		/// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				PreparedStatement stat = con.prepareStatement("DELETE FROM " + TABLE_NAME + "WHERE compId=?");
				stat.setLong(1, company.getCompId());
				System.out.println("Executing: " + stat.toString());
				int rowsDeleted = stat.executeUpdate();

				if (rowsDeleted > 0) {
					System.out.println("A company " + company.getCompName() + " has been deleted successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {

			System.out.println("Cannot remove company : " + company.getCompName() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
	}

	@Override
	public void updateCompany(Company company) {
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			PreparedStatement stat = con
					.prepareStatement("UPDATE " + TABLE_NAME + " SET email=?, password=? WHERE compName=?");
			stat.setString(1, company.getEmail());
			stat.setString(2, company.getPassword());
			stat.setString(3, company.getCompName());

			System.out.println("Executing: " + stat.toString());

			int rowsUpdated = stat.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println(company + " has been updated successfully");
			} else
				System.out.println("An Error Has Occurred. Check if entered data is correct.");

		} catch (SQLException e) {

			System.out.println("Cannot update company : " + company.getCompName() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
	}

	@Override
	public Company getCompany(long compId) {
		// 1. Get a connection (from pool)
		Company company = new Company();
		// 1. Get a connection (from pool)
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

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return company;
	}

	@Override
	public Collection<Company> getAllCompanies() {
		ArrayList<Company> companies = new ArrayList<Company>();
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
				System.out.println("Executing: " + stat.toString());
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					Company company = new Company();
					company.setCompId(rows.getLong("compId"));
					company.setCompName(rows.getString("compName"));
					company.setPassword(rows.getString("password"));
					companies.add(company);
				}
			}
		} catch (SQLException e) {

			System.out.println("SQL Error" + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return companies;
	}

	@Override
	public Collection<Coupon> getCoupons(long compId) {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				PreparedStatement stat = con.prepareStatement("SELECT * FROM coupon.coupon where couponId in "
						+ "(select couponId from company_coupon where compId like " + compId + ")");
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					Coupon coupon = new Coupon();
					coupon.setCouponId(rows.getLong("couponId"));
					coupon.setTitle(rows.getString("title"));
					coupon.setStartDate(rows.getDate("startDate"));
					coupon.setEndDate(rows.getDate("endDate"));
					coupon.setAmount(rows.getInt("amount"));
					coupon.setType(CouponType.valueOf(rows.getString("type")));
					coupon.setMessage(rows.getString("message"));
					coupon.setPrice(rows.getDouble("price"));
					coupon.setImage(rows.getString("image"));
					coupons.add(coupon);
				}
			}
		} catch (SQLException e) {

			System.out.println("SQL Error" + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return coupons;

	}

	@Override
	public boolean login(String compName, String password) {
		boolean succeeded = false;
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE compName=?");
			stat.setString(1, compName);

			ResultSet rows = stat.executeQuery();
			while (rows.next()) {
				String myName = rows.getString("compName");
				String myPassword = rows.getString("password");

				if (myName.equals(compName) && myPassword.equals(password)) {
					System.out.println("Success!");
					return succeeded = true;
				} else {
					System.out.println("Failure!");
					return succeeded = false;
				}
			}
		} catch (SQLException e) {

			System.out.println("An error occured" + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}

		return succeeded;
	}

	@Override
	public long getCompanyId(String compName) throws DAOException {
		long compId = 0;
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				PreparedStatement stat = con
						.prepareStatement("SELECT compId FROM " + TABLE_NAME + " WHERE compName=? ");
				stat.setString(1, compName);
				System.out.println("Executing: " + stat.toString());
				ResultSet rows = stat.executeQuery();
				compId = rows.getLong("compId");
			}
		} catch (SQLException e) {

			System.out.println("Cannot found company with ID " + compName + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return compId;
	}

	private Connection getConnection() throws SQLException {
		
		return CouponSystem.getConnectionPool().getConnection();
	}

	private void releaseConnection(Connection con) {

		CouponSystem.getConnectionPool().free(con);
	}
}
