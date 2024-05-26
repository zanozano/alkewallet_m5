package org.alkewallet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alkewallet.dao.UserDAO;
import org.alkewallet.model.Account;
import org.alkewallet.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            try {
                List<Account> accounts = userDAO.getUserAccounts(user.getEmail());
                request.setAttribute("accounts", accounts);
                request.getRequestDispatcher("/views/accountView.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error retrieving user accounts", e);
            }
        } else {
            response.sendRedirect("login");
        }
    }
}

