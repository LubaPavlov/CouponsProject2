package imports.d20170427coupProjDafnaWeiss.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * A Customer Model for the Web Client - without the Password 
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
@XmlRootElement
public class WebCustomer {
	
	private long id;
	private String custName;
	private Collection<Coupon> coupons;
	
	/**
	 * An empty constructor
	 */
	public WebCustomer() {
	}
	
	/**
	 * A constructor using all the fields
	 * @param custName The given customer name
	 * @param id The given customer id
	 */
	public WebCustomer(long id,String custName){
		this.custName = custName;
		this.id = id;
	}
	
	// getters
	//--------
	// get the Customer ID
	public long getId() {
		return id;
	}
	// get the Customer Name
	public String getCustName() {
		return custName;
	}
	// get the Customer COUPONS list
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	// setters
	//--------
	// set the Customer ID
	public void setId(long id) {
		this.id = id;
	}
	// set the Customer Name
	public void setCustName(String custName) {
		this.custName = custName;
	}
	// set the Customer COUPONS list
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	// toString
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", coupons=" + coupons + "]";
	}
}
