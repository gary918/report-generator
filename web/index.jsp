<%-- 
    Document   : index
    Created on : Dec 17, 2009, 11:19:37 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "java.io.PrintWriter" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporter</title>
    </head>
    <body>
        <h1>Report Generator</h1>
        <jsp:useBean id="delegate" scope="page" class="com.ff.reportgenerator.Delegate"/>
        <%
        delegate.generateReport();


        PrintWriter pw = new PrintWriter(out);
        String fileName = com.ff.reportgenerator.Reporter.createPieChart(session,pw);
        String graphURL = request.getContextPath()+"/servlet/DisplayChart?filename="+fileName;
        //String fileName ="file";
        //String graphURL ="WWW";
        //com.ff.reportgenerator.Reporter.createPieChart(request, response);

                %>
<img src="<%= graphURL %>" width=500 height=300 border=0 usemap="#<%= fileName %>"/>
    </body>
    /usr/local/mysql/bin/mysqld -u root<br>
        
</html>
