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
import com.ibm.grading.system.Subject;

public class StudentSubjectList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StudentSubjectList() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String operation = request.getParameter("operation");
		String studentName = request.getParameter("student_name");
		String subjectName = request.getParameter("subject_name");
		String offset = request.getParameter("offset");
		
		StudentSubjectDataSource dataSource = new StudentSubjectDataSource();
		int rowCount = 0;
		double pageCount = 0;
		int tableRowCount = 0;
		
		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}

		if (operation.equals("student")) {
			
			List<User> students = new ArrayList<User>();
			
			for (@SuppressWarnings("unused") User student : dataSource.getStudentList()) {
				rowCount++;
			}
			
			for (User student : dataSource.getStudentListTable(offset)) {
				students.add(student);
				tableRowCount++;
			}
			
			pageCount = (double) rowCount / 5;
			
			
			if(pageCount <= 1) {
				pageCount = 1;
			}else if(rowCount % 5 != 0) { //if has remainder
				pageCount += 1;
			}
			
			request.setAttribute("pageCount", pageCount);
			request.setAttribute("rowCount", rowCount);
			request.setAttribute("tableRowCount", tableRowCount);
			request.setAttribute("studentList", students);
			request.getRequestDispatcher("StudentList.jsp").forward(request, response);

		} else if (operation.equals("subject")) {

			List<Subject> subjects = new ArrayList<Subject>();
			for (Subject subject : dataSource.getSubjectList()) {
				subjects.add(subject);
			}

			request.setAttribute("subjectList", subjects);
			request.getRequestDispatcher("SubjectList.jsp").forward(request, response);
			
		} else if (operation.equals("student_search")) {
			
			List<User> students = new ArrayList<User>();
			for (User student : dataSource.searchStudent(studentName)) {
				students.add(student);
				rowCount++;
			}
			
			request.setAttribute("rowCount", rowCount);
			request.setAttribute("studentList", students);
			request.getRequestDispatcher("StudentList.jsp").forward(request, response);
			
		}else {
			List<Subject> subjects = new ArrayList<Subject>();
			for (Subject subject : dataSource.searchSubject(subjectName)) {
				subjects.add(subject);
			}
			
			request.setAttribute("rowCount", rowCount);
			request.setAttribute("subjectList", subjects);
			request.getRequestDispatcher("SubjectList.jsp").forward(request, response);
		}

		dataSource.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
