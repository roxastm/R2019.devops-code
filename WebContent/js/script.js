$(".add_grade").click(function() {
	var student_id = $('#student_id').val();
	var grade = $('#grade').val();
	var subject_id = $('#subject_id').val();

	if (grade > 100 || grade < 60) {
		alert("Invalid Grade");
	} else if (isNumber(grade)) {
		alert("Invalid Grade");
	} else {
		$.ajax({
			url : 'AddStudentSubject',
			data : {
				student_id : student_id,
				grade : grade,
				subject_id : subject_id
			},
			method : 'POST',
			success : function(data) {
				if (data == 0) {
					alert("Subject already exist.");
				} else {
					location.reload();
				}
			}

		})
	}

})

$('#add_student')
		.click(
				function() {

					var name = $('#name').val();
					var address = $('#address').val();
					var gender = $('#gender').val();
					var username = $('#username').val();
					var password = $('#password').val();

					if (isEmptyOrSpaces(name) || isEmptyOrSpaces(address)
							|| isEmptyOrSpaces(gender)
							|| isEmptyOrSpaces(username)
							|| isEmptyOrSpaces(password)) {

						alert("All fields are required.");

					} else {
						$
								.ajax({
									url : 'AddStudent',
									data : {
										name : name,
										address : address,
										gender : gender,
										username : username,
										password : password
									},
									method : 'POST',
									success : function(data) {
										if (data == 0) {
											alert("Username already exist");
										} else {
											window.location.href = 'StudentSubjectList?operation=student';
										}
									}
								})
					}

				})

$('#add_subject')
		.click(
				function() {

					var title = $('#title').val();
					var description = $('#description').val();

					if (isEmptyOrSpaces(title) || isEmptyOrSpaces(description)) {
						alert("All fields are required");
					} else {
						$
								.ajax({
									url : 'AddSubject',
									data : {
										title : title,
										description : description
									},
									method : 'POST',
									success : function(data) {
										if (data == 0) {
											alert("Subject already exist");
										} else {
											window.location.href = 'StudentSubjectList?operation=subject';
										}
									}
								})
					}

				})

$('#update_student').click(
		function() {

			var student_id = $(this).val();
			var name = $('#name').val();
			var address = $('#address').val();
			var gender = $('#gender').val();
			var operation = 'update';

			if (isEmptyOrSpaces(name) || isEmptyOrSpaces(address)
					|| isEmptyOrSpaces(gender)) {
				alert("All fields are required");
			} else {
				$.ajax({
					url : 'UpdateStudent',
					data : {
						student_id : student_id,
						name : name,
						address : address,
						gender : gender,
						operation : operation
					},
					method : 'POST',
					success : function(data) {

						location.reload();

					}
				})
			}

		})

$('#delete_student').click(function() {
	var student_id = $(this).val();
	var account_id = $('#account_id').val();
	var operation = 'delete';

	$.ajax({
		url : 'UpdateStudent',
		data : {
			student_id : student_id,
			account_id : account_id,
			operation : operation
		},
		method : 'POST',
		success : function(data) {

			window.location.href = 'StudentSubjectList?operation=student';

		}
	})
})

$('#update_grade').click(function() {
	var grade = $('#grade_value').val();
	var grade_id = $('#grade_id').val();
	var student_id = $(this).val();

	if (grade > 100 || grade < 60) {
		alert("Invalid Grade");
	} else if (isNumber(grade)) {
		alert("Invalid Grade");
	} else {
		$.ajax({
			url : 'UpdateGrade',
			data : {
				grade : grade,
				grade_id : grade_id,
				student_id : student_id
			},
			method : 'POST',
			success : function(data) {

				location.reload();

			}
		})
	}

})

$('#delete_subject').click(function() {
	var subject_id = $(this).val();
	const operation = 'delete';

	$.ajax({
		url : 'UpdateSubject',
		data : {
			subject_id : subject_id,
			operation : operation
		},
		method : 'POST',
		success : function(data) {

			location.reload();

		}
	})
})

$('.update_subject').click(function() {
	var subject_id = $(this).val();
	var title = $('#title').val();
	var description = $('#description').val();
	const operation = 'update';

	if (isEmptyOrSpaces(title) || isEmptyOrSpaces(description)) {
		alert("All fields are required");
	} else {
		$.ajax({
			url : 'UpdateSubject',
			data : {
				subject_id : subject_id,
				title : title,
				description : description,
				operation : operation
			},
			method : 'POST',
			success : function(data) {

				console.log(data)
				if (data == 0) {
					alert("Subject already exist");
				} else {
					location.reload();
				}

			}
		})
	}

})

$('#search_student').click(function() {
	var student_name = $('#student_name').val();
	window.location.href = 'StudentSubjectList?operation=student_search&student_name='+student_name;

})

$('#view_all_student').click(function() {

	window.location.href = 'StudentSubjectList?operation=student';

})

$('#search_subject').click(function() {
	var subject_name = $('#subject_name').val();
	window.location.href = 'StudentSubjectList?operation=subject_search&subject_name='+subject_name;

})

$('#view_all_subject').click(function() {

	window.location.href = 'StudentSubjectList?operation=subject';

})

$('#open_edit_modal').click(function() {
	$('#update_modal').show();
})

$('#open_delete_modal').click(function() {
	$('#delete_modal').show();
})

$('.delete_subject_modal').click(function() {
	$('#delete_modal_subject').show();

	$('#delete_subject').val($(this).val());
})

$('.edit_subject_modal').click(function() {
	$('#update_subject_modal').show();

	$('#title').val($(this).attr("data-title"));
	$('#description').val($(this).attr("data-description"));
	$('#subject_id').val($(this).val());
})

$('.open_grade_modal').click(function() {
	$('#grade_modal').show();
	$('#grade_id').val($(this).val());

	var grade = $(this).attr("data-value");
	$('#grade_value').val(grade);

})

$('.close').click(function() {
	$('#update_modal').hide();
	$('#delete_modal').hide();
	$('#grade_modal').hide();
	$('#delete_modal_subject').hide();
	$('#update_subject_modal').hide();
})

function isNumber(n) {
	return typeof n == 'number' && !isNaN(n) && isFinite(n);
}

function isEmptyOrSpaces(str) {
	return str === null || str.match(/^ *$/) !== null;
}