package com.example.andri.eventsapp.model.bd;

import com.example.andri.eventsapp.model.User;

import java.util.List;

/**
 * Created by andri on 31/08/2017.
 */

public interface UserDAO {
    public User create(User user) throws Exception;

    public User getUser(String keyUserId ) throws Exception;

    public void update(User user) throws Exception;

    public void delete(User user) throws Exception;

    public List list() throws Exception;


}
