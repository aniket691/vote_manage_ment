package com.app.servlet;

import com.app.dao.UserDao;
import com.app.dao.UserDaoImpl;
import com.app.entities.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ShowListServlet")
public class ShowListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public ShowListServlet() {
		super();
		try {
			userDao = new UserDaoImpl();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize UserDao", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check if session is alive
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("login.html");
			return;
		}

		// Set the response content type to HTML
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			// Fetch user details from the database
			List<User> userList = userDao.getUserDetails();

			// Generate HTML output with CSS styling
			out.println("<html><head><title>User List</title><style>");
			out.println(
					"body { font-family: Arial, sans-serif; background-color: #f2f2f2; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
			out.println(
					".container { background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); width: 400px; text-align: center; }");
			out.println("h2 { color: #333; margin-bottom: 20px; }");
			out.println("ul { list-style-type: none; padding: 0; margin: 0; }");
			out.println(
					"li { display: flex; justify-content: space-between; align-items: center; margin: 10px 0; padding: 15px; background-color: #f9f9f9; border: 1px solid #ddd; border-radius: 5px; transition: background-color 0.3s ease; }");
			out.println("li:hover { background-color: #f1f1f1; }");
			out.println("form { margin: 0; }");
			out.println(
					"button { background-color: #4CAF50; color: white; border: none; border-radius: 5px; padding: 10px 15px; cursor: pointer; }");
			out.println("button:hover { background-color: #45a049; }");
			out.println("a { text-decoration: none; color: #4CAF50; display: block; margin-top: 10px; }");
			out.println("</style></head><body>");
			out.println("<div class='container'>");
			out.println("<h2>List of Users</h2>");
			out.println("<ul>");
			for (User user : userList) {
				out.println("<li>");
				out.println("<div><strong>" + user.getFirstName() + " " + user.getLastName() + "</strong><br>Email: "
						+ user.getEmail() + "</div>");
				out.println("<form action='ShowListServlet' method='post'>");
				out.println("<input type='hidden' name='userId' value='" + user.getUserId() + "'/>");
				out.println("<button type='submit'>Vote</button>");
				out.println("</form>");
				out.println("</li>");
			}
			out.println("</ul>");
			out.println("<a href='LogoutServlet'>Logout</a>");
			out.println("</div>");
			out.println("</body></html>");
		} catch (SQLException e) {
			e.printStackTrace();
			out.println("<html><body>");
			out.println("<h2>Error fetching user details!</h2>");
			out.println("</body></html>");
		} finally {
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("login.html");
			return;
		}

		String userId = request.getParameter("userId");
		if (userId != null) {
			session.setAttribute("selectedUserId", userId);
			response.sendRedirect("VoteServlet");
		}
	}
}
