<%--
  Created by IntelliJ IDEA.
  User: comp
  Date: 23.11.2018
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
          <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/HomeCSS.css">
    <title>Hello.</title>
  </head>
  <body>
  <div class="loginPage">
  <form action="Login" method="post"><br><br>
    Login: <input type="text" name="login"><br><br>
    Pass: <input type="password" name="pass"><br><br>
    <input type="submit" value="Login">
  </form>
  </div>
  </body>
</html>
