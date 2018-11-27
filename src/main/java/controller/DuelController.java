package controller;

import Server.NewGameServer;
import Server.ServerGame;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.Socket;

public class DuelController extends HttpServlet {
    private InputStream is;
    private OutputStream os;
    private User userOut;
    private User user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        user = (User) session.getAttribute("user");
        req.setAttribute("user", user);
        req.setAttribute("ready", false);
        req.getRequestDispatcher("duel.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        user = (User) session.getAttribute("user");
        //ServerGame.getServer();//Server with out Thread pool
        NewGameServer server = new NewGameServer(); //Server with Thread pool

        Socket socket = new Socket("localhost", 2323);

        is = socket.getInputStream();
        os = socket.getOutputStream();

        session.setAttribute("socket", socket);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
        objectOutputStream.writeObject(user);
        objectOutputStream.flush();

            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(is);
                userOut = (User) objectInputStream.readObject();

            }catch (Exception e){
                System.out.println("Error load User.");
            }

        req.setAttribute("ready", true);
        session.setAttribute("playerHealth", userOut.getHealth());
        session.setAttribute("enemyHealth", userOut.getEnemy().getHealth());
        System.out.println("-------------------------" + userOut.getHealth() + " / " + userOut.getEnemy().getHealth() + "-------------------------");
        req.setAttribute("user", userOut);
        req.getRequestDispatcher("duel.jsp").forward(req,resp);
    }
}
