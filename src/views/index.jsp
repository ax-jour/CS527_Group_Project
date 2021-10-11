<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Rutgers CS 527 project 1, Team 5 </title>
    <link rel="stylesheet" href="static/css/style.css"/>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<body>
    <div class="title">CS 527 Project 1</div>
    <div class="choiceButtonRow">
    <table>
        <td>
            Database:<select id="selectList">
                <option value="instacart" selected>instacart</option>
                <option value="amazon">amazon</option>
            </select>
        </td>
        <td></td>
        <td></td>
        <td><input id="mysql" type="radio" name="meta" value="mysql" checked>mysql</td>
        <td><input id="redshift" type="radio" name="meta" value="redshift">redshift</td>
    </table>
    </div>
    <div>
        <textarea class="inputBox" name="query" id="inputContent" placeholder="Input your query here" rows="10"></textarea>
    </div>
    <div style="width: 100%">
        <div style="width: 50%;float: left">
            <input type="button" value="run" onclick="updateTable()">
        </div>
        <div class="runningTimeBox" style="width: 20%; float: right">
            <span class="runningTimeBox runningTimeRes" id="runningTime">0</span> ms Time Elapsed
        </div>
    </div>
    <div class="resultDiv">
        <table class="resultBox" id="databaseTable" border="">
        </table>
    </div>
</body>
<script src="${pageContext.request.contextPath}/static/js/basic.js" type="text/javascript"></script>
</html>