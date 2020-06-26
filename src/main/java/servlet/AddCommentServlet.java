package servlet;

import lombok.SneakyThrows;
import manager.CommentManager;
import model.Comment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/addComment")
public class AddCommentServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = String.valueOf(req.getParameter("user_id_task_id"));
        CommentManager commentManager=new CommentManager();
        String []split=str.split(",");
        int userId= Integer.parseInt(split[0]);
        int taskId= Integer.parseInt(split[1]);
        String text = req.getParameter("text");
        Comment comment;
        comment=Comment.builder()
                .comment(text)
                .taskId(taskId)
                .userId(userId)
                .build();
        commentManager.addComment(comment);
        resp.sendRedirect("/task");
    }
}
