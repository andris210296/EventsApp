package com.example.andri.eventsapp.model.bd;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.User;
import com.google.firebase.FirebaseException;

import java.util.List;

/**
 * Created by andri on 16/09/2017.
 */

public interface EventDAO{
    public void create(Event event)throws Exception;

    public List list()throws Exception;

    public void update(Event event)throws Exception;

    public void delete(Event event) throws Exception;

    public void participate(User user, Event event ) throws Exception;

    public List participants(Event event)throws Exception;

    public void leave(User user, Event event ) throws Exception;
}
