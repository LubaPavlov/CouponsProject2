package imports.d20170427coupProjDafnaWeiss.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.AdminFacade;
import imports.d20170427coupProjDafnaWeiss.model.Company;
import imports.d20170427coupProjDafnaWeiss.model.Customer;
import imports.d20170427coupProjDafnaWeiss.model.WebCompany;
import imports.d20170427coupProjDafnaWeiss.model.WebCustomer;

@Path("/admin")
public class AdminService {
	
	@Context
    private HttpServletRequest request;
	private HttpServletResponse response;
		
	/*	
	------------------
 	COMPANY's Methods
	------------------ 
	*/
	//CREATE a new Company - add a company to the Company Table in DB
	@GET
	@Path("addCompany")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addCompany(@FormParam("name") String compName,
				             @FormParam("pass") String password,
				             @FormParam("email") String email) {
		
		// getting the session and the logged in facade object
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		// set the company fields in the constructor
		Company company = new Company(compName,password,email);
		
		try	{
			
			if(admin.createCompany(company)){
				
				return "SUCCEED TO ADD A NEW COMPANY: name = " + compName + ", id = " + company.getId();
			}
			
			else {
				return "FAILED TO ADD A NEW COMPANY: "
				+ "There is already a company with the same name: " + compName + " - please change the company name";
			}
		}
		
		catch (MyException e){
			
			e.printStackTrace();
			return "AdminService: FAILED ADD A NEW COMPANY: " + e.getMessage();
		}
	}
	
	// REMOVE a Company
	@GET
	@Path("removeCompany")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String removeCompany(@QueryParam("compId") long id){
		
		// getting the session and the logged in facade object
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		try	{
			
			Company company = admin.getCompany(id);
			
			if (company != null){
				
				admin.removeCompany(company);
				return "SUCCEED TO REMOVE A COMPANY: name = " + company.getCompName() + ", id = " + id;
			}
			
			else {
				return "FAILED TO REMOVE A COMPANY: there is no such id! " + id + " - please enter another company id";
			}
		}
		
		catch (MyException e){
			
			e.printStackTrace();
			return "AdminService: FAILED REMOVE A COMPANY: " + e.getMessage();
		}
	}
	
	// UPDATE a company
	@GET
	@Path("updateCompany")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateCompany(@QueryParam("compId") long id,
								@QueryParam("pass") String password,
								@QueryParam("email") String email) {
		
		// getting the session and the logged in facade object
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		
		try	{
			
			Company company = admin.getCompany(id);
			
			if (company != null){
				//set the new password and e-mail of the company
				company.setPassword(password);
				company.setEmail(email);
				//update the DataBase				
				admin.updateCompany(company);
				return "SUCCEED TO UPDATE A COMPANY: name = " + company.getCompName() + ", id = " + id;
			}
			
			else {
				return "FAILED TO UPDATE A COMPANY: there is no such id! " + id + " - please enter another company id";
			}
		}
		
		catch (MyException e){
						
			e.printStackTrace();
			return "AdminService: FAILED UPDATE A COMPANY: " + e.getMessage();
		}
	}
	
	// SHOW All Companies
	/**
	 * Get ALL the Companies from the Company table in the Database, by calling the getAllCompanies method from the ADMIN facade  
	 * @return A collection of all the companies from the Database (without the passwords and the e-mails)
	 */
	@GET
	@Path("getAllCompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCompany> getAllCompanies(){
		
		// getting the session and the logged in facade object
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		// get the List of all the Companies from the Table in the DataBase
		Collection<Company> companies = null;
		Collection<WebCompany> webCompanies = null;
		
		try	{
			
			companies = admin.getAllCompanies();
			// Get the List of all the Companies without the Password and the e-mail
			webCompanies = new ArrayList<>();	
			
			if (companies != null){
				
				for (Company company: companies){
					// setting each WebCompany's id + name in the constructor
					WebCompany webCompany = new WebCompany(company.getId(),company.getCompName());
					// setting the Coupon Collection in each WebCompany Object
					webCompany.setCoupons(company.getCoupons());
					// adding the WebCompany Object to the WebCompanies List
					webCompanies.add(webCompany);
				}
			}
			
			else {
				response.getWriter().print("AdminService: FAILED GET ALL COMPANIES: there are no companies in the DB table!");	
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("AdminService: FAILED GET ALL COMPANIES: " + e.getMessage());
			e.printStackTrace();
		}
		
		return webCompanies; 
	}
	
	// GET Company BY ID
	/**
	 * Get a Company BY ID (without the passwords and the e-mails) from the Company table in the Database ,by calling the getCompany method from the ADMIN facade  
	 * @param id The given company's ID that we want to get
	 * @return Company The company of the given ID
	 */
	@GET
	@Path("getCompany")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public WebCompany getCompany(@QueryParam("compId") long id) {
		
		// getting the session and the logged in facade object
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		Company company = null;
		WebCompany webCompany = null;
		
		try	{
			
			company = admin.getCompany(id);
			// Get the Company Object without the Password and the e-mail
			if (company != null){
				// setting the WebCompany's id + name in the constructor
				webCompany = new WebCompany(company.getId(),company.getCompName());
				// setting the Coupon Collection in the WebCompany Object
				webCompany.setCoupons(company.getCoupons());						
			}
			
			else {
				response.getWriter().print("FAILED GET COMPANY BY ID: there is no such id!"
												+ id + " - please enter another company id");
			}
		}
		
		catch (MyException | IOException e){
			System.err.println("AdminService: FAILED GET COMPANY BY ID: " + e.getMessage());
			e.printStackTrace();
		}
		
		return webCompany;
	}
	
	/*	
	------------------
 	CUSTOMER's Methods
	------------------ 
	 */
	//CREATE a new Customer - add a customer to the Customer Table in DB
		@GET
		@Path("addCustomer")
		@Produces(MediaType.TEXT_PLAIN)
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public String addCustomer(@QueryParam("name") String custName,
								  @QueryParam("pass") String password){
			
			// getting the session and the logged in facade object
			HttpSession session = request.getSession(false);									
			AdminFacade admin = (AdminFacade)session.getAttribute("facade");
			
			// set the customer fields in the constructor
			Customer customer = new Customer(custName,password);
			
			try	{
				
				if(admin.createCustomer(customer)){
					
					return "SUCCEED TO ADD A NEW CUSTOMER: name = " + custName + ", id = " + customer.getId();
				}
				
				else {
					return "FAILED TO ADD A NEW CUSTOMER: "
					+ "There is already a customer with the same name: " + custName + " - please change the customer name";
				}
			}
			
			catch (MyException e){
				
				e.printStackTrace();
				return "AdminService: FAILED ADD A NEW CUSTOMER: " + e.getMessage();
			}
		}
		
		// REMOVE a Customer
		@GET
		@Path("removeCustomer")
		@Produces(MediaType.TEXT_PLAIN)
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public String removeCustomer(@QueryParam("custId") long id){
			
			// getting the session and the logged in facade object
			HttpSession session = request.getSession(false);									
			AdminFacade admin = (AdminFacade)session.getAttribute("facade");
			
			try	{
				
				Customer customer = admin.getCustomer(id);
				
				if (customer != null){
					
					admin.removeCustomer(customer);
					return "SUCCEED TO REMOVE A CUSTOMER: name = " + customer.getCustName() + ", id = " + id;
				}
				
				else {
					return "FAILED TO REMOVE A CUSTOMER: there is no such id! " + id + " - please enter another customer id";
				}
			}
			
			catch (MyException e){
				
				e.printStackTrace();
				return "AdminService: FAILED REMOVE A CUSTOMER: " + e.getMessage();
			}
		}
		
		// UPDATE a customer
		@GET
		@Path("updateCustomer")
		@Produces(MediaType.TEXT_PLAIN)
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public String updateCustomer(@QueryParam("custId") long id,
									 @QueryParam("pass") String password){
			
			// getting the session and the logged in facade object
			HttpSession session = request.getSession(false);									
			AdminFacade admin = (AdminFacade)session.getAttribute("facade");
			
			try	{
				
				Customer customer = admin.getCustomer(id);
				
				if (customer != null){
					//set the new password of the customer
					customer.setPassword(password);
					//update the DataBase
					admin.updateCustomer(customer);
					return "SUCCEED TO UPDATE A CUSTOMER: name = " + customer.getCustName() + ", id = " + id;
				}
				
				else {
					return "FAILED TO UPDATE A CUSTOMER: there is no such id! " + id + " - please enter another customer id";
				}
			}
			
			catch (MyException e){
							
				e.printStackTrace();
				return "AdminService: FAILED UPDATE A CUSTOMER: " + e.getMessage();
			}
		}
		
		// SHOW All Customers
		/**
		 * Get ALL the Customers from the Customer table in the Database, by calling the getAllCustomers method from the ADMIN facade  
		 * @return A collection of all the customers from the Database (without the passwords)
		 */
		@GET
		@Path("getAllCustomers")
		@Produces(MediaType.APPLICATION_JSON)
		public Collection<WebCustomer> getAllCustomers(){
			
			// getting the session and the logged in facade object
			HttpSession session = request.getSession(false);									
			AdminFacade admin = (AdminFacade)session.getAttribute("facade");
			
			// get the List of all the Customers from the Table in the DataBase
			Collection<Customer> customers = null;
			Collection<WebCustomer> webCustomers = null;
			
			try	{
				
				customers = admin.getAllCustomers();
				// Get the List of all the Companies without the Password and the e-mail
				webCustomers = new ArrayList<>();	
				
				if (customers != null){
					
					for (Customer customer: customers){
						// setting each WebCustomer's id + name in the constructor
						WebCustomer webCustomer = new WebCustomer(customer.getId(),customer.getCustName());
						// setting the Coupon Collection in each WebCustomer Object
						webCustomer.setCoupons(customer.getCoupons());										
						// adding the WebCustomer Object to the WebCustomers List
						webCustomers.add(webCustomer);
					}
				}
				
				else {
					response.getWriter().print("AdminService: FAILED GET ALL CUSTOMERS: there are no customers in the DB table!");	
				}
			}
			
			catch (MyException | IOException e){
				System.err.println("AdminService: FAILED GET ALL CUSTOMERS: " + e.getMessage());
				e.printStackTrace();
			}
			
			return webCustomers; 
		}
		
		// GET Customer BY ID
		/**
		 * Get a Customer BY ID (without the passwords) from the Customer table in the Database ,by calling the getCustomer method from the ADMIN facade  
		 * @param id The given customer's ID that we want to get
		 * @return Customer The customer of the given ID
		 */
		@GET
		@Path("getCustomer")
		@Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public WebCustomer getCustomer(@QueryParam("custId") long id) {
			
			// getting the session and the logged in facade object
			HttpSession session = request.getSession(false);									
			AdminFacade admin = (AdminFacade)session.getAttribute("facade");
			
			Customer customer = null;
			WebCustomer webCustomer = null;
			
			try	{
				
				customer = admin.getCustomer(id);
				// Get the Customer Object without the Password and the e-mail
				if (customer != null){
					// setting the WebCustomer's id + name in the constructor
					webCustomer = new WebCustomer(customer.getId(),customer.getCustName());
					// setting the Coupon Collection in the WebCustomer Object
					webCustomer.setCoupons(customer.getCoupons());							
				}
				
				else {
					response.getWriter().print("FAILED GET CUSTOMER BY ID: there is no such id!"
													+ id + " - please enter another customer id");
				}
			}
			
			catch (MyException | IOException e){
				System.err.println("AdminService: FAILED GET CUSTOMER BY ID: " + e.getMessage());
				e.printStackTrace();
			}
			
			return webCustomer;
		}
}
