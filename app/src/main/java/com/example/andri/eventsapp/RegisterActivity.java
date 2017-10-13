package com.example.andri.eventsapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.example.andri.eventsapp.model.EventModel;
import com.example.andri.eventsapp.model.User;
import com.example.andri.eventsapp.model.UserModel;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtLogin;
    EditText edtPassword;
    EditText edtEmail;
    EditText edtUserDate;

    AppCompatButton btnNewRegister;
    AppCompatButton btnGoBack;

    private UserModel userM;

    AlertDialog.Builder dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dlg = new AlertDialog.Builder(this);

        try {
            userM = new UserModel();
        }catch (Exception e){
            openDlg(getString(R.string.exBD));
        }

        edtLogin = (EditText) findViewById(R.id.edtNewLogin);
        edtPassword = (EditText) findViewById(R.id.edtNewPassword);
        edtEmail = (EditText) findViewById(R.id.edtNewEmail);
        edtUserDate = (EditText) findViewById(R.id.edtNewUserDate);

        btnNewRegister = (AppCompatButton) findViewById(R.id.btnNewRegister);
        btnNewRegister.setOnClickListener(this);

        btnGoBack = (AppCompatButton) findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btnNewRegister.getId()) {

            try {
                User user = new User(edtLogin.getText().toString(),
                        edtPassword.getText().toString(),
                        edtEmail.getText().toString(),
                        edtUserDate.getText().toString());


                if(userM.loginIsUnique(user.getLogin())) {

                    userM.getUsers().add(user);
                    userM.create(user);
                    userM.setUser(user);

                    clearEdts();
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.putExtra("userM",userM);
                    intent.putExtra("eventM",new EventModel());
                    startActivity(intent);
                }else
                    openDlg(getString(R.string.exUniqueLogin));

            } catch (Exception e) {
                openDlg(e.getMessage());
                openDlg(getString(R.string.exIncorrectlyTypedField));
            }

        }

        if (v.getId() == btnGoBack.getId()) {

            clearEdts();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }


    }

    public void clearEdts(){
        edtLogin.getText().clear();
        edtPassword.getText().clear();
        edtEmail.getText().clear();
        edtUserDate.getText().clear();
    }

    public void openDlg(String message) {
        dlg.setMessage(message.toString());
        dlg.setNeutralButton("OK", null);
        dlg.show();
    }
}
