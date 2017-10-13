package com.example.andri.eventsapp.model;

/**
 * Created by andri on 24/08/2017.
 */

import com.example.andri.eventsapp.model.bd.UserDAO;
import com.example.andri.eventsapp.model.bd.UserJDBC;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User implements Serializable {

    private String keyUserId;
    private String login;
    private String password;
    private String email;
    private Date userDate;

    public User() {

    }

    public User(String login, String password, String email, String userDate) {
        setKeyUserId(new String());
        setLogin(login);
        setPassword(password);
        setEmail(email);
        setUserDate(userDate);
    }

    public User(String keyUserId, String login, String password, String email, String userDate) {
        setKeyUserId(keyUserId);
        setLogin(login);
        setPassword(password);
        setEmail(email);
        setUserDate(userDate);
    }


    // Method that returns an object User due to an id
    public static User getUsuario_from_Id(String keyUserId) throws Exception {
        UserDAO uDAO = new UserJDBC();
        return uDAO.getUser(keyUserId);

    }

    public String getKeyUserId() {
        return keyUserId;
    }

    public void setKeyUserId(String keyUserId) {
        this.keyUserId = keyUserId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date data = this.userDate;
        String sDate = dateFormat.format(data);
        return sDate;
    }

    public void setUserDate(String userDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = (Date) formatter.parse(userDate);
            this.userDate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
