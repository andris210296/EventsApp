package com.example.andri.eventsapp;

import android.content.Intent;
import android.support.v7.app.*;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.*;

import com.example.andri.eventsapp.R;
import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.EventModel;
import com.example.andri.eventsapp.model.User;
import com.example.andri.eventsapp.model.UserModel;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    User user;
    private UserModel userM;

    private EventModel eventM;


    AlertDialog.Builder dlg;


    AppCompatButton btnLogin;
    AppCompatButton btnRegister;

    EditText edtLogin;
    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dlg = new AlertDialog.Builder(this);

        try {
            userM = new UserModel();
            eventM = new EventModel();

        }catch (Exception e){
            openDlg(e.getMessage());
            openDlg(getString(R.string.exBD));
        }

        btnLogin =(AppCompatButton) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegister = (AppCompatButton) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtPassword = (EditText) findViewById(R.id.edtPassword);



    }

    @Override
    public void onClick(View v) {

        if( v.getId() == btnLogin.getId()) {
            try {
                if(userM.login(edtLogin.getText().toString(),edtPassword.getText().toString())) {
                    eventM.updateListEvents();
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.putExtra("userM",userM);
                    //intent.putExtra("eventM",eventM);
                    startActivity(intent);
                    clearEdts();
                }else
                    throw  new Exception();

            }catch (Exception e){
                openDlg(e.getMessage());
                openDlg(getString(R.string.exLogin));
            }

        }

        if( v.getId() == btnRegister.getId()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

    }

    public void clearEdts(){
        edtLogin.getText().clear();
        edtPassword.getText().clear();
    }

    public void openDlg(String message){
        dlg.setMessage(message.toString());
        dlg.setNeutralButton("OK",null);
        dlg.show();
    }
}
