package imports.d20170427coupProjDafnaWeiss.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionPool {
	
	// create a connection pool
	public static final ConnectionPool getInstance(){
		
		return null;
	}

	
	// create a connection to the SQL DataBase
	public synchronized static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dafna_schema","root", "root");
			return connection;
			
			// wait if the connections are full
			// wait();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// return connection
	public synchronized void returnConnection(Connection connection){
		// unlock the waiting list...
		// notify();		
	}
	
	// closing all connections
	public void closeAllConnections(){
		//connection.close();
	}
}
