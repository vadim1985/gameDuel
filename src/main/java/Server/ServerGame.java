package Server;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import entity.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGame implements Runnable{
    private static ServerGame ourInstance = new ServerGame();

    private static ServerSocket serverSocket;
    private static Socket player1Socket;
    private static Socket player2Socket;
    private User player1;
    private User player2;
    private InputStream player1InputStream;
    private InputStream player2InputStream;
    private OutputStream player1OutputStream;
    private OutputStream player2OutputStream;


    public static ServerGame getServer() throws IOException {
        new Thread(ourInstance).start();
        System.out.println("Поток запущен...");
        return ourInstance;
    }

    private ServerGame() {
        try {
            serverSocket = new ServerSocket(2323);
        } catch (IOException e) {
            System.out.println("--ServerSocket Error!!!");
            System.out.println(e.getMessage());
        }
    }


    public void run() {
        try {
        player1Socket = serverSocket.accept();
        System.out.println("Первый готов!");
        player2Socket = serverSocket.accept();
        System.out.println("Второй готов!");

            player1InputStream = player1Socket.getInputStream();
            player1OutputStream = player1Socket.getOutputStream();
            player2InputStream = player2Socket.getInputStream();
            player2OutputStream = player2Socket.getOutputStream();

            player1 = getUserFromInputStream(player1InputStream);
            System.out.println(player1);
            player2 = getUserFromInputStream(player2InputStream);
            System.out.println(player2);

            player1.setEnemy(player2);
            player2.setEnemy(player1);

            sendPlayer(player1, player1OutputStream);
            sendPlayer(player2, player2OutputStream);

            game();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private User getUserFromInputStream(InputStream inputStream){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (User) objectInputStream.readObject();
        } catch (Exception e) {
            System.out.println("--Serialization Error!--");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void game() throws Exception{
            while (true){
                int strike1 = player1InputStream.read();
                int strike2 = player2InputStream.read();
                player1.setHealth(player1.getHealth() - strike2);
                player2.setHealth(player2.getHealth() - strike1);

                player1.addAction(player2.getName() + " ударил вас на " + strike2 + " урона.");
                player1.addAction("Вы ударили " + player2.getName() + " на " + strike1 + " урона.");
                player2.addAction(player1.getName() + " ударил вас на " + strike1 + " урона.");
                player2.addAction("Вы ударили " + player1.getName() + " на " + strike2 + " урона.");

                isGameOver(player1, player2);
                isGameOver(player2, player1);

                sendPlayer(player1, player1OutputStream);
                sendPlayer(player2, player2OutputStream);
            }
    }

    private void isGameOver(User firstUser, User secondUser) throws Exception{
        if (firstUser.getHealth() <= 0){
            System.out.println(firstUser.getName() + " проиграл.");
            firstUser.setOver(true);
            secondUser.setOver(true);
            secondUser.setWinner(true);

            sendPlayer(player1, player1OutputStream);
            sendPlayer(player2, player2OutputStream);
            player1Socket.close();
            player2Socket.close();
            serverSocket.close();

            UserDAO userDAO = new UserDAOImpl();
            userDAO.gameOver(firstUser.getName(), false);
            userDAO.gameOver(secondUser.getName(), true);
        }
    }

    private void sendPlayer(User user, OutputStream os) throws Exception{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
        objectOutputStream.writeObject(user);
        objectOutputStream.flush();
    }
}
