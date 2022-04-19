<%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/15/2022
  Time: 2:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <!--getmdl-select-->
    <link rel="stylesheet" href="styles/getmdl-select.min.css">
    <script defer src="js/getmdl-select.min.js"></script>
    <!-- styles -->
    <link rel="stylesheet" href="styles/login.css"></link>
</head>
<body>
<div class="mdl-layout mdl-js-layout main-div">

    <section class="container">
        <div class="mdl-card mdl-shadow--2dp through mdl-shadow--16dp">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h2 class="mdl-card__title-text">Login</h2>
            </div>

            <div class="mdl-card__supporting-text">
                <div class="error-div">
                    <span class="error-span"><%=session.getAttribute("notification") != null ? session.getAttribute("notification").toString() : ""%></span>
                </div>
                <form method="POST" action="?action=login">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label ">
                        <input class="mdl-textfield__input" id="name" name="name" autofocus required/>
                        <label class="mdl-textfield__label" for="name">Name</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label ">
                        <input class="mdl-textfield__input" id="surname" name="surname" required/>
                        <label class="mdl-textfield__label" for="surname">Surname</label>

                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label ">
                        <input class="mdl-textfield__input" id="cardNumber" name="cardNumber" required/>
                        <label class="mdl-textfield__label" for="cardNumber">Card number</label>
                    </div>

                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label getmdl-select getmdl-select__fix-height">
                        <input type="text" value="" class="mdl-textfield__input" id="cardType" readonly required>
                        <input type="hidden" value="" name="cardType">
                        <i class="mdl-icon-toggle__label material-icons">keyboard_arrow_down</i>
                        <label for="cardType" class="mdl-textfield__label">Card type</label>
                        <ul for="cardType" class="mdl-menu mdl-menu--bottom-left mdl-js-menu">
                            <li class="mdl-menu__item" data-val="VISA">VISA</li>
                            <li class="mdl-menu__item" data-val="MASTERCARD">MASTERCARD</li>
                            <li class="mdl-menu__item" data-val="AMERICAN_EXPRESS">AMERICAN_EXPRESS</li>
                        </ul>
                    </div>

<%--                    <label for="expirationDate" id="expDateLabel">Expiration date</label>--%>
<%--                    <div class="mdl-textfield mdl-js-textfield">--%>
<%--                        <input class="mdl-textfield__input" type="date" id="expirationDate" name="expirationDate"--%>
<%--                               required/>--%>
<%--                        <!-- <label class="mdl-textfield__label" for="expirationDate">Expiration date</label> -->--%>
<%--                    </div>--%>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label has-placeholder">
                        <input class="mdl-textfield__input" type="date" id="expirationDate" placeholder="" name="expirationDate"
                               required>
                        <label class="mdl-textfield__label" for="expirationDate">Expiration date</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label ">
                        <input class="mdl-textfield__input" type="password" id="pin" name="pin" required/>
                        <label class="mdl-textfield__label" for="pin">PIN</label>
                    </div>
                    <button type="submit"
                            class="mdl-cell mdl-cell--12-col mdl-button mdl-button--raised mdl-button--colored mdl-js-button mdl-js-ripple-effect mdl-color-text--white">
                        Login
                    </button>
                </form>
            </div>
        </div>
    </section>
</div>


</body>
</html>
