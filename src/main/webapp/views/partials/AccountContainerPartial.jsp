<%@ page import="org.alkewallet.model.Account" %>
<%@ page import="java.util.List" %>

<table class="table table-striped">
    <thead>
        <tr>
            <th>ID</th>
            <th>Code</th>
            <th>Balance</th>
        </tr>
    </thead>
    <tbody>
        <%
        int index = 1;
        for (Account account : (List<Account>) request.getAttribute("accounts")) {
        %>
            <tr>
                <td><%= index++ %></td>
                <td><%= account.getCurrencyCode() %></td>
                <td><%= account.getAmount() %></td>
            </tr>
        <% } %>
    </tbody>
</table>
