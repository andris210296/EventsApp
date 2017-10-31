package com.example.andri.eventsapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.EventModel;
import com.example.andri.eventsapp.model.User;
import com.example.andri.eventsapp.model.UserModel;

import java.io.Serializable;
import java.util.List;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private Event event;
    private List<Event> events;
    private EventModel eventM;

    private EditText edtNameEvent;
    private EditText edtDescriptionEvent;
    private EditText edtDateEvent;
    private EditText edtTimeEvent;

    private Button btnNewEvent;


    AlertDialog.Builder dlg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        dlg = new AlertDialog.Builder(this);

        edtNameEvent = (EditText) findViewById(R.id.edtNewNameEvent);
        edtDescriptionEvent = (EditText) findViewById(R.id.edtNewDescriptionEvent);
        edtDateEvent = (EditText) findViewById(R.id.edtNewDateEvent);
        edtTimeEvent = (EditText) findViewById(R.id.edtNewTimeEvent);

        btnNewEvent = (AppCompatButton) findViewById(R.id.btnNewEvent);
        btnNewEvent.setOnClickListener(this);

        Intent intent = getIntent();
        user = (User) intent.getExtras().get("user");
        event = (Event) intent.getExtras().get("event");
        events = (List<Event>) intent.getExtras().getSerializable("events");

        try {
            eventM = new EventModel();

        } catch (Exception e) {
            openDlg(getString(R.string.exBD));
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnNewEvent.getId()) {

            try {
                event = new Event(edtNameEvent.getText().toString(),
                        edtDescriptionEvent.getText().toString(),
                        edtDateEvent.getText().toString(),
                        edtTimeEvent.getText().toString(),
                        user);


                eventM.getEvents().add(event);
                eventM.createEvent(event);

                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("event", event);
                intent.putExtra("events", (Serializable) events);
                startActivity(intent);



            } catch (Exception e) {
                openDlg(getString(R.string.exIncorrectlyTypedField));
            }
        }

    }

    public void openDlg(String message){
        dlg.setMessage(message.toString());
        dlg.setNeutralButton("OK",null);
        dlg.show();
    }
}
