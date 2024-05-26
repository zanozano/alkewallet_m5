package org.alkewallet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.alkewallet.dao.UserDAO;
import org.alkewallet.model.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            response.sendRedirect("account");
        } else {
            request.getRequestDispatcher("/views/loginView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            try {
                User user = userDAO.authenticate(email, password);

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect("account");
                } else {
                    request.setAttribute("error", "Invalid username or password");
                    request.getRequestDispatcher("/views/loginView.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                request.setAttribute("error", "Database error");
                request.getRequestDispatcher("/views/loginView.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("empty", "Username or password cannot be empty");
            request.getRequestDispatcher("/views/loginView.jsp").forward(request, response);
        }
    }
}
