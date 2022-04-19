<%@ page import="org.unibl.etf.admin_app.beans.LogBean" %>
<%@ page import="com.itextpdf.text.Document" %>
<%@ page import="com.itextpdf.text.pdf.PdfWriter" %>
<%@ page import="com.itextpdf.text.pdf.PdfPTable" %>
<%@ page import="com.itextpdf.text.pdf.PdfPCell" %>
<%@ page import="com.itextpdf.text.Phrase" %>
<%@ page import="com.itextpdf.text.DocumentException" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.InputStream" %><%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/26/2022
  Time: 7:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="userBean" class="org.unibl.etf.admin_app.beans.UserBean" scope="session"></jsp:useBean>
<jsp:useBean id="infoService" class="org.unibl.etf.admin_app.service.InfoService" scope="application"></jsp:useBean>
<html>
<head>
    <title>Logs</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>

    <%--    css--%>
    <link rel="stylesheet" href="styles/logs.css">
    <link rel="stylesheet" href="styles/navigation.css">
</head>
<%
    if (!(userBean.isLoggedIn())) response.sendRedirect("login.jsp");


    if(request.getParameter("download") != null){

        File pdfFile = new File(application.getRealPath("/WEB-INF/logs.pdf"));
        try {
            infoService.saveLogsToPDF(pdfFile);

            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + pdfFile.getName() + "\"");

            FileInputStream  in = new FileInputStream(pdfFile);
            int i;
            while ((i=in.read()) != -1) {
                out.write(i);
            }
            in.close();
            out.close();
        } catch (DocumentException e) {
            session.setAttribute("notification", "An error occurred while downloading PDF!");
            response.sendRedirect("error.jsp");
        }


    }
%>
<body>

<div class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer
                mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">Logs</span>
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

<form action="logs.jsp" method="get">
    <div style="margin-left: auto; margin-right: 0; padding : 10px">
    <button type="submit" name="download"
            class="dialog-button fab-button mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect">
        <i class="material-icons">download</i>
    </button>
    </div>
</form>

<table class="mdl-data-table mdl-js-data-table mdl-shadow--4dp mdl-data-table__header--sorted-descending">
    <thead>
    <tr>
        <th class="mdl-data-table__cell--non-numeric">Type</th>
        <th class="mdl-data-table__cell--non-numeric">Message</th>
        <th class="mdl-data-table__cell--non-numeric">Date and time</th>
    </tr>
    </thead>
    <tbody id="client_tbody">
    <% for (LogBean log : infoService.getLogs()) {
    %>
    <tr onclick="museumClick(this)">
        <td class="mdl-data-table__cell--non-numeric"><%= log.getType() %></td>
        <td class="mdl-data-table__cell--non-numeric"><%= log.getMessage() %></td>
        <td class="mdl-data-table__cell--non-numeric"><%= log.getDateTime() %></td>
    </tr>
    <%
        }%>

    </tbody>
</table>
    </main>
</div>
</body>
</html>
