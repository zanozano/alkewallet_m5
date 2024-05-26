package org.alkewallet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("account");
        } else {
            request.getRequestDispatcher("/views/loginView.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            User user = null;
            try {
                user = userDAO.authenticate(email, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (user != null) {
                request.getSession().setAttribute("user", user);
                System.out.println("validated");
                response.sendRedirect("account");
            } else {
                System.out.println("error");
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/views/loginView.jsp").forward(request, response);
            }
        } else {
            System.out.println("empty parameters");
            request.setAttribute("empty", "Username or password cannot be empty");
            request.getRequestDispatcher("/views/loginView.jsp").forward(request, response);
        }
    }
}
