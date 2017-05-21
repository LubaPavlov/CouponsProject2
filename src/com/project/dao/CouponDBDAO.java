package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.main.CouponSystem;

public class CouponDBDAO implements CouponDAO {

	private static final String TABLE_NAME = "coupon";
	private Connection con = null;

	@Override
	public void createCoupon(Coupon coupon) {
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				// 2. Create SQL insert
				PreparedStatement stat = con.prepareStatement("INSERT INTO " + TABLE_NAME
						+ "(title, startDate, endDate, amount, type, message, price, image) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

				stat.setString(1, coupon.getTitle());
				stat.setDate(2, coupon.getStartDate());
				stat.setDate(3, coupon.getEndDate());
				stat.setInt(4, coupon.getAmount());
				stat.setString(5, coupon.getType().toString());
				stat.setString(6, coupon.getMessage());
				stat.setDouble(7, coupon.getPrice());
				stat.setString(8, coupon.getImage());

				System.out.println("Executing: " + stat.toString());

				int rowsInserted = stat.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("A new coupon " + coupon.getTitle() + " has been created successfully");
				} else
					System.out.println("An Error Has Occurred. Check if entered data is correct.");
			}
		} catch (SQLException e) {

			System.out.println("Cannot create coupon : " + coupon.getTitle() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
	}

	@Override
	public void removeCoupon(Coupon coupon) {
		// 1. Get a connection (from pool)
				try {
					con = getConnection();
					if (con != null) {
						// 2. Create SQL UPDATE
						PreparedStatement stat = con.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE couponId=?; "
								+ "DELETE FROM company_coupon WHERE couponId=?; "
								+ "DELETE FROM customer_coupon WHERE couponId=?;");

						stat.setLong(1, coupon.getCouponId());
						stat.setLong(2, coupon.getCouponId());
						stat.setLong(3, coupon.getCouponId());
						
						System.out.println("Executing: " + stat.toString());

						int rowsDeleted = stat.executeUpdate();
						
						if (rowsDeleted > 0) {
							System.out.println(coupon + " has been deteled successfully");
						} else
							System.out.println("An Error Has Occurred.");
					}
				} catch (SQLException e) {

					System.out.println("Cannot delete coupon : " + coupon.getTitle() + ". " + e.getMessage());

				} finally {
					// 3. Release connection
					releaseConnection(con);
				}
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				// 2. Create SQL UPDATE
				PreparedStatement stat = con.prepareStatement("UPDATE " + TABLE_NAME + " SET"
						+ "(title, startDate, endDate, amount, type, message, price, image) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

				stat.setString(1, coupon.getTitle());
				stat.setDate(2, coupon.getStartDate());
				stat.setDate(3, coupon.getEndDate());
				stat.setInt(4, coupon.getAmount());
				stat.setString(5, coupon.getType().toString());
				stat.setString(6, coupon.getMessage());
				stat.setDouble(7, coupon.getPrice());
				stat.setString(8, coupon.getImage());

				System.out.println("Executing: " + stat.toString());

				int rowsInserted = stat.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println(coupon + " has been updated successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {

			System.out.println("Cannot update coupon : " + coupon.getTitle() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}

	}

	@Override
	public Coupon getCoupon(long couponId) {
		Coupon coupon = new Coupon();
		try {
			con = getConnection();
			if (con != null) {
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE couponId=? ");
				stat.setLong(1, couponId);
				System.out.println("Executing: " + stat.toString());
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					coupon.setCouponId(rows.getLong("couponId"));
					coupon.setTitle(rows.getString("title"));
					coupon.setStartDate(rows.getDate("startDate"));
					coupon.setEndDate(rows.getDate("endDate"));
					coupon.setAmount(rows.getInt("amount"));
                    coupon.setType(CouponType.valueOf(rows.getString("type")));
					coupon.setMessage(rows.getString("message"));
					coupon.setPrice(rows.getDouble("price"));
					coupon.setImage(rows.getString("image"));
				}
			}
		} catch (SQLException e) {

			System.out.println("Cannot found a coupon : " + couponId + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return coupon;
	}

	@Override
	public Collection<Coupon> getAllCoupons() {
		ArrayList<Coupon> coupons = new ArrayList <Coupon>();
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
				System.out.println("Executing: " + stat.toString());
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					Coupon coupon = new Coupon();
					coupon.setCouponId(rows.getLong("couponId"));
					coupon.setTitle(rows.getString("title"));
					coupon.setStartDate(rows.getDate("startDate"));
					coupon.setEndDate(rows.getDate("endDate"));
					coupon.setAmount(rows.getInt("amount"));
                    coupon.setType(CouponType.valueOf(rows.getString("type")));
					coupon.setMessage(rows.getString("message"));
					coupon.setPrice(rows.getDouble("price"));
					coupon.setImage(rows.getString("image"));
					coupons.add(coupon);
				}
			}
		} catch (SQLException e) {

			System.out.println("SQL Error" + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return coupons;

	}

	@Override
	public Coupon getCouponByType(CouponType couponType) {
		Coupon coupon = new Coupon();
		try {
			con = getConnection();
			if (con != null) {
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE type=? ");
				stat.setString(1, couponType.toString());
				System.out.println("Executing: " + stat.toString());
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					coupon.setCouponId(rows.getLong("couponId"));
					coupon.setTitle(rows.getString("title"));
					coupon.setStartDate(rows.getDate("startDate"));
					coupon.setEndDate(rows.getDate("endDate"));
					coupon.setAmount(rows.getInt("amount"));
                    coupon.setType(CouponType.valueOf(rows.getString("type")));
					coupon.setMessage(rows.getString("message"));
					coupon.setPrice(rows.getDouble("price"));
					coupon.setImage(rows.getString("image"));
				}
			}
		} catch (SQLException e) {

			System.out.println("Cannot found a coupon : " + couponType + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return coupon;
	}

	private Connection getConnection() throws SQLException {

		//return DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, "root", "123123");
		return CouponSystem.getConnectionPool().getConnection();
	}

	private void releaseConnection(Connection con) {

		/*try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		CouponSystem.getConnectionPool().free(con);
	}

}
