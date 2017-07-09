/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
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
import com.project.beans.Customer;
import com.project.exceptions.CouponSystemException;
import com.project.main.CouponSystem;


// TODO: Auto-generated Javadoc
/**
 * The Class CompanyDBDAO implements CompanyDAO interface.
 * Providing methods to INSERT, UPDATE, DELETE 
 * and SELECT data from and to MySQL database.
 */
public class CompanyDBDAO implements CompanyDAO {

	private static final String TABLE_NAME = "company";
	private Connection con = null;
	
	/**
	 * A method to CREATE a new Company in the Company table with auto-generated ID 
	 *
	 * @param Company
	 *            the company to be created
	 * @throws CouponSystemException
	 * 
	 */
	@Override
	public void createCompany(Company company) throws CouponSystemException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			//Create MySQL INSERT prepare statement
			PreparedStatement stat = con.prepareStatement(
					"INSERT INTO " + TABLE_NAME + "(compName, password, email)" + " VALUES (?, ?, ?)");
			//Set parameters in the INSERT query
			stat.setString(1, company.getCompName());
			stat.setString(2, company.getPassword());
			stat.setString(3, company.getEmail());
			System.out.println("Executing: " + stat.toString());
			//Execute update statement
			int rowsInserted = stat.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new company " + company.getCompName() + " has been created successfully");
			} else
				System.out.println("An Error Has Occurred. Check if entered data is correct.");

		} catch (SQLException e) {

			throw new CouponSystemException("Cannot create company : " + company.getCompName() + ". " + e);

		} finally {
			//Release connection
			releaseConnection(con);
		}
	}

	/**
	 *  A method to REMOVE Company from the Company table
	 *
	 * @param Company
	 *            the company to be deleted
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	@Override
	public void removeCompany(Company company)throws CouponSystemException {
		//Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				//Create MySQL DELETE prepare statement
				PreparedStatement stat = con.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE compId=?");
				//Set parameter in the DELETE query 
				stat.setLong(1, company.getCompId());
				System.out.println("Executing: " + stat.toString());
				// Execute update statement
				int rowsDeleted = stat.executeUpdate();

				if (rowsDeleted > 0) {
					System.out.println("A company " + company.getCompName() + " has been deleted successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {

			throw new CouponSystemException("Cannot remove company : " + company.getCompName() + ". " + e);

		} finally {
			//Release connection
			releaseConnection(con);
		}
	}

	/**
	 * A method to UPDATE Company details in the Company table except Company name and ID
	 *
	 * @param Company
	 *            the company to be updated
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		//Get a connection from the Connection Pool
		try {
			con = getConnection();
			//Create MySQL UPDATE prepare statement
			PreparedStatement stat = con
					.prepareStatement("UPDATE " + TABLE_NAME + " SET email=?, password=? WHERE compName=?");
			//Set parameters in the UPDATE query
			stat.setString(1, company.getEmail());
			stat.setString(2, company.getPassword());
			stat.setString(3, company.getCompName());
			System.out.println("Executing: " + stat.toString());
            //Execute update statement
			int rowsUpdated = stat.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println(company + " has been updated successfully");
			} else
				System.out.println("An Error Has Occurred. Check if entered data is correct.");

		} catch (SQLException e) {

			throw new CouponSystemException("Cannot update company : " + company.getCompName() + ". " + e);

		} finally {
			//Release connection
			releaseConnection(con);
		}
	}

	/**
	 * A method to GET a Company by Company ID from Company table
	 *
	 * @param comp_id
	 *            the company id
	 * @return the company
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	@Override	
	public Company getCompany(long compId) throws CouponSystemException {
		//Create a new Company object
		Company company = new Company();
	        // Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				//Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE compId=? ");
				//Set parameter in the SELECT query
				stat.setLong(1, compId);
				System.out.println("Executing: " + stat.toString());
				//Execute query statement
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					//Set object's fields
					company.setCompId(rows.getLong("compId"));
					company.setCompName(rows.getString("compName"));
					company.setPassword(rows.getString("password"));
				}
			}
		} catch (SQLException e) {

			throw new CouponSystemException("Cannot found the company  with ID : " + compId + ". " + e);

		} finally {
			//Release connection
			releaseConnection(con);
		}
		return company;
	}

	/**
	 * A method to GET a collections of all companies from Company table
	 *
	 * @return the collection of all companies
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		ArrayList<Company> companies = new ArrayList<Company>();
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				//Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
				System.out.println("Executing: " + stat.toString());
				//Execute query statement
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

			throw new CouponSystemException("SQL Error" + ". " + e);

		} finally {
			//Release connection
			releaseConnection(con);
		}
		return companies;
	}

	/**
	 * A method to GET a collections of all coupons of a specific company  
	 *
	 * @param compId
	 *            the company id
	 * @return the coupons of the provided company ID 
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	@Override    
	public Collection<Coupon> getCoupons(long compId) throws CouponSystemException {		
		//Create a new Coupons collection
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				//Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM coupon.coupon where couponId in "
						+ "(select couponId from company_coupon where compId like " + compId + ")");
				//Execute query statement
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					//Create a new coupon object
					Coupon coupon = new Coupon();
					//Set object's fields
					coupon.setCouponId(rows.getLong("couponId"));
					coupon.setTitle(rows.getString("title"));
					coupon.setStartDate(rows.getDate("startDate"));
					coupon.setEndDate(rows.getDate("endDate"));
					coupon.setAmount(rows.getInt("amount"));
					coupon.setType(CouponType.valueOf(rows.getString("type")));
					coupon.setMessage(rows.getString("message"));
					coupon.setPrice(rows.getDouble("price"));
					coupon.setImage(rows.getString("image"));
					// Add the object to the Coupons Collection
					coupons.add(coupon);
				}
			}
		} catch (SQLException e) {

			throw new CouponSystemException("SQL Error " + ". " + e);

		} finally {
			//Release connection
			releaseConnection(con);
		}
		return coupons;

	}
	/**
	 * A method to LOGIN 
	 *
	 * @param compName
	 *            the company name
	 * @param password
	 *            the company password
	 * @return true, if successful
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	@Override
	public boolean login(String compName, String password) throws CouponSystemException {
		boolean succeeded = false;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			// Create MySQL SELECT prepare statement
			PreparedStatement stat = con.prepareStatement("SELECT password FROM " + TABLE_NAME + " WHERE compName=?");
			// Set parameter in the SELECT query
			stat.setString(1, compName);
			// Execute query statement
			ResultSet rows = stat.executeQuery();
			while (rows.next()) {
				String myPassword = rows.getString("password");
				if (myPassword.equals(password)) {
					System.out.println("Success!");
					return succeeded = true;
				} else {
					System.out.println("Failure!");
					return succeeded = false;
				}
			}
		} catch (SQLException e) {

			throw new CouponSystemException("An error occured" + e);
			
		} finally {
			//Release connection
			releaseConnection(con);
		}

		return succeeded;
	}

	/**
	 * A method to GET a Company ID by Company Name 
	 *
	 * @param compName
	 *            the company name
	 * @return the company id
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	@Override 
	public long getCompanyId(String compName) throws CouponSystemException {
		long compId = 0;
		//Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				//Create MySQL SELECT prepare statement
				PreparedStatement stat = con
						.prepareStatement("SELECT compId FROM " + TABLE_NAME + " WHERE compName=? ");
				//Set parameter in the SELECT query
				stat.setString(1, compName);
				System.out.println("Executing: " + stat.toString());
				//Execute query statement
				ResultSet rows = stat.executeQuery();
				// Set field
				compId = rows.getLong("compId");
			}
		} catch (SQLException e) {

			throw new CouponSystemException("Cannot found company ID " + compName + ". " + e);

		} finally {
			//Release connection
			releaseConnection(con);
		}
		return compId;
	}
	
	@Override
	public Company getCompanyByName(String name) throws CouponSystemException {
		Company company = null;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE compName=?");
				// Set parameter in the SELECT query
				stat.setString(1, name);
				System.out.println("Executing: " + stat.toString());
				// Execute query statement
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					// Create a new Customer object
					company = new Company();
					// Set object's fields
					company.setCompId(rows.getLong("compId"));
					company.setCompName(rows.getString("compName"));
					company.setPassword(rows.getString("password"));
					return company;
				}
			}
		} catch (SQLException e) {

			throw new CouponSystemException("Cannot found compnany " + name + ". " + e);

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return company;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection to pool
	 * @throws SQLException
	 *             the SQL exception
	 */
	private Connection getConnection() throws SQLException {
		
		return CouponSystem.getConnectionPool().getConnection();
	}

	/**
	 * Release connection.
	 *
	 * @param con
	 *            the connection to pool
	 */
	private void releaseConnection(Connection con) {

		CouponSystem.getConnectionPool().free(con);
	}

	@Override
	public void addCoupon(Company company, long couponId) throws CouponSystemException {
		// Get a connection from the Connection Pool
				try {
					con = getConnection();
					if (con != null) {
						System.out.println("Connected");
						// Create MySQL INSERT prepare statement
						PreparedStatement stat = con
								.prepareStatement("INSERT INTO company_coupon " + "(compId, couponId)" + " VALUES (?, ?)");
						// Set parameter in the INSERT query
						stat.setLong(1, company.getCompId());
						stat.setLong(2, couponId);
						System.out.println("Executing: " + stat.toString());
						// Execute query statement
						int rowsInserted = stat.executeUpdate();
						if (rowsInserted > 0) {
							System.out.println("A new coupon has been added successfully");
						} else
							System.out.println("An Error Has Occurred.");
					}
				} catch (SQLException e) {

					throw new CouponSystemException("Cannot add coupon : " + ". " + e);

				} finally {
					// Release connection
					releaseConnection(con);
				}
		
	}

}
