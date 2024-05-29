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

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/signUpView.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (password.equals(confirmPassword)) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);

            try {
                boolean success = userDAO.createUser(newUser);

                if (!success) {
                    request.setAttribute("error", "Error creating user");
                } else {
                    response.sendRedirect("login");
                    return;
                }
            } catch (SQLException e) {
                request.setAttribute("error", "Database error");
            }
        } else {
            request.setAttribute("error", "Passwords do not match");
        }
        request.getRequestDispatcher("/views/signUpView.jsp").forward(request, response);
    }
}
