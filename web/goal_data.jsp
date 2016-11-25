<%-- 
    Document   : goal_data
    Created on : 2013-11-20, 9:43:43
    Author     : gary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.sql.*,javax.sql.*,javax.naming.*"%>
<%@page import="com.ff.reportgenerator.*"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Goal Data</title>
        <link rel="stylesheet" href="css/reports.css">
    </head>
    <body>
        <%-- Quick links --%>
        <h2>|<%
            String goalName;
            for (int goalNumber = 0; goalNumber < 6; goalNumber++) {
                goalName = "14-[" + goalNumber + "]";
            %><a href="#<%=goalName%>"><%=goalName%></a>|<%
                }
            %><a href="#Performance">Performance|</a><a href="#Monthly Summary">Monthly Summary|</a>
        </h2>
        <jsp:useBean id="delegate" scope="page" class="com.ff.reportgenerator.Delegate"/>
        <%            int lineNumber = 0; // line number
            for (int goalNumber = 0; goalNumber < 6; goalNumber++) {
                goalName = "14-[" + goalNumber + "]";
        %>


        <a name="<%=goalName%>"></a><h1><%=goalName%></h1>
        <h2>Completed</h2>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%
                            ArrayList records = delegate.queryGroups(goalName, "Completed", "SEGMENT");
                            Iterator<DynamicRecord> it = records.iterator();
                            boolean needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryProjects(goalName, "Completed");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%
                        if (value.startsWith("ISVeProject")) {
                        %>
                        <a href="<%=Utility.PROJECT_URL_PREFIX + value%>"><%=value%></a>
                        <%
                        } else {%>
                        <%=value%><%
                            }
                        %></td><%
                            }%></tr><%
                        }

                    %>
            </tbody>
        </table>

        <h2>Onging</h2>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryGroups(goalName, "Onging", "SEGMENT");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryProjects(goalName, "Onging");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%
                        if (value.startsWith("ISVeProject")) {
                        %>
                        <a href="<%=Utility.PROJECT_URL_PREFIX + value%>"><%=value%></a>
                        <%
                        } else {%>
                        <%=value%><%
                            }
                        %></td><%
                            }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <%} // for loop

            goalName = "Performance";
        %>


        <a name="<%=goalName%>"></a><h1><%=goalName%></h1>
        <h2>Completed</h2>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%
                            ArrayList records = delegate.queryGroups(goalName, "Completed", "ISV_COMMUNITY_NAME");
                            Iterator<DynamicRecord> it = records.iterator();
                            boolean needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            it = null;
                            delegate.queryGroups(goalName, "Completed", "SEGMENT");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryProjects(goalName, "Completed");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%
                        if (value.startsWith("ISVeProject")) {
                        %>
                        <a href="<%=Utility.PROJECT_URL_PREFIX + value%>"><%=value%></a>
                        <%
                        } else {%>
                        <%=value%><%
                            }
                        %></td><%
                            }%></tr><%
                        }

                    %>
            </tbody>
        </table>

        <h2>Onging</h2>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryGroups(goalName, "Onging", "SEGMENT");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryGroups(goalName, "Onging", "ISV_COMMUNITY_NAME");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryProjects(goalName, "Onging");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%
                        if (value.startsWith("ISVeProject")) {
                        %>
                        <a href="<%=Utility.PROJECT_URL_PREFIX + value%>"><%=value%></a>
                        <%
                        } else {%>
                        <%=value%><%
                            }
                        %></td><%
                            }%></tr><%
                        }

                    %>
            </tbody>
        </table>

        <% goalName = "14-[";%>    
        <a name="Monthly Summary"></a><h1>Monthly Summary</h1>
        <h2>Completed</h2>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%
                            records = delegate.queryGroups(goalName, "Completed", "ISV_COMMUNITY_NAME");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryGroups(goalName, "Completed", "SEGMENT");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryProjects(goalName, "Completed");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%
                        if (value.startsWith("ISVeProject")) {
                        %>
                        <a href="<%=Utility.PROJECT_URL_PREFIX + value%>"><%=value%></a>
                        <%
                        } else {%>
                        <%=value%><%
                            }
                        %></td><%
                            }%></tr><%
                        }

                    %>
            </tbody>
        </table>

        <h2>Onging</h2>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryGroups(goalName, "Onging", "SEGMENT");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryGroups(goalName, "Onging", "ISV_COMMUNITY_NAME");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%=value%></td><%
                        }%></tr><%
                        }

                    %>
            </tbody>
        </table>
        <br>
        <table border="1" cellpadding="0" cellspacing="0">
            <thead>
                <tr bgcolor="gray">
                        <%                            records = delegate.queryProjects(goalName, "Onging");
                            it = records.iterator();
                            needHeader = true;
                            lineNumber = 0;
                            while (it.hasNext()) {
                                DynamicRecord dr = (DynamicRecord) it.next();
                                if (needHeader) {
                                    %><th>#</th><%
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
                %><tr><td><%=++lineNumber%></td><%
                    Collection values = dr.getValues();
                    Iterator<String> vit = values.iterator();
                    while (vit.hasNext()) {
                        String value = (String) vit.next();
                    %><td><%
                        if (value.startsWith("ISVeProject")) {
                        %>
                        <a href="<%=Utility.PROJECT_URL_PREFIX + value%>"><%=value%></a>
                        <%
                        } else {%>
                        <%=value%><%
                            }
                        %></td><%
                            }%></tr><%
                        }

                    %>
            </tbody>
        </table>
    </body>
</html>