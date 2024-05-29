package org.alkewallet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.alkewallet.model.Transaction;
import org.alkewallet.model.Account;
import org.alkewallet.model.User;
import org.alkewallet.util.DatabaseConnection;

public class UserDAO {

    public static final String VALIDATE_USER = "SELECT * FROM users WHERE email = ? AND password = ?";

    public static final String CREATE_USER = "INSERT INTO users (id, email, password) VALUES (?, ?, ?)";

    public static final String GET_USER_ID = "SELECT id FROM users WHERE email = ?";

    public static final String GET_ACCOUNTS = "SELECT a.id as account_id, c.currency_code, b.amount " +
            "FROM users u " +
            "JOIN accounts a ON u.id = a.user_id " +
            "JOIN balances b ON a.id = b.account_id " +
            "JOIN currencies c ON b.currency_code = c.currency_code " +
            "WHERE u.email = ? " +
            "ORDER BY CASE WHEN c.currency_code = 'CLP' THEN 0 ELSE 1 END";

    public static final String GET_TRANSACTIONS = "SELECT amount, transaction_date, sender_id, receiver_id, currency_code, type " +
            "FROM transactions " +
            "WHERE sender_id = ? OR receiver_id = ?";

    public static final String SET_TRANSACTION = "INSERT INTO transactions (transaction_id, amount, transaction_date, sender_id, receiver_id, currency_code, type) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String SET_DEPOSIT = "UPDATE balances AS b " +
            "SET amount = amount + ? " +
            "FROM accounts AS a " +
            "JOIN users AS u ON a.user_id = u.id " +
            "WHERE b.account_id = a.id " +
            "AND u.email = ? " +
            "AND b.currency_code = ?";

    public static final String SET_WITHDRAW = "UPDATE balances AS b " +
            "SET amount = amount - ? " +
            "FROM accounts AS a " +
            "JOIN users AS u ON a.user_id = u.id " +
            "WHERE b.account_id = a.id " +
            "AND u.email = ? " +
            "AND b.currency_code = ?";

    public User authenticate(String email, String password) throws SQLException {

        User user = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(VALIDATE_USER)) {

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

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {

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

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ACCOUNTS)) {

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

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_TRANSACTIONS)) {

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

    public boolean transaction(double amount, UUID senderId, UUID receiverId, String currency, String type) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_TRANSACTION)) {

            UUID transactionId = UUID.randomUUID();

            statement.setObject(1, transactionId);
            statement.setDouble(2, amount);
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setObject(4, senderId);
            statement.setObject(5, receiverId);
            statement.setString(6, currency);
            statement.setString(7, type);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        }
    }

    public UUID getUserIdByEmail(String email) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_ID)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userIdString = resultSet.getString("id");
                return UUID.fromString(userIdString);
            } else {
                return null;
            }
        }
    }

    public boolean deposit(String email, double amount, String currency) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_DEPOSIT)) {

            statement.setDouble(1, amount);
            statement.setString(2, email);
            statement.setString(3, currency);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                UUID senderId = getUserIdByEmail(email);
                UUID receiverId = getUserIdByEmail(email);
                if (senderId != null) {
                    return transaction(amount, senderId, receiverId, currency, "DEPOSIT");
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    public boolean withdraw(String email, double amount, String currency) throws SQLException {

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_WITHDRAW)) {

            statement.setDouble(1, amount);
            statement.setString(2, email);
            statement.setString(3, currency);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                UUID senderId = getUserIdByEmail(email);
                UUID receiverId = getUserIdByEmail(email);
                if (senderId != null) {
                    return transaction(amount, senderId, receiverId, currency, "WITHDRAW");
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    public boolean exchange(String email, double amount, String currency) throws SQLException {

        final String baseCurrency = "CLP";

        double baseAmount = amount * exchangeRate(currency);

        boolean withdrawalSuccess = withdraw(email, amount, baseCurrency);

        if (withdrawalSuccess) {

            return deposit(email, baseAmount, currency);
        } else {
            return false;
        }
    }

    public static double exchangeRate(String currency) {
        return switch (currency) {
            case "USD" -> 0.00108;
            case "EUR" -> 0.00100;
            case "THB" -> 0.0396045;
            case "CNY" -> 0.00780;
            default -> 1.0;
        };
    }

}
