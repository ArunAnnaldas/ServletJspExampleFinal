package com.Query;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FetchData")
public class FetchData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static List<String[]> employees;
	
	public FetchData() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection con;
		Statement st;
		ResultSet r;
		PrintWriter out;
		ResultSetMetaData rsmd;
		int columnCounter;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\aannaldas\\Documents\\EmployeeData.accdb");
			st = con.createStatement();
			r = st.executeQuery("select * from EmployeeDetails");
			rsmd = r.getMetaData();
			columnCounter = rsmd.getColumnCount();
			out = response.getWriter();
			employees= new ArrayList<>();

			while (r.next()) {
				// **************section for the table header ******************
				String[] headerRow = new String[columnCounter];
				
				out.println("<form method=" + "\"post" + "\"action=" + "\"ExportToExcel\">");
				out.println("<table cellpadding=" + "\"1" + "\" cellspacing=" +"\"1" + "\"border=" + "\"1" + "\"bordercolor=" +"\"gray\">");
				out.println("<tr>");
				
				for(int i = 1; i <=columnCounter; i++)
				{
					out.println("<th>" + rsmd.getColumnName(i) + "</th>");
					Object obj = rsmd.getColumnName(i);
			        headerRow[i-1] = (obj == null) ?null:obj.toString();
				}
				employees.add(headerRow);
				out.println("</tr>");
				
				// ************** section for data rows ***********************

				do {
					
					String[] row = new String[columnCounter];
					out.println("<tr>");
					for(int j=1;j<=columnCounter;j++)
					{						
						out.println("<td>" + r.getString(j) + "</td>");
						Object obj = r.getObject(j);
				        row[j-1] = (obj == null) ?null:obj.toString();
					}
					employees.add(row);
					out.println("</tr>");
				}while (r.next());
				
				for( String[] row: employees ){
				    for( String s: row ){
				        System.out.print( " " + s );
				    }
				    System.out.println();
				}
				
				out.println("</table>");
				out.println("<br><br><input type=" + "\"submit" + "\" value=" + "\"Export To Excel\">");
				out.println("</form>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
