<%@ page import="org.alkewallet.model.User" %>
<%@ page session="true" %>

<% User user = (User) session.getAttribute("user"); %>

<nav class="navbar navbar-dark navbar-expand-lg bg-color">
    <div class="container">
        <a class="navbar-brand" href="/">AlkeWallet</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <% if (user != null) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="/account">My Account</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout" onclick="logout()">Logout</a>
                    </li>
                <% } else { %>
                    <li class="nav-item">
                        <a class="nav-link active" href="/login">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/signup">Signup</a>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>
