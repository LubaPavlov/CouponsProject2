package imports.d20170427coupProjDafnaWeiss.facade;

import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.dao.CompanyDBDAO;
import imports.d20170427coupProjDafnaWeiss.dao.CouponDBDAO;
import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Company;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
/**
 * Company Facade: Manage the Coupon's Methods by the COMPANY client
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class CompanyFacade implements CouponClientFacade {

	// loading the DAO Objects
	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private Company loginCompany;
	
	/**
	 * An empty constructor
	 */
	public CompanyFacade() {
	}
	
	@Override
	/**
	 * Login to the Coupon System with COMPANY Client Type and keep the Logged in Company Object
	 * @param name The Company name that logged in the system
	 * @param Password The Company password that logged in the system
	 * @return	True upon login success (if the name and the password are correct) otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean login(String name, String password) throws MyException {
		
		loginCompany = companyDBDAO.login(name, password);
		System.out.println("CompanyFacade: loginCompany = " + name);
		return (loginCompany != null);
	}
	
	// GET the Company that was Logged In to the Coupon system
	/**
	 * Get the Company Object that succeed to logged in to the Coupon System
	 * @return The Company Object that logged in the system
	 */
	public Company getLoginCompany() {
		
		return loginCompany;
	}
	
	/*
	---------------------------------------------------------
	COUPON Methods in COMPANY FACADE of the Logged in Company
	---------------------------------------------------------
	*/
	// ADD a new Coupon to a Company: return TRUE when succeed
	/**
	 * Create a new Coupon by the given Company, by calling the couponDBDAO createCoupon method (create the coupon) 
	 *  and the companyDBDAO addCoupon method (adding it to the company coupon collection)
	 * @param company The given company who wants to create a new coupon
	 * @param coupon The new coupon to be created
	 * @return True upon creation success (if the coupon title isn't exist in the coupon table) otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean createCoupon(Company company,Coupon coupon) throws MyException {
		// checking if the Coupon Title is already exist
		if (couponDBDAO.isTitleExist(coupon.getTitle())){
			// there is a coupon with the same title 
			System.out.println("CompanyFacade: There is already a coupon with the same title: " + coupon.getTitle() + " - please change the coupon title");
			return false;
		}
		
		else {
			// the coupon title is OK - CREATE A NEW COUPON
			couponDBDAO.createCoupon(coupon);
			System.out.println("CompanyFacade: SUCCEED TO ADD a NEW COUPON that belongs to COMPANY " + company.getCompName());
			
			// adding the coupon to the COMPANY-COUPON JOIN table in DB
			companyDBDAO.addCoupon(company,coupon);
			System.out.println("CompanyFacade: SUCCEED TO ADD a NEW COUPON to the COMPANY_COUPON JOIN table");
			
			return true;
		}
	}
	
	// REMOVE a Coupon from a Company
	/**
	 * Remove a Coupon from the Database by calling the couponDBDAO removeCoupon method  
	 * @param company The given company who wants to remove the given coupon
	 * @param coupon The coupon to be removed
	 * @return True upon removal success (if the given coupon belongs to the given company) otherwise False	  
	 * @throws MyException SQL exception
	 */
	public boolean removeCoupon (Company company,Coupon coupon) throws MyException {
		// checking if the given coupon belongs to the logged in Company
		if (companyDBDAO.isCouponBelongToCompany(company.getId(), coupon.getId())){
			// removing the coupon it belongs to the logged in Company
			couponDBDAO.removeCoupon(coupon);
			System.out.println("CompanyFacade: SUCCEED TO REMOVE a COUPON: id = " + coupon.getId());
			return true;
		}
		
		else {
			System.out.println("CompanyFacade: YOU DON'T ALLOWED TO REMOVE THIS COUPON, id = " + coupon.getId());
			return false;
		}
	}
	
	// UPDATE the END_DATE and the PRICE of a Company's Coupon
	/**
	 * Update a Coupon in the Database by calling the couponDBDAO updateCoupon method  
	 * @param company The given company who wants to update the given coupon
	 * @param coupon The coupon to be updated
	 * @return True upon update success (if the given coupon belongs to the given company) otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean updateCoupon (Company company,Coupon coupon) throws MyException {
		// checking if the given coupon belongs to the logged in Company
		if (companyDBDAO.isCouponBelongToCompany(company.getId(), coupon.getId())){
			// updating the coupon it belongs to the logged in Company
			couponDBDAO.updateCoupon(coupon);
			System.out.println("CompanyFacade: SUCCEED TO UPDATE a COUPON: id = " + coupon.getId());
			return true;
		}
		
		else {
			System.out.println("CompanyFacade: YOU DON'T ALLOWED TO UPDATE THIS COUPON, id = " + coupon.getId());
			return false;
		}
	}
	
	// SHOW all the Company's fields
	/**
	 * Get and Present all the given Company's fields
	 * @param company The given company to be presented
	 * @return A list of all the given company's fields (name and value)
	 */
	public String showCompanyFields(Company company) {
		
		return company.toString();
	}
	
	// SHOW All Coupons of a Company
	/**
	 * Get ALL the given Company's Coupons from the Database, by calling the companyDBDAO getCoupons method
	 * @param company The given company whose list of coupons we want to get 
	 * @return A collection of all the given company's coupons from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCoupons(Company company) throws MyException {
		
		return companyDBDAO.getCoupons(company);
	}
	
	// SHOW All coupons that belong to a company BY TYPE
	/**
	 * Get all the given Company's Coupons of the given TYPE, from the Database, by calling the companyDBDAO getCouponsByType method
	 * @param company The given company whose list of coupons we want to get by type  
	 * @param couponType The given coupon Type to get the list coupons according to it
	 * @return A collection of all the given company's coupons of the given type from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByType(Company company,CouponType couponType) throws MyException {
		
		return companyDBDAO.getCouponsByType(company, couponType);
	}
	
	// SHOW All coupons that belong to a company BY MAXIMUM PRICE
	/**
	 * Get all the given Company's Coupons that cost less or equal to the given Maximum PRICE, from the Database,
	 *  by calling the companyDBDAO getCouponsByPrice method
	 * @param company The given company whose list of coupons we want to get by Maximum PRICE  
	 * @param maxPrice The given Maximum PRICE to get the list of coupons according to it
	 * @return A collection of all the given company's coupons that cost less or equal to the given Maximum PRICE from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByPrice(Company company,double maxPrice) throws MyException {
		
		return companyDBDAO.getCouponsByPrice(company, maxPrice);
	}
	
	// SHOW All coupons that belong to a company that won't EXPIRED till the MAXIMUN DATE
	/**
	 * Get all the given Company's Coupons whose expiration date is less or equal to the given Maximum DATE, from the Database,
	 *  by calling the companyDBDAO getCouponsByDate method
	 * @param company The given company whose list of coupons we want to get by Maximum DATE  
	 * @param maxDate The given Maximum DATE to get the list of coupons according to it
	 * @return A collection of all the given company's coupons whose expiration date less or equal to the given Maximum DATE from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByDate(Company company,String maxDate) throws MyException {
		
		return companyDBDAO.getCouponsByDate(company, maxDate);
	}
	
	// GET Coupon BY ID
	/**
	 * Get a Coupon BY ID from the Coupon table in the Database ,by calling the couponDBDAO getCoupon method 
	 * @param id The given coupon's ID that we want to get
	 * @return Coupon The coupon of the given ID
	 * @throws MyException SQL exception 
	 */
	public Coupon getCoupon(long id) throws MyException {
		// checking if the given coupon belongs to the logged in Company
		Company company = getLoginCompany();
		Coupon coupon = null;
		
		if (companyDBDAO.isCouponBelongToCompany(company.getId(), id)){
			
			coupon = couponDBDAO.getCoupon(id);
		}
		
		else {
			System.out.println("CompanyFacade: YOU DON'T ALLOWED TO SEE THIS COUPON DETAILS, id = " + id);
		}
		
		return coupon;
	}
}
