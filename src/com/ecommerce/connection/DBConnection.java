package com.ecommerce.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection connection;
	
	public DBConnection(String url, String user, String password) throws ClassNotFoundException, SQLException {
		
		// 1. Register driver
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    
	    // 2. Create Connection
	    this.connection = DriverManager.getConnection(url, user, password);
	   }
	
	//GET connection
	public Connection getConnection() {
		return connection;
	}
	
	//Close Connection
	public void closeConnection() throws SQLException {
		if(this.connection != null) {
			this.connection.close();
		}
	}
	
}
