package imports.d20170427coupProjDafnaWeiss.facade;

import java.util.Collection;
import java.util.Date;

import imports.d20170427coupProjDafnaWeiss.dao.CouponDBDAO;
import imports.d20170427coupProjDafnaWeiss.dao.CustomerDBDAO;
import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
import imports.d20170427coupProjDafnaWeiss.model.Customer;
/**
 * Customer Facade: Manage the PURCHASE of Coupons by the CUSTOMER client
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class CustomerFacade implements CouponClientFacade {

	// loading the DAO Objects
	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private Customer loginCustomer;
	
	/**
	 * An empty constructor
	 */
	public CustomerFacade()	{
	}

	@Override
	/**
	 * Login to the Coupon System with CUSTOMER Client Type and keep the Logged in Customer Object
	 * @param name The Customer name that logged in the system
	 * @param Password The Customer password that logged in the system
	 * @return	true upon login success (if the name and the password are correct) otherwise false
	 * @throws MyException SQL exception
	 */
	public boolean login(String name, String password) throws MyException {
		
		loginCustomer = customerDBDAO.login(name, password);
		return (loginCustomer != null);
	}
	
	// GET the Customer that was Logged In to the Coupon system
	public Customer getLoginCustomer() {
		
		return loginCustomer;
	}
	
	/*
	----------------------------------
	 COUPON Methods in CUSTOMER FACADE
	----------------------------------
	*/
	// PURCHASE a Coupon by a Customer - return TRUE if succeed
	/**
	 * Purchase a given Coupon by a given Customer, after checking if the coupon is OK:
	 *  The given customer didn't buy the same coupon before, the coupon isn't OUT OF STOCK, and the coupon isn't EXPIRED yet
	 * @param customer The given customer who wants to purchase the given coupon
	 * @param coupon The given coupon that the given customer wants to purchase
	 * @return true upon purchase success (if the coupon is OK) otherwise false
	 * @throws MyException SQL exception
	 */
	public boolean purchaseCoupon(Customer customer, Coupon coupon) throws MyException{
		//checking if the customer had already purchased this coupon
		if (customerDBDAO.isCouponExist(customer,coupon)){
			System.out.println("CustomerFacade: You can't purchase the same Coupon twice! - ID = " + coupon.getId());
			return false;
		}
		else {
			// checking if there is any coupon left in this coupon stock
			if (coupon.getAmount() == 0){
				System.out.println("CustomerFacade: SORRY, This coupon (ID " + coupon.getId() + ") is OUT OF STOCK");
				return false;
			}
			else {
				// checking if this coupon isn't EXPIRED
				Date currentDate = new Date();
				if (coupon.getEndDate().compareTo(currentDate) <= 0){
					System.out.println("CustomerFacade: SORRY, This coupon is EXPIRED. ID = " + coupon.getId());
					return false;
				}
				else {
					// adding the coupon to the CUSTOMER-COUPON JOIN table in DB
					customerDBDAO.addCoupon(customer,coupon);
					System.out.println("CustomerFacade: ADD the purchased Coupon to the CUSTOMER_COUPON JOIN table");
					
					// updating the amount of the coupons left (less 1) in the Coupon table in DB
					System.out.println("CustomerFacade: The Coupon Amount before the purchase: " + coupon.getAmount());
					coupon.setAmount(coupon.getAmount() - 1);
					couponDBDAO.updateCoupon(coupon);
					System.out.println("CustomerFacade: UPDATING THE COUPON AMOUNT: " + coupon.getAmount());
					
					return true;
				}
			}
		}
	}
	
	// SHOW all the Coupons that this Customer has purchased
	/**
	 * Get ALL the given Customer's purchased Coupons from the Database, by calling the customerDBDAO getCoupons method
	 * @param customer The given customer whose list of purchased coupons we want to get  
	 * @return A collection of all the given customer's purchased coupons from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getAllPurchasedCoupons(Customer customer) throws MyException {
		
		return customerDBDAO.getCoupons(customer);
	}
	
	// SHOW all the Coupons that this Customer has purchased BY TYPE
	/**
	 * Get all the given Customer's purchased Coupons of the given TYPE, from the Coupon table in the Database, by calling the customerDBDAO getCouponsByType method
	 * @param customer The given customer whose list of purchased coupons we want to get by type  
	 * @param couponType The given coupon Type to get the list coupons according to it
	 * @return A collection of all the given customer's purchased coupons of the given type from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(Customer customer,CouponType couponType) throws MyException {
		
		return customerDBDAO.getCouponsByType(customer, couponType);
	}
	
	// SHOW all the Coupons that this Customer has purchased BY MAX PRICE
	/**
	 * Get all the given Customer's purchased Coupons that cost less or equal to the given Maximum PRICE, from the Coupon table in the Database,
	 *  by calling the customerDBDAO getCouponsByPrice method
	 * @param customer The given customer whose list of purchased coupons we want to get by Maximum PRICE  
	 * @param maxPrice The given Maximum PRICE to get the list of coupons according to it
	 * @return A collection of all the given customer's purchased coupons that cost less or equal to the given Maximum PRICE from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPrice(Customer customer,double maxPrice) throws MyException {
		
		return customerDBDAO.getCouponsByPrice(customer, maxPrice);
	}
	
	// GET Coupon BY ID
	/**
	 * Get a Coupon BY ID from the Coupon table in the Database ,by calling the couponDBDAO getCoupon method 
	 * @param id The given coupon's ID that we want to get
	 * @return Coupon The coupon of the given ID
	 * @throws MyException SQL exception 
	 */
	public Coupon getCoupon(long id) throws MyException {
	// checking if the given coupon was purchased by the logged in Customer
	Customer customer = getLoginCustomer();
	Coupon coupon = null;
	
	if (customerDBDAO.isCouponBelongToCustomer(customer.getId(), id)){
		
		coupon = couponDBDAO.getCoupon(id);
	}
	else {
		System.out.println("CompanyFacade: YOU DON'T ALLOWED TO SEE THIS COUPON DETAILS, id = " + id);
	}
	
	return coupon;
		//return couponDBDAO.getCoupon(id);
	}
}
