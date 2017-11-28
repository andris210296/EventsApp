package com.example.andri.eventsapp.model.bd;

import android.util.Log;

import com.example.andri.eventsapp.model.User;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andri on 31/08/2017.
 */

public class UserJDBC implements UserDAO, ChildEventListener {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private static final String USER = "user/";

    List<User> users = new ArrayList<>();

    public UserJDBC() {
        myRef.addChildEventListener(this);
    }

    @Override
    public User create(User user) {
        String key = myRef.push().getKey();
        user.setKeyUserId(key);
        myRef.child(USER + key).setValue(user);
        return user;
    }

    @Override
    public void delete(User user) {
        myRef.child(USER + user.getKeyUserId()).removeValue();
    }

    @Override
    public void update(User user) {
        myRef.child(USER + user.getKeyUserId()).child("keyUserId").setValue(user.getKeyUserId());
        myRef.child(USER + user.getKeyUserId()).child("login").setValue(user.getLogin());
        myRef.child(USER + user.getKeyUserId()).child("password").setValue(user.getPassword());
        myRef.child(USER + user.getKeyUserId()).child("email").setValue(user.getEmail());
        myRef.child(USER + user.getKeyUserId()).child("userDate").setValue(user.getUserDate());

    }

    @Override
    public List list() {
        return users;
    }

    @Override
    public User getUser(String keyUserId) {
        for (User user : users) {
            if (user.getKeyUserId().matches(keyUserId)) {
                return user;
            }
        }
        return null;
    }


    //Firebase methods

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if(dataSnapshot.exists()){
            for(DataSnapshot ds: dataSnapshot.getChildren()){
                User user = ds.getValue(User.class);
                if(user.getKeyUserId() != null)
                    users.add(user);
            }
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        for (User user : users) {
            if (user.getKeyUserId().matches(dataSnapshot.getKey())) {

                User userUpdated = dataSnapshot.getValue(User.class);

                user.setKeyUserId(dataSnapshot.getKey());
                user.setLogin(userUpdated.getLogin());
                user.setPassword(userUpdated.getPassword());
                user.setEmail(userUpdated.getEmail());
                user.setUserDate(userUpdated.getUserDate());
            }
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        for (User user : users) {
            if (user.getKeyUserId().matches(dataSnapshot.getKey())) {
                users.remove(user);
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
