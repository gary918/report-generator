<%-- 
    Document   : create_chart
    Created on : Jan 19, 2010, 4:01:45 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Create Chart</h1>
        <jsp:useBean id="delegate" scope="page" class="com.ff.reportgenerator.Delegate"/>
        <%
        String fileName = delegate.createChart("segment", "Pie", session, out);
        String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
        //String graphURL = delegate.createChart("segment", "Pie", request, out);
        %>
        <img src="<%= graphURL%>" width=500 height=300 border=0 usemap="#<%= fileName%>"/>
        <br>
        <%
        String barFileName = delegate.createDefinedBarChart("Key_Sun_IPs", session, out);
        String barGraphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + barFileName;
        //String graphURL = delegate.createChart("segment", "Pie", request, out);
        %>
        <img src="<%= barGraphURL%>" width=500 height=300 border=0 usemap="#<%= barFileName%>"/>

    </body>
</html>
