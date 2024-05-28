<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getParameter("pageTitle") %></title>
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/img/favicon.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
    <jsp:include page="/views/partials/navbarPartial.jsp" />

    <main id="main-content">
        <div class="container my-5">
            <%
                String actionPanel = request.getParameter("actionPanel");
                if (actionPanel != null) {
                    %>
                    <jsp:include page="<%= actionPanel %>" />
                    <%
                }

                String content = request.getParameter("content");
                if (content != null) {
                    %>
                    <jsp:include page="<%= content %>" />
                    <%
                }

                String transferModal = request.getParameter("transferModal");
                if (transferModal != null) {
                    %>
                    <jsp:include page="<%= transferModal %>" />
                    <%
                }

                String withdrawModal = request.getParameter("withdrawModal");
                if (withdrawModal != null) {
                    %>
                    <jsp:include page="<%= withdrawModal %>" />
                    <%
                }

                String exchangeModal = request.getParameter("exchangeModal");
                if (exchangeModal != null) {
                    %>
                    <jsp:include page="<%= exchangeModal %>" />
                    <%
                }
            %>
        </div>
    </main>

    <jsp:include page="/views/partials/footerPartial.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>
