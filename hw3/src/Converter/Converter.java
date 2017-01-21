package Converter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Converter
    extends HttpServlet
{
    public void doGet (HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
    {
	Connection conn = null;
	Statement state = null;
	try{
		String currencyStr = request.getParameter( "input currency" );
	        String currencyFrom = request.getParameter( "currency" );
	        String currencyTo = request.getParameter( "result" );
	        float dollarParse = Float.parseFloat( currencyStr );
	        float currencyResult = 0;
	        float from = 0;
		float to = 0;

	        PrintWriter out;
	        String title = "Homework 3 - Currency Converter - Jerry Springfield Jr.";

	        response.setContentType("text/html");
		//Create the driver for MySQL
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		//Setup the database URL
		String url = "jdbc:mysql://uml.cs.uga.edu:3306/currency";

		//Establish the database connection
		conn = DriverManager.getConnection(url, "demo", "demo");

		//Create a statement
		state = conn.createStatement();

		//Set the SQL SELECT query to list all currencies
		String sql = "select currsymbol, dollarvalue from currency";

		//Run the query
		if( state.execute(sql) ){// statement returned a result
			//Retrieve the result
			ResultSet r = state.getResultSet();

			//Iterates through the rows of the table.
			while( r.next() ){
				if(r.getString("currsymbol") == currencyFrom){
					from = r.getFloat("dollarvalue");
				}
				if(r.getString("currsymbol") == currencyTo){
					to = r.getFloat("dollarvalue");
				}
			}
			if(currencyFrom == "USD"){
				currencyResult = dollarParse * from * to;
			}
			else if(currencyFrom != "USD" && currencyTo == "USD"){
				currencyResult = dollarParse / from;
			}
			else{
				currencyResult = (dollarParse / from) * to;
			}
		}
		else {
			System.out.println(state.getUpdateCount() + " rows affected.");
		}
		out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println(title);
		out.println("</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"#FFFFFF\">");
		out.println("<h3>" + title + "</h3>" );
		out.println("The resulting amount is " + currencyResult + ".");
		out.println("</body");
		out.println("</html>");
		out.close();
	}
	catch( Exception e ){
		e.printStackTrace();
	}
	finally {
		try{
			if( state != null ) state.close();
			if( conn != null ) conn.close();
		}
		catch( SQLException sqe ){
			sqe.printStackTrace();
		}
	}
    }

}
