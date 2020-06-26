<%@ page import="model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="servlet.LoginServlet" %>
<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: MIHRAN
  Date: 21.06.2020
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserHome</title>
</head>
<body>

<%List<Task> tasks = (List<Task>) request.getAttribute("tasks");%>
<%User user = (User) request.getAttribute("user");%>
<h1>Welcome  <%=user.getName()%> page <% if (user.getPictureUrl() != null) { %>
    <img src="/image?path=<%=user.getPictureUrl()%>" width="50"/> <%}%>
</h1> <a href="/logout"
         title="index.jsp">logout</a>
<br>
<div style="float: left;margin-top:10px">
    <table border=" 1px solid black">
        <caption>My tasks</caption>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>description</th>
            <th>deadline</th>
            <th>status</th>
            <th>user_Id</th>
            <th>Update Status</th>
        </tr>
        <%
            for (Task task : tasks) {%>
        <tr>
            <td><%=task.getId()%>
            </td>
            <td><a href="/task?id=<%=task.getId()%>" title=" Task page"><%=task.getName()%>
            <td><%=task.getDescription()%>
            </td>
            <td><%=task.getDeadline()%>
            </td>
            <td><%=task.getStatus()%>
            </td>
            <td><%=task.getUserId()%>
            </td>
            <td>
                <form action="/changeStatus" method="post">
                    <input type="hidden" name="taskId" value="<%=task.getId()%>">
                    <select name="status">
                        <option value="NEW">NEW</option>
                        <option value="FINISHED">FINISHED</option>
                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                    </select><input type="submit" name="ok">
                </form>
            </td>
        </tr>
        <%
            }
        %>

    </table>
</div>

</body>
</html>
