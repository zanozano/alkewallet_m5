package org.alkewallet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alkewallet.dao.UserDAO;
import org.alkewallet.model.Account;
import org.alkewallet.model.Transaction;
import org.alkewallet.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/deposit")
public class UpdateBalanceServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = user.getEmail();
        double amount;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid amount format", e);
        }

        String currency = request.getParameter("currency");

        boolean success = false;
        try {
            success = userDAO.deposit(email, amount, currency);
        } catch (SQLException e) {
            throw new ServletException("Error updating balance", e);
        }

        if (success) {
            try {
                List<Account> accounts = userDAO.getUserAccounts(user.getEmail());
                request.setAttribute("accounts", accounts);

                List<Transaction> transactions = userDAO.getUserTransactions(user.getId());
                request.setAttribute("transactions", transactions);
            } catch (SQLException e) {
                throw new ServletException("Error updating balance", e);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }  else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
