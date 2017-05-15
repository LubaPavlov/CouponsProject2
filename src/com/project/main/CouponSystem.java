package com.project.main;

import java.sql.SQLException;

import com.coupons.pool.ConnectionPool;

public class CouponSystem {
	private static ConnectionPool pool;
	
	static{
		String url = "jdbc:mysql://localhost:3306"; //+ dbName;
		
		String username = "root";
		String password = "123123";
		try {
			pool = new ConnectionPool(url, username, password);
		} catch (SQLException e) {
			System.out.println("Cannot create connection pool. "
					+ "reason: " + e.getMessage());
			System.exit(1);
		}
	}
	
	public static ConnectionPool getConnectionPool()
	{
		return pool;
	}
}
