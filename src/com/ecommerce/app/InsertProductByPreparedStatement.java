package com.ecommerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.connection.DBConnection;

/**
 * Servlet implementation class InsertProductByPreparedStatement
 */
@WebServlet("/insert-product-by-preparedstatement")
public class InsertProductByPreparedStatement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertProductByPreparedStatement() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("inser-product-preparedstatement.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		Integer price = Integer.parseInt(request.getParameter("price"));
		
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
						String query = "insert into eproduct(name, price) values(?, ?)";
						
						// 3. Create a statement
						PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
						
						// 4. Set parameters
						pstmt.setString(1, name);
						pstmt.setInt(2, price);
						
						// 4. Execute query
						int noOfRowsAffected = pstmt.executeUpdate();
						out.print("<h3>No. of Product added "+noOfRowsAffected+"</h3>");
						pstmt.close();
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
