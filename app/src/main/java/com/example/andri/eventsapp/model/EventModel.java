package com.example.andri.eventsapp.model;

import com.example.andri.eventsapp.model.bd.EventDAO;
import com.example.andri.eventsapp.model.bd.EventJDBC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andri on 16/09/2017.
 */

public class EventModel {

    private Event event;
    private EventDAO eDAO;

    private List<Event> events;


    public EventModel() throws Exception {

        seteDAO(new EventJDBC());
        setEvent(new Event());
        setEvents(new ArrayList<Event>());
        updateListEvents();

    }


    public void createEvent(Event event) throws Exception {
        eDAO.create(event);
        updateListEvents();

    }

    public void updateListEvents() throws Exception {
        getEvents().clear();
        List<Event> e = geteDAO().list();
        if (!e.isEmpty()) {
            for (Event event : e) {
                getEvents().add(event);
            }

        } else
            getEvents().clear();
        setEvents(events);

    }

    public void myEventsList(User user) throws Exception{
        getEvents().clear();
        List<Event> e = geteDAO().list();
        if (!e.isEmpty()) {
            for (Event event : e) {
                if(event.getCreator().getKeyUserId().equals(user.getKeyUserId())) {
                    getEvents().add(event);
                }
            }

        } else
            getEvents().clear();
        setEvents(events);
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventDAO geteDAO() {
        return eDAO;
    }

    public void seteDAO(EventDAO eDAO) {
        this.eDAO = eDAO;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
