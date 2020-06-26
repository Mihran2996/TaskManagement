package servlet;

import lombok.SneakyThrows;
import manager.UserManager;
import model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/removeUser")
public class RemoveUserServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        int userId = Integer.parseInt(id);

        try {
            UserManager userManager = new UserManager();
            userManager.deleteById(userId);
            resp.sendRedirect("/managerHome");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
