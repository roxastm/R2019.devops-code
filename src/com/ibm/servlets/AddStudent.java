package com.ibm.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.grading.system.User;
import com.ibm.grading.system.StudentSubjectDataSource;

public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddStudent() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		StudentSubjectDataSource dataSource = new StudentSubjectDataSource();
		User studentDetails = new User();
		studentDetails.setName(name);
		studentDetails.setAddress(address);
		studentDetails.setGender(gender);
		studentDetails.setUsername(username);
		studentDetails.setPassword(password);

		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}

		if (dataSource.addStudent(studentDetails)) {
			List<User> students = new ArrayList<User>();
			for (User student : dataSource.getStudentList()) {
				students.add(student);
			}

			request.setAttribute("studentList", students);
			request.getRequestDispatcher("StudentList.jsp").forward(request, response);
		} else {
			response.getWriter().println(0);
		}

		dataSource.close();
	}

}
