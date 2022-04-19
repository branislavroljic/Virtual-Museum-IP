<%@ page import="org.unibl.etf.virtual_bank_jsp_m2.models.Transaction" %><%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 4/3/2022
  Time: 11:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="transactions" type="java.util.List<org.unibl.etf.virtual_bank_jsp_m2.models.Transaction>"
             scope="session"></jsp:useBean>
<html>
<head>
    <title>Transactions</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>

    <script src="js/transactions.js"></script>
    <link rel="stylesheet" href="styles/transactions.css"/>
    <link rel="stylesheet" href="styles/navigation.css"/>
    <%--        <link rel="stylesheet" href="styles/home.css"/>--%>
    <%--    --%>
</head>
<body>

<div class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer
                mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">Transactions</span>
        </div>
    </header>
    <div class="mdl-layout__drawer">
        <span class="mdl-layout-title">Menu</span>
        <nav class="mdl-navigation">
            <a class="mdl-navigation__link" style="color: black" href="?action=home"><i
                    class="material-icons">person</i>Account</a>

            <a class="mdl-navigation__link " style="color: black" href="?action=transactions"><i
                    class="material-icons">article</i>Transactions</a>
            <a class="mdl-navigation__link" style="color: black" href="?action=logout"><i
                    class="material-icons">logout</i>Logout</a>
        </nav>
    </div>

    <main class="mdl-layout__content content">
        <div class="reload-div">
            <button id="reload-transactions" onclick="reloadTransactions()"
                    class="mdl-button mdl-js-button mdl-button--fab" style="margin-bottom: 20px">
                <i class="material-icons">refresh</i>
            </button>
        </div>

        <div class="table-responsive table-div" style="overflow-y: auto">

            <table class="mdl-data-table mdl-js-data-table mdl-shadow--4dp">
                <thead>
                <tr>
                    <th class="mdl-data-table__cell--non-numeric">Date</th>
                    <th class="mdl-data-table__cell--non-numeric">Description</th>
                    <th>Amount</th>
                </tr>
                </thead>
                <tbody id="transaction_body">
                <% for (Transaction t : transactions) {
                %>
                <tr>
                    <td class="mdl-data-table__cell--non-numeric"><%= t.getDate() %></td>
                    <td class="mdl-data-table__cell--non-numeric"><%= t.getDescription()%></td>
                    <td style="text-align: right"><%=  String.format("%.2f", t.getAmount()) %></td>
                </tr>
                <%}%>
                </tbody>
            </table>

        </div>
    </main>
</div>
</body>
</html>
