<%@ page import="org.unibl.etf.admin_app.beans.VirtualVisitBean" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.sql.Time" %>
<%@ page import="org.apache.commons.io.FileUtils" %>
<%@ page import="org.apache.commons.io.FilenameUtils" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.io.File" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.FileWriter" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.time.LocalTime" %><%--
  Created by IntelliJ IDEA.
  User: legion
  Date: 3/24/2022
  Time: 4:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="visit_dialog.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="userBean" class="org.unibl.etf.admin_app.beans.UserBean" scope="session"></jsp:useBean>
<jsp:useBean id="visitService" class="org.unibl.etf.admin_app.service.VirtualVisitService"
             scope="application"></jsp:useBean>
<%--<jsp:useBean id="requestVisit" class="org.unibl.etf.admin_app.beans.VirtualVisitBean" scope="request"></jsp:useBean>--%>
<%--<jsp:setProperty name="requestVisit" property="*"/>--%>
<html>
<head>
    <title>Virtual visits</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <script src="js/dialog-polyfill.js"></script>
    <script src="js/visits.js"></script>
    <link rel="stylesheet" href="styles/dialog-polyfill.css"></link>
    <link rel="stylesheet" href="styles/navigation.css">
</head>
<%
    if (!(userBean.isLoggedIn())) response.sendRedirect("login.jsp");
    if (session.getAttribute("museumId") == null && request.getParameter("museum_id") == null)
        response.sendRedirect("museums.jsp");
    if (request.getParameter("museum_id") != null)
        session.setAttribute("museumId", request.getParameter("museum_id"));

    if (request.getParameter("delete_visit") != null) {
        if (!visitService.deleteVirtualVisit(request.getParameter("id"))) {
            session.setAttribute("notification", "Deleting virtual visit failed!");
            response.sendRedirect("error.jsp");
        }
    }

    if (request.getContentType() != null && request.getContentType().contains("multipart/form-data")) {

        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        VirtualVisitBean requestVisit = new VirtualVisitBean();
        HashMap<String, FileItem> fileItems = new HashMap<>();
        boolean ytVideoFound = false;
        boolean mp4VideoFound = false;
        try {
            for (FileItem fileItem : upload.parseRequest(request)) {
                String fieldName = fileItem.getFieldName();
                if (!fileItem.isFormField()) {
                    String filename = FilenameUtils.getName(fileItem.getName());
                    if (FilenameUtils.getExtension(filename).equals("mp4")) {
                        mp4VideoFound = true;
                    }
                    fileItems.put(filename, fileItem);
                } else {

                    if ("date".equals(fieldName))
                        requestVisit.setDate(Date.valueOf(fileItem.getString()));
                    else if ("time".equals(fieldName)) {
                        System.out.println(fileItem.getString());
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        long ms = sdf.parse(fileItem.getString()).getTime();
                        requestVisit.setStartTime(new Time(ms));
                    } else if ("duration".equals(fieldName))
                        requestVisit.setDuration(Integer.parseInt(fileItem.getString()));
                    else if ("price".equals(fieldName))
                        requestVisit.setPrice(Double.parseDouble(fileItem.getString()));
                    else if ("ytvideo".equals(fieldName)) {
                        ytVideoFound = true;
                        fileItems.put(fieldName, fileItem);
                    }
                }
            }

            if (!ytVideoFound && !mp4VideoFound)
                throw new Exception("No video uploaded!");
            requestVisit.setMuseumId((String) session.getAttribute("museumId"));
            if (visitService.addVirtualVisit(requestVisit)) {

                //visitService.insertArtifacts(fileItems, requestVisit.getId());
                File dir = new File("artifacts" + File.separator + requestVisit.getId());
                FileUtils.deleteDirectory(dir);
                dir.mkdirs();
                if (ytVideoFound) {
                    PrintWriter pw = new PrintWriter(new FileWriter(dir + File.separator + "video.yt"));
                    pw.println(fileItems.get("ytvideo").getString());
                    pw.close();
                    fileItems.remove("ytvideo");
                }
                for (HashMap.Entry<String, FileItem> entry : fileItems.entrySet()) {
                    File file = new File(dir + File.separator + entry.getKey());
                    System.out.println(file.getAbsolutePath());
                    entry.getValue().write(file);
                }
                System.out.println("odradio fajlove");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("notification", e.getMessage());
            response.sendRedirect("error.jsp");
        }
    } else System.out.println("nije add_visit");
%>

<body>
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

        <div style="margin-left: auto; margin-right: 0; padding : 10px">
            <button id="add-visit"
                    class="dialog-button fab-button mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect">
                <i class="material-icons">add</i>
            </button>
        </div>

        <div style="overflow-y: auto">
            <table style="width: 100%;" class="mdl-data-table mdl-js-data-table mdl-shadow--4dp">
                <thead>
                <tr>
                    <%--        <th class="mdl-data-table__cell--non-numeric">Edit</th>--%>
                    <th class="mdl-data-table__cell--non-numeric">Date</th>
                    <th class="mdl-data-table__cell--non-numeric">Time</th>
                    <th class="mdl-data-table__cell--non-numeric">Duration(min)</th>
                    <th class="mdl-data-table__cell--non-numeric">Price</th>
                    <th class="mdl-data-table__cell--non-numeric">Artifacts</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody id="client_tbody">
                <%
                    Date currentDate = new Date(new java.util.Date().getTime());
                    LocalTime currentTime = LocalTime.now();
                    Time currentTimePlusOneHour = new Time(new java.util.Date().toInstant().plus(Duration.ofHours(1)).toEpochMilli());
                    System.out.println(currentTimePlusOneHour);

                    for (VirtualVisitBean v : visitService.getVirtualVisitsOfMuseum((String) session.getAttribute("museumId"))) {

                %>
                <tr>
                    <td style="display: none;"><%= v.getId() %>
                    </td>
                    <%--        <%}%>--%>
                    <td class="mdl-data-table__cell--non-numeric"><%= v.getDate() %>
                    </td>
                    <td class="mdl-data-table__cell--non-numeric"><%= v.getStartTime()%>
                    </td>
                    <td class="mdl-data-table__cell--non-numeric"><%= v.getDuration() %>
                    </td>
                    <td class="mdl-data-table__cell--non-numeric"><%= v.getPrice()%>
                    </td>
                    <td class="mdl-data-table__cell--non-numeric">
                        <form action="artifacts.jsp" method="get">
                            <input type="hidden" name="visit_id" value=<%= v.getId() %>>
                            <button type="submit" class="mdl-button mdl-js-button mdl-button--icon">
                                <i class="material-icons">collections</i>
                            </button>
                        </form>
                    </td>

                    <% if (!(v.getDate().toLocalDate().isEqual(currentDate.toLocalDate()) && v.getStartTime().toLocalTime().isBefore(currentTime)
                            && (v.getStartTime().toLocalTime().plusMinutes(v.getDuration()).isAfter(currentTime)
                            || v.getStartTime().toLocalTime().plusMinutes(v.getDuration()).isAfter(LocalTime.MIDNIGHT)))) {
                    %>
                    <td>
                        <form action="visits.jsp" method="post">
                            <input type="hidden" name="id" value=<%= v.getId() %>>
                            <button type="submit" name="delete_visit"
                                    class="mdl-button mdl-js-button mdl-button--icon">
                                <i class="material-icons">delete</i>
                            </button>
                        </form>
                    </td>
                    <%} else {%>
                    <td></td>
                    <%}%>
                </tr>
                <%
                    }%>

                </tbody>
            </table>
        </div>
    </main>
</div>
</body>
</html>
