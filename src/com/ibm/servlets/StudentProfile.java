package com.ibm.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.grading.system.Grade;
import com.ibm.grading.system.StudentSubjectDataSource;
import com.ibm.grading.system.User;

public class StudentProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StudentProfile() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String operation = request.getParameter("operation");

		StudentSubjectDataSource dataSource = new StudentSubjectDataSource();

		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}

		User userObject = (User) request.getSession(false).getAttribute("user");
		String userId = userObject.getUserId();

		if (operation.equals("student")) {
			request.setAttribute("student", dataSource.findStudent(userId));
			request.getRequestDispatcher("StudentProfile.jsp").forward(request, response);
		} else {

			int total = 0;
			int numberOfSubjects = 0;

			List<Grade> grades = dataSource.getStudentGrade(userId);
			for (Grade g : grades) {
				total += g.getGrade();
				numberOfSubjects++;
			}

			DecimalFormat numberFormat = new DecimalFormat("#.00");
			double avg = (double) total / numberOfSubjects;

			request.setAttribute("gradeList", dataSource.getStudentGrade(userId));
			request.setAttribute("average", numberFormat.format(avg));

			request.getRequestDispatcher("StudentSubject.jsp").forward(request, response);
		}

		dataSource.close();
	}

}
