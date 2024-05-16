package com.app.servlet;

import com.app.dao.UserDao;
import com.app.dao.UserDaoImpl;
import com.app.entities.User;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public RegisterServlet() {
		super();
		try {
			userDao = new UserDaoImpl();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize UserDao", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		double regAmt = Double.parseDouble(request.getParameter("reg_amt"));
		String regDateStr = request.getParameter("reg_date");
		String role = request.getParameter("role");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date regDate = null;
		try {
			regDate = sdf.parse(regDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		User newUser = new User(firstName, lastName, email, password, regAmt, regDate, role);

		try {
			userDao.addUser(newUser);
			response.setContentType("text/html");
			response.getWriter().println("<html><body><h3>Registration Successful!</h3>");
			response.getWriter().println("<a href='login.html'>Click here to login</a></body></html>");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setContentType("text/html");
			response.getWriter().println("<html><body><h3>Registration Failed! Please try again.</h3>");
			response.getWriter().println("<a href='registration.html'>Back to Registration</a></body></html>");
		}
	}
}
