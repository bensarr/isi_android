package dev.benswift.tp1.model;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private int id;
    private String login;
    private String password;

    public Utilisateur() {
    }

    public Utilisateur(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return id + " - " + login;
    }
}
