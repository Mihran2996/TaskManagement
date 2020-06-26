package servlet;


import lombok.SneakyThrows;
import manager.TaskManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/changeTaskUser")
public class ChangeTaskUserServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskManager taskManager = new TaskManager();
        String tkId = req.getParameter("taskId");
        String usId = req.getParameter("userId");
        int taskId = Integer.parseInt(tkId);
        int userId = Integer.parseInt(usId);
        taskManager.changeTaskId(taskId, userId);
        resp.sendRedirect("/managerHome");
    }
}
