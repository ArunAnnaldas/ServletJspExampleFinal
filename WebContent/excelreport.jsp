<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@ page import="java.util.List" %>
    <%@ page import="com.Query.FetchData" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  <h1>Excel Report</h1>
     <table cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
      <%
       List<String[]> employees  = (List<String[]>)request.getAttribute("employees");
             if (employees != null) {
                 response.setContentType("application/vnd.ms-excel");
                 response.setHeader("Content-Disposition", "inline; filename="+ "employeereport.xls");
             }
       for(String[] row: employees){
    	   for(String s: row){
    		   System.out.println(s);
    	   }
       }
             
       for(String[] row: employees){
       %>
       <tr>
       <%  for(String s: row){%>	   
       <td><%=s%></td>
      <% 
       }
      %>
      </tr>
      <%} %>
     </table>
</body>
</html>