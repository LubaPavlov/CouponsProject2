package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.exceptions.DAOException;

//
//CompanyDBDAO class implements CompanyDAO interface.
//Provide's methods to insert and retrieve data from and to the DB.
//

public class CompanyDBDAO implements CompanyDAO {

	private static final String TABLE_NAME = "company";
	private Connection con = null;

	@Override
	public void createCompany(Company company) throws DAOException{

		/// 1. get a connection (from pool)
		try {
			con = getConnection();

			// 2. create sql insert
			PreparedStatement stat = con.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?,?)");
			stat.setLong(1, company.getComp_id());
			stat.setString(2, company.getCompName());
			stat.setString(3, company.getPassword());
			stat.setString(4, company.getEmail());

			System.out.println("Executing: " + stat.toString());
			stat.executeUpdate();

		} catch (SQLException e) {
			// TODO: deal with exception
			e.printStackTrace();
		}

		finally {
			// 3. release connection
			releaseConnection(con);
		}

	}

	@Override
	public void removeCompany(Company Company) {
		// TODO data should be removed in join table as well

	}

	@Override
	public void updateCompany(Company Company) {
		// TODO company name cannot be updated

	}

	@Override
	public Company getCompany(long comp_id) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Company> getAllCompanies() {
		return null;
		// TODO Auto-generated method stub

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
		// String url = "jdbc:mysql://localhost:3306/coupon", "root",
		// "password";
		return DriverManager.getConnection("jdbc:mysql://localhost/coupon", "root", "123123");
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
