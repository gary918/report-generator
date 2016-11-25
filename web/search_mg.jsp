<%-- 
    Document   : search
    Created on : 2014-5-7, 16:19:18
    Author     : gary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
        <link rel="stylesheet" href="css/rg.css">

        <script src="js/jquery-1.9.1.js"></script>

        <script type="text/javascript">

            function search() {
                //alert("Gary calling");
                $("#testtable").empty(); // clear the result table
                var _url = "http://" + location.host + "/ReportGenerator/MongoDBServlet";
                jQuery.ajax({
                    url: _url,
                    type: "get",
                    data: $("form").serialize(),
                    dataType: "json",
                    success: function(d) {
                        //alert(JSON.stringify(d));
                        //动态生成table
                        var mongotable = $("<table id=\"mytable\" border=\"1\"></table>");
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
            }

            function selectElementContents(el) {
                var body = document.body, range, sel;
                if (document.createRange && window.getSelection) {
                    range = document.createRange();
                    sel = window.getSelection();
                    sel.removeAllRanges();
                    try {
                        range.selectNodeContents(el);
                        sel.addRange(range);
                    } catch (e) {
                        range.selectNode(el);
                        sel.addRange(range);
                    }
                } else if (body.createTextRange) {
                    range = body.createTextRange();
                    range.moveToElementText(el);
                    range.select();
                }
            }
        </script>
    </head>

    <body>
        <h1>Search</h1>
        <form>
            <table id="userInput">
                <tbody>
                    <tr>
                        <td>Segment:
                            <select id="Segment" name="Segment">
                                <option value="All">All</option>
                                <option value="Telco">Telco</option>
                                <option value="Financial">FSI</option>
                                <option value="Horizontal">Horizontal</option>
                            </select>
                        </td>
                        <td>ISVe_Goal:
                            <select id="ISVe_Goal" name="ISVe_Goal">
                                <option value="All">All</option>
                                <%--
                                <option value="15-\[0\]">15-[0]</option>
                                <option value="15-\[1\]">15-[1]T5/M6/SC Proof Points</option>
                                <option value="15-\[2\]">15-[2]Deepen Relationship with Top ISVs</option>
                                <option value="15-\[3.1\]">15-[3.1]M7 & SiS Platform Support</option>
                                <option value="15-\[3.2\]">15-[3.2]Solaris & Studio Platform Support</option>
                                
                                <option value="16-0">16-0 ISV Engineering</option>
                                <option value="16-1">16-1 SPARC Growth</option>
                                <option value="16-2">16-2 Deepen Relationship with Top ISVs</option>
                                <option value="16-3.1">16-3.1 SPARC & SWiS Platform Support</option>
                                <option value="16-3.2">16-3.2 Solaris & Studio Platform Support</option>
                                <option value="16-4">16-4 Security</option>
                                --%>
                                <option value="17-0">17-0 CAE (All projects outside of specific goal)</option>
                                <option value="17-1.1">17-1.1 [OPC] OPC Adoption</option>
                                <option value="17-1.2">17-1.2 [OPC] SWiSdev</option>
                                <option value="17-2.1">17-2.1 [FOSS] Porting & Availability</option>
                                <option value="17-2.2">17-2.2 [FOSS] SPARC Performance</option>
                                <option value="17-2.3">17-2.3 [FOSS] Community Engagements</option>
                                <option value="17-3.1">17-3.1 [SWiS] DAX Adoption</option>
                                <option value="17-3.2">17-3.2 [SWiS] SSM Adoption</option>
                                <option value="17-3.3">17-3.3 [SWiS] HW Crypto Adoption</option>
                                <option value="17-4.1">17-4.1 [Key ISVs] SPARC Performance</option>
                                <option value="17-4.2">17-4.2 [Key ISVs] Solaris Feature Adoption</option>
                                <option value="17-4.3">17-4.3 [Key ISVs] Deepen Relationship with ISV</option>
                            </select>
                        </td>
                        <td>Project_Phase:
                            <select id="Project_Phase" name="Project_Phase">
                                <option value="All">All</option>
                                <option value="Complete">Complete</option>
                                <option value="Ongoing">Ongoing</option>
                            </select>
                        </td>
                        <td>Project_Type:
                            <select id="Project_Type" name="Project_Type">
                                <option value="All">All</option>
                                <option value="Education">Education</option>
                                <option value="Publication">Publication</option>
                                <option value="Port">Port</option>
                                <option value="Sizing Study">Sizing Study</option>                                                            
                            </select>
                        </td>
                        <td>Keywords:
                            <select id="Keywords" name="Keywords">
                                <option value="All">All</option>
                                <option value="Non-OPI">Non-OPI</option>
                                <option value="OPI">OPI</option>                                                           
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="button" value="Search" onclick="search()" />
                        </td>
                        <td>
                            <input type="button" value="Select" onclick="selectElementContents( document.getElementById('testtable'));" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
        <div id="testtable"></div>

    </body>
</html>
