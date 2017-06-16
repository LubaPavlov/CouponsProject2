package imports.d20170427coupProjDafnaWeiss.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
import imports.d20170427coupProjDafnaWeiss.model.Customer;
/**
 * Customer DAO implementation: Customer Methods using the DataBase
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class CustomerDBDAO implements CustomerDAO {

	private static ConnectionPoolSingleton pool = ConnectionPoolSingleton.getInstance();
	
	/**
	 * An empty constructor
	 */
	public CustomerDBDAO(){
	}
	
	// LOGIN - checking whether there is a customer with the given name with the given password
	@Override
	/**
	 * Login: check the name and the password of the customer in conjunction with the customer table in the Database 
	 * @param custName The login customer name
	 * @param password The login customer password
	 * @return Customer The customer that succeed to logged in the system or NULL if the login failed
	 * @throws MyException SQL exception
	 */
	public Customer login(String custName, String password) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			Customer customer = null;
			
			ResultSet results = connection.createStatement().executeQuery("SELECT id FROM customer WHERE `cust_name` = '" + custName 
					+ "' AND `password` = '" + password + "'");
			
			if(results.next()){
				
				customer = getCustomer(results.getLong("id"));
			}
			
			else {
				
				System.out.println("The CUSTOMER NAME or the PASSWORD are incorrect - please try again");
			}
			
			return customer;
		}
		
		catch (SQLException e){
			
			throw new MyException("An Error occured while a CUSTOMER is trying to LOGIN: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// CREATE a new Customer - ADDING a new Customer in the DB's Customer table getting the generated id
	@Override
	/**
	 * Create a new Customer in the Customer table in the Database
	 * @param customer The given customer to be created
	 * @throws MyException SQL exception
	 */
	public void createCustomer(Customer customer) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			String query = "INSERT INTO customer(`cust_name`,`password`) VALUES ('" + customer.getCustName() + "', '" + customer.getPassword() +  "')";
			
			// setting the generated id from the DB's Company table
			Statement stmt = connection.createStatement();
			int numOfIds = stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			
			if (numOfIds > 0){
				
				ResultSet results = stmt.getGeneratedKeys();
				
				if (results.next()){
					customer.setId(results.getInt(1));
				}
			}
		}
		catch (SQLException e){
			
			throw new MyException("An Error occured while trying to INSERT A NEW CUSTOMER into the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// REMOVING Customer from the DB's tables
	@Override
	/**
	 * Remove a Customer from Database
	 * @param customer The given customer to be removed
	 * @throws MyException SQL exception
	 */
	public void removeCustomer(Customer customer) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			// deleting the customer from the JOIN table
			connection.createStatement().execute("DELETE FROM customer_coupon WHERE cust_id=" + customer.getId());
			// deleting the customer from the CUSTOMER table
			connection.createStatement().execute("DELETE FROM customer WHERE id=" + customer.getId());
		}
		
		catch (SQLException e){
			
			throw new MyException("An Error occured while trying to DELETE A CUSTOMER from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// UPDATING the DB's Customer table
	@Override
	/**
	 * Update a Customer in the Customer table in the Database
	 * @param customer The given customer to be updated
	 * @throws MyException SQL exception
	 */
	public void updateCustomer(Customer customer) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			connection.createStatement().execute("UPDATE customer SET `password`='" + customer.getPassword() + "' WHERE id=" + customer.getId());
		}
		
		catch (SQLException e){
			
			throw new MyException("An Error occured while trying to UPDATE A CUSTOMER in the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// SET and GET a new Customer Object in the DAO classes by reading from the DB's Customer table
	@Override
	/**
	 * Get a Customer from the Customer table in the Database BY ID
	 * @param id The customer's ID in the Database that we want to get
	 * @return Customer the customer of the given ID
	 * @throws MyException SQL exception 
	 */
	public Customer getCustomer(long id) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM customer WHERE id=" + id);
			
			Customer customer = null;
			
			if(results.next()){
				// creating new Customer object
				customer = new Customer();
				
				// setting the object's fields
				customer.setId(results.getLong("id"));
				customer.setCustName(results.getString("cust_name"));
				customer.setPassword(results.getString("password"));
			}
			
			return customer;
		}
		
		catch (SQLException e){
			
			throw new MyException("An Error occured while trying to GET A CUSTOMER from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// GETTING ALL the Customers from the DB's Customer table
	@Override
	/**
	 * Get all the Customers from the Customer table in the Database 
	 * @return A collection of all the customers from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Customer> getAllCustomers() throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Customer> allCustomers = new ArrayList<Customer>();
			Customer customer = null;
			
			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM customer");
			
			while(results.next()){
				// creating new Customer object
				customer = new Customer();
				
				// setting the object's fields
				customer.setId(results.getLong("id"));
				customer.setCustName(results.getString("cust_name"));
				customer.setPassword(results.getString("password"));
				
				// adding the object to the Customers Collection
				allCustomers.add(customer);
			}
			
			return allCustomers;
		}
		
		catch (SQLException e){
			
			throw new MyException("An Error occured while trying to GET ALL CUSTOMERS from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// GETTING ALL COUPONS that was purchased by specific Customer  
	@Override
	/**
	 * Get ALL the given Customer's purchased COUPONS from the customer_coupon join table in the Database
	 * @param customer The given customer whose list of purchased coupons we want to get 
	 * @return A collection of all the given customer's purchased coupons from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCoupons(Customer customer) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			Coupon coupon = null;
			
			String query = "SELECT c.id, c.title, c.start_date, c.end_date, c.amount, c.type, c.message, c.price, c.image "
					+ "FROM customer_coupon cc INNER JOIN coupon c ON (cc.coupon_id = c.id) WHERE cc.cust_id=" + customer.getId();
			
			ResultSet results = connection.createStatement().executeQuery(query);
			
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
				coupons.add(coupon);
			}
			
			customer.setCoupons(coupons);
			return coupons;
		}
		
		catch (SQLException e){
			
			throw new MyException
			("An Error occured while trying to GET ALL COUPONS of " + customer.getCustName() + " from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// GETTING the list of all COUPONS that belong to a specific customer BY TYPE 
	/**
	 * Get all the given Customer's purchased COUPONS of the given TYPE, from the Database
	 * @param customer The given customer whose list of purchased coupons we want to get by type  
	 * @param couponType The given coupon Type to get the list coupons according to it
	 * @return A collection of all the given customer's purchased coupons of the given type from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByType(Customer customer, CouponType couponType) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			Collection<Coupon> couponsByType = new ArrayList<Coupon>();
			Coupon coupon = null;
			
			String query = "SELECT c.id, c.title, c.start_date, c.end_date, c.amount, c.type, c.message, c.price, c.image "
					+ "FROM customer_coupon cc INNER JOIN coupon c ON (cc.coupon_id = c.id) WHERE cc.cust_id=" + customer.getId()+ " AND c.type = '" + couponType + "'";
			
			ResultSet results = connection.createStatement().executeQuery(query);
			
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
			
			customer.setCoupons(couponsByType);
			return couponsByType;
		}
		
		catch (SQLException e){
			
			throw new MyException
			("An Error occured while trying to GET ALL COUPONS of " + customer.getCustName() + " from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// GETTING the list of COUPONS that belong to a specific customer BY MAX PRICE 
	/**
	 * Get all the given Customer's purchased COUPONS that cost less or equal to the given Maximum PRICE, from the Database,
	 * @param customer The given customer whose list of purchased coupons we want to get by Maximum PRICE  
	 * @param maxPrice The given Maximum PRICE to get the list of coupons according to it
	 * @return A collection of all the given customer's purchased coupons that cost less or equal to the given Maximum PRICE from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByPrice(Customer customer, double maxPrice) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Coupon> couponsByPrice = new ArrayList<Coupon>();
			Coupon coupon = null;
			
			String query = "SELECT c.id, c.title, c.start_date, c.end_date, c.amount, c.type, c.message, c.price, c.image "
					+ "FROM customer_coupon cc INNER JOIN coupon c ON (cc.coupon_id = c.id) WHERE cc.cust_id=" + customer.getId()+ " AND c.price <= " + maxPrice;
			
			ResultSet results = connection.createStatement().executeQuery(query);
			
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
				couponsByPrice.add(coupon);
			}
			
			customer.setCoupons(couponsByPrice);
			return couponsByPrice;
		}
		
		catch (SQLException e){
			
			throw new MyException
			("An Error occured while trying to GET ALL COUPONS of " + customer.getCustName() + " from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// ADDING a new COUPON to the Customer_Coupon JOIN table in DB 
	/**
	 * Add a new coupon to the Customer_Coupon JOIN table in the Database
	 * @param customer The given customer that purchased the given coupon 
	 * @param coupon The given coupon we want to add to the customer_coupon join table in the Database
	 * @throws MyException SQL exception
	 */
	public void addCoupon (Customer customer, Coupon coupon) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			String query = "INSERT INTO customer_coupon(`cust_id`,`coupon_id`) VALUES (" + customer.getId() + ", " + coupon.getId() + ")";
			connection.createStatement().execute(query);
		}
		
		catch (SQLException e){
			
			throw new MyException
			("An Error occured while trying to GET ALL COUPONS of " + customer.getCustName() + " from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// CHECHING if the Customer had already PURCHASED this Coupon
	/**
	 * Check if the given Customer had already purchased the given Coupon
	 * @param customer The given customer
	 * @param coupon The given coupon we want to check
	 * @return True if the given coupon was already purchased by the given customer otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean isCouponExist(Customer customer, Coupon coupon) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			String query = "SELECT coupon_id FROM customer_coupon WHERE cust_id = " + customer.getId() + " AND coupon_id = " + coupon.getId();
			ResultSet results = connection.createStatement().executeQuery(query);
			return (results.next());		// the customer had already purchased this coupon
		}
		
		catch (SQLException e){
			
			throw new MyException("An Error occured while a CUSTOMER is trying to PURCHASE a Coupon: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}
	
	// CHECKING if there is already a Customer with the SAME NAME
	/**
	 * Check if the Customer name is already exist in the Customer table in the Database
	 * @param custName The given name of the new customer
	 * @return True if the given name exist in the customer table in the Database otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean isNameExist(String custName) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			ResultSet results = connection.createStatement().executeQuery("SELECT id FROM customer WHERE cust_name = '" + custName + "'");
			return (results.next());		// there is a customer with the same name	
		}
		
		catch (SQLException e){
			
			throw new MyException("An Error occured while a CUSTOMER is trying to LOGIN: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}
	
	// CHECKING if the Coupon was PURCHASED by the Customer 
	/**
	 * Check if the given COUPON was purchased by the given Customer 
	 * @param custId The Id of the given customer	
	 * @param couponId The Id of the given coupon
	 * @return True if the given coupon was purchased by the given customer otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean isCouponBelongToCustomer(long custId, long couponId) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			ResultSet results = connection.createStatement().executeQuery
					("SELECT cust_id FROM customer_coupon WHERE `cust_id` = " + custId + " && `coupon_id` = " + couponId);
			return (results.next());
		}
		
		catch (SQLException e){
			
			throw new MyException("CustomerDBDAO: An Error occured while a checking if COUPON was PURCHASED by a CUSTOMER: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}
}
