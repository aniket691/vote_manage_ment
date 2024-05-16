package com.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/VoteServlet")
public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get the parameter from the session
		HttpSession session = request.getSession(false);
		String selectedUserId = (String) session.getAttribute("selectedUserId");

		if (selectedUserId == null) {
			response.sendRedirect("ShowListServlet");
			return;
		}

		// Display the vote confirmation
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Vote Confirmation</title><style>");
		out.println(
				"body { font-family: Arial, sans-serif; background-color: #f2f2f2; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
		out.println(
				".container { background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); width: 400px; text-align: center; }");
		out.println("h2 { color: #333; margin-bottom: 20px; }");
		out.println("p { color: #555; font-size: 16px; }");
		out.println("a { text-decoration: none; color: #4CAF50; display: block; margin-top: 20px; }");
		out.println("a:hover { color: #45a049; }");
		out.println("</style></head><body>");
		out.println("<div class='container'>");
		out.println("<h2>Vote Confirmation</h2>");
		out.println("<p>Your vote for User ID: <strong>" + selectedUserId
				+ "</strong> has been submitted successfully!</p>");
		out.println("<a href='LogoutServlet'>Logout</a>");
		out.println("</div>");
		out.println("</body></html>");
	}
}
