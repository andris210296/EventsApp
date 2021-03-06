package com.example.andri.eventsapp.model.bd;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andri on 16/09/2017.
 */

public class EventJDBC implements EventDAO, ChildEventListener {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private static final String EVENT = "event/";
    private static final String PARTICIPANTS = "participants/";

    List<Event> events = new ArrayList<>();

    public EventJDBC() {
        myRef.addChildEventListener(this);
    }

    @Override
    public void create(Event event) throws Exception {
        String key = myRef.push().getKey();
        event.setKeyEventId(key);
        myRef.child(EVENT + key).setValue(event);
    }

    @Override
    public List list() throws Exception {
        return events;
    }

    @Override
    public void update(Event event) throws Exception {
        myRef.child(EVENT + event.getKeyEventId()).child("keyEventId").setValue(event.getKeyEventId());
        myRef.child(EVENT + event.getKeyEventId()).child("name").setValue(event.getName());
        myRef.child(EVENT + event.getKeyEventId()).child("description").setValue(event.getDescription());
        myRef.child(EVENT + event.getKeyEventId()).child("eventDate").setValue(event.getEventDate());
        myRef.child(EVENT + event.getKeyEventId()).child("time").setValue(event.getTime());
        myRef.child(EVENT + event.getKeyEventId()).child("creator").setValue(event.getCreator());
        myRef.child(EVENT + event.getKeyEventId()).child("participants").setValue(event.getParticipants());
        myRef.child(EVENT + event.getKeyEventId()).child("latitude").setValue(event.getLatitude());
        myRef.child(EVENT + event.getKeyEventId()).child("longitude").setValue(event.getLongitude());

    }

    @Override
    public void delete(Event event) throws Exception {
        myRef.child(EVENT + event.getKeyEventId()).removeValue();
    }

    @Override
    public void participate(User user, Event event) throws Exception {
        myRef.child(EVENT + event.getKeyEventId()).child(PARTICIPANTS + user.getKeyUserId()).setValue(user.getKeyUserId());

    }

    @Override
    public void leave(User user, Event event) throws Exception {
        myRef.child(EVENT + event.getKeyEventId()).child(PARTICIPANTS + user.getKeyUserId()).removeValue();
    }


    //Firebase methods

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.exists() && !dataSnapshot.getKey().equals("user")) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Event event = ds.getValue(Event.class);
                events.add(event);
            }
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.exists() && !dataSnapshot.getKey().equals("user")) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                for (Event event : events) {
                    if (event.getKeyEventId().matches(ds.getKey())) {
                        Event eventUpdated = ds.getValue(Event.class);

                        event.setKeyEventId(ds.getKey());
                        event.setCreator(eventUpdated.getCreator());
                        event.setEventDate(eventUpdated.getEventDate());
                        event.setName(eventUpdated.getName());
                        event.setDescription(eventUpdated.getDescription());
                        event.setTime(eventUpdated.getTime());
                        event.setParticipants(eventUpdated.getParticipants());
                        event.setLatitude(eventUpdated.getLatitude());
                        event.setLongitude(eventUpdated.getLongitude());
                    }
                }
            }
        }

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists() && !dataSnapshot.getKey().equals("user")) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                for (Event event : events) {
                    if (event.getKeyEventId().matches(ds.getKey())) {
                        events.remove(event);
                    }
                }
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
