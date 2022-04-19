<%@ page import="org.unibl.etf.virtual_bank_jsp_m2.models.BankAccount" %>
<%@ page import="org.unibl.etf.virtual_bank_jsp_m2.models.Transaction" %><%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/15/2022
  Time: 3:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="account" type="org.unibl.etf.virtual_bank_jsp_m2.models.BankAccount" scope="session"></jsp:useBean>
<jsp:useBean id="transactions" type="java.util.List<org.unibl.etf.virtual_bank_jsp_m2.models.Transaction>"
             scope="session"></jsp:useBean>
<html>
<head>
    <title>Bank</title>
    <title>Lab 3 - 03</title>
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>

    <script src="js/home.js"></script>
    <link rel="stylesheet" href="styles/home.css"/>
    <link rel="stylesheet" href="styles/navigation.css"/>
</head>
<body>

<div class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer
                mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">Account</span>
        </div>
    </header>
    <div class="mdl-layout__drawer">
        <span class="mdl-layout-title">Menu</span>
        <nav class="mdl-navigation">
            <a class="mdl-navigation__link" style="color: black" href="?action=home">
                <i class="material-icons">person</i>Account</a>
            <a class="mdl-navigation__link " style="color: black" href="?action=transactions"><i class="material-icons">article</i>Transactions</a>
            <a class="mdl-navigation__link" style="color: black" href="?action=logout"><i
                    class="material-icons">logout</i>Logout</a>
        </nav>
    </div>
    <main class="mdl-layout__content">
        <div class="content">
            <div class="demo-card-square mdl-card mdl-shadow--2dp">
                <div class="mdl-card__title mdl-card--expand">
                    <i class="material-icons card-icon" style="height: 100%">account_circle</i>
                    <%--                <% if(account.getBlocked()) {%>--%>
                    <h4 id="blockedHeader">BLOCKED</h4>
                    <%--                <% } %>--%>
                </div>
                <div class="mdl-card__supporting-text">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label has-placeholder">
                        <input class="mdl-textfield__input" type="text" id="name" placeholder=""
                               value=<%= account.getName()%>>
                        <label class="mdl-textfield__label" for="name">Name</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label has-placeholder">
                        <input class="mdl-textfield__input" type="text" id="surname" placeholder=""
                               value=<%= account.getSurname()%>>
                        <label class="mdl-textfield__label" for="surname">Surname</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label has-placeholder">
                        <input class="mdl-textfield__input" type="text" id="cardNumber" placeholder=""
                               value=<%= account.getCardNumber()%>>
                        <label class="mdl-textfield__label" for="cardNumber">Card number</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label has-placeholder">
                        <input class="mdl-textfield__input" type="text" id="cardType" placeholder=""
                               value=<%= account.getCardType().toString()%>>
                        <label class="mdl-textfield__label" for="cardType">Card type</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label has-placeholder">
                        <input class="mdl-textfield__input" type="text" id="expirationDate" placeholder=""
                               value=<%= account.getExpirationDate()%>>
                        <label class="mdl-textfield__label" for="expirationDate">Expiration date</label>
                    </div>

                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable has-placeholder">
                        <label class="mdl-button mdl-js-button mdl-button--icon" for="password">
                            <i class="material-icons" onclick="toggle()">visibility</i>
                        </label>
                        <div class="mdl-textfield__expandable-holder" style="padding-left: 10px">
                            <input class="mdl-textfield__input" type="password" id="password" placeholder="" value=<%= account.getPin()%>
                                   readonly>
                            <label class="mdl-textfield__label" for="password">Expandable Input</label>
                        </div>
                    </div>
                    <!-- <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label has-placeholder">
                      <input class="mdl-textfield__input" type="password" id="pin" placeholder="">
                      <label class="mdl-textfield__label" for="pin">PIN</label>
                      <label class="mdl-button mdl-js-button mdl-button--icon" for="sample6">
                          <i class="material-icons" onclick="toggle()" >visibility</i>
                        </label>

                    </div> -->
                </div>
                <div class="mdl-card__actions mdl-card--border">
                    <%--                <% if(account.getBlocked()) {%>--%>
                    <button class="mdl-button mdl-js-button mdl-js-ripple-effect" style="color: green"
                            id="enable-account">
                        Enable
                    </button>
                    <%--                <%} else { %>--%>
                    <button class="mdl-button mdl-js-button mdl-js-ripple-effect" id="block-account"
                            style="color: red">
                        Block
                    </button>
                    <%--                <% } %>--%>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>
