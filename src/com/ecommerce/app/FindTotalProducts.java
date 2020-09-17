package com.ecommerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.connection.DBConnection;

/**
 * Servlet implementation class FindTotalProducts
 */
@WebServlet("/find-total-product")
public class FindTotalProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindTotalProducts() { }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//Get Config
				InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
				Properties props= new Properties();
				props.load(ins);
				
				// 1. Create aconnection		
				try {
					DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("user"), 
							props.getProperty("password"));
					if(conn != null) {
						// 2. Create a query
						String query = "{call find_total_eproducts(?)}";
						
						// 3. Create a statement
						CallableStatement cstmt = conn.getConnection().prepareCall(query);
						
						// 4. registering out parameter
						cstmt.registerOutParameter(1, Types.INTEGER);
						
				        // 5. Execute query
					     cstmt.execute();
					 
					     int count = cstmt.getInt(1);
					  
						
						
						out.print("<h4>Total Records are </h4>");
						out.print(count);
						
						// 6. Close connection
						cstmt.close();
					}
					
					conn.closeConnection();
					out.print("<h3>Connection is closed!</h3>");
				}catch(ClassNotFoundException  e) {
					e.printStackTrace();
				}catch(SQLException e) {
					e.printStackTrace();
				}
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}	

}
