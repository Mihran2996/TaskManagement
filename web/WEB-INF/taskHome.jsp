<%@ page import="model.Task" %>
<%@ page import="model.User" %>
<%@ page import="model.Comment" %>
<%@ page import="java.util.List" %>
<%@ page import="manager.UserManager" %>
<%@ page import="model.UserType" %>
<%@ page import="servlet.LoginServlet" %><%--
  Created by IntelliJ IDEA.
  User: MIHRAN
  Date: 26.06.2020
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task</title>
</head>
<body>
<h1>Welcome to task page
</h1>
<%Task task = (Task) request.getAttribute("task");%>
<%User user = (User) request.getSession().getAttribute("user");%>
<% List<Comment> comments = (List<Comment>) request.getAttribute("comments");%>
<div>
    <table border=" 1px solid black">
        <caption>tasks</caption>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>description</th>
            <th>deadline</th>
            <th>status</th>
            <th>user_Id</th>
        </tr>
        <tr>
            <td><%=task.getId()%>
            <td><%=task.getName()%>
                </a>
            </td>
            <td><%=task.getDescription()%>
            </td>
            <td><%=task.getDeadline()%>
            </td>
            <td><%=task.getStatus()%>
            </td>
            <td><%=task.getUserId()%>
            </td>
        </tr>

    </table>
</div>
<div>
    <table border="1px solid black">
        <caption>Task comments</caption>
        <tr>
            <td>id</td>
            <td>task_id</td>
            <td>user_Id</td>
            <td>comment</td>
            <td>create_date</td>
            <td>delete</td>
        </tr>
        <%
            for (Comment comment : comments) {
                UserManager userManager = new UserManager();
                User user1 = userManager.getUserById(comment.getUserId());
                if (user.getType() == UserType.MANAGER) {%>
        <tr>
            <td><%=comment.getId()%>
            </td>
            <td><%=comment.getTaskId()%>
            </td>
            <td><%=comment.getUserId()%>
            </td>
            <td><%=comment.getComment()%>
            </td>
            <td><%=comment.getDate()%>
            </td>
            <td><a href="/removeComment?id=<%=comment.getId()%>">delete</a></td>
        </tr>
        <% } else if (user1.getType() == UserType.USER && user.getType() == UserType.USER) { %>
        <tr>
            <td><%=comment.getId()%>
            </td>
            <td><%=comment.getTaskId()%>
            </td>
            <td><%=comment.getUserId()%>
            </td>
            <td><%=comment.getComment()%>
            </td>
            <td><%=comment.getDate()%>
            </td>
            <td><a href="/removeComment?id=<%=comment.getId()%>">delete</a></td>
        </tr>

        <% }%>
        <%}%>
    </table>
</div>
<br>
<form action="/addComment" method="post">
    <input type="hidden" name="user_id_task_id" value="<%=user.getId()+","+task.getId()%>">
    <textarea name="text" rows="5" title="comment" cols="30" required></textarea><br>
    <input type="submit" value="send">
</form>

</body>
</html>
