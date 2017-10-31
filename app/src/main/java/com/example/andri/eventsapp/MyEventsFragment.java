package com.example.andri.eventsapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.EventModel;
import com.example.andri.eventsapp.model.RVAdapter;
import com.example.andri.eventsapp.model.User;
import com.example.andri.eventsapp.model.UserModel;
import com.example.andri.eventsapp.model.bd.EventDAO;
import com.example.andri.eventsapp.model.bd.EventJDBC;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyEventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyEventsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;


    private View view;

    private Event event;
    private EventModel eventM;
    private UserModel userM;
    private User user;
    private ArrayList<Event> events;

    private RecyclerView rv;

    private FloatingActionButton fabNewEvent;

    AlertDialog.Builder dlg;

    public MyEventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyEventsFragment newInstance(String param1, String param2) {
        MyEventsFragment fragment = new MyEventsFragment();
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

            try {
                eventM = new EventModel();

                user = (User) getArguments().get("user");
                events = (ArrayList<Event>) getArguments().getSerializable("events");

                eventM.setEvents(events);

            } catch (Exception e) {
                openDlg(getString(R.string.exList));
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_events, container, false);

        dlg = new AlertDialog.Builder(view.getContext());


        rv = (RecyclerView) view.findViewById(R.id.rvMyEvents);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);

        fabNewEvent = (FloatingActionButton) view.findViewById(R.id.fabAddEvent);
        fabNewEvent.setOnClickListener(this);


        try {

            RVAdapter adapter = new RVAdapter(eventM.myEventsList(user));
            rv.setAdapter(adapter);

        } catch (Exception e) {
            openDlg(getString(R.string.exList));
            openDlg(e.getMessage());
        }

        return view;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == fabNewEvent.getId()) {

            Intent intent = new Intent(view.getContext(), EventActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("event",new Event());
            intent.putExtra("events" , (Serializable) eventM.getEvents());;
            startActivity(intent);
        }

    }

    public void openDlg(String message) {
        dlg.setMessage(message.toString());
        dlg.setNeutralButton("OK", null);
        dlg.show();
    }


    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.

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
            Toast.makeText(context, "MyEvents Fragment Attached", Toast.LENGTH_SHORT).show();
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
