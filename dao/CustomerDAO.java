package imports.d20170427coupProjDafnaWeiss.dao;

import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.Customer;
/**
 * Customer DAO Interface: Customer Methods using the DataBase
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public interface CustomerDAO {
	/**
	 * Create a new Customer in the Customer table in the Database
	 * @param customer The given customer to be created
	 * @throws MyException SQL exception
	 */
	public void createCustomer(Customer customer)throws MyException;
	/**
	 * Remove a Customer from Database
	 * @param customer The given customer to be removed
	 * @throws MyException SQL exception
	 */
	public void removeCustomer(Customer customer)throws MyException;
	/**
	 * Update a Customer in the Customer table in the Database
	 * @param customer The given customer to be updated
	 * @throws MyException SQL exception
	 */
	public void updateCustomer(Customer customer)throws MyException;
	/**
	 * Get a Customer from the Customer table in the Database BY ID
	 * @param id The customer's ID in the Database that we want to get
	 * @return Customer the customer of the given ID
	 * @throws MyException SQL exception 
	 */
	public Customer getCustomer(long id)throws MyException;
	/**
	 * Get all the Customers from the Customer table in the Database 
	 * @return A collection of all the customers from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Customer> getAllCustomers()throws MyException;
	/**
	 * Get ALL the given Customer's purchased COUPONS from the customer_coupon join table in the Database
	 * @param customer The given customer whose list of purchased coupons we want to get 
	 * @return A collection of all the given customer's purchased coupons from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCoupons(Customer customer)throws MyException;
	/**
	 * Login: check the name and the password of the customer in conjunction with the customer table in the Database 
	 * @param custName The login customer name
	 * @param password The login customer password
	 * @return Customer The customer that succeed to logged in the system or NULL if the login failed
	 * @throws MyException SQL exception
	 */
	public Customer login(String custName, String password)throws MyException;
}

