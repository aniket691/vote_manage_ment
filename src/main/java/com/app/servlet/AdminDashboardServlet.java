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

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    public AdminDashboardServlet() {
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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Fetch user details from the database
            List<User> userList = userDao.getUserDetails();

            // Get the top two candidates
            List<User> topTwoCandidates = userList.size() > 2 ? userList.subList(0, 2) : userList;

            // Generate HTML response
            out.println("<html><head><title>Admin Dashboard</title></head><body>");
            out.println("<h1>Top Two Candidates</h1>");
            out.println("<table border='1'><tr><th>ID</th><th>Name</th></tr>");

            for (User user : topTwoCandidates) {
                out.println("<tr><td>" + user.getUserId() + "</td><td>" + user.getFirstName() + " " + user.getLastName() + "</td></tr>");
            }

            out.println("</table>");
            out.println("<br><a href='LogoutServlet'>Logout</a>"); // Logout link
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body><h3>Error fetching user details!</h3></body></html>");
        } finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
