package imports.d20170427coupProjDafnaWeiss.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

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
import imports.d20170427coupProjDafnaWeiss.facade.CompanyFacade;
import imports.d20170427coupProjDafnaWeiss.model.Company;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
import imports.d20170427coupProjDafnaWeiss.util.StringToDate;

@Path("/company")
public class CompanyService {
	
	@Context
    private HttpServletRequest request;
	private HttpServletResponse response;

	/*
	---------------------------------------------------------
	COUPON Methods in COMPANY FACADE of the Logged in Company
	---------------------------------------------------------
	*/
	// ADD a new Coupon to the logged in Company
	/**
	 * Add a new Coupon to the logged in Company, by calling the createCoupon and addCoupon methods from the COMPANY facade 
	 * @param title The given coupon title
	 * @param startDate The given coupon start date
	 * @param endDate The given coupon end date
	 * @param amount The given coupon amount
	 * @param type The given coupon type
	 * @param message The given coupon message
	 * @param price The given coupon price
	 * @param image The given coupon image
	 * @return String message if it was success or a failure 
	 */
	@GET
	@Path("addCoupon")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addCoupon(@QueryParam("title") String title,
							@QueryParam("sdate") String sDate,
							@QueryParam("edate") String eDate,
							@QueryParam("amount") int amount,
							@QueryParam("type") String couponType,
							@QueryParam("message") String message,
							@QueryParam("price") double price,
				            @QueryParam("image") String image){
		// getting the session and the logged in facade object and the logged in company
		HttpSession session = request.getSession(false);									
		CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		Company company = companyFacade.getLoginCompany();
		session.setAttribute("company", company);
		
		CouponType type = CouponType.valueOf(couponType);		// convert String to ENUM
		Date startDate = StringToDate.dateFormat(sDate);		// convert String to Date
		Date endDate = StringToDate.dateFormat(eDate);			// convert String to Date
		
		// set the coupon fields in the constructor
		Coupon coupon = new Coupon(title,startDate,endDate,amount,type,message,price,image);
		
		try	{
			
			if(companyFacade.createCoupon(company,coupon)){
				
				return "SUCCEED TO ADD A NEW COUPON: title = " + title + ", id = " + coupon.getId() + 
														" to " + company.getCompName() + " COMPANY";
			}
			
			else {
				return "FAILED TO ADD A NEW COUPON: "
				+ "There is already a coupon with the same title: " + title + " - please change the coupon title";
			}
		}
		
		catch (MyException e){
			
			e.printStackTrace();
			return "CompanyService: FAILED ADD A NEW COUPON to " + company.getCompName() + " COMPANY: " + e.getMessage();
		}
	}
	
	// REMOVE a Coupon from the logged in Company
	/**
	 * Remove a Coupon from the Database by calling the removeCoupon method from the COMPANY facade 
	 * @param coupId The id of the coupon to be removed from the list of coupons of the logged in company
	 * @return String message if it was success or a failure  
	 */
	@GET
	@Path("removeCompany")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String removeCoupon(@QueryParam("coupId") long id){
		
		// getting the session and the logged in facade object and the logged in company
		HttpSession session = request.getSession(false);									
		CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		Company company = companyFacade.getLoginCompany();
		session.setAttribute("company", company);
		
		try	{
			
			Coupon coupon = companyFacade.getCoupon(id);
			
			if (company != null){
				
				if(companyFacade.removeCoupon(company,coupon)){
					
					return "SUCCEED TO REMOVE A COUPON: title = " + coupon.getTitle() + ", id = " + id
							+ " to COMPANY name: " + company.getCompName();
				}
				
				else {
					return "FAILED TO REMOVE A COUPON: you don't allowed to remove this coupon! id = " + id + " - please enter another coupon id";
				}
			}
			
			else {
				return "FAILED TO REMOVE A COUPON: there is no such id! " + id + " - please enter another coupon id";
			}
		}
		
		catch (MyException e){
			
			e.printStackTrace();
			return "CompanyService: FAILED REMOVE A COUPON: " + e.getMessage();
		}
	}
	
	// UPDATE a Coupon of the logged in Company
	/**
	 * Update a Coupon in the Database by calling the updateCoupon method from the COMPANY facade 
	 * @param coupId The id of the coupon to be updated in the list of coupons of the logged in company
	 * @param endDate The given coupon's new end date to be updated
	 * @param price The given coupon's new price to be updated
	 * @return String message if it was success or a failure  
	 */
	@GET
	@Path("updateCompany")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateCoupon(@QueryParam("coupId") long id,
						       @QueryParam("edate") String eDate,							
						       @QueryParam("price") double price){
		
		// getting the session and the logged in facade object and the logged in company
		HttpSession session = request.getSession(false);									
		CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		Company company = companyFacade.getLoginCompany();
		session.setAttribute("company", company);
		
		try	{
			
			Coupon coupon = companyFacade.getCoupon(id);
			
			if (coupon != null){
				//set the new end date and price of the coupon
				Date endDate = StringToDate.dateFormat(eDate);			// convert String to Date
				coupon.setEndDate(endDate);
				coupon.setPrice(price);
				
				//update the DataBase
				if(companyFacade.updateCoupon(company,coupon)){
					
					return "SUCCEED TO UPDATE A COUPON: title = " + coupon.getTitle() + ", id = " + id
							+ " to COMPANY name: " + company.getCompName();
				}
				
				else {
					return "FAILED TO UPDATE A COUPON: you don't allowed to update this coupon! id = " + id + " - please enter another coupon id";
				}
			}
			
			else {
				return "FAILED TO UPDATE A COUPON: there is no such id! " + id + " - please enter another coupon id";
			}
		}
		
		catch (MyException e){
			
			e.printStackTrace();
			return "CompanyService: FAILED UPDATE A COUPON: " + e.getMessage();
		}
	}
	
	// SHOW All Coupons of the logged in Company
	/**
	 * Get ALL the Coupons that belong to the logged in company from the Database, by calling the getAllCoupons method from the COMPANY facade  
	 * @return A collection of all the coupons of the logged in company from the Database
	 */
	@GET
	@Path("getCompCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupons(){
		
		// getting the session and the logged in facade object and the logged in company
		HttpSession session = request.getSession(false);									
		CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		Company company = companyFacade.getLoginCompany();
		session.setAttribute("company", company);
		
		// get the List of all the Companies from the Table in the DataBase
		Collection<Coupon> coupons = null;
		
		try	{
			
			coupons = companyFacade.getCoupons(company);
			
			if (coupons == null){
				
				response.getWriter().print("FAILED GET ALL COUPONS: " + company.getCompName() + " company has no coupons!");	
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("CompanyService: FAILED GET ALL COUPONS of " + company.getCompName() + " COMPANY: " + e.getMessage());
			e.printStackTrace();
		}
		
		return coupons; 
	}

	// SHOW All coupons that belong to the logged in company BY TYPE
	/**
	 * Get all the logged in Company's Coupons of the given TYPE, from the Database, by calling the getCouponsByType method from the COMPANY facade
	 * @param couponType The given coupon Type to get the list coupons according to it
	 * @return A collection of all the logged in company's coupons of the given type from the Database
	 */
	@GET
	@Path("getCompCouponsByType")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Collection<Coupon> getCouponsByType(@QueryParam("type") String couponType){
		// getting the session and the logged in facade object and the logged in company
		HttpSession session = request.getSession(false);									
		CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		Company company = companyFacade.getLoginCompany();
		session.setAttribute("company", company);
		
		// get the List of all the Coupons that belong to the logged in company from the Table in the DataBase
		Collection<Coupon> coupons = null;
		
		try	{
			
			CouponType type = CouponType.valueOf(couponType);		// convert String to ENUM
			coupons = companyFacade.getCouponsByType(company,type);
			
			if (coupons == null){
				
				response.getWriter().print("FAILED GET COUPONS BY TYPE: " + company.getCompName() + 
													" company has no coupons of type: " + type + "!");
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("CompanyService: FAILED GET COUPONS BY TYPE of " + company.getCompName() + " COMPANY: " + e.getMessage());
			e.printStackTrace();
		}
		
		return coupons; 
	}
	
	// SHOW All coupons that belong to the logged in company BY MAXIMUM PRICE
	/**
	 * Get all the logged in Company's Coupons that cost less or equal to the given Maximum PRICE, from the Database, by calling the getCouponsByPrice method from the COMPANY facade
	 * @param maxPrice The given Maximum PRICE to get the list of coupons according to it
	 * @return A collection of all the logged in company's coupons that cost less or equal to the given Maximum PRICE
	 */
	@GET
	@Path("getCompCouponsByPrice")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Collection<Coupon> getCouponsByPrice(@QueryParam("maxPrice") double maxPrice){
		// getting the session and the logged in facade object and the logged in company
		HttpSession session = request.getSession(false);									
		CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		Company company = companyFacade.getLoginCompany();
		session.setAttribute("company", company);
		
		// get the List of all the Coupons from the Table in the DataBase
		Collection<Coupon> coupons = null;
		
		try	{
			
			coupons = companyFacade.getCouponsByPrice(company,maxPrice);
			
			if (coupons == null){
				
				response.getWriter().print("FAILED GET COUPONS BY PRICE: " + company.getCompName() + 
											" company has no coupons that cost less or equal to: " + maxPrice +  " NIS!" );	
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("CompanyService: FAILED GET COUPONS BY PRICE of " + company.getCompName() + " COMPANY: " + e.getMessage());
			e.printStackTrace();
		}
		
		return coupons; 
	}
	
	// SHOW All coupons that belong to the logged in company that don't EXPIRED till the MAXIMUN DATE
	/**
	 * Get all the given Company's Coupons whose expiration date is less or equal to the given Maximum DATE, from the Database, by calling the getCouponsByPrice method from the COMPANY facade
	 * @param  maxDate The given Maximum DATE to get the list of coupons according to it
	 * @return A collection of all the given company's coupons whose expiration date less or equal to the given Maximum DATE 
	 */
	@GET
	@Path("getCompCouponsByDate")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Collection<Coupon> getCouponsByDate(@QueryParam("maxDate") String maxDate){
		// getting the session and the logged in facade object and the logged in company
		HttpSession session = request.getSession(false);									
		CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		Company company = companyFacade.getLoginCompany();
		session.setAttribute("company", company);
		
		// get the List of all the Coupons from the Table in the DataBase
		Collection<Coupon> coupons = null;
		
		try	{
			
			coupons = companyFacade.getCouponsByDate(company,maxDate);
			
			if (coupons == null){
				
				response.getWriter().print("FAILED GET COUPONS BY DATE: " + company.getCompName() +
											" company has no coupons that expire before : " + maxDate + "!" );	
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("CompanyService: FAILED GET COUPONS BY DATE of " + company.getCompName() + " COMPANY: " + e.getMessage());
			e.printStackTrace();
		}
		
		return coupons; 
	}
	// GET Coupon BY ID
	/**
	 * Get a Coupon BY ID from the Database ,by calling the getCoupon method from the COMPANY facade  
	 * @param id The given coupon's ID that we want to get
	 * @return Coupon The coupon of the given ID
	 */
	@GET
	@Path("getCompCoupon")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Coupon getCoupon(@QueryParam("coupId") long id) {
		
		// getting the session and the logged in facade object and the logged in company
		HttpSession session = request.getSession(false);									
		CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		Company company = companyFacade.getLoginCompany();
		session.setAttribute("company", company);
		
		Coupon coupon = null;
		
		try	{
			
			coupon = companyFacade.getCoupon(id);
			
			if (coupon == null){
				
				response.getWriter().print("FAILED GET COUPON BY ID: there is no such coupon id " + id + 
						" that belongs to " + company.getCompName() + " COMPANY - please enter another coupon id");
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("CompanyService: FAILED GET COUPON BY ID: " + e.getMessage());
			e.printStackTrace();
		}
		
		return coupon;
	}
}
