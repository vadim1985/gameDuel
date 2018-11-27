package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private int damage;
    private int health;
    private int rating;
    private User enemy;
    private List<String> action = new ArrayList<String>();
    private boolean over = false;
    private boolean winner = false;

    public User() {
    }

    public User(String name, int damage, int health, int rating) {
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.rating = rating;
        this.enemy = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public User getEnemy() {
        return enemy;
    }

    public void setEnemy(User enemy) {
        this.enemy = enemy;
    }

    public void addAction(String s){
        action.add(s);
    }

    public List<String> getAction(){
        return action;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", damage=" + damage +
                ", health=" + health +
                '}';
    }
}
