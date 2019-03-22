<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<style>
.container {
	max-width: 500px;
	margin: auto;
	background: white;
	padding: 10px;
	text-align: center;
	margin-top: 200px;
}

input {
	margin-top: 5px;
	margin-bottom: 5px;
	display: inline-block;
	*display: inline; /* for IE7*/
	zoom: 1; /* for IE7*/
	vertical-align: middle;
	margin-left: 20px
}

label {
	display: inline-block;
	*display: inline; /* for IE7*/
	zoom: 1; /* for IE7*/
	float: left;
	padding-top: 5px;
	text-align: right;
	width: 140px;
}

button {
	width: 80%;
	margin-top: 50px;
	background-color: #4CAF50;
	border: none;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
}

form {
	border: 1px solid black;
	padding: 20px 10px;
	border-radius: 10px;
}
</style>


</head>

<c:choose>
	<c:when test="${sessionScope.user != null}">
		<c:choose>

			<c:when test="${sessionScope.user.getRole() == 'admin'}">
				<%
					response.sendRedirect("AdminPage.jsp");
				%>
			</c:when>

			<c:otherwise>
				<%
					response.sendRedirect("StudentPage.jsp");
				%>
			</c:otherwise>

		</c:choose>
	</c:when>
</c:choose>

<body>
	<div class="container">
		<form action="Login" method="POST">

			<div>
				<label>User Name:</label> <input type="text" name="username" />
			</div>
			<div>
				<label>Password</label> <input type="password" name="password" />
			</div>

			<div>
				<button class="btn btn-success" type="submit"
					style="margin-bottom: 10px;">Sign In</button>
			</div>

		</form>

		<c:choose>
			<c:when test="${result != null}">
				<span style="color: red;">${result}</span>
			</c:when>
		</c:choose>
	</div>


	${result = null }

</body>
</html>