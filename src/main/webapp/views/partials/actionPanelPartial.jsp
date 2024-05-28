<%@ page import="org.alkewallet.model.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    String email = (user != null) ? user.getEmail() : "No email available";
%>

<div class="d-flex justify-content-between">

    <div class="d-flex flex-column gap-2 align-items-center justify-content-center">
        <img src="./img/avatar.svg" alt="avatar" width="140px" height="140px">
        <p class="m-0"><%= email %></p>
    </div>

    <div class="d-flex flex-column justify-content-end gap-2">
        <h4>Select your Action</h4>
        <div class="d-flex gap-2">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalTransfer">
                Transfer
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
