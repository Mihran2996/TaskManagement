package servlet;


import lombok.SneakyThrows;
import manager.CommentManager;
import manager.TaskManager;
import model.Comment;
import model.Task;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/task")
public class TaskPageServlet extends HttpServlet {
    private static  int taskId;
    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskManager taskManager=new TaskManager();
        CommentManager commentManager=new CommentManager();
        if(req.getParameter("id")!=null){
            String id = req.getParameter("id");
            TaskPageServlet.taskId = Integer.parseInt(id);
        }
        Task task = taskManager.getTaskById(TaskPageServlet.taskId);
        req.setAttribute("task",task);
        List<Comment> comments = commentManager.getAllCommentByTaskId(TaskPageServlet.taskId);
        req.setAttribute("comments",comments);
        req.getRequestDispatcher("/WEB-INF/taskHome.jsp").forward(req,resp);
    }
}
