<%@ page import="org.alkewallet.model.Account" %>
<%@ page import="org.alkewallet.model.Transaction" %>
<%@ page import="java.util.List" %>

<h3 class="mt-5">User Account</h3>
<table class="table table-striped mt-2">
    <thead>
        <tr>
            <th>ID</th>
            <th>Code</th>
            <th>Balance</th>
        </tr>
    </thead>
    <tbody>
        <%
        int iAccount = 1;
        for (Account account : (List<Account>) request.getAttribute("accounts")) {
        %>
            <tr>
                <td><%= iAccount++ %></td>
                <td><%= account.getCurrencyCode() %></td>
                <td><%= account.getAmount() %></td>
            </tr>
        <% } %>
    </tbody>
</table>

<h3 class="mt-5">User Transactions</h3>
<table class="table table-striped mt-2">
    <thead>
        <tr>
            <th>ID</th>
            <th>Currency</th>
            <th>Amount</th>
            <th>Type</th>
            <th>Date</th>
            <th>Sender ID</th>
            <th>Receiver ID</th>
        </tr>
    </thead>
    <tbody>
        <%
        int iTransaction = 1;
        for (Transaction transaction : (List<Transaction>) request.getAttribute("transactions")) {
        %>
            <tr>
                <td><%= iTransaction++ %></td>
                <td><%= transaction.getCurrencyCode() %></td>
                <td><%= transaction.getAmount() %></td>
                <td><%= transaction.getType() %></td>
                <td><%= transaction.getTransactionDate() %></td>
                <td><%= transaction.getSenderId() %></td>
                <td><%= transaction.getReceiverId() %></td>
            </tr>
        <% } %>
    </tbody>
</table>
