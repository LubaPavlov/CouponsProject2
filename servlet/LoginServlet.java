package imports.d20170427coupProjDafnaWeiss.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.ClientType;
import imports.d20170427coupProjDafnaWeiss.facade.CouponClientFacade;
import imports.d20170427coupProjDafnaWeiss.facade.CouponSystemSingleton;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private CouponSystemSingleton system;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }
    
    // init function - start the Coupon System
    @Override
	public void init() throws ServletException {
		system = CouponSystemSingleton.getInstance();
		System.out.println("Loaded...");
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// check whether there is a open session
		HttpSession session = request.getSession(false);
		
		if (session != null){
			
			session.invalidate();								// killing the session if exist  		
		}
		
		session = request.getSession(true);						// create a new session for a new client
		
		// getting the data from the Login HTML form
		String username = request.getParameter("name");
		String password = request.getParameter("pass");
		String clientType = request.getParameter("type");
		ClientType type = ClientType.valueOf(clientType);		// convert String to ENUM
		
		try	{
			
			CouponClientFacade facade = system.login(username, password, type);
			
			if (facade != null){
				// updating the session with the login facade
				session.setAttribute("facade", facade);			
				// dispatcher to the right Page according to the Client Type
				switch (type){
					case ADMIN:
						
						request.getRequestDispatcher("HTML/adminSPA.html").forward(request, response);		
						break;
						
					case COMPANY:
						// updating the session with the logged in company
						//Company company = ((CompanyFacade)facade).getLoginCompany();
						//session.setAttribute("company", company);
						request.getRequestDispatcher("HTML/companySPA.html").forward(request, response);	
						break;
						
					case CUSTOMER:
						// updating the session with the logged in customer
						//Customer customer = ((CustomerFacade)facade).getLoginCustomer();
						//session.setAttribute("customer", customer);
						request.getRequestDispatcher("HTML/customerSPA.html").forward(request, response);	
						break;
						
					default:
						break;
				}
			}
			
			else {
				// return to the Login HTML form if the user name or password are incorrect
				//response.getWriter().print("The UserName or the Password are incorrect! please try again");
				response.sendRedirect("HTML/login.html");
			}
			
		}
		
		catch (MyException e){
			e.printStackTrace();
		}
	}
}
