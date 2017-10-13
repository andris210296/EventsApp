package com.example.andri.eventsapp.model;

import android.support.v7.app.AlertDialog;

import com.example.andri.eventsapp.model.bd.UserDAO;
import com.example.andri.eventsapp.model.bd.UserJDBC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andri on 31/08/2017.
 */

public class UserModel {

    private User user;
    private UserDAO uDAO;

    private List<User> users;


    public UserModel() throws Exception{

        setuDAO(new UserJDBC());
        setUser(new User());
        setUsers(new ArrayList<User>());
        updateListUsers();

    }

    public void create(User user) throws Exception{
        uDAO.create(user);
        updateListUsers();
    }

    public void update(User user) throws Exception{
        uDAO.update(user);
        updateListUsers();
    }

    public void delete(User user) throws Exception{
        uDAO.delete(user);
        updateListUsers();
    }


    // Method that login the user and registers his informations in memory system
    public boolean login(String login, String password) throws Exception {
        updateListUsers();
        for (User user : users) {
            if (user.getLogin().matches(login) && user.getPassword().matches(password)) {
                setUser(user);
                return true;
            }
        }
        return false;
    }

    // Method that verifies if already exists an user with this login
    public boolean loginIsUnique(String u) throws Exception{
        updateListUsers();
        for (User user : getUsers()) {
            if (user.getLogin().matches(u)) {
                return false;
            }
        }
        return true;
    }


    public void updateListUsers() throws Exception {
        getUsers().clear();
        List<User> u = getuDAO().list();
        if (!u.isEmpty()) {
            for (User user : u) {
                getUsers().add(user);
            }

        } else
            getUsers().clear();
        setUsers(users);

    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDAO getuDAO() {
        return uDAO;
    }

    public void setuDAO(UserDAO uDAO) {
        this.uDAO = uDAO;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
