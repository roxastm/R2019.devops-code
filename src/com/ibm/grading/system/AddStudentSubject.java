package com.ibm.grading.system;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddStudentSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddStudentSubject() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String studentId = request.getParameter("student_id");
		String subjectId = request.getParameter("subject_id");
		String grade = request.getParameter("grade");
		
		StudentSubjectDataSource dataSource = new StudentSubjectDataSource();
		
		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}
		
		
		if(dataSource.addStudentGrade(studentId, subjectId, grade)) {
			request.setAttribute("subjectList", dataSource.getSubjectList());
			request.setAttribute("student", dataSource.findStudent(studentId));
			request.setAttribute("gradeList", dataSource.getStudentGrade(studentId));
			request.getRequestDispatcher("StudentDetails.jsp").forward(request, response);
		}else {
			response.getWriter().println(0);
		}

		dataSource.close();
	}

}
