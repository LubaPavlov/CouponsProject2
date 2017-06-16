package imports.d20170427coupProjDafnaWeiss.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
/**
 * Coupon DAO implementation: Coupon Methods using the DataBase
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class CouponDBDAO implements CouponDAO {
	
	private static ConnectionPoolSingleton pool = ConnectionPoolSingleton.getInstance();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * An empty constructor
	 */
	public CouponDBDAO(){
	}
	
	// ADDING a new Coupon in the DB's Coupon table and getting the generated id
	@Override
	/**
	 * Create a new Coupon in the Coupon table in the Database
	 * @param coupon The given coupon to be created
	 * @throws MyException SQL exception
	 */
	public void createCoupon(Coupon coupon) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			String query = "INSERT INTO coupon(`title`,`start_date`,`end_date`,`amount`,`type`,`message`,`price`,`image`) "
					+ "VALUES ('" + coupon.getTitle() + "', '" + sdf.format(coupon.getStartDate()) +  "', '" 
					+ sdf.format(coupon.getEndDate()) + "', '" + coupon.getAmount() + "', '" + coupon.getType().name() + "', '" 
					+ coupon.getMessage() + "', '" + coupon.getPrice() + "', '" + coupon.getImage() +"')";
			
			// setting the generated id from the DB's Company table
			Statement stmt = connection.createStatement();
			int numOfIds = stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			
			if (numOfIds > 0){
				
				ResultSet results = stmt.getGeneratedKeys();
				
				if (results.next()){
					coupon.setId(results.getInt(1));
				}
			}
		}
		
		catch (SQLException e){
			
			throw new MyException("CouponDBDAO: An Error occured while trying to INSERT A NEW COUPON into the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// REMOVING Coupon from the DB's tables
	@Override
	/**
	 * Remove a Coupon from Database
	 * @param coupon The given coupon to be removed
	 * @throws MyException SQL exception
	 */
	public void removeCoupon(Coupon coupon) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			// deleting the coupon from the COMPANY-JOIN table
			connection.createStatement().execute("DELETE FROM company_coupon WHERE coupon_id=" + coupon.getId());
			System.out.println("CouponDBDAO: Deleting COUPON id = " + coupon.getId() + " from the COMPANY_COUPON JOIN table");
			
			// deleting the coupon from the CUSTOMER-JOIN table
			connection.createStatement().execute("DELETE FROM customer_coupon WHERE coupon_id=" + coupon.getId());
			System.out.println("CouponDBDAO: Deleting COUPON id = " + coupon.getId() + " from the CUSTOMER_COUPON JOIN table");
			
			// deleting the coupon from the COUPON table
			connection.createStatement().execute("DELETE FROM coupon WHERE id=" + coupon.getId());
			System.out.println("CouponDBDAO: Deleting COUPON id = " + coupon.getId() + " from the COUPON table");
			
		}
		
		catch (SQLException e){
			
			throw new MyException("CouponDBDAO: An Error occured while trying to DELETE A COUPON from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// UPDATING the DB's Coupon table
	@Override
	/**
	 * Update a Coupon in the Coupon table in the Database
	 * @param coupon The given coupon to be updated
	 * @throws MyException SQL exception
	 */
	public void updateCoupon(Coupon coupon) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			long id = coupon.getId();
			String newTitle = coupon.getTitle();
			Date newStartDate = coupon.getStartDate();
			Date newEndDate = coupon.getEndDate();
			int newAmount = coupon.getAmount();
			CouponType newType = coupon.getType();
			String newMessage = coupon.getMessage();
			double newPrice = coupon.getPrice();
			String newImage = coupon.getImage();
			
			// updating all the Coupon fields
			String query = "UPDATE coupon SET `title`='" + newTitle + "', "
					+ "`start_date`='" + sdf.format(newStartDate) + "', " + "`end_date`='" + sdf.format(newEndDate) + "', "
					+ "`amount`=" + newAmount + ", " + "`type`='" + newType.name() + "', "
					+ "`message`='" + newMessage + "', " + "`price`=" + newPrice + ", "
					+ "`image`='" + newImage + "' WHERE id=" + id;
			
			connection.createStatement().execute(query);
		}
		
		catch (SQLException e){
			
			throw new MyException("CouponDBDAO: An Error occured while trying to UPDATE A COUPON in the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// SET and GET a new Coupon Object in the DAO classes by reading from the DB's Coupon table
	@Override
	/**
	 * Get a Coupon from the Coupon table in the Database BY ID
	 * @param id the coupon's ID in the Database that we want to get
	 * @return Coupon The coupon of the given ID
	 * @throws MyException SQL exception 
	 */
	public Coupon getCoupon(long id) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM coupon WHERE id=" + id);
			Coupon coupon = null;
			
			if(results.next()){
				// creating new Coupon object
				coupon = new Coupon();
				
				// setting the object's fields
				coupon.setId(results.getLong("id"));
				coupon.setTitle(results.getString("title"));
				coupon.setStartDate(results.getDate("start_date"));
				coupon.setEndDate(results.getDate("end_date"));
				coupon.setAmount(results.getInt("amount"));
				
				CouponType couponType = CouponType.valueOf(results.getString("type"));
				coupon.setType(couponType);
				
				coupon.setMessage(results.getString("message"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setImage(results.getString("image"));
			}
			
			return coupon;
		}
		
		catch (SQLException e){
			
			throw new MyException("An Error occured while trying to GET A COUPON BY ID from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// GETTING ALL the Coupons from the DB's Coupon table
	@Override
	/**
	 * Get all the Coupons from the Coupon table in the Database 
	 * @return A collection of all the coupons from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getAllCoupons() throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Coupon> allCoupons = new ArrayList<Coupon>();
			Coupon coupon = null;
			
			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM coupon");
			
			while(results.next()){
				// creating new Coupon object
				coupon = new Coupon();
				
				// setting the object's fields
				coupon.setId(results.getLong("id"));
				coupon.setTitle(results.getString("title"));
				coupon.setStartDate(results.getDate("start_date"));
				coupon.setEndDate(results.getDate("end_date"));
				coupon.setAmount(results.getInt("amount"));
				
				CouponType couponType = CouponType.valueOf(results.getString("type"));
				coupon.setType(couponType);
				
				coupon.setMessage(results.getString("message"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setImage(results.getString("image"));
				
				// adding the object to the Coupons Collection
				allCoupons.add(coupon);
			}
			
			return allCoupons;
		}
		
		catch (SQLException e){
			
			throw new MyException("CouponDBDAO: An Error occured while trying to GET ALL COUPONS from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// GETTING a collection of all the Coupons of a specific TYPE
	@Override
	/**
	 * Get all the Coupons of the given TYPE from the Coupon table in the Database
	 * @param couponType The given coupon Type to get the coupons according to it
	 * @return A collection of all the coupons of the given type from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Coupon> couponsByType = new ArrayList<Coupon>();
			Coupon coupon = null;
			
			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM coupon WHERE `type` = '" + couponType + "'");
			while(results.next()){
				// creating new Coupon object
				coupon = new Coupon();
				
				// setting the object's fields
				coupon.setId(results.getLong("id"));
				coupon.setTitle(results.getString("title"));
				coupon.setStartDate(results.getDate("start_date"));
				coupon.setEndDate(results.getDate("end_date"));
				coupon.setAmount(results.getInt("amount"));
				coupon.setType(couponType);
				coupon.setMessage(results.getString("message"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setImage(results.getString("image"));
				
				// adding the object to the Coupons Collection
				couponsByType.add(coupon);
			}
			
			return couponsByType;
		}
		
		catch (SQLException e){
			
			throw new MyException("CouponDBDAO: An Error occured while trying to GET A COUPON BY TYPE from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// CHECKING if there is already a Coupon with the SAME TITLE
	/**
	 * Check if the given Coupon TITLE is already exist in the Coupon table in the Database
	 * @param title The given title of the new coupon
	 * @return True if the given title exist in the coupon table in the Database otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean isTitleExist(String title) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			ResultSet results = connection.createStatement().executeQuery("SELECT id FROM coupon WHERE `title` = '" + title + "'");
			return (results.next());		// there is a coupon with the same title
		}
		
		catch (SQLException e){
			
			throw new MyException("CouponDBDAO: An Error occured while a COMPANY is trying to CREATE A NEW COUPON: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}
}




