package imports.d20170427coupProjDafnaWeiss.service;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.CustomerFacade;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
import imports.d20170427coupProjDafnaWeiss.model.Customer;

@Path("/customer")
public class CustomerService {
	
	@Context
    private HttpServletRequest request;
	private HttpServletResponse response;
	
	/*
	-----------------------------------------------------------
	COUPON Methods in CUSTOMER FACADE of the Logged in Customer
	-----------------------------------------------------------
	*/
	// PURCHASE a Coupon by a Customer 
	/**
	 * Purchase a given Coupon by the logged in Customer, after checking if the coupon is OK:
	 *  The given customer didn't buy the same coupon before, the coupon isn't OUT OF STOCK, and the coupon isn't EXPIRED yet
	 * @param coupId The id of the coupon the logged in customer wants to purchase
	 * @return String message if it was success or a failure 
	 */
	@GET
	@Path("purchaseCoupon")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String purchaseCoupon(@QueryParam("coupId") long id){
		
		// getting the session and the logged in facade object and the logged in customer
		HttpSession session = request.getSession(false);									
		CustomerFacade customerFacade = (CustomerFacade)session.getAttribute("facade");
		Customer customer = customerFacade.getLoginCustomer();
		session.setAttribute("customer", customer);
		
		try	{
			
			Coupon coupon = customerFacade.getCoupon(id);
			
			if (coupon != null){
				
				if(customerFacade.purchaseCoupon(customer,coupon)){
					
					return "SUCCEED TO PURCHASE A COUPON: title = " + coupon.getTitle() + ", id = " + id
							+ " by CUSTOMER name: " + customer.getCustName();
				}
				
				else {
					return "FAILED TO PURCHASE A COUPON: you can't purchase this coupon! id = " + id + " - please try another coupon id";
				}
			}
			
			else {
				return "FAILED TO PURCHASE A COUPON: there is no such id! " + id + " - please try another coupon id";
			}
		}
		
		catch (MyException e){
			
			e.printStackTrace();
			return "CustomerService: FAILED PURCHASE A COUPON: " + e.getMessage();
		}
	}
	
	// SHOW All Purchased Coupons of the logged in Customer
	/**
	 * Get ALL the Purchased Coupons of the logged in customer from the Database, by calling the getAllPurchasedCoupons method from the CUSTOMER facade  
	 * @return A collection of all the Purchased coupons of the logged in customer from the Database
	 */
	@GET
	@Path("getCustCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCoupons(){
		
		// getting the session and the logged in facade object and the logged in customer
		HttpSession session = request.getSession(false);									
		CustomerFacade customerFacade = (CustomerFacade)session.getAttribute("facade");
		Customer customer = customerFacade.getLoginCustomer();
		session.setAttribute("customer", customer);
		
		// get the List of all the Coupons that belong to the logged in customer from the Table in the DataBase
		Collection<Coupon> coupons = null;
		
		try	{
			
			coupons = customerFacade.getAllPurchasedCoupons(customer);
			
			if (coupons == null){
				
				response.getWriter().print("FAILED GET ALL COUPONS: " + customer.getCustName() + " customer has no coupons!");
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("CustomerService: FAILED GET ALL COUPONS of " + customer.getCustName() + " CUSTOMER: " + e.getMessage());
			e.printStackTrace();
		}
		
		return coupons; 
	}
		
	// SHOW All Purchased coupons of the logged in customer BY TYPE
	/**
	 * Get all the logged in Company's Purchased Coupons of the given TYPE, from the Database, by calling the getAllPurchasedCouponsByType method from the CUSTOMER facade
	 * @param couponType The given coupon Type to get the list coupons according to it
	 * @return A collection of all the logged in customer's Purchased coupons of the given type from the Database
	 */
	@GET
	@Path("getCustCouponsByType")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Collection<Coupon> getAllPurchasedCouponsByType(@QueryParam("type") String couponType){
		// getting the session and the logged in facade object and the logged in customer
		HttpSession session = request.getSession(false);									
		CustomerFacade customerFacade = (CustomerFacade)session.getAttribute("facade");
		Customer customer = customerFacade.getLoginCustomer();
		session.setAttribute("customer", customer);
		
		// get the List of all the Purchased Coupons of to the logged in customer from the Table in the DataBase
		Collection<Coupon> coupons = null;
		
		try	{
			
			CouponType type = CouponType.valueOf(couponType);		// convert String to ENUM
			coupons = customerFacade.getAllPurchasedCouponsByType(customer,type);
			
			if (coupons == null){
				
				response.getWriter().print("FAILED GET COUPONS BY TYPE: " + customer.getCustName() + 
													" customer has no coupons of type: " + type + "!");
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("CustomerService: FAILED GET COUPONS BY TYPE of " + customer.getCustName() + " CUSTOMER: " + e.getMessage());
			e.printStackTrace();
		}
		
		return coupons; 
	}
	
	// SHOW All Purchased coupons of the logged in customer BY MAXIMUM PRICE
	/**
	 * Get all the logged in Customer's Purchased Coupons that cost less or equal to the given Maximum PRICE, from the Database, by calling the getAllPurchasedCouponsByPrice method from the CUSTOMER facade
	 * @param maxPrice The given Maximum PRICE to get the list of coupons according to it
	 * @return A collection of all the logged in customer's Purchased coupons that cost less or equal to the given Maximum PRICE
	 */
	@GET
	@Path("getCompCouponsByPrice")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Collection<Coupon> getAllPurchasedCouponsByPrice(@QueryParam("maxPrice") double maxPrice){
		// getting the session and the logged in facade object and the logged in customer
		HttpSession session = request.getSession(false);									
		CustomerFacade customerFacade = (CustomerFacade)session.getAttribute("facade");
		Customer customer = customerFacade.getLoginCustomer();
		session.setAttribute("customer", customer);
		
		// get the List of all the Coupons from the Table in the DataBase
		Collection<Coupon> coupons = null;
		
		try	{
			
			coupons = customerFacade.getAllPurchasedCouponsByPrice(customer,maxPrice);
			
			if (coupons == null){
				
				response.getWriter().print("FAILED GET COUPONS BY PRICE: " + customer.getCustName() + 
											" customer has no coupons that cost less or equal to: " + maxPrice +  " NIS!" );	
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("CompanyService: FAILED GET COUPONS BY PRICE of " + customer.getCustName() + " CUSTOMER: " + e.getMessage());
			e.printStackTrace();
		}
		
		return coupons; 
	}
	
	// GET Coupon BY ID
	/**
	 * Get a Coupon BY ID from the Database ,by calling the getCoupon method from the CUSTOMER facade  
	 * @param id The given coupon's ID that we want to get
	 * @return Coupon The coupon of the given ID
	 */
	@GET
	@Path("getCustCoupon")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Coupon getCoupon(@QueryParam("coupId") long id) {
		
		// getting the session and the logged in facade object and the logged in customer
		HttpSession session = request.getSession(false);									
		CustomerFacade customerFacade = (CustomerFacade)session.getAttribute("facade");
		Customer customer = customerFacade.getLoginCustomer();
		session.setAttribute("customer", customer);
		
		Coupon coupon = null;
		
		try	{
			
			coupon = customerFacade.getCoupon(id);
			
			if (coupon == null){
				
				response.getWriter().print("FAILED GET COUPON BY ID: there is no such coupon id " + id + 
						" that was purchased by " + customer.getCustName() + " CUSTOMER - please enter another coupon id");
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("CustomerService: FAILED GET COUPON BY ID: " + e.getMessage());
			e.printStackTrace();
		}
		
		return coupon;
	}
}
