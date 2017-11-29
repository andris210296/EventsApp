package com.example.andri.eventsapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.EventModel;
import com.example.andri.eventsapp.model.RVAdapter;
import com.example.andri.eventsapp.model.User;
import com.example.andri.eventsapp.model.UserModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllEventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllEventsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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


    public AllEventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllEventsFragment newInstance(String param1, String param2) {
        AllEventsFragment fragment = new AllEventsFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_events, container, false);

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rvAllEvents);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(events,getContext());
        //adapter.getImbDelete().setVisibility(View.INVISIBLE);

        rv.setAdapter(adapter);
        return view;
    }


    public List<Event> events(){

        User novo = new User("login","senha","email@email.com","12/12/1212");
        List<User> participants = new ArrayList<>();
        participants.add(novo);
        participants.add(novo);
        participants.add(novo);

        List<Event> allEvents = new ArrayList<>();
        allEvents.add(new Event("Aniversário1","Só uma festa de boas","12/12/1212",
                " - 21:45",novo,new LatLng(0,0)));
        allEvents.add(new Event("Aniversário2","Só duas festas de boas","12/12/1212",
                " - 21:45",novo,new LatLng(0,0)));
        allEvents.add(new Event("Aniversário3","Só três festas de boas","12/12/1212",
                " - 21:45",novo,new LatLng(0,0)));
        allEvents.add(new Event("Aniversário4","Só três festas de boas","12/12/1212",
                " - 21:45",novo,new LatLng(0,0)));
        allEvents.add(new Event("Aniversário5","Só três festas de boas","12/12/1212",
                " - 21:45",novo,new LatLng(0,0)));
        allEvents.add(new Event("Aniversário6","Só três festas de boas","12/12/1212",
                " - 21:45",novo,new LatLng(0,0)));

        return allEvents;

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
            Toast.makeText(context,"AllEvents Fragment Attached",Toast.LENGTH_SHORT).show();
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
