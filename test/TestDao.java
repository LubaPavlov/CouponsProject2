package imports.d20170427coupProjDafnaWeiss.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import imports.d20170427coupProjDafnaWeiss.dao.CompanyDBDAO;
import imports.d20170427coupProjDafnaWeiss.dao.CouponDBDAO;
import imports.d20170427coupProjDafnaWeiss.dao.CustomerDBDAO;
import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Company;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
import imports.d20170427coupProjDafnaWeiss.model.Customer;

public class TestDao extends Thread {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) {
		
		// fill the DataBase
		//fillDatabase()

		//------------------
		// COMPANY's Methods
		//------------------
		System.out.println("*************************");
		System.out.println("*     C O M P A N Y     *");
		System.out.println("*************************");
		
		Company company = new Company("Osem","1234","osem@gmail.com");
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		
		// creating a new company in the Company table in the DB
		try	{
			companyDBDAO.createCompany(company);
			System.out.println("adding a new Company: " + company);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// Updating a company in the Company table in the DB
		try	{
			company.setCompName("Strauss");
			company.setPassword("1234");
			company.setEmail("strauss@gmail.com");
			
			companyDBDAO.updateCompany(company);
			System.out.println("updating a Company: " + company);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// Deleting a company from the Company table in the DB
		try	{
			companyDBDAO.removeCompany(company);
			System.out.println("removing the Company: " + company);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// getting a company by ID from the Company table in the DB
		long compId = 2;
		try	{
			company = companyDBDAO.getCompany(compId);
			System.out.println("getting a Company: " + company + " compId= " + compId);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// getting a list of all the companies from the Company table in the DB
		try	{
			Collection<Company> allCompanies = companyDBDAO.getAllCompanies();
			
			for (Company comp : allCompanies) {
				System.out.println("Company: " + comp);
			}
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// getting the list of coupons that belong to a specific company from the Company_Coupon table in the DB
		try	{
			Collection<Coupon> coupons = companyDBDAO.getCoupons(company);
			
			for (Coupon coup : coupons) {
				System.out.println("Company: " + company.getCompName() + " bought a " + coup.getType().name() + " coupon: " + coup.getTitle());
			}
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		//-------------------
		// CUSTOMER's Methods
		//-------------------
		System.out.println("*************************");
		System.out.println("*     C U S T O M E R   *");
		System.out.println("*************************");
		
		Customer customer = new Customer("Sarit","abcd");
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		
		// creating a new customer in the Customer table in the DB
		try	{
			customerDBDAO.createCustomer(customer);
			System.out.println("adding a new Customer: " + customer);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// Updating a customer in the Customer table in the DB
		try	{
			customer.setCustName("Ami");
			customer.setPassword("efgh");
			
			customerDBDAO.updateCustomer(customer);
			System.out.println("updating a Customer: " + customer);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
				
		// Deleting a customer from the Customer table in the DB
		try	{
			customerDBDAO.removeCustomer(customer);
			System.out.println("removing the Customer: " + customer);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// getting a customer by ID from the Customer table in the DB
		long custId = 2;
		try	{
			customer = customerDBDAO.getCustomer(custId);
			System.out.println("getting a Customer: " + customer + " custId= " + custId);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// getting a list of all the customers from the Customer table in the DB
		try	{
			Collection<Customer> allCustomers = customerDBDAO.getAllCustomers();
			
			for (Customer cust : allCustomers) {
				System.out.println("Customer: " + cust);
			}
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// getting the list of coupons that belong to a specific customer from the Customer_Coupon table in the DB
		try	{
			Collection<Coupon> coupons = customerDBDAO.getCoupons(customer);
			
			for (Coupon coup : coupons) {
				System.out.println("Customer: " + customer.getCustName() + " bought a " + coup.getType().name() + " coupon: " + coup.getTitle());
			}
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		//-----------------
		// COUPON's Methods
		//-----------------
		System.out.println("*************************");
		System.out.println("*      C O U P O N      *");
		System.out.println("*************************");
		
		Coupon coupon = new Coupon
				("Free Meal",dateFormat("2017-01-01"),dateFormat("2017-06-01"),2000,CouponType.RESTURANTS,"Free business meal for 1 person",40,"");
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		
		// creating a new coupon in the Coupon table in the DB
		try	{
			couponDBDAO.createCoupon(coupon);
			System.out.println("adding a new Coupon: " + coupon);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// Updating a coupon in the Coupon table in the DB
		try	{
			coupon.setTitle("15% discount");
			coupon.setStartDate(dateFormat("2017-01-01"));
			coupon.setEndDate(dateFormat("2017-02-01"));
			coupon.setAmount(500);
			coupon.setType(CouponType.RESTURANTS);
			coupon.setMessage("15% discount for desert only on oredering one meal");
			coupon.setPrice(50);
			coupon.setImage("");
			
			couponDBDAO.updateCoupon(coupon);
			System.out.println("updating a Coupon: " + coupon);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// Deleting a coupon from the Coupon table in the DB
		try	{
			couponDBDAO.removeCoupon(coupon);
			System.out.println("removing the Coupon: " + coupon);
			
		} catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// getting a coupon by ID from the Coupon table in the DB
		long couponId = 2;
		try	{
			coupon = couponDBDAO.getCoupon(couponId);
			System.out.println("getting the Coupon: " + coupon + " couponId= " + couponId);
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// getting a list of all the coupons from the Coupon table in the DB
		try	{
			Collection<Coupon> allCoupons = couponDBDAO.getAllCoupons();
			
			for (Coupon coup : allCoupons) {
				System.out.println("Coupon: " + coup);
			}
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
		
		// getting all coupons of the same TYPE from the Coupon table in the DB
		CouponType couponType = CouponType.RESTURANTS;
		
		try	{
			Collection<Coupon> couponsByType = couponDBDAO.getCouponsByType(couponType);
			System.out.println("All the coupons of type " + couponType + " are: ");
			
			for (Coupon coup : couponsByType) {
				System.out.println(coup);
			}
			
		} 
		catch (MyException e) {
			System.err.println(e.getMessage());
		}
	}
	
	// getting a Date Object from a String field
	private static Date dateFormat(String dateFormat){
		
		Date date = null;
		
		try	{
			date = sdf.parse(dateFormat);
		}
		catch (ParseException e){
			e.printStackTrace();
		}
		return date;
	}

	// fill the DB tables
	private static void fillDatabase() {
		
		Company company = new Company();
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		
			// 1st company
			company.setCompName("Osem");
			company.setPassword("1234");
			company.setEmail("osem@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			// 2sc company
			company.setCompName("Strauss");
			company.setPassword("1234");
			company.setEmail("strauss@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			// 3rd company
			company.setCompName("Club In");
			company.setPassword("2345");
			company.setEmail("club_in@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			// 4th company
			company.setCompName("Hilton");
			company.setPassword("2345");
			company.setEmail("hilton@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			// 5th company
			company.setCompName("HandM");
			company.setPassword("6789");
			company.setEmail("hm@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			// 6th company
			company.setCompName("Castro");
			company.setPassword("6789");
			company.setEmail("castro@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			// 7th company
			company.setCompName("Colman");
			company.setPassword("6789");
			company.setEmail("colman@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			// 8th company
			company.setCompName("LaMetayel");
			company.setPassword("6789");
			company.setEmail("lametayel@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			// 9th company
			company.setCompName("Guliver");
			company.setPassword("0123");
			company.setEmail("guliver@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			// 9th company
			company.setCompName("Arkia");
			company.setPassword("0123");
			company.setEmail("arkia@gmail.com");
			
			try	{
				companyDBDAO.createCompany(company);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		
		//-----------------------------------
		// CUSTOMER - fill the DataBase table
		//-----------------------------------
			
		Customer customer = new Customer();
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
					
			// 1st customer
			customer.setCustName("Sarit");
			customer.setPassword("abcd");
			
			try	{
				customerDBDAO.createCustomer(customer);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
			
			// 2sc customer
			customer.setCustName("Ami");
			customer.setPassword("efgh");
			
			try	{
				customerDBDAO.createCustomer(customer);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
			
			// 3rd customer
			customer.setCustName("Iris");
			customer.setPassword("ijkl");
			
			try	{
				customerDBDAO.createCustomer(customer);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
			
			// 4th customer
			customer.setCustName("Ilan");
			customer.setPassword("mnop");
			
			try	{
				customerDBDAO.createCustomer(customer);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
			
			// 5th customer
			customer.setCustName("Ilana");
			customer.setPassword("qrst");
			
			try	{
				customerDBDAO.createCustomer(customer);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
			
			// 6th customer
			customer.setCustName("Aliza");
			customer.setPassword("uvwx");
			
			try	{
				customerDBDAO.createCustomer(customer);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
			
			// 7th customer
			customer.setCustName("David");
			customer.setPassword("z012");
			
			try	{
				customerDBDAO.createCustomer(customer);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
			
		//---------------------------------
		// COUPON - fill the DataBase table
		//---------------------------------
			
		Coupon coupon = new Coupon();
		CouponDBDAO couponDBDAO = new CouponDBDAO();
					
			// 1st coupon
			coupon.setTitle("Free Meal");
			coupon.setStartDate(dateFormat("2017-01-01"));
			coupon.setEndDate(dateFormat("2017-06-01"));
			coupon.setAmount(2000);
			coupon.setType(CouponType.RESTURANTS);
			coupon.setMessage("Free business meal for 1 person");
			coupon.setPrice(40);
			coupon.setImage("");
			
			try	{
				couponDBDAO.createCoupon(coupon);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}	
		
			// 2sc coupon
			coupon.setTitle("15% discount");
			coupon.setStartDate(dateFormat("2017-01-01"));
			coupon.setEndDate(dateFormat("2017-02-01"));
			coupon.setAmount(500);
			coupon.setType(CouponType.RESTURANTS);
			coupon.setMessage("15% discount for desert only on oredering one meal");
			coupon.setPrice(50);
			coupon.setImage("");
			
			try	{
				couponDBDAO.createCoupon(coupon);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}	
			
			// 3rd coupon
			coupon.setTitle("10% discount");
			coupon.setStartDate(dateFormat("2017-02-01"));
			coupon.setEndDate(dateFormat("2018-02-01"));
			coupon.setAmount(50000);
			coupon.setType(CouponType.ELECTRICITY);
			coupon.setMessage("10% discount for all the electrical products");
			coupon.setPrice(200);
			coupon.setImage("");
			
			try	{
				couponDBDAO.createCoupon(coupon);
			}
			catch (Exception e){
				e.printStackTrace();
			}	
			
			// 4th coupon
			coupon.setTitle("20% discount");
			coupon.setStartDate(dateFormat("2017-06-01"));
			coupon.setEndDate(dateFormat("2017-09-01"));
			coupon.setAmount(10000);
			coupon.setType(CouponType.CLOTHING);
			coupon.setMessage("SALE: 20% discount for the items of the old collection");
			coupon.setPrice(50);
			coupon.setImage("");
			
			try	{
				couponDBDAO.createCoupon(coupon);
			} catch (MyException e) {
				System.err.println(e.getMessage());
			}	
			
			// 5th coupon
			coupon.setTitle("1 plus 1");
			coupon.setStartDate(dateFormat("2017-06-01"));
			coupon.setEndDate(dateFormat("2017-09-01"));
			coupon.setAmount(10000);
			coupon.setType(CouponType.CLOTHING);
			coupon.setMessage("SALE: 1+1 for the items of the old collection (for the cheeper one)");
			coupon.setPrice(50);
			coupon.setImage("");
			
			try	{
				couponDBDAO.createCoupon(coupon);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
			
			// 6th coupon
			coupon.setTitle("Free Breakfest");
			coupon.setStartDate(dateFormat("2017-10-01"));
			coupon.setEndDate(dateFormat("2018-03-01"));
			coupon.setAmount(5000);
			coupon.setType(CouponType.HOTELS);
			coupon.setMessage("Get one Breakfest free for ordering 3 nights (per person)");
			coupon.setPrice(80);
			coupon.setImage("");
			
			try	{
				couponDBDAO.createCoupon(coupon);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
			
			// 7th coupon
			coupon.setTitle("Free Massage");
			coupon.setStartDate(dateFormat("2017-12-01"));
			coupon.setEndDate(dateFormat("2018-03-01"));
			coupon.setAmount(2500);
			coupon.setType(CouponType.HOTELS);
			coupon.setMessage("Gift Card: Get a free Massage");
			coupon.setPrice(250);
			coupon.setImage("");
			
			try	{
				couponDBDAO.createCoupon(coupon);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}	
			
			// 8th coupon
			coupon.setTitle("20% discount");
			coupon.setStartDate(dateFormat("2017-04-01"));
			coupon.setEndDate(dateFormat("2018-09-01"));
			coupon.setAmount(10000);
			coupon.setType(CouponType.SPORTS);
			coupon.setMessage("20% discount for all the sports shoes");
			coupon.setPrice(50);
			coupon.setImage("");
			
			try {
				couponDBDAO.createCoupon(coupon);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}	
			
			// 9th coupon
			coupon.setTitle("1+1");
			coupon.setStartDate(dateFormat("2017-10-01"));
			coupon.setEndDate(dateFormat("2017-12-01"));
			coupon.setAmount(10000);
			coupon.setType(CouponType.SPORTS);
			coupon.setMessage("1+1 for all the swimming aquipments (for the cheeper one)");
			coupon.setPrice(50);
			coupon.setImage("");
			
			try	{
				couponDBDAO.createCoupon(coupon);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}	
			
			// 10th coupon
			coupon.setTitle("Free fleece gloves");
			coupon.setStartDate(dateFormat("2017-10-01"));
			coupon.setEndDate(dateFormat("2017-12-01"));
			coupon.setAmount(2000);
			coupon.setType(CouponType.CAMPING);
			coupon.setMessage("Get free fleece gloves for buying a fleece coat");
			coupon.setPrice(100);
			coupon.setImage("");
			
			try	{
				couponDBDAO.createCoupon(coupon);
			} 
			catch (MyException e) {
				System.err.println(e.getMessage());
			}
	}
}
