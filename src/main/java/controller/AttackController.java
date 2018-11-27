package controller;

import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class AttackController extends HttpServlet {
    User userOut;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Socket socket = (Socket) session.getAttribute("socket");
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        int strike = 0 + (int) (Math.random() * user.getDamage());
        os.write(strike);

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            userOut = (User) objectInputStream.readObject();

        }catch (Exception e){
            System.out.println("Error load User.");
        }

        req.setAttribute("ready", true);
        req.setAttribute("user", userOut);

        req.getRequestDispatcher("duel.jsp").forward(req,resp);
    }
}
