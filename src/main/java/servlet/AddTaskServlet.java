package servlet;

import lombok.SneakyThrows;
import manager.TaskManager;
import model.Task;
import model.TaskStatus;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = "/addTask")
public class AddTaskServlet extends HttpServlet {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            TaskManager taskManager = new TaskManager();
            String status = req.getParameter("status");
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String deadline = req.getParameter("deadline");
            String userId = req.getParameter("userId");
            Task task = Task.builder()
                    .status(TaskStatus.valueOf(status))
                    .name(name)
                    .description(description)
                    .deadline(sdf.parse(deadline))
                    .userId(Integer.parseInt(userId))
                    .build();
            taskManager.addTask(task);
            resp.sendRedirect("/managerHome");
    }
}
