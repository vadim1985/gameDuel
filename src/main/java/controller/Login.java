package controller;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Login extends HttpServlet {
    UserDAO userDAO = new UserDAOImpl();
    User user = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        long startTime = System.currentTimeMillis();
        user = userDAO.selectUser(login, pass);
        if (user == null){
            userDAO.addUser(login, pass);
            user = userDAO.selectUser(login, pass);
        }
        long stopTime = System.currentTimeMillis();
        long fullTime = stopTime - startTime;
        String strTimeOut = "db: " + userDAO.getCountRequestToDB() + "req (" + fullTime + "ms)";

        HttpSession session = req.getSession();
        session.setAttribute("timeDB", strTimeOut);
        session.setAttribute("user", user);
        resp.sendRedirect("home");
    }

}
