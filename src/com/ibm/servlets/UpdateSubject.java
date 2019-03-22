package com.ibm.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.grading.system.StudentSubjectDataSource;
import com.ibm.grading.system.Subject;


public class UpdateSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public UpdateSubject() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String subjectId = request.getParameter("subject_id");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String operation = request.getParameter("operation");
		
		StudentSubjectDataSource dataSource = new StudentSubjectDataSource();
		Subject subject = new Subject();
		
		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}
		
		subject.setTitle(title);
		subject.setDescription(description);
		subject.setSubjectId(Integer.parseInt(subjectId));
		
		if (operation.equals("update")) {
			if(dataSource.updateSubject(subject) == false) {
				response.getWriter().println(0);
			}else {
				response.getWriter().println(1);
			}
		} else {
			dataSource.deleteSubject(subject);
		}
		
		
		dataSource.close();
	}

}
