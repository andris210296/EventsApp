package com.example.andri.eventsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.EventModel;
import com.example.andri.eventsapp.model.User;
import com.example.andri.eventsapp.model.UserModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View view;


    private User user;
    private UserModel userM;
    private EventModel eventM;

    private EditText edtLogin;
    private EditText edtPassword;
    private EditText edtEmail;
    private EditText edtUserDate;

    private Button btnUpdate;
    private Button btnDelete;

    private AlertDialog.Builder dlg;


    public HomeFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        edtLogin = (EditText) view.findViewById(R.id.edtUpdateLogin);
        edtPassword = (EditText) view.findViewById(R.id.edtUpdatePassword);
        edtEmail = (EditText) view.findViewById(R.id.edtUpdateEmail);
        edtUserDate = (EditText) view.findViewById(R.id.edtUpdateUserDate);

        btnUpdate = (AppCompatButton) view.findViewById(R.id.btnUpdateUser);
        btnUpdate.setOnClickListener(this);

        btnDelete = (AppCompatButton) view.findViewById(R.id.btnDeleteUser);
        btnDelete.setOnClickListener(this);

        dlg = new AlertDialog.Builder(view.getContext());

        Intent intent = getActivity().getIntent();
        user = (User) intent.getExtras().get("user");

        try {
            userM = new UserModel();
            userM.setUser(user);
        }catch (Exception e){
            openDlg(getString(R.string.exBD));
        }

        setTextEdts(userM.getUser());

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btnUpdate.getId()) {

            try {

                User user = new User(userM.getUser().getKeyUserId(),
                        edtLogin.getText().toString(),
                        edtPassword.getText().toString(),
                        edtEmail.getText().toString(),
                        edtUserDate.getText().toString());

                if(userM.loginIsUnique(user.getLogin()) || user.getLogin().matches(edtLogin.getText().toString())) {
                    userM.update(user);

                    clearEdts();
                    setTextEdts(user);

                }else
                    openDlg(getString(R.string.exUniqueLogin));

            } catch (Exception e) {
                openDlg(getString(R.string.exIncorrectlyTypedField));
            }

        }
        if(v.getId() == btnDelete.getId()){

            try{
                setUserLogged(userM.getUser());
                clearEdts();
                Intent intent = new Intent(view.getContext(), MenuActivity.class);
                startActivity(intent);

                userM.delete(userM.getUser());

            }catch (Exception e){
                openDlg(getString(R.string.exDelete));
            }
        }

    }

    public void setUserLogged(User user){
        try {
            userM.setUser(user);
        }catch (Exception e){
            openDlg(getString(R.string.exGetUserById));
        }

    }

    public void setTextEdts(User user){
        edtLogin.setText(user.getLogin());
        edtPassword.setText(user.getPassword());
        edtEmail.setText(user.getEmail());
        edtUserDate.setText(user.getUserDate());
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context,"Home Fragment Attached",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
