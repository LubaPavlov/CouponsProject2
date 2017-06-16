package imports.d20170427coupProjDafnaWeiss.facade;

import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.dao.CompanyDBDAO;
import imports.d20170427coupProjDafnaWeiss.dao.CustomerDBDAO;
import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Company;
import imports.d20170427coupProjDafnaWeiss.model.Customer;
/**
 * Admin Facade: Manage the Company's and Customer's Methods by the ADMIN client
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class AdminFacade implements CouponClientFacade {
	
	// loading the DAO Objects
	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	
	/**
	 * An empty constructor
	 */
	public AdminFacade() {
	}

	@Override
	/**
	 * Login to the Coupon System with ADMIN Client Type
	 * @param name The Admin name ("admin") that logged in the system
	 * @param Password the Admin password ("1234") that logged in the system
	 * @return	True upon login success (if the name and the password are correct) otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean login(String name, String password){
		
		return (name.equals("admin") && password.equals("1234"));
	}
	
	/*	
	------------------
 	COMPANY's Methods
	------------------ 
	*/
	// CREATE a new Company - add a company to the Company Table in DB
	/**
	 * Create a new Company in the company table in the Database by calling the companyDBDAO createCompany method 
	 * @param company The given company to be created
	 * @return True upon creation success (if the company name isn't exist in the company table) otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean createCompany(Company company) throws MyException {
		// checking if the Company Name is already exist
		if (companyDBDAO.isNameExist(company.getCompName())){
			// there is a company with the same name - change the company name
			System.out.println("AdminFacade: There is already a COMPANY with the same NAME: " + company.getCompName() + " - please change the company name");
			return false;	
		}
		
		else {
			// the company name is OK - CREATE A NEW COMPANY
			companyDBDAO.createCompany(company);
			return true;
		}
	}
	
	// REMOVE a Company
	/**
	 * Remove a Company (including its coupons) from the Database by calling the companyDBDAO removeCompany method  
	 * @param company The given company to be removed
	 * @throws MyException SQL exception
	 */
	public void removeCompany (Company company) throws MyException {
		
		companyDBDAO.removeCompany(company);
	}
	
	// UPDATE a company
	/**
	 * Update a Company in the Database by calling the companyDBDAO updateCompany method  
	 * @param company The given company to be updated
	 * @throws MyException SQL exception
	 */
	public void updateCompany (Company company) throws MyException {
		companyDBDAO.updateCompany(company);
	}
	
	// SHOW All Companies
	/**
	 * Get ALL the Companies from the Company table in the Database, by calling the companyDBDAO getAllCompanies method   
	 * @return A collection of all the companies from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Company> getAllCompanies() throws MyException {
		
		Collection<Company> companies = companyDBDAO.getAllCompanies();
		
		for (Company company: companies){
			
			companyDBDAO.getCoupons(company);		// setting the Coupon Collection in each Company Object
		}
		
		return companies; 
	}
	
	// SHOW Company BY ID
	/**
	 * Get a Company BY ID from the Company table in the Database ,by calling the companyDBDAO getCompany method 
	 * @param id The given company's ID that we want to get
	 * @return Company The company of the given ID
	 * @throws MyException SQL exception 
	 */
	public Company getCompany(long id) throws MyException {
		
		Company company = companyDBDAO.getCompany(id);
		
		if (company != null){
			
			companyDBDAO.getCoupons(company);		// setting the Coupon Collection in the Company Object
		}
		
		return company;
	}
	
	/*	
	------------------
 	CUSTOMER's Methods
	------------------ 
	 */
	// CREATE a new Customer - add a customer to the Customer Table in DB
	/**
	 * Create a new Customer in the Customer table in the Database by calling the customerDBDAO createCustomer method 
	 * @param customer The given customer to be created
	 * @return True upon creation success (if the customer name isn't exist in the customer table) otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean createCustomer(Customer customer) throws MyException {
		// checking if the Customer Name is already exist
		if (customerDBDAO.isNameExist(customer.getCustName())){
			// there is a customer with the same name - change the customer name
			System.out.println("AdminFacade: There is already a customer with the same name: " + customer.getCustName() + " - please change the customer name");
			return false;
		}
		
		else {
			// the customer name is OK - CREATE A NEW CUSTOMER
			customerDBDAO.createCustomer(customer);
			return true;
		}
	}
	
	// REMOVE a Customer
	/**
	 * Remove a Customer from the Database by calling the customerDBDAO removeCustomer method  
	 * @param customer The given customer to be removed
	 * @throws MyException SQL exception
	 */
	public void removeCustomer (Customer customer) throws MyException {
		
		customerDBDAO.removeCustomer(customer);
	}
	
	// UPDATE a customer
	/**
	 * Update a Customer in the Database by calling the customerDBDAO updateCustomer method  
	 * @param customer The given customer to be updated
	 * @throws MyException SQL exception
	 */
	public void updateCustomer (Customer customer) throws MyException {
		
		customerDBDAO.updateCustomer(customer);
	}
	
	// SHOW All Customers
	/**
	 * Get ALL the Customers from the Customer table in the Database, by calling the customerDBDAO getAllCustomers method   
	 * @return A collection of all the customers from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Customer> getAllCustomers() throws MyException {
		
		Collection<Customer> customers = customerDBDAO.getAllCustomers();
		
		for (Customer customer: customers){
			
			customerDBDAO.getCoupons(customer);		// setting the Coupon Collection in each Customer Object
		}
		
		return customers; 
	}
	
	// SHOW Customer BY ID
	/**
	 * Get a customer BY ID from the customer table in the Database ,by calling the customerDBDAO getCustomer method 
	 * @param id The given customer's ID that we want to get
	 * @return Customer The customer of the given ID
	 * @throws MyException SQL exception 
	 */
	public Customer getCustomer(long id) throws MyException {
		
		Customer customer = customerDBDAO.getCustomer(id);
		
		if (customer != null){
			
			customerDBDAO.getCoupons(customer);		// setting the Coupon Collection in the Customer Object
		}
		return customer;
	}
}
