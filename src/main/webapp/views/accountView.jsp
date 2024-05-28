<jsp:include page="/views/layouts/mainLayout.jsp">
    <jsp:param name="pageTitle" value="Account" />
    <jsp:param name="actionPanel" value="/views/partials/actionPanelPartial.jsp" />
    <jsp:param name="content" value="/views/partials/userDataTablesPartial.jsp" />
    <jsp:param name="depositModal" value="/views/partials/depositModalPartial.jsp" />
    <jsp:param name="withdrawModal" value="/views/partials/withdrawModalPartial.jsp" />
    <jsp:param name="exchangeModal" value="/views/partials/exchangeModalPartial.jsp" />
</jsp:include>
