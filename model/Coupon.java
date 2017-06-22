package imports.d20170427coupProjDafnaWeiss.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Coupon Model
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
@XmlRootElement
public class Coupon {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	/**
	 * An empty constructor
	 */
	public Coupon() {
	}

	/**
	 * A constructor using all the fields
	 * @param title The given coupon title
	 * @param startDate The given coupon start date
	 * @param endDate The given coupon end date
	 * @param amount The given coupon amount
	 * @param type The given coupon type
	 * @param message The given coupon message
	 * @param price The given coupon price
	 * @param image The given coupon image
	 */
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image)	{
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	// getters
	//--------
	// get the Coupon ID
	public long getId() {
		return id;
	}
	// get the Coupon Title
	public String getTitle() {
		return title;
	}
	// get the Coupon Start Date
	public Date getStartDate() {
		return startDate;
	}
	// get the Coupon End Date
	public Date getEndDate() {
		return endDate;
	}
	// get the Coupon Amount
	public int getAmount() {
		return amount;
	}
	// get the Coupon Type
	public CouponType getType() {
		return type;
	}
	// get the Coupon Message
	public String getMessage() {
		return message;
	}
	// get the Coupon Price
	public double getPrice() {
		return price;
	}
	// get the Coupon image
	public String getImage() {
		return image;
	}
	
	// setters
	//--------
	// set the Coupon ID
	public void setId(long id) {
		this.id = id;
	}
	// set the Coupon Title
	public void setTitle(String title) {
		this.title = title;
	}
	// set the Coupon Start Date
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	// set the Coupon End Date
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	// set the Coupon Amount
	public void setAmount(int amount) {
		this.amount = amount;
	}
	// set the Coupon Type
	public void setType(CouponType type) {
		this.type = type;
	}
	// set the Coupon Message
	public void setMessage(String message) {
		this.message = message;
	}
	// get the Coupon Price
	public void setPrice(double price) {
		this.price = price;
	}
	// set the Coupon image
	public void setImage(String image) {
		this.image = image;
	}

	// toSring
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + sdf.format(startDate) + ", endDate=" + sdf.format(endDate)
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}
}
