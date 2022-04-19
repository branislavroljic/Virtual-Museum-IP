<%@ page import="org.unibl.etf.admin_app.beans.ReportBean" %><%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/26/2022
  Time: 7:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="userBean" class="org.unibl.etf.admin_app.beans.UserBean" scope="session"></jsp:useBean>
<jsp:useBean id="infoService" class="org.unibl.etf.admin_app.service.InfoService" scope="application"></jsp:useBean>
<jsp:useBean id="userService" class="org.unibl.etf.admin_app.service.UserService" scope="application"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/home.css">
</head>
<%
    if (!(userBean.isLoggedIn())) response.sendRedirect("login.jsp");
%>
<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer
                mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">Home</span>
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
        <div>
            <div class="statistics-div">
                <div class="info-div"><span class="static-span"> Number of logged in: </span>  <%= infoService.countRegisteredUsers() %></div>

                <div class="info-div"><span class="static-span">  Number of registered: </span> <%= infoService.countLoggedInUsers() %></div>
            </div>

            <div style="width: 90%; margin: auto">
                <canvas id="line-chart"></canvas>
            </div>
            <script>
                let ctx = document.getElementById("line-chart");
                const lineChart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: [
                            <% for(ReportBean report : infoService.getLoginsByHourFromLast24Hour()) { %>
                            <%= "'" + report.getDate() + " at "  + report.getHour() + "h', "%>
                            <% }%>
                        ],
                        datasets: [{
                            label: '# of logins for the last 24 fours',
                            data: [
                                <% for(ReportBean report : infoService.getLoginsByHourFromLast24Hour()) {%>
                                <%= report.getCount()  + ", " %>
                                <%}%>
                            ],
                            borderWidth: 1,
                            backgroundColor: "blue",
                            borderColor: "lightblue",
                            lineTension: 0,
                            radius: 5
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
            </script>
        </div>
    </main>
</div>
</body>
</html>
