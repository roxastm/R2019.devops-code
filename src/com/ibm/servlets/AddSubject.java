package com.ibm.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.grading.system.StudentSubjectDataSource;
import com.ibm.grading.system.Subject;


public class AddSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddSubject() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		
		StudentSubjectDataSource dataSource = new StudentSubjectDataSource();
		Subject subjectDetails = new Subject();
		subjectDetails.setTitle(title);
		subjectDetails.setDescription(description);
		
		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}
		
		if(dataSource.addSubject(subjectDetails)) {
			List<Subject> subjects = new ArrayList<Subject>();
			for(Subject subject: dataSource.getSubjectList()) {
				subjects.add(subject);
			}
			
			request.setAttribute("subjectList", subjects);
			request.getRequestDispatcher("SubjectList.jsp").forward(request, response);
		}else {
			response.getWriter().println(0);
		}
		
		dataSource.close();
	}

}
