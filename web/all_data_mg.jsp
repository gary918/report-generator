<%-- 
    Document   : all_data_test
    Created on : 2014-5-5, 15:36:58
    Author     : gary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All Data MongoDB</title>
        <link rel="stylesheet" href="css/rg.css">
    </head>
    <script src="js/jquery-1.9.1.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            // alert("Gary calling");
            var _url = "http://localhost:8080/ReportGenerator/MongoDBServlet";
            jQuery.ajax({
                url: _url,
                type: "get",
                dataType: "json",
                success: function(d) {
                    //alert(JSON.stringify(d));
                    //动态生成table
                    var mongotable = $("<table border=\"1\"></table>");
                    mongotable.appendTo("#testtable");
                    var number = 0;
                    for (var o in d) {
                        number++;
                        //生成tr,添加到table中
                        var tr = $("<tr></tr>");
                        tr.appendTo(mongotable);
                        //生成td
                        var td_num = $("<td width=\"5\">" + number + "</td>");
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
                    //alert($("#testtable").html());
                }
            });
        })
    </script>
    <body>
        <div>All Data MongoDB</div>
        <div id="testtable"></div>
    </body>

</html>
