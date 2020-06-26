package servlet;

import lombok.SneakyThrows;
import manager.TaskManager;
import model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/changeStatus")
public class ChangeTaskStatusServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskManager taskManager = new TaskManager();
        String status = req.getParameter("status");
        String id = req.getParameter("taskId");
        int taskId= Integer.parseInt(id);
        taskManager.changeStatus(status,taskId);
        resp.sendRedirect("/userHome");
    }
}
