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
	<jsp:include page="Header.jsp"/>
	
	<form action="StudentProfile" method="POST" class="option">
		<button class="btn" type="submit" value="student" name="operation">
			Profile</button>
		<button class="btn" type="submit" value="subject" name="operation">Subjects</button>
	</form>
	
</body>
</html>