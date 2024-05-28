<%@ page import="org.alkewallet.model.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    String email = (user != null) ? user.getEmail() : "No email available";
%>

<div class="d-flex justify-content-between">

    <div class="d-flex flex-column gap-2 align-items-center justify-content-center">
        <h2 class="mt-5">My Account</h2>
        <img src="./img/avatar.svg" alt="avatar" width="140px" height="140px">
        <span class="badge rounded-pill text-bg-primary"><%= email %></span>
    </div>

    <div class="d-flex flex-column justify-content-end gap-2">
        <h5>Select your Action</h5>
        <div class="d-flex gap-2">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalDeposit">
                Deposit
            </button>

            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalWithdraw">
                Withdraw
            </button>

            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalExchange">
                Exchange Currency
            </button>
        </div>
    </div>
</div>
