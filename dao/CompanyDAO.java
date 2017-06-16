package imports.d20170427coupProjDafnaWeiss.dao;

import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Company;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
/**
 * Company DAO Interface: Company Methods using the DataBase
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public interface CompanyDAO {
	/**
	 * Create a new Company in the Company table in the Database
	 * @param company The given company to be created
	 * @throws MyException SQL exception
	 */
	public void createCompany(Company company)throws MyException;
	/**
	 * Remove a Company from Database, including all the company's coupons
	 * @param company The given company to be removed
	 * @throws MyException SQL exception
	 */
	public void removeCompany(Company company)throws MyException;
	/**
	 * Update a Company in the Company table in the Database
	 * @param company The given company to be updated
	 * @throws MyException SQL exception
	 */
	public void updateCompany(Company company)throws MyException;
	/**
	 * Get a Company from the Company table in the Database BY ID
	 * @param id The company's ID in the Database that we want to get
	 * @return Company the company of the given ID
	 * @throws MyException SQL exception 
	 */
	public Company getCompany(long id)throws MyException;
	/**
	 * Get all the Companies from the Company table in the Database 
	 * @return A collection of all the companies from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Company> getAllCompanies()throws MyException;
	/**
	 * Get ALL the given Company's COUPONS from the company_coupon join table in the Database
	 * @param company The given company whose list of coupons we want to get 
	 * @return A collection of all the given company's coupons from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCoupons(Company company)throws MyException;
	/**
	 * Login: check the name and the password of the company in conjunction with the company table in the Database 
	 * @param compName The login company name
	 * @param password The login company password
	 * @return Company The company that succeed to logged in the system or NULL if the login failed
	 * @throws MyException SQL exception
	 */
	public Company login(String compName, String password)throws MyException;
}
