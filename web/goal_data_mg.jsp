<%-- 
    Document   : goal_data_mg
    Created on : 2014-5-23, 14:05:38
    Author     : gary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Goal Data</title>
        <link rel="stylesheet" href="css/rg.css">
        <script src="js/jquery-1.9.1.js"></script>
        
        <script type="text/javascript">

            function search(conditions, tableName) {
                //alert("Gary calling");
                var _url = "http://localhost:8080/ReportGenerator/MongoDBServlet?" + conditions;
                jQuery.ajax({
                    url: _url,
                    type: "get",
                    data: $("form").serialize(),
                    dataType: "json",
                    success: function(d) {
                        //alert(JSON.stringify(d));
                        //动态生成table
                        var mongotable = $("<table class=\"resultTable\"></table>");
                        mongotable.appendTo("#" + tableName);
                        var number = 0;
                        for (var o in d) {
                            number++;
                            //生成tr,添加到table中
                            var tr = $("<tr></tr>");
                            tr.appendTo(mongotable);
                            //生成td
                            var td_num = $("<td>" + number + "</td>");
                            var td_id = $("<td width=\"150\"><a href=\"http://twiki.us.oracle.com/bin/view/ISVeProjects/" + d[o].PROJECT_ID + "\">" + d[o].PROJECT_ID + "</a></td>");
                            var td_name = $("<td width=\"300\">" + d[o].Project_Name + "</td>");
                            var td_segment = $("<td width=\"150\">" + d[o].Segment + "</td>");
                            var td_city = $("<td width=\"150\">" + d[o].Project_Type + "</td>");
                            var td_goal = $("<td width=\"300\">" + d[o].ISVe_Goal + "</td>");
                            var td_code = $("<td width=\"150\">" + d[o].Execution_End + "</td>");

                            //把td添加到tr中
                            td_num.appendTo(tr);
                            td_id.appendTo(tr);
                            td_name.appendTo(tr);
                            td_segment.appendTo(tr);
                            td_city.appendTo(tr);
                            td_goal.appendTo(tr);
                            td_code.appendTo(tr);
                        }
                        //alert($("#complete_table").html());
                    }
                });
            }

            search("Project_Phase=Complete", "complete_table");
            search("Project_Phase=Ongoing", "ongoing_table");
        </script>
    </head>
    <body>
        <h2>Completed</h2>
        <div id="complete_table"></div>
        <h2>Ongoing</h2>
        <div id="ongoing_table"></div>
    </body>
</html>
