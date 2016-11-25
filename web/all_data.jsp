<%-- 
    Document   : index
    Created on : 2008-12-16, 15:14:16
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.sql.*,javax.sql.*,javax.naming.*"%>
<%@page import="com.ff.reportgenerator.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/reports.css">
    </head>
    <body>
        <h1>Getting Data</h1>

        <table border="1">
            <thead>
                <tr>
                    <jsp:useBean id="delegate" scope="page" class="com.ff.reportgenerator.Delegate"/>
                    <%
        //delegate.generateReport();
        ArrayList records = delegate.queryDatabase();
        Iterator<DynamicRecord> it = records.iterator();
        boolean needHeader = true;
        while (it.hasNext()) {
            DynamicRecord dr = (DynamicRecord) it.next();
            if (needHeader) {
                ArrayList columns = dr.getColumns();
                Iterator<String> cit = columns.iterator();
                while (cit.hasNext()) {
                    %><th><%=cit.next()%></th><%
                }
                    %>
                </tr>
            </thead>
            <tbody>
                <%
                needHeader = false;
            }
                %><tr><%
            Collection values = dr.getValues();
            Iterator<String> vit = values.iterator();
            while (vit.hasNext()) {
                    %><td><%=vit.next()%></td><%
            }
                %></tr><%

        }

                %>
            </tbody>
        </table>

    </body>
</html>
