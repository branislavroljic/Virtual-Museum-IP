<%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/13/2022
  Time: 7:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="display: flex; justify-content: center">
    <h3 style="color: red">An error occurred!</h3>
    <p><%= exception.getMessage() %></p>
    <p><%=session.getAttribute("notification")!=null?session.getAttribute("notification").toString():""%></p>
</body>
</html>
