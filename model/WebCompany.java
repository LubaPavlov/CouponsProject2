package imports.d20170427coupProjDafnaWeiss.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * A Company Model for the Web Client - without the Password and the e-mail 
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
@XmlRootElement
public class WebCompany {
	
	private long id;
	private String compName;
	private Collection<Coupon> coupons;
	
	/**
	 * An empty constructor
	 */
	public WebCompany() {
	}
	
	/**
	 * A constructor using all the fields
	 * @param compName The given company name
	 * @param id The given company id
	 */
	public WebCompany(long id,String compName){
		this.compName = compName;
		this.id = id;
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
	// set the Company COUPONS list
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	// toString
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", coupons=" + coupons + "]";
	}
}
