package com.ibm.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.grading.system.StudentSubjectDataSource;

public class ManageStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManageStudent() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String studentId = request.getParameter("edit");
		StudentSubjectDataSource dataSource = new StudentSubjectDataSource();

		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}
		
		request.setAttribute("subjectList", dataSource.getSubjectList());
		request.setAttribute("student", dataSource.findStudent(studentId));
		request.setAttribute("gradeList", dataSource.getStudentGrade(studentId));
		request.getRequestDispatcher("StudentDetails.jsp").forward(request, response);

		dataSource.close();
	}

}
