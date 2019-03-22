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

		<tr>
			<td></td>
			<td><button type="button" class="btn-green" id="open_edit_modal"
					value="${student.getUserId()}">Edit</button>
				<button type="button" class="btn-red" id="open_delete_modal"
					value="${student.getUserId()}">Delete</button></td>
		</tr>
	</table>


	<h2>Subject List</h2>
	<form action="EditStudentSubject" method="POST">
		<table border="1">
			<tr>
				<th>Title</th>
				<th>Description</th>
				<th>Grade</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${gradeList}" var="grade">
				<tr>
					<td>${grade.getTitle()}</td>
					<td>${grade.getDescription()}</td>
					<td>${grade.getGrade()}</td>
					<td><button class="btn btn-info open_grade_modal" type="button"
							value="${grade.getGradeId()}" data-value="${grade.getGrade()}">View</button></td>
				</tr>
			</c:forEach>

		</table>

	</form>


	<form action="AddStudentSubject" method="POST" id="addForm">

		<div class="container border">
			<h3>Add Subject</h3>
			<table>
				<tr>
					<td>Subject</td>
					<td><select class="input-style" name="subject_id" id="subject_id">
							<c:forEach items="${subjectList}" var="subject">
								<option value="${subject.getSubjectId()}">${subject.getTitle()}</option>
							</c:forEach>

					</select></td>
				</tr>

				<tr>
					<td>Grade:</td>
					<td><input class="input-style" required max=100 min=60 type="number" name="grade"
						id="grade"></td>
				</tr>
			</table>


			<input type="hidden" value="${student.getUserId()}" name="student_id"
				id="student_id" />
			<button type="button" class="add_grade btn">Add Subject</button>
		</div>
	</form>
</div>

<!-- MODAL UPDATE -->

<div id="update_modal" class="modal">

	<div class="modal-content">
		<span class="close">&times;</span>
		<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" value="${student.getName() } " id="name" /></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input type="text" value="${student.getAddress() }"
					id="address" /></td>
			</tr>
			<tr>
				<td>Gender:</td>
				<td><input type="text" value="${student.getGender() }"
					id="gender" /></td>
			</tr>

			<tr>
				<td></td>
				<td><button type="button" id="update_student"
						value="${student.getUserId()}">Update</button></td>
			</tr>
		</table>

	</div>

</div>

<!-- MODAL DELETE -->

<div id="delete_modal" class="modal">

	<div class="modal-content-sm">
		<span class="close">&times;</span>

		<p>Are you sure you want to delete this record?</p>
		<input type="hidden" value="${student.getAccountId()}" id="account_id">
		<button type="button" id="delete_student"
			value="${student.getUserId()}">Delete</button>

	</div>

</div>

<!-- MODAL GRADE -->

<div id="grade_modal" class="modal">

	<div class="modal-content-sm">
		<span class="close">&times;</span>
		<input class="input-style" type="hidden" id="grade_id" />
		Grade: <input class="input-style" type="text" id="grade_value" />
		<button class="btn" type="button" id="update_grade"
			value="${student.getUserId()}">Update Grade</button>

	</div>

</div>


<jsp:include page="Footer.jsp" />