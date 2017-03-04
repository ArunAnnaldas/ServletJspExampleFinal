<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form method="post" action="FetchData">
		<table>
			<tr>
				<td>FirstName :</td>
				<td><input type="text" name="fname"></td>
			</tr>
			<tr>
				<td>LastName :</td>
				<td><input type="text" name="lname"></td>
			</tr>
			<tr>
				<td>EmployeeID :</td>
				<td><input type="text" name="employeeID"></td>
		</table>
		<input type="submit" value="Submit">
	</form>
</body>
</html>