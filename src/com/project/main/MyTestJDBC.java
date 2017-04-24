package com.project.main;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MyTestJDBC {

	public static void main(String[] args) {

		// 1. load the driver - note: the driver's jar must be included
		// in your classpath (project-properties, build path, add-external-jar)
		try {
			// Dynamically load a class to the jvm
			// this is the common way of loading jdbc
			// drivers - to allow maximum flexibility
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("------Driver loaded");

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/coupon", "root", "123123")) {
			// 2. create a connection
			System.out.println("--- connection established");
			printAnyTable(con, "coupon.company");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void printAnyTable(Connection con, String tableName) throws SQLException {

		Statement stat = con.createStatement();
		String sql = "SELECT * FROM " + tableName + " limit 10";
		ResultSet rs = stat.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();

		while (rs.next()) {
			for (int i = 1; i < rsmd.getColumnCount(); i++) {

				System.out.println(rsmd.getColumnName(i) + " " + rs.getString(i));

			}
			System.out.println("-----------------------------------------");
		}
	}
	
	
}
