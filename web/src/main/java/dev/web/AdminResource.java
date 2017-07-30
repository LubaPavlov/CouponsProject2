package dev.web;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.persistence.annotations.Index;

import com.project.beans.Company;
import com.project.beans.Customer;
import com.project.exceptions.DAOException;
import com.project.exceptions.FacadeException;
import com.project.facade.AdminFacade;
import com.project.main.ClientType;
import com.project.main.CouponSystem;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("admin")
public class AdminResource {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("createcompany")
	public String createCompany(@FormParam("name") String compName, @FormParam("pass") String password,
			@FormParam("email") String email) {

		CouponSystem newsys = CouponSystem.getInstance();
		AdminFacade facade = null;

		try {
			facade = (AdminFacade) newsys.login("admin", "1234", ClientType.ADMIN);
			Company company = new Company(compName, password, email);
			facade.createCompany(company);
		} catch (LoginException | FacadeException | DAOException e) {
			System.out.println(e.getLocalizedMessage());
		}

		return compName;

	}

	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("deletecompany")
	public String removeCompany(@FormParam("name")String compName) {

		CouponSystem newsys = CouponSystem.getInstance();
		AdminFacade facade = null;
		try {
			facade = (AdminFacade) newsys.login("admin", "1234", ClientType.ADMIN);
			long compId = facade.getCompanyIdByName(compName);
			facade.removeCompany(facade.getCompanyById(compId));
		} catch (LoginException | FacadeException | DAOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return compName;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getcustomers")
	public Collection<Customer> getAllCustomers() {

		CouponSystem newsys = CouponSystem.getInstance();
		AdminFacade facade = null;
		Collection<Customer> customers = null;
		try {

			facade = (AdminFacade) newsys.login("admin", "1234", ClientType.ADMIN);
			customers = facade.getAllCustomers();

		} catch (LoginException | FacadeException | DAOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return customers;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("companies")
	public Collection<Company> getAllCompanies() throws FacadeException, LoginException, DAOException {
		CouponSystem newsys = CouponSystem.getInstance();
		AdminFacade facade = (AdminFacade) newsys.login("admin", "1234", ClientType.ADMIN);
		return facade.getAllCompanies();
	}

}
