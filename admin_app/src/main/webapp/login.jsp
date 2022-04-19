<%@ page import="org.unibl.etf.admin_app.beans.UserBean" %><%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/13/2022
  Time: 5:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="userBean" class="org.unibl.etf.admin_app.beans.UserBean" scope="session"></jsp:useBean>
<jsp:useBean id="userService" class="org.unibl.etf.admin_app.service.UserService" scope="application"></jsp:useBean>

<%
    String token = null;
    if((token = request.getParameter("token")) != null){
        UserBean user = userService.loginUser(token);
        if(user != null){
            userBean.setId(user.getId());
            userBean.setUsername(user.getUsername());
            userBean.setLoggedIn(true);
            session.setAttribute("notification", "");
            response.sendRedirect("users.jsp");
        }else{
            session.setAttribute("notification", "Invalid login! Token is invalid!");
        }
    }else {
        session.setAttribute("notification", "Invalid login! Token parameter not found!");
    }
%>

<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2 style="color:red;"><%= session.getAttribute("notification")%></h2>
</body>
</html>
