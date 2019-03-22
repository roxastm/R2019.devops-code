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
	<table border="1">
		<tr>
			<th>Title</th>
			<th>Description</th>
			<th>Grade</th>

		</tr>
		<c:forEach items="${gradeList}" var="grade">
			<tr>
				<td>${grade.getTitle()}</td>
				<td>${grade.getDescription()}</td>
				<td>${grade.getGrade()}</td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td style="background: bisque;">Average</td>
			<td style="background: bisque;">${average}</td>
		</tr>
	</table>

</div>
</body>
</html>