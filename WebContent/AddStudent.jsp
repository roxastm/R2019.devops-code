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

<h3>Add Student</h3>
<form action="AddStudent" method="POST">
	<table>
		<tr>
			<td>Name:</td>
			<td><input required type="text" name="name" id="name"/></td>
		</tr>

		<tr>
			<td>Address:</td>
			<td><input required type="text" name="address" id="address"/></td>
		</tr>

		<tr>
			<td>Gender:</td>
			<td><select name="gender" id="gender">
					<option value="male">Male</option>
					<option value="female">Female</option>
			</select></td>
		</tr>

		<tr>
			<td>User Name:</td>
			<td><input required type="text" name="username" autocomplete="off" id="username"/></td>
		</tr>

		<tr>
			<td>Password:</td>
			<td><input required type="password" name="password" id="password"/></td>
		</tr>


		<tr>
			<td></td>
			<td><button type="button" value="add" name="add" id="add_student">Insert
					Student</button></td>
		</tr>
	</table>



</form>

</div>
<jsp:include page="Footer.jsp" />