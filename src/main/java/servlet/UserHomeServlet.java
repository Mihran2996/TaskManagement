package servlet;

import lombok.SneakyThrows;
import manager.TaskManager;
import model.Task;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/userHome")
public class UserHomeServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        TaskManager taskManager = new TaskManager();
        List<Task> all = taskManager.getAllTaskByUserId(user.getId());
        req.setAttribute("tasks", all);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/userHome.jsp").forward(req, resp);
    }
}
