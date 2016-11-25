<%-- 
    Document   : create_define_chart
    Created on : Apr 14, 2010, 6:54:28 PM
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
        <h1>Hello World!</h1>
        <jsp:useBean id="delegate" scope="page" class="com.ff.reportgenerator.Delegate"/>
        <%
        String barFileName = delegate.createDefinedBarChart("Key_Sun_IPs", session, out);
        String barGraphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + barFileName;
        //String graphURL = delegate.createChart("segment", "Pie", request, out);
        %>
        <img src="<%= barGraphURL%>" width=500 height=300 border=0 usemap="#<%= barFileName%>"/>
    </body>
</html>
