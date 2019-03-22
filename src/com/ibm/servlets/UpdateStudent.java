package com.ibm.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.grading.system.StudentSubjectDataSource;
import com.ibm.grading.system.User;

public class UpdateStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateStudent() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String studentId = request.getParameter("student_id");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		String operation = request.getParameter("operation");
		String accountId = request.getParameter("account_id");
		
		StudentSubjectDataSource dataSource = new StudentSubjectDataSource();
		User studentDetails = new User();
		studentDetails.setName(name);
		studentDetails.setAddress(address);
		studentDetails.setGender(gender);
		studentDetails.setUserId(studentId);
		studentDetails.setAccountId(accountId);

		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}

		if (operation.equals("update")) {
			dataSource.updateStudent(studentDetails);
		} else {
			dataSource.deleteStudent(studentDetails);
		}

		dataSource.close();
	}

}
