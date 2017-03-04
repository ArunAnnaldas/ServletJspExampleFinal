package com.Query;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/ExportToExcel")
public class ExportToExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExportToExcel() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("employees", FetchData.employees);
		RequestDispatcher rd = request.getRequestDispatcher("excelreport.jsp");
		rd.forward(request, response);
		
	}

}
