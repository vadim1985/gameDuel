<%--
  Created by IntelliJ IDEA.
  User: comp
  Date: 23.11.2018
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/HomeCSS.css">
    <title>Select next...</title>
</head>
<body>
<div class="imgDuel" style="background-image: url('<%=request.getContextPath()%>/img/duel.png');">
    <br>
    <a href="/duel"><button class="btnDuel">Дуэли</button></a>
    <a href="/exit"><button class="btnExit">Выход</button></a>
</div>

</body>
<br>
<div class="footer">
<p>page:${time} ms, ${timeDB}</p>
</div>
</html>
