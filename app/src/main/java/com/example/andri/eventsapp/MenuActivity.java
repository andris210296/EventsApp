package com.example.andri.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.view.MenuItem;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.EventModel;
import com.example.andri.eventsapp.model.User;
import com.example.andri.eventsapp.model.UserModel;

import java.io.Serializable;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    User user;
    EventModel eventM;

    AlertDialog.Builder dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dlg = new AlertDialog.Builder(this);

        try {
            eventM = new EventModel();
        }catch (Exception e){
            openDlg(getString(R.string.exBD));

        }

        Intent intent = getIntent();
        user = (User) intent.getExtras().get("user");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("user" , user);
        arguments.putSerializable("events" , (Serializable) eventM.getEvents());
        homeFragment.setArguments(arguments);

        transaction.replace(R.id.content,homeFragment).commit();

    }



    public void openDlg(String message){
        dlg.setMessage(message.toString());
        dlg.setNeutralButton("OK",null);
        dlg.show();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();

            Bundle arguments = new Bundle();
            arguments.putSerializable("user" , user);
            try {
                eventM.updateListEvents();
            } catch (Exception e) {
                e.printStackTrace();
            }
            arguments.putSerializable("events" , (Serializable) eventM.getEvents());

            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(arguments);

            MyEventsFragment myEventsFragment = new MyEventsFragment();
            myEventsFragment.setArguments(arguments);

            AllEventsFragment allEventsFragment = new AllEventsFragment();
            allEventsFragment.setArguments(arguments);


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content,homeFragment).commit();
                    return true;
                case R.id.navigation_my_events:
                    transaction.replace(R.id.content,myEventsFragment).commit();
                    return true;
                case R.id.navigation_all_events:
                    transaction.replace(R.id.content,allEventsFragment).commit();
                    return true;
            }
            return false;
        }

    };



}
