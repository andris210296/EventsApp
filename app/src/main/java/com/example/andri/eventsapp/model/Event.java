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
    private HashMap<String, String> participants;
    private Double latitude;
    private Double longitude;


    public Event( ){

    }

    public Event(String name, String description, String eventDate, String time, User creator){
        setKeyEventId(null);
        setName(name);
        setDescription(description);
        setEventDate(eventDate);
        setTime(time);
        setCreator(creator);
        setParticipants(new HashMap<String, String>());
        setLatitude(null);
        setLongitude(null);
    }

    public Event(String name, String description, String eventDate, String time, User creator, double latitude, double longitude){
        setKeyEventId(null);
        setName(name);
        setDescription(description);
        setEventDate(eventDate);
        setTime(time);
        setCreator(creator);
        setParticipants(new HashMap<String, String>());
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Event(String keyEventId,String name, String description, String eventDate, String time, User creator, double latitude, double longitude){
        setKeyEventId(keyEventId);
        setName(name);
        setDescription(description);
        setEventDate(eventDate);
        setTime(time);
        setCreator(creator);
        setParticipants(new HashMap<String, String>());
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Event(String keyEventId, String name, String description, String eventDate, String time, User creator, HashMap<String, String> participants, double latitude, double longitude){
        setKeyEventId(keyEventId);
        setName(name);
        setDescription(description);
        setEventDate(eventDate);
        setTime(time);
        setCreator(creator);
        setParticipants(participants);
        setLatitude(latitude);
        setLongitude(longitude);
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

    public HashMap<String, String> getParticipants() {
        return participants;
    }

    public void setParticipants(HashMap<String, String> participants) {
        this.participants = participants;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
