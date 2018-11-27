package DAO;

import entity.User;
import java.sql.*;

public class UserDAOImpl implements UserDAO{
    private int countRequestToDB;

    private final static String SELECT_USER = "SELECT username, damage, health, rating FROM user";
    private final static String SELECT_SIMPLE_USER = SELECT_USER + " WHERE username = ? AND pass = ?";
    private final static String INSERT_USER = "INSERT INTO user(username, pass, damage, health, rating) VALUES (?, ?, 10, 100, 0)";
    private final static String UPDATE_USER = "UPDATE user SET damage = ?, health = ?, rating = ? where username = ?";
    private final static String SELECT_USER_BY_NAME = SELECT_USER + " WHERE username = ?";


    Connection connection = null;

    public UserDAOImpl() {
        connection = getConnection();
    }


    public int getCountRequestToDB() {
        return countRequestToDB;
    }

    PreparedStatement preparedStatement = null;

    public User selectUser(String name, String pass) {
        countRequestToDB++;
        try {
            preparedStatement = connection.prepareStatement(SELECT_SIMPLE_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            return userFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println("--Select user error!--");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addUser(String name, String pass){
        countRequestToDB++;
        try {
            preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("--Insert user error!--");
            System.out.println(e.getMessage());
        }
    }

    public void gameOver(String name, boolean isWinner) {
        countRequestToDB++;
            User user = getUsetBuName(name);
            if (isWinner)user.setRating(user.getRating() + 1);
            else user.setRating(user.getRating() - 1);
            user.setDamage(user.getDamage() + 1);
            user.setHealth(user.getHealth() + 1);
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setInt(1, user.getDamage());
            preparedStatement.setInt(2, user.getHealth());
            preparedStatement.setInt(3, user.getRating());
            preparedStatement.setString(4, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("--Update user error!--");
            System.out.println(e.getMessage());
        }
    }

    private User getUsetBuName(String name){
        countRequestToDB++;
        try {
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return userFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println("--Select user by name error!--");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private User userFromResultSet(ResultSet resultSet) throws SQLException{
        User user = null;
        while (resultSet.next()){
            String username = resultSet.getString("username");
            int damage = resultSet.getInt("damage");
            int health = resultSet.getInt("health");
            int rating = resultSet.getInt("rating");
            user = new User(username, damage, health, rating);
        }
        return user;
    }

    private Connection getConnection(){
        Connection connection = null;
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/test_db";
            String user = "root";
            String pass = "root";

            Class.forName(driver);
            System.out.println("Driver is ready");
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection established");
        } catch (ClassNotFoundException e) {
            System.out.println("--Connection failed-- " +  e.getMessage());
        } catch (SQLException e) {
            System.out.println("--Connection failed-- " +  e.getMessage());
        }
        return connection;
    }
}
