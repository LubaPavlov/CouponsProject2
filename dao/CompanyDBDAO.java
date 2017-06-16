package imports.d20170427coupProjDafnaWeiss.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Company;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
/**
 * Company DAO implementation: Company Methods using the DataBase
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class CompanyDBDAO implements CompanyDAO {

	private static ConnectionPoolSingleton pool = ConnectionPoolSingleton.getInstance();
	
	/**
	 * An empty constructor
	 */
	public CompanyDBDAO(){
	}
	
	// LOGIN - checking whether there is a company with the given name with the given password
	@Override
	/**
	 * Login: check the name and the password of the company in conjunction with the company table in the Database 
	 * @param compName The login company name
	 * @param password The login company password
	 * @return Company The company that succeed to logged in the system or NULL if the login failed
	 * @throws MyException SQL exception
	 */
	public Company login(String compName, String password) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Company company = null;
			
			// checking if the there is a Company with the login NAME and PASSWORD
			ResultSet results = connection.createStatement().executeQuery("SELECT id FROM company WHERE `comp_name` = '" + compName 
					+ "' AND `password` = '" + password + "'");
					+ "' AND `password` = '" + password + "'");

			if(results.next()){
				
				company = getCompany(results.getLong("id"));
			}
			
			else {
				System.out.println("CompanyDBDAO: The COMPANY NAME or the PASSWORD are incorrect - please try again");
			}
			System.out.println("CompanyDBDAO: company = " + company);
			return company;
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while a COMPANY is trying to LOGIN: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}
	
	// CREATE a new Company - ADDING a new Company in the DB's Company table and getting the generated id
	@Override
	/**
	 * Create a new Company in the Company table in the Database
	 * @param company The given company to be created
	 * @throws MyException SQL exception
	 */
	public void createCompany(Company company) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			String query = "INSERT INTO company(`comp_name`,`password`,`email`) "
					+ "VALUES ('" + company.getCompName() + "', '" + company.getPassword() + "', '" + company.getEmail() + "')";
			
			// getting and setting the generated id from the DB's Company table
			Statement stmt = connection.createStatement();
			int numOfIds = stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			
			if (numOfIds > 0){
				
				ResultSet results = stmt.getGeneratedKeys();
				
				if (results.next()){
					company.setId(results.getInt(1));
				}
			}
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while trying to INSERT A NEW COMPANY into the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// REMOVING a Company from the DB's Company table (and the JOIN tables)
	@Override
	/**
	 * Remove a Company from Database, including all the company's coupons
	 * @param company The given company to be removed
	 * @throws MyException SQL exception
	 */
	public void removeCompany(Company company) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			// getting a list with all the COMPANY'S COUPONS
			Collection<Coupon> companyCoupons = getCoupons(company);
			
			// deleting All the COMPANY'S COUPONS
			// checking if this Company has any COUPONS
			if (!companyCoupons.isEmpty()){
				
				// deleting the Company from the COMPANY_COUPON JOIN table 
				connection.createStatement().execute("DELETE FROM company_coupon WHERE comp_id=" + company.getId());
				System.out.println("Deleting " + company.getCompName() + " COMPANY from the COMPANY_COUPON JOIN table");
				
				// getting the Coupon IDs for the query of the DELETE coupons FROM company_coupon
				String couponIds = "";
				
				for (Coupon coupon : companyCoupons){
					
					couponIds += coupon.getId() + ",";
					System.out.println("CompanyDBDAO: DELETING " + coupon.getTitle() + " COUPON, ID = " + coupon.getId());
				}
				
				// deleting the COMPANY'S COUPONS from the CUSTOMER_COUPON JOIN table 
				couponIds = couponIds.substring(0,couponIds.length()-1); // removing the last comma
				connection.createStatement().execute("DELETE FROM customer_coupon WHERE coupon_id IN (" + couponIds + ")");
				System.out.println("CompanyDBDAO: Deleting COUPONS from the CUSTOMER_COUPON JOIN table");
				
				// deleting COUPONS from the Coupon table
				connection.createStatement().execute("DELETE FROM coupon WHERE id IN (" + couponIds + ")");
				System.out.println("CompanyDBDAO: Deleting COUPONS from the COUPON table");
			}
			
			else {
				System.out.println("CompanyDBDAO: The COMPANY: " + company.getCompName() + " has NO COUPONS");
			}
			
			// deleting the Company from the COMPANY table
			connection.createStatement().execute("DELETE FROM company WHERE id=" + company.getId());
			System.out.println("Deleting COMPANY from the COMPANY table");
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while trying to DELETE A COMPANY from the DB table: " + e.getMessage());
		}
		
		finally {
			// always return the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}

	// UPDATING the DB's Company table (except the Company Name)
	@Override
	/**
	 * Update a Company in the Company table in the Database
	 * @param company The given company to be updated
	 * @throws MyException SQL exception
	 */
	public void updateCompany(Company company) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			String query = "UPDATE company SET `password`='" + company.getPassword() + "', " + "`email`='" + company.getEmail() + "' WHERE id=" + company.getId();
			connection.createStatement().execute(query);
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while trying to UPDATE A COMPANY in the DB table: " + e.getMessage());
		}
		
		finally {
			// always return the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}

	// SET and GET a Company Object in the Company Model class by reading from the DB's Company table by ID
	@Override
	/**
	 * Get a Company from the Company table in the Database BY ID
	 * @param id The company's ID in the Database that we want to get
	 * @return Company the company of the given ID
	 * @throws MyException SQL exception 
	 */
	public Company getCompany(long id) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM company WHERE id=" + id);
			
			Company company = null;
			
			if(results.next()){
				// creating new Company object
				company = new Company();
				
				// setting the object's fields
				company.setId(results.getLong("id"));
				company.setCompName(results.getString("comp_name"));
				company.setPassword(results.getString("password"));
				company.setEmail(results.getString("email"));
			}
			
			return company;
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while trying to GET A COMPANY from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}

	// GETTING ALL the Companies from the DB's Company table
	@Override
	/**
	 * Get all the Companies from the Company table in the Database 
	 * @return A collection of all the companies from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Company> getAllCompanies() throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Company> allCompanies = new ArrayList<Company>();
			Company company = null;
			
			ResultSet results = connection.createStatement().executeQuery("SELECT * FROM company");
			
			while(results.next()){
				// creating new Company object
				company = new Company();
				
				// setting the object's fields
				company.setId(results.getLong("id"));
				company.setCompName(results.getString("comp_name"));
				company.setPassword(results.getString("password"));
				company.setEmail(results.getString("email"));
				
				// adding the object to the Companies Collection
				allCompanies.add(company);
			}
			
			return allCompanies;
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while trying to GET ALL COMPANIES from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}

	// GETTING the list of COUPONS that belong to a specific Company
	@Override
	/**
	 * Get ALL the given Company's COUPONS from the company_coupon join table in the Database
	 * @param company The given company whose list of coupons we want to get 
	 * @return A collection of all the given company's coupons from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCoupons(Company company) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Coupon> coupons = new ArrayList<Coupon>();
			Coupon coupon = null;
			
			String query = "SELECT c.id, c.title, c.start_date, c.end_date, c.amount, c.type, c.message, c.price, c.image "
					+ "FROM company_coupon cc INNER JOIN coupon c ON (cc.coupon_id = c.id) WHERE cc.comp_id=" + company.getId();
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
			
			company.setCoupons(coupons);
			return coupons;
		}
		
		catch (SQLException e){
			
			throw new MyException
			("CompanyDBDAO: An Error occured while trying to GET ALL COUPONS of " + company.getCompName() + " from the DB table: " + e.getMessage());
		}
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}

	// GETTING the list of COUPONS that belong to a specific company BY TYPE
	/**
	 * Get all the given Company's COUPONS of the given TYPE, from the Database
	 * @param company The given company whose list of coupons we want to get by type  
	 * @param couponType The given coupon Type to get the list coupons according to it
	 * @return A collection of all the given company's coupons of the given type from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByType(Company company,CouponType couponType) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Coupon> couponsByType = new ArrayList<Coupon>();
			Coupon coupon = null;
			
			String query = "SELECT c.id, c.title, c.start_date, c.end_date, c.amount, c.message, c.price, c.image "
					+ "FROM company_coupon cc INNER JOIN coupon c ON (cc.coupon_id = c.id) WHERE cc.comp_id=" + company.getId() + " AND c.type = '" + couponType.name() + "'";
			
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
			
			return couponsByType;
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while trying to GET ALL COUPONS BY TYPE of " + company.getCompName() + " from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}

	// GETTING the list of COUPONS that belong to a specific company BY MAXIMUM PRICE
	/**
	 * Get all the given Company's COUPONS that cost less or equal to the given Maximum PRICE, from the Database,
	 * @param company The given company whose list of coupons we want to get by Maximum PRICE  
	 * @param maxPrice The given Maximum PRICE to get the list of coupons according to it
	 * @return A collection of all the given company's coupons that cost less or equal to the given Maximum PRICE from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByPrice(Company company, double maxPrice) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Coupon> couponsByPrice = new ArrayList<Coupon>();
			Coupon coupon = null;
			
			String query = "SELECT c.id, c.title, c.start_date, c.end_date, c.amount, c.type, c.message, c.price, c.image "
					+ "FROM company_coupon cc INNER JOIN coupon c ON (cc.coupon_id = c.id) WHERE cc.comp_id= " + company.getId() + " AND c.price <= " + maxPrice ;
			
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
			
			return couponsByPrice;
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while trying to GET ALL COUPONS BY MAX PRICE of " + company.getCompName() + " from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// GETTING the list of COUPONS that belong to a specific company BY MAXIMUM DATE
	/**
	 * Get all the given Company's COUPONS whose expiration date is less or equal to the given Maximum DATE, from the Database,
	 * @param company The given company whose list of coupons we want to get by Maximum DATE  
	 * @param maxDate The given Maximum DATE to get the list of coupons according to it
	 * @return A collection of all the given company's coupons whose expiration date less or equal to the given Maximum DATE from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByDate(Company company, String maxDate) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			Collection<Coupon> couponsByDate = new ArrayList<Coupon>();
			Coupon coupon = null;
			
			String query = "SELECT c.id, c.title, c.start_date, c.end_date, c.amount, c.type, c.message, c.price, c.image "
					+ "FROM company_coupon cc INNER JOIN coupon c ON (cc.coupon_id = c.id) WHERE cc.comp_id= " + company.getId() 
					+ " AND c.end_date <= '" + maxDate + "'";
			
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
				couponsByDate.add(coupon);
			}
			
			return couponsByDate;
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while trying to GET ALL COUPONS BY MAX PRICE of " + company.getCompName() + " from the DB table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// ADDING a new COUPON to the Company_Coupon JOIN table in DB
	/**
	 * Add a new COUPON to the Company_Coupon JOIN table in the Database
	 * @param company The given company that the coupon belongs to
	 * @param coupon The given coupon we want to add to the company_coupon join table in the Database
	 * @throws MyException SQL exception
	 */
	public void addCoupon(Company company, Coupon coupon) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			String query = "INSERT INTO company_coupon(`comp_id`,`coupon_id`) VALUES (" + company.getId() + ", " + coupon.getId() + ")";
			connection.createStatement().execute(query);
		}
		
		catch (SQLException e){
			
			throw new MyException
			("CompanyDBDAO: An Error occured while trying to ADD COUPON of " + company.getCompName() + " to the DB JOIN table: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool 
			pool.returnConnection(connection);
		}
	}
	
	// CHECKING if there is already a Company with the SAME NAME
	/**
	 * Check if the Company NAME is already exist in the Company table in the Database
	 * @param compName The given name of the new company 
	 * @return True if the given name exist in the company table in the Database otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean isNameExist(String compName) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			ResultSet results = connection.createStatement().executeQuery("SELECT id FROM company WHERE `comp_name` = '" + compName + "'");
			return (results.next());
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while a COMPANY is trying to LOGIN (checking the name): " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}
	
	// CHECKING if the Coupon BELONGS to the Company 
	/**
	 * Check if the given COUPON belongs to the given Company 
	 * @param compId The Id of the given company	
	 * @param couponId The Id of the given coupon
	 * @return True if the given coupon belongs to the given company otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean isCouponBelongToCompany(long compId, long couponId) throws MyException {
		// getting a connection from the Connection Pool
		Connection connection = pool.getConnection();
		
		try {
			
			ResultSet results = connection.createStatement().executeQuery
					("SELECT comp_id FROM company_coupon WHERE `comp_id` = " + compId + " && `coupon_id` = " + couponId);
			return (results.next());
		}
		
		catch (SQLException e){
			
			throw new MyException("CompanyDBDAO: An Error occured while a checking if COUPON is BELONG to a COMPANY: " + e.getMessage());
		}
		
		finally {
			// always returning the connection to the Connection Pool
			pool.returnConnection(connection);
		}
	}
}
