package DAO;

import entity.User;

public interface UserDAO {
        int getCountRequestToDB();
        User selectUser(String name, String pass);
        void addUser(String name, String pass);
        void gameOver(String name, boolean isWinner);
}
