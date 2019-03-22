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

	<input class="input-style" id="student_name" type="text"
		placeholder="Search name">
	<button class="btn" id="search_student">Search</button>
	<button class="btn" id="view_all_student">View All</button>
	<a href="AddStudent.jsp" id="link_add" style="float: right;">Add
		new student</a>
	<form action="ManageStudent" method="POST">

		<table border="1">
			<tr>
				<th>Student Id</th>
				<th>Name</th>
				<th>Address</th>
				<th>Gender</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${studentList}" var="student">
				<tr>
					<td>${student.getUserId()}</td>
					<td>${student.getName()}</td>
					<td>${student.getAddress()}</td>
					<td>${student.getGender()}</td>
					<td><button class="btn btn-info" type="submit"
							value="${student.getUserId()}" name="edit">View</button></td>
				</tr>
			</c:forEach>
		</table>
		
		<p>${tableRowCount} of ${rowCount} rows</p>

		<div class="pagination">
			<a href="#">&laquo;</a>
			<a href="StudentSubjectList?operation=student&offset=0">1</a>
			<c:choose>
				<c:when test="${pageCount > 1 }">
					<c:forEach begin="1" end="${pageCount - 1}" varStatus="loop">
						<a href="StudentSubjectList?operation=student&offset=${loop.index * 5}">${loop.index + 1}</a>
					</c:forEach>
				</c:when>
			</c:choose>
			<a href="#">&raquo;</a>
		</div>
	</form>



	<button id="myBtn" style="display: none;">Open Modal</button>
	<div id="myModal" class="modal">

		<div class="modal-content">
			<span class="close">&times;</span>
			<form action="AddStudent" method="POST">
				<table>
					<tr>
						<td>Name:</td>
						<td><input required type="text" name="name" /></td>
					</tr>

					<tr>
						<td>Address:</td>
						<td><input required type="text" name="address" /></td>
					</tr>

					<tr>
						<td>Gender:</td>
						<td><select name="gender">
								<option value="male">Male</option>
								<option value="female">Female</option>
						</select></td>
					</tr>

					<tr>
						<td>User Name:</td>
						<td><input required type="text" name="username" /></td>
					</tr>

					<tr>
						<td>Password:</td>
						<td><input required type="password" name="password" /></td>
					</tr>


					<tr>
						<td></td>
						<td><button type="submit" value="add" name="add">Insert
								Student</button></td>
					</tr>
				</table>

			</form>
		</div>

	</div>
</div>

<jsp:include page="Footer.jsp" />