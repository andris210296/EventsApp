package com.example.andri.eventsapp.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andri on 24/08/2017.
 */
import java.util.*;

public class Event implements Serializable{

    private String keyEventId;
    private String name;
    private String description;
    private Date eventDate;
    private String time;
    private User creator;
    private List<User> participants;
    private LatLng eventLocation;

    public Event( ){

    }

    public Event(String name, String description, String eventDate, String time, User creator, LatLng eventLocation){
        setKeyEventId(new String());
        setName(name);
        setDescription(description);
        setEventDate(eventDate);
        setTime(time);
        setCreator(creator);
        setParticipants(new ArrayList<User>());
        setEventLocation(eventLocation);
    }

    public Event(String keyEventId, String name, String description, String eventDate, String time, User creator, List<User> participants, LatLng eventLocation){
        setKeyEventId(keyEventId);
        setName(name);
        setDescription(description);
        setEventDate(eventDate);
        setTime(time);
        setCreator(creator);
        setParticipants(participants);
        setEventLocation(eventLocation);
    }

    public String getKeyEventId() {
        return keyEventId;
    }

    public void setKeyEventId(String keyEventId) {
        this.keyEventId = keyEventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date data = this.eventDate;
        String sDate = dateFormat.format(data);
        return sDate;
    }

    public void setEventDate(String eventDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = (Date) formatter.parse(eventDate);
            this.eventDate = date;
        }
         catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public LatLng getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(LatLng eventLocation) {
        this.eventLocation = eventLocation;
    }
}
