<%@ page import="servlet.LoginServlet" %>
<%@ page import="model.User" %>
<%@ page import="model.Task" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: MIHRAN
  Date: 21.06.2020
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ManagerHome</title>
</head>
<body>
<h1>Welcome manager homepage`</h1> <a href="/logout" title="index.jsp">logout</a>


<%
    List<User> users = (List<User>) request.getAttribute("users");
    User currentUser = (User) request.getSession().getAttribute("user");

%>


<caption style="color:blue;">ADMIN</caption>
<ul style="color: brown">
    <li><%=currentUser.getName()%>
    </li>
    <li><%=currentUser.getSurname()%>
    </li>
    <li><%=currentUser.getEmail()%>
    </li>
</ul>
<%List<Task> tasks = (List<Task>) request.getAttribute("tasks");%>
<div style="width: 800px; margin-left:100px">

    <div style="width: 50%;float: left">
        <h3> Add User</h3>
        <form action="/userRegister" method="post" enctype="multipart/form-data">
            <select name="type" required>
                <option value="MANAGER">MANAGER</option>
                <option value="USER">USER</option>
            </select>
            <br>
            <br>
            <input type="text" placeholder="name" name="name" required><br>
            <br>
            <input type="text" placeholder="surname" name="surname" required><br>
            <br>
            <input type="text" placeholder="email" name="email" required><br>
            <br>
            <input type="password" placeholder="password" name="password" required><br>
            <br>
            <input type="file" name="image" required><br>
            <input type="submit" value="click">
        </form>
        <%
            String msg = "";
            if (request.getAttribute("msg") != null) {
                msg = (String) request.getAttribute("msg");
                request.removeAttribute("msg");
            }%>

        <span><%=msg%></span>
    </div>
    <div style="width: 50%;float: left">
        <h3> Add Task</h3>
        <form action="/addTask" method="post">
            <select name="status">
                <option value="NEW">NEW</option>
                <option value="FINISHED">FINISHED</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
            </select>
            <br>
            <br>
            <input type="text" placeholder="name" name="name" required><br>
            <br>
            <input type="text" placeholder="description" name="description" required><br>
            <br>
            <input type="date" placeholder="deadline" name="deadline" required><br>
            <br>
            <select name="userId" required>
                <%
                    for (User user : users) {%>
                <option value="<%=user.getId()%>"><%=user.getName() + " " + user.getSurname()%>
                </option>
                <%}%>
            </select>
            <br><br>
            <input type="submit" value="click">
        </form>
    </div>
</div>
<table border=" 1px solid black">
    <caption>Users</caption>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>surname</th>
        <th>email</th>
        <th>type</th>
        <th>picture</th>
        <th>delete</th>
    </tr>
    <%
        for (User user : users) {%>
    <tr>
        <td><%=user.getId()%>
        </td>
        <td><%=user.getName()%>
        </td>
        <td><%=user.getSurname()%>
        </td>
        <td><%=user.getEmail()%>
        </td>
        <td><%=user.getType()%>
        </td>
        <td><%
            if (user.getPictureUrl() != null) { %>
            <img src="/image?path=<%=user.getPictureUrl()%>" width="50"/>
            <% } else {%>
            <img src="../img/download.jpg" width="50">
            <%}%>

        </td>
        <td><a href="/removeUser?id=<%=user.getId()%>">delete</a></td>

    </tr>
    <%
        }
    %>

</table>
</div>
<div style="margin-left: 780px">
    <table border=" 1px solid black">
        <caption>tasks</caption>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>description</th>
            <th>deadline</th>
            <th>status</th>
            <th>user_Id</th>
            <th>update task id</th>
        </tr>
        <%
            for (Task task : tasks) {%>
        <tr>
            <td><%=task.getId()%>
            <td><a href="/task?id=<%=task.getId()%>" title=" Task page"><%=task.getName()%>
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
            <td>
                <form action="/changeTaskUser" method="post">
                    <input type="hidden" name="taskId" value="<%=task.getId()%>">
                    <select name="userId">
                    <% for (User user : users) {%>
                        <option value="<%=user.getId()%>"><%=user.getName()%>
                        </option>
                    <% } %>
                    </select>
                    <input type="submit" value="click">
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
