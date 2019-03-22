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
	<h3>Add Subject</h3>
	<form action="AddSubject" method="POST">
		<table>
			<tr>
				<td>Title:</td>
				<td><input required type="text" name="title" id="title" /></td>
			</tr>

			<tr>
				<td>Description:</td>
				<td><input required type="text" name="description"
					id="description" /></td>
			</tr>


			<tr>
				<td></td>
				<td><button type="button" value="add" name="add"
						id="add_subject">Insert Subject</button></td>
			</tr>
		</table>
	</form>
</div>
<jsp:include page="Footer.jsp" />