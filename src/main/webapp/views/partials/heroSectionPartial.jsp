<%@ page import="org.alkewallet.model.User" %>
<%@ page session="true" %>

<%
    User user = (User) session.getAttribute("user");
    String buttonText = (user != null) ? "Access My Account" : "Get Started";
%>

<section class="hero-section overflow-hidden">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-md-6">
                <h1 class="font-weight-bold custom-heading">Welcome to AlkeWallet</h1>
                <p>Your secure and convenient digital wallet solution</p>
                <a href="<%= (user != null) ? "/account" : "/login" %>" class="btn btn-primary"><%= buttonText %></a>
            </div>
            <div class="col-md-6">
                <img src="img/wallet.png" alt="Wallet Image" class="img-fluid animate__animated animate__fadeInRight">
            </div>
        </div>
    </div>
</section>
