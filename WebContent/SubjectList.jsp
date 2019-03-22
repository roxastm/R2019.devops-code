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

	<input class="input-style" id="subject_name" type="text" placeholder="Search name">
	<button class="btn" id="search_subject">Search</button>
	<button class="btn" id="view_all_subject">View All</button>
	
	<form action="ManageSubject" method="POST">
		<table class="table table-striped">
			<tr>
				<th>Subject Id</th>
				<th>Title</th>
				<th>Description</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${subjectList}" var="subject">
				<tr>
					<td>${subject.getSubjectId()}</td>
					<td>${subject.getTitle()}</td>
					<td>${subject.getDescription()}</td>

					<td><button class="btn edit_subject_modal"
							type="button" value="${subject.getSubjectId()}" name="edit"
							data-title="${subject.getTitle()}"
							data-description="${subject.getDescription()}">View</button>
						<button class="btn-danger delete_subject_modal" type="button"
							value="${subject.getSubjectId()}" name="delete">Delete</button></td>
				</tr>
			</c:forEach>
		</table>
	</form>

	<a href="AddSubject.jsp" id="link_add">Add new subject</a>

</div>

<!-- MODAL UPDATE -->

<div id="update_subject_modal" class="modal">

	<div class="modal-content">
		<span class="close">&times;</span>
		<table>

			<tr>
				<td>Title:</td>
				<td><input type="text" id="title" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><input type="text" id="description" /></td>
			</tr>

			<tr>
				<td></td>
				<td><button class="update_subject" type="button" id="subject_id">Update</button></td>

			</tr>
		</table>

	</div>

</div>

<!-- MODAL DELETE -->

<div id="delete_modal_subject" class="modal">

	<div class="modal-content-sm">
		<span class="close">&times;</span>

		<p>Are you sure you want to delete this record?</p>
		<button type="button" id="delete_subject">Delete</button>

	</div>

</div>
<jsp:include page="Footer.jsp" />