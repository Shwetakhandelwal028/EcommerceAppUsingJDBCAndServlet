package com.ecommerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.connection.DBConnection;

/**
 * Servlet implementation class FindProduct
 */
@WebServlet("/find-product")
public class FindProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindProduct() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("find-product.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		
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
				String query = "call find_eproduct(?)";
				
				// 3. Create a statement
				CallableStatement cstmt = conn.getConnection().prepareCall(query);
				
				// 4. Set parameters
				
				cstmt.setInt(1, id);
				
				// 4. Execute query
				ResultSet rst = cstmt.executeQuery();
				while(rst.next()) {
					out.print("<h4>Product Details are:</h4>");
					out.print("<br>---------------------------------<br>");
					out.print(rst.getInt(1)+" | "+rst.getString(2)+" | "+rst.getDouble(3));
				}
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

}
