<%--
  Created by IntelliJ IDEA.
  User: MIHRAN
  Date: 21.06.2020
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
<%
    String msg = "";
    if (session.getAttribute("msg") != null) {
        msg = (String) session.getAttribute("msg");
        session.removeAttribute("msg");
    }%>
<span style="color: red">
    <%=msg%>

</span><br>
<form action="/login" method="post">
    <input type="text" name="email" placeholder="Please input email"><br>
    <input type="password" name="password" placeholder="Please input password"><br>
    <input type="submit" value="Login"><br>
</form>
</body>
</html>
