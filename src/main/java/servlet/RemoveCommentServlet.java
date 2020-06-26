package servlet;

import lombok.SneakyThrows;
import manager.CommentManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/removeComment")
    public class RemoveCommentServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
//        String userId = req.getParameter("userId");
        int taskId = Integer.parseInt(id);
        CommentManager commentManager=new CommentManager();
        commentManager.deleteCommentById(taskId);
        resp.sendRedirect("/task");
    }
}
