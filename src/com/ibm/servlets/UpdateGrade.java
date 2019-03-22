package com.ibm.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.grading.system.StudentSubjectDataSource;

public class UpdateGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateGrade() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String grade = request.getParameter("grade");
		String gradeId = request.getParameter("grade_id");
		
		StudentSubjectDataSource dataSource = new StudentSubjectDataSource();
		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}
		
		if(!dataSource.updateGrade(gradeId, grade)) {
			response.getWriter().println(0);
		}
		
		dataSource.close();
	}

}
