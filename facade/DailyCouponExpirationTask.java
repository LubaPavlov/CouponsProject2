package imports.d20170427coupProjDafnaWeiss.facade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import imports.d20170427coupProjDafnaWeiss.dao.ConnectionPoolSingleton;
import imports.d20170427coupProjDafnaWeiss.dao.CouponDBDAO;
import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
/**
 * The Daily Task: Check for expired coupons and delete them from the Database
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class DailyCouponExpirationTask implements Runnable {

	private static ConnectionPoolSingleton pool = ConnectionPoolSingleton.getInstance();
	private static boolean quit = false;
	private Connection connection;
	
	// loading the DAO Objects
	private CouponDBDAO couponDBDAO = new CouponDBDAO(); 
	
	/**
	 * An empty constructor
	 */
	public DailyCouponExpirationTask(){
	}
	
	@Override
	/**
	 * A Thread that check, once a day, for expired coupons and delete them from all the Database tables
	 */
	public void run(){
		
		long lastExecTime = 0;
		
		while (!quit){

			// waiting 5 seconds to check if the "quit" is true (if someone called the "Stop Daily Task" method)
			try	{
				
				Thread.sleep(5000);
			}
			
			catch (InterruptedException e) {
			}
			
			// if 24 hours have passed - do the Daily Task 
			long now = System.currentTimeMillis();
			
			if (now-lastExecTime >= 24*60*60*1000){
				
				try	{
					// getting a connection from the Connection Pool
					connection = pool.getConnection();
					
					//getting the list of all the expired coupons by ID
					String query = "SELECT id FROM coupon WHERE end_date < CURDATE()";
					ResultSet results = connection.createStatement().executeQuery(query);
					
					//getting the Coupon by ID and delete it from all the tables
					while(results.next()){
						
						long id = results.getLong("id");
						couponDBDAO.removeCoupon(couponDBDAO.getCoupon(id));
						System.out.println("DailyTask: REMOVE EXPIRED COUPON: id = " + id);
					}
					
					lastExecTime = now;
				}
				catch (MyException | SQLException e){
					
					System.err.println("DailyTask: An error occured trying to delete the expired coupons: " + e.getMessage());
				}
				
				finally {
					// always returning the connection to the Connection Pool 
					try {
						pool.returnConnection(connection);
					}
					
					catch (MyException e) {
						System.err.println("DailyTask: An error occured trying to return connection in the Dayily Task: " + e.getMessage());
					}
				}
			}
		}
	}
	
	// stopping the Daily Task
	/**
	 * A static method that stop the Daily Task
	 */
	public static void stopTask(){
		
		quit = true;
		System.out.println("DailyTask: STOPING THE DAILY TASK");
	}
}
