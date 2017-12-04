package com.example.andri.eventsapp.model.bd;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.User;
import com.google.firebase.FirebaseException;

import java.util.List;

/**
 * Created by andri on 16/09/2017.
 */

public interface EventDAO {
    void create(Event event) throws Exception;

    List list() throws Exception;

    void update(Event event) throws Exception;

    void delete(Event event) throws Exception;

    void participate(User user, Event event) throws Exception;

    void leave(User user, Event event) throws Exception;
}
