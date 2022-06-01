<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.05.2022
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Page</title>
</head>
<body>
username (forward) = ${user}
<br>
username (redirect) = ${username}
<form action="controller">
    <input type="hidden" name="command" value="logout">
    <input type="submit" value="logout">
</form>
</body>
</html>
