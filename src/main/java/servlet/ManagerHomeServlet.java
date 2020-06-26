package servlet;

import lombok.SneakyThrows;
import manager.TaskManager;
import manager.UserManager;
import model.Task;
import model.User;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/managerHome")
public class ManagerHomeServlet extends HttpServlet {
    @SneakyThrows
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            TaskManager taskManager = new TaskManager();
            UserManager userManager = new UserManager();
            List<User> allUsers = userManager.getAllUsers();
            List<Task> allTask = taskManager.getAllTask();
            req.setAttribute("tasks", allTask);
            req.setAttribute("users", allUsers);
            req.getRequestDispatcher("/WEB-INF/managerHome.jsp").forward(req,resp);

    }

}
