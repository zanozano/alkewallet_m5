package org.alkewallet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.alkewallet.model.Transaction;
import org.alkewallet.model.Account;
import org.alkewallet.model.User;
import org.alkewallet.util.DatabaseConnection;

public class UserDAO {

    public User authenticate(String email, String password) throws SQLException {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }
        }
        return user;
    }

    public boolean createUser(User user) throws SQLException {
        String query = "INSERT INTO users (id, email, password) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            UUID uuid = UUID.randomUUID();

            statement.setString(1, uuid.toString());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        }
    }

    public List<Account> getUserAccounts(String email) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT a.id as account_id, c.currency_code, b.amount " +
                       "FROM users u " +
                       "JOIN accounts a ON u.id = a.user_id " +
                       "JOIN balances b ON a.id = b.account_id " +
                       "JOIN currencies c ON b.currency_code = c.currency_code " +
                       "WHERE u.email = ? " +
                       "ORDER BY CASE WHEN c.currency_code = 'CLP' THEN 0 ELSE 1 END";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getString("account_id"));
                account.setCurrencyCode(resultSet.getString("currency_code"));
                account.setAmount(resultSet.getDouble("amount"));
                accounts.add(account);
            }
        }
        return accounts;
    }

    public List<Transaction> getUserTransactions(String userId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT amount, transaction_date, sender_id, receiver_id, currency_code, type " +
                "FROM transactions " +
                "WHERE sender_id = ? OR receiver_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            UUID userUUID = UUID.fromString(userId);

            statement.setObject(1, userUUID);
            statement.setObject(2, userUUID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTransactionDate(resultSet.getDate("transaction_date"));
                transaction.setSenderId(resultSet.getString("sender_id"));
                transaction.setReceiverId(resultSet.getString("receiver_id"));
                transaction.setCurrencyCode(resultSet.getString("currency_code"));
                transaction.setType(resultSet.getString("type"));
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public boolean deposit(String email, double amount, String currency) throws SQLException {

        String query = "UPDATE balances AS b " +
                "SET amount = amount + ? " +
                "FROM accounts AS a " +
                "JOIN users AS u ON a.user_id = u.id " +
                "WHERE b.account_id = a.id " +
                "AND u.email = ? " +
                "AND b.currency_code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, amount);
            statement.setString(2, email);
            statement.setString(3, currency);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;
        }
    }


}
