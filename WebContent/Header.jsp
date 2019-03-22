<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<c:choose>
	<c:when test="${sessionScope.user.getUserId() == null}">
		<%
			response.sendRedirect("Login.jsp");
		%>
	</c:when>
</c:choose>
<html>
<head>
<meta charset="ISO-8859-1">
<title>IBM</title>
<link rel="stylesheet" href=css/style.css>
</head>
<body>

	<ul>		
		<li style="float: left"><a href="Login.jsp">Hello, ${sessionScope.user.getName()}</a></li>
		<li style="float: right"><a href="Logout">Logout</a></li>
	</ul>
