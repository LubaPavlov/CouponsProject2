package imports.d20170427coupProjDafnaWeiss.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Customer Model
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
@XmlRootElement
public class Customer {
	
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;
	
	/**
	 * An empty constructor
	 */
	public Customer() {
	}

	/**
	 * A constructor using all the fields
	 * @param custName The given customer name
	 * @param password The given customer password
	 */
	public Customer(String custName, String password){
		this.custName = custName;
		this.password = password;
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
	// get the Customer Password
	public String getPassword() {
		return password;
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
	// set the Customer Password
	public void setPassword(String password) {
		this.password = password;
	}
	// set the Customer COUPONS list
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	// toString
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons + "]";
	}
}
