package servlet;

import lombok.SneakyThrows;
import manager.UserManager;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    UserManager userManager = new UserManager();

    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        StringBuilder msg = new StringBuilder();
        if (email == null || email.length() == 0) {
            msg.append("Email filed is required<br>");
        }
        if (password == null || password.length() == 0) {
            msg.append("Password filed is required<br>");
        }
        if (msg.toString().equals("")) {
            User byEmailAndPassword = userManager.getUserByEmailAndPassword(email, password);
            if (byEmailAndPassword != null && byEmailAndPassword.getType() == UserType.MANAGER) {
                req.getSession().setAttribute("user", byEmailAndPassword);
                resp.sendRedirect("/managerHome");
                return;
            } else if (byEmailAndPassword != null && byEmailAndPassword.getType() == UserType.USER) {
                req.getSession().setAttribute("user", byEmailAndPassword);
                resp.sendRedirect("/userHome");
                return;
            } else {
                msg.append("User does not exists!");
                req.getSession().setAttribute("msg", msg.toString());
                resp.sendRedirect("/");
                return;
            }
        }
        req.getSession().setAttribute("msg", msg.toString());
        resp.sendRedirect("/");
    }
}
