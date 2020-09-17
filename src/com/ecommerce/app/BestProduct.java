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
 * Servlet implementation class BestProduct
 */
@WebServlet("/best-product")
public class BestProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BestProduct() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("best-product.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		
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
						String query = "{call best_eproduct(?)}";
						
						// 3. Create a statement
						CallableStatement cstmt = conn.getConnection().prepareCall(query);
						
						// 4. Set input parameters
					    cstmt.setString(1, name);	
						
						// 5. register output parameter
					    cstmt.registerOutParameter(1, Types.VARCHAR);
					    
						// 6. Execute query
						cstmt.execute();
						
						String bestProduct = cstmt.getString(1);
						out.print(bestProduct);
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
