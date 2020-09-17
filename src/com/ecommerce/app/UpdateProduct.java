package com.ecommerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
 * Servlet implementation class UpdateProduct
 */
@WebServlet("/update-product")
public class UpdateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProduct() {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		
		out.print("<h1> Update Product</h1>");
		
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
						String query = "Select * from eproduct";
						
						// 3. Create a statement
						Statement stmt = conn.getConnection().createStatement();
						
						// 4. Execute query
						ResultSet rst = stmt.executeQuery(query);
						
						while(rst.next()) {
							out.print("<br>---------------------------------<br>");
							out.print(rst.getInt(1)+" | "+rst.getString(2)+" | "+rst.getDouble(3));
						}
						rst.close();
						stmt.close();
						
					}
					
					conn.closeConnection();
	
				}catch(ClassNotFoundException  e) {
					e.printStackTrace();
				}catch(SQLException e) {
					e.printStackTrace();
				}			
		out.print("<form action='update-product' method='POST'>");
		out.print("<br/>Product Id : <input type='text' name='id'> <br/>");
		out.print("Product Name : <input type='text' name='name'> <br/>");
		out.print("Product Price : <input type='text' name='price'> <br/>");
		out.print("<input type='submit' value='Update Product'>");
		out.print("</form>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
        Integer id = Integer.parseInt(request.getParameter("id"));
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
				
				String query = "update eproduct set name ='"+name+"', price ='"+price+"' where p_id ='"+id+"'";
				
				// 3. Create a statement
				Statement stmt = conn.getConnection().createStatement();
				
				// 4. Execute query
				int noOfRowsAffected = stmt.executeUpdate(query);
				out.print("<h3>No. of Product updated "+noOfRowsAffected+"</h3>");
				stmt.close();
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


