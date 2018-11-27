<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: comp
  Date: 24.11.2018
  Time: 1:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/DuelCSS.css">
    <title>Let's fight...</title>
</head>
<body>
<%
    boolean ready = (boolean) request.getAttribute("ready");
    if (ready){
%>
<div class="tablePlayer">
<table border="1">
    <tr>
        <td><h3>Your name: </h3></td><td>${user.name}</td>
    </tr>
    <tr>
        <td><h3>Your damage: </h3></td><td>${user.damage}</td>
    </tr>
    <tr>
        <td><h3>Your health: </h3></td><td>${user.health}</td>
    </tr>
</table>
    <br>
    <progress value="${user.health}" max="${playerHealth}"></progress>
</div>
<div class="tableOpponent">
<table border="1">
    <tr>
        <td><h3>Opponent name: </h3></td><td>${user.enemy.name}</td>
    </tr>
    <tr>
        <td><h3>Opponent damage: </h3></td><td>${user.enemy.damage}</td>
    </tr>
    <tr>
        <td><h3>Opponent health: </h3></td><td>${user.enemy.health}</td>
    </tr>
</table>
    <br>
    <progress value="${user.enemy.health}" max="${enemyHealth}"></progress>
</div>
<div class="actionList">
    <c:if test="${user.over}">
        <c:if test="${user.winner}">
            <H1>Вы убили ${user.enemy.name}.</H1>
        </c:if>
    </c:if>
    <br>
    <c:forEach items="${user.action}" var="list">
        <ul>
            <li>
                    ${list}
            </li>
        </ul>
    </c:forEach>
</div>

<%}else {%>
<br>
<form action="duel" method="post">
    <input type="submit" value="Начать дуэль" class="btnAttack">
</form>
<%}%>
<br>
<p>
    <c:if test="${!user.over}">
        <br>
        <form action="attack" method="post" onsubmit="document.getElementById('subBtn').disabled = true;">
            <input type="submit" value="Атаковать" id="subBtn"  class="btnAttack">
        </form>
        <br>
    </c:if>
</p>
<br>
<div class="footer">
<p>page:${time} ms, ${timeDB}</p>
</div>
</body>
</html>
