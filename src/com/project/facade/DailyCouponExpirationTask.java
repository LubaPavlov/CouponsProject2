/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.facade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.project.dao.CouponDAO;
import com.project.exceptions.DAOException;
import com.project.main.CouponSystem;

// TODO: Auto-generated Javadoc
/**
 * The Class DailyCouponExpirationTask.
 */
public class DailyCouponExpirationTask implements Runnable {
	
	private Connection con = null;
	private static boolean quit = false;
	private CouponDAO couponDAO;
	
	/**
	 * An empty constructor.
	 */
	public DailyCouponExpirationTask(){
	}
	
	@Override
	/**
	 * A Thread that check, once a day, for expired coupons and delete them from all the tables
	 */
	public void run(){
		
		long lastExecTime = 0;
		
		while (!quit){

			// waiting 5 seconds to check if the "quit" is true,  called the "Stop Daily Task" method
			try	{
				
				Thread.sleep(5000);
			}
			
			catch (InterruptedException e) {
			}
			
			// Do the Daily Task after 24 hours
			long now = System.currentTimeMillis();
			
			if (now-lastExecTime >= 24*60*60*1000){
				
				try	{
					//Get a connection from the Connection Pool
					con = getConnection();				
					//Get the list of all the expired coupons by ID
					String query = "SELECT couponId FROM coupon WHERE endDate < CURDATE()";
					ResultSet results = con.createStatement().executeQuery(query);					
					//Get Coupon by ID and delete from all the tables
					while(results.next()){						
						long couponId= results.getLong("couponId");
						couponDAO.removeCoupon(couponDAO.getCoupon(couponId));
						System.out.println("REMOVE EXPIRED COUPON: ID = " + couponId);
					}
					
					lastExecTime = now;
				}
				catch (SQLException e){
					
					System.err.println("An error occured: " + e.getMessage());
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finally {
					//Release connection
					releaseConnection(con);
				}
			}
		}
	}
	
	/**
	 * A static method to stop Daily Task.
	 */
	public static void stopTask(){
		
		quit = true;
		System.out.println("STOP DAILY TASK");
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		
		return CouponSystem.getConnectionPool().getConnection();
	}

	/**
	 * Release connection.
	 *
	 * @param the connection
	 */
	private void releaseConnection(Connection con) {

		CouponSystem.getConnectionPool().free(con);
	}
}
