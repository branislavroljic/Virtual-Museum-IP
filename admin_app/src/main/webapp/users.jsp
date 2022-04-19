<%@ page import="org.unibl.etf.admin_app.beans.UserBean" %>
<%@ page import="org.unibl.etf.admin_app.beans.enums.UserRole" %>
<%@ page import="org.unibl.etf.admin_app.beans.enums.UserStatus" %>
<%@ page import="org.unibl.etf.admin_app.service.UserService" %>
<%@ page import="java.util.Enumeration" %>

<%@include file="users_dialog.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/13/2022
  Time: 5:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="userBean" class="org.unibl.etf.admin_app.beans.UserBean" scope="session"></jsp:useBean>
<jsp:useBean id="userService" class="org.unibl.etf.admin_app.service.UserService" scope="application"></jsp:useBean>
<jsp:useBean id="requestUser" class="org.unibl.etf.admin_app.beans.UserBean" scope="request"></jsp:useBean>
<jsp:setProperty name="requestUser" property="*"/>
<%@page errorPage="error.jsp" %>


<html>
<head>
    <title>Users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <script src="js/users.js"></script>
    <script src="js/dialog-polyfill.js"></script>
    <link rel="stylesheet" href="styles/dialog-polyfill.css"></link>
    <link rel="stylesheet" href="styles/navigation.css"></link>
</head>


<%
    if (!(userBean.isLoggedIn())) response.sendRedirect("login.jsp");

    if (request.getParameter("add_user") != null) {

        if (!userService.addUser(requestUser)) {
            session.setAttribute("notification", "Adding user failed!");
            response.sendRedirect("error.jsp");
        }
    } else if (request.getParameter("edit_user") != null) {

        if (!userService.editUser(requestUser)) {
            session.setAttribute("notification", "Editing user failed!");
            response.sendRedirect("error.jsp");
        }
    }

    if(request.getParameter("delete") != null){
        if(!userService.deleteUser(request.getParameter("id"))){
            session.setAttribute("notification", "Deleting user failed!");
            response.sendRedirect("error.jsp");
        }
    }


    if (request.getParameter("toggle_status") != null) {

        if (!userService.updateUserStatus(request.getParameter("id"))) {
            session.setAttribute("notification", "Updating user's status failed!");
            response.sendRedirect("error.jsp");
        }
    }

    if(request.getParameter("reset_password") != null){
            if(!userService.resetPassword(request.getParameter("id"))){
                session.setAttribute("notification", "Updating user's status failed!");
                response.sendRedirect("error.jsp");
            }
    }
%>

<body>

<%--TOAST--%>
<div aria-live="assertive" aria-atomic="true" aria-relevant="text" class="mdl-snackbar mdl-js-snackbar">
    <div class="mdl-snackbar__text"></div>
    <button type="button" class="mdl-snackbar__action"></button>
</div>



<div class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer
                mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">Users</span>
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
        <div class="page-content" style="display: flex; flex-direction: column;">
            <div style="margin-left: auto; margin-right: 0; padding : 10px">
            <button id="add-client"
                    class="dialog-button fab-button mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect">
                <i class="material-icons">add</i>
            </button>
            </div>
            <div style="overflow-y: auto">
            <table style="width: 100%" class="mdl-data-table mdl-js-data-table mdl-shadow--4dp">
                <thead>
                <tr>
                    <th class="mdl-data-table__cell--non-numeric">Edit</th>
                    <th class="mdl-data-table__cell--non-numeric">Username</th>
                    <th class="mdl-data-table__cell--non-numeric">Name</th>
                    <th class="mdl-data-table__cell--non-numeric">Surname</th>
                    <th class="mdl-data-table__cell--non-numeric">E-mail</th>
                    <th class="mdl-data-table__cell--non-numeric">Role</th>
                    <th class="mdl-data-table__cell--non-numeric" style="text-align: left; ">Status</th>
                    <th>Reset Password</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody id="client_tbody">
                <% for (UserBean user : userService.getUsers()) {
                    if (user.equals(userBean)) //skip this admin
                        continue;
                %>
                <tr>
                    <td style="display: none;"><%= user.getId() %></td>
                    <td class="mdl-data-table__cell--non-numeric">
                        <button type="button" onclick="editClient(this)" class="mdl-button mdl-js-button mdl-button--icon">
                            <i class="material-icons">edit</i>
                        </button>
                    </td>
                    <td class="mdl-data-table__cell--non-numeric"><%= user.getUsername()%></td>
                    <td class="mdl-data-table__cell--non-numeric"><%= user.getName()%></td>
                    <td class="mdl-data-table__cell--non-numeric"><%= user.getSurname() %></td>
                    <td class="mdl-data-table__cell--non-numeric"><%=user.getEmail()%></td>
                    <td class="mdl-data-table__cell--non-numeric"><%= user.getRole() %></td>

                    <td>
                        <form action="users.jsp" method="post" style="max-width: 18%;">
                            <input type="hidden" name="id" value=<%= user.getId() %>>
                            <input type="hidden" name="toggle_status" value=<%= user.getId() %>>
                            <% if (user.getStatus() == UserStatus.REQUESTED) {%>
                            <button type="submit" class="mdl-button mdl-js-button mdl-js-ripple-effect" style="color: #2041b7;">ACCEPT REQUEST</button>
                            <% } else {%>

                            <label style="max-width: 18%" onchange="this.form.submit()" class="mdl-switch mdl-js-switch mdl-js-ripple-effect" for=<%="chb" + user.getId()%>>

                                <input type="checkbox" id=<%="chb" + user.getId()%> class="mdl-switch__input"
                                    <%=user.getStatus() == UserStatus.ACTIVE ? "checked='checked'" : "" %>/>
                                <span class="mdl-switch__label" style="color: <%=user.getStatus() == UserStatus.ACTIVE ? "red" : "green"%>"><%=user.getStatus() == UserStatus.ACTIVE ? "BLOCK" : "ACTIVATE" %></span>
                            </label>
                            <% } %>
                        </form>
                    </td>
                    <td>
                        <form action="users.jsp" method="post" onsubmit="return confirm('Do you really want to reset password from user: <%= user.getUsername()%>');">
                            <input type="hidden" name="id" value=<%= user.getId() %>>
                            <button type="submit" name="reset_password"
                                    class="mdl-button mdl-js-button mdl-button--icon">
                                <i class="material-icons">lock_reset</i>
                            </button>
                        </form>
                    </td>
                    <td>
                        <form action="users.jsp" method="post" onsubmit="return confirm('Do you really want to delete user: <%= user.getUsername()%>');">
                            <input type="hidden" name="id" value=<%= user.getId() %>>
                            <button type="submit" name="delete"
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
            </div>

        </div>
    </main>
</div>


</body>
</html>
