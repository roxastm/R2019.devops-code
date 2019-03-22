package com.ibm.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.grading.system.Account;
import com.ibm.grading.system.LoginDataSource;
import com.ibm.grading.system.User;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		LoginDataSource dataSource = new LoginDataSource();
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		
		if (!dataSource.open()) {
			System.out.println("cant connect");
		} else {
			System.out.println("connected");
		}

		User user = dataSource.login(account);
			
		
		if (user != null) {
			
			if (user.getRole().equals("admin")) {
				request.getSession().setAttribute("user", user);
				request.getRequestDispatcher("AdminPage.jsp").forward(request, response);;
			} else {
				request.getSession().setAttribute("user", user);
				request.getRequestDispatcher("StudentPage.jsp").forward(request, response);
			}

		} else {
			request.getSession().setAttribute("result", "Invalid credentials.");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}

		dataSource.close();

	}

}
