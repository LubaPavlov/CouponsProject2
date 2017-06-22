package imports.d20170427coupProjDafnaWeiss.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Company Model
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
@XmlRootElement
public class Company {
	
	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;
	
	/**
	 * An empty constructor
	 */
	public Company() {
	}
	
	/**
	 * A constructor using all the fields
	 * @param compName The given company name
	 * @param password The given company password
	 * @param email The given company e-mail
	 */
	public Company(String compName, String password, String email){
		this.compName = compName;
		this.password = password;
		this.email = email;
	}
	
	// getters
	//--------
	// get the Company ID
	public long getId() {
		return id;
	}
	// get the Company Name
	public String getCompName() {
		return compName;
	}
	// get the Company Password
	public String getPassword() {
		return password;
	}
	// get the Company E-MAIL
	public String getEmail() {
		return email;
	}
	// get the Company COUPONS list
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	// setters
	//--------
	// set the Company ID
	public void setId(long id) {
		this.id = id;
	}
	// set the Company Name
	public void setCompName(String compName) {
		this.compName = compName;
	}
	// set the Company Password
	public void setPassword(String password) {
		this.password = password;
	}
	// set the Company E-mail
	public void setEmail(String email) {
		this.email = email;
	}
	// set the Company COUPONS list
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	// toString
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons + "]";
	}
}
