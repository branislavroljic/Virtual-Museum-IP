<%@ page import="org.unibl.etf.admin_app.beans.MuseumBean" %>
<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/23/2022
  Time: 11:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="museum_dialog.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="userBean" class="org.unibl.etf.admin_app.beans.UserBean" scope="session"></jsp:useBean>
<jsp:useBean id="museumService" class="org.unibl.etf.admin_app.service.MuseumService" scope="application"></jsp:useBean>
<jsp:useBean id="requestMuseum" class="org.unibl.etf.admin_app.beans.MuseumBean" scope="request"></jsp:useBean>
<jsp:setProperty name="requestMuseum" property="*"/>
<html>
<head>
    <title>Museums</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <script src="js/dialog-polyfill.js"></script>
    <script src="js/museum_dialog.js"></script>
    <script src="js/museums.js"></script>
    <link rel="stylesheet" href="styles/dialog-polyfill.css"></link>
    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/museums.css">
</head>

<%
    if (!(userBean.isLoggedIn())) response.sendRedirect("login.jsp");

    Enumeration<String> enumeration = request.getParameterNames();
    System.out.println("ovo: " + (request.getParameter("delete-museum") != null));

    if (request.getParameter("add_museum") != null) {

        if (!museumService.addMuseum(requestMuseum)) {
            session.setAttribute("notification", "Adding museum failed!");
            response.sendRedirect("error.jsp");
        }
    } else if (request.getParameter("edit_museum") != null) {

        if (!museumService.editMuseum(requestMuseum)) {
            session.setAttribute("notification", "Editing museum failed!");
            response.sendRedirect("error.jsp");
        }
    } else if (request.getParameter("delete-museum") != null) {
        System.out.println("Bio u delete museum");
        if (!museumService.deleteMuseum(request.getParameter("id"))) {
            System.out.println("Obrisao museum");
            session.setAttribute("notification", "Deleting museum failed!");
            response.sendRedirect("error.jsp");
        }
    }
%>
<body>

<div class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer
                mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">Museums</span>
        </div>
    </header>
    <div class="mdl-layout__drawer">
        <span class="mdl-layout-title">Menu</span>
        <nav class="mdl-navigation">
            <a class="mdl-navigation__link" style="color: black" href="home.jsp"><i class="material-icons">home</i>Home</a>
            <a class="mdl-navigation__link" style="color: black" href="users.jsp"><i class="material-icons">person</i>Users</a>
            <a class="mdl-navigation__link" style="color: black" href="museums.jsp"><i class="material-icons">museum</i>Museums</a>
            <a class="mdl-navigation__link " style="color: black" href="logs.jsp"><i class="material-icons">article</i>Logs</a>
        </nav>
    </div>
    <main class="mdl-layout__content">

        <div style="margin-left: auto; margin-right: 0; padding : 10px">
            <button id="add-museum"
                    class="dialog-button fab-button mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect">
                <i class="material-icons">add</i>
            </button>
        </div>
        <table class="mdl-data-table mdl-js-data-table mdl-shadow--4dp" id="museums_table">
            <thead>
            <tr>
                <th class="mdl-data-table__cell--non-numeric">Edit</th>
                <th class="mdl-data-table__cell--non-numeric">Name</th>
                <th class="mdl-data-table__cell--non-numeric">Address</th>
                <th class="mdl-data-table__cell--non-numeric">Phone</th>
                <th class="mdl-data-table__cell--non-numeric">City</th>
                <th class="mdl-data-table__cell--non-numeric">Country</th>
                <th class="mdl-data-table__cell--non-numeric">Type</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody id="client_tbody">
            <% for (MuseumBean mb : museumService.getMuseums()) {

                System.out.println("muzej je : " + mb);
            %>
            <tr>
                <td style="display: none;"><%= mb.getId() %>
                </td>
                <td class="mdl-data-table__cell--non-numeric">
                    <button type="button" onclick="event.stopPropagation(); editMuseum(this)"
                            class="mdl-button mdl-js-button mdl-button--icon edit-button">
                        <i class="material-icons">edit</i>
                    </button>
                </td>
                <td class="mdl-data-table__cell--non-numeric"><%= mb.getName()%>
                </td>
                <td class="mdl-data-table__cell--non-numeric"><%= mb.getAddress()%>
                </td>
                <td class="mdl-data-table__cell--non-numeric"><%= mb.getTel()%>
                </td>
                <td class="mdl-data-table__cell--non-numeric"><%= mb.getCity()%>
                </td>
                <td class="mdl-data-table__cell--non-numeric"><%= mb.getCountry()%>
                </td>
                <td class="mdl-data-table__cell--non-numeric"><%= mb.getType()%>
                </td>
                <%--        <% } %>--%>
                <td>
                    <form action="museums.jsp" method="post">
                        <input type="hidden" name="id" value=<%= mb.getId() %>>
                        <button type="submit" name="delete-museum"
                                class="mdl-button mdl-js-button mdl-button--icon">
                            <i class="material-icons">delete</i>
                        </button>
                    </form>
                </td>
            </tr>
            <%
                }%>

            </tbody>
        </table>
        <div id="map"></div>

        <!-- Async script executes immediately and must be after any DOM elements used in callback. -->
        <script
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB6Oob6m_puuaRHE9VmEsjiqa4MfUoOMUY&callback=initMap&v=weekly"
                async
        ></script>


    </main>
</div>
</body>
</html>
