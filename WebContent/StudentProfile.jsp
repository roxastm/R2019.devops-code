<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${sessionScope.user.getUserId() == null}">
		<%
			response.sendRedirect("Login.jsp");
		%>
	</c:when>
</c:choose>
<jsp:include page="Header.jsp" />
<div class="container">
	<table>
		<tr>
			<td>Student Id:</td>
			<td>${student.getUserId() }</td>
		</tr>
		<tr>
			<td>Name:</td>
			<td>${student.getName() }</td>
		</tr>
		<tr>
			<td>Address:</td>
			<td>${student.getAddress() }</td>
		</tr>
		<tr>
			<td>Gender:</td>
			<td>${student.getGender() }</td>
		</tr>

	</table>
</div>
</body>
</html>