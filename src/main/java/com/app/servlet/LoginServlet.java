package com.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Database credentials
        String jdbcURL = "jdbc:mysql://localhost:3306/iacsd_mar24";
        String dbUser = "root";
        String dbPassword = "Dalal691*";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            System.out.println("Connected to the database");

            // SQL query to check the username, password, and get the role
            String sql = "SELECT role FROM users WHERE email = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Successful login
                String role = resultSet.getString("role");

                HttpSession session = request.getSession();
                session.setAttribute("username", username); // Storing username in session
                session.setAttribute("role", role); // Storing role in session

                // Redirect based on role
                if ("ADMIN".equals(role)) {
                    response.sendRedirect("AdminDashboardServlet");
                } else {
                    response.sendRedirect("ShowListServlet");
                }
            } else {
                // Failed login
                out.println("<html><body>");
                out.println("<script>alert('Invalid username or password. Please try again.');"
                        + "window.location='login.html';</script>");
                out.println("</body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h2>Database connection error!</h2>");
            out.println("</body></html>");
        } finally {
            // Close resources
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
