<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/26/2022
  Time: 1:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="userBean" class="org.unibl.etf.admin_app.beans.UserBean" scope="session"></jsp:useBean>
<jsp:useBean id="visitService" class="org.unibl.etf.admin_app.service.VirtualVisitService"
             scope="application"></jsp:useBean>
<html>
<head>
    <title>Artifacts</title>
    <link rel="stylesheet" href="styles/artifacts.css">
</head>

<%
    if (!(userBean.isLoggedIn())) response.sendRedirect("login.jsp");

    if (request.getParameter("visit_id") == null) response.sendRedirect("visits.jsp");
%>
<body>
<div class="container">

    <%
        Map<String, List<String>> artifacts = visitService.getArtifactsPaths(request.getParameter("visit_id"));
        for (String imgPath : artifacts.get("img")) { %>
        <img class="item" src="<%= imgPath %>" alt="Image" >
    <% } %>
    <% if (artifacts.containsKey("mp4")) {%>
    <video width="320" height="240" autoplay="autoplay" controls="controls">
        <source src="<%= artifacts.get("mp4") %>" type="video/mp4">
    </video>
    <% } else if (artifacts.containsKey("yt")) {%>
    <iframe width="320" height="240"
            src="<%= artifacts.get("yt") %>">
    </iframe>
    <% }%>
</div>
</body>
</html>
