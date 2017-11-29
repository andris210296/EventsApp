package com.example.andri.eventsapp.model.bd;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andri on 16/09/2017.
 */

public class EventJDBC implements EventDAO, ChildEventListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("event");

    List<Event> events;

    public EventJDBC() {
        myRef.addChildEventListener(this);
        events = new ArrayList<>();
    }

    @Override
    public void create(Event event) throws Exception {
        myRef.push().setValue(event);
    }

    @Override
    public List list() throws Exception {



        return events;
    }

    @Override
    public void update(Event event) throws Exception {
        myRef.child(event.getKeyEventId()).child("keyEventId").setValue(event.getKeyEventId());
        myRef.child(event.getKeyEventId()).child("name").setValue(event.getName());
        myRef.child(event.getKeyEventId()).child("description").setValue(event.getDescription());
        myRef.child(event.getKeyEventId()).child("eventDate").setValue(event.getEventDate());
        myRef.child(event.getKeyEventId()).child("time").setValue(event.getTime());
        myRef.child(event.getKeyEventId()).child("creator").setValue(event.getCreator());
        myRef.child(event.getKeyEventId()).child("participants").setValue(event.getParticipants());

    }

    @Override
    public void delete(Event event) throws Exception {
        myRef.child(event.getKeyEventId()).removeValue();
    }

    @Override
    public void participate(User user, Event event) throws Exception {


    }

    @Override
    public List participants(Event event) throws Exception {
        return null;
    }

    @Override
    public void leave(User user, Event event) throws Exception {

    }


    //Firebase methods

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Event event = dataSnapshot.getValue(Event.class);
        myRef.child(dataSnapshot.getKey()).child("keyEventId").setValue(dataSnapshot.getKey());
        event.setKeyEventId(dataSnapshot.getKey());
        events.add(event);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        for (Event event : events) {
            if (event.getKeyEventId().matches(dataSnapshot.getKey())) {
                Event eventUpdated = dataSnapshot.getValue(Event.class);

                event.setKeyEventId(dataSnapshot.getKey());
                event.setCreator(eventUpdated.getCreator());
                event.setEventDate(eventUpdated.getEventDate());
                event.setName(eventUpdated.getName());
                event.setDescription(eventUpdated.getDescription());
                event.setTime(eventUpdated.getTime());
                event.setParticipants(eventUpdated.getParticipants());
            }
        }

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        for (Event event : events) {
            if (event.getKeyEventId().matches(dataSnapshot.getKey())) {
                events.remove(event);
            }
        }

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
