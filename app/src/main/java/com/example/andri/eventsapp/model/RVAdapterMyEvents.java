package com.example.andri.eventsapp.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andri.eventsapp.EventActivity;
import com.example.andri.eventsapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andri on 31/08/2017.
 */

public class RVAdapterMyEvents extends RecyclerView.Adapter<RVAdapterMyEvents.EventViewHolder> {

    private Event event;
    private List<Event> events;
    private GoogleMap mMap;

    private Context mContext;


    public RVAdapterMyEvents(List<Event> events, Context mContext) {
        this.events = events;
        this.mContext = mContext;
    }


    public class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNameEvent;
        TextView txtDateEvent;
        TextView txtTimeEvent;
        MapView mapView;
        ImageButton imbOpts;


        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtNameEvent = (TextView) itemView.findViewById(R.id.txtNameEvent);
            txtDateEvent = (TextView) itemView.findViewById(R.id.txtDateEvent);
            txtTimeEvent = (TextView) itemView.findViewById(R.id.txtTimeEvent);
            mapView = (MapView) itemView.findViewById(R.id.mapView);
            imbOpts = (ImageButton) itemView.findViewById(R.id.imbOpts);

        }
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(final EventViewHolder eventViewHolder, final int i) {
        eventViewHolder.txtNameEvent.setText(events.get(i).getName());
        eventViewHolder.txtDateEvent.setText(events.get(i).getEventDate());
        eventViewHolder.txtTimeEvent.setText(events.get(i).getTime());
        eventViewHolder.mapView.onCreate(null);

        eventViewHolder.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.getUiSettings().setMapToolbarEnabled(false);
                LatLng myPosition = new LatLng(events.get(i).getLatitude(), events.get(i).getLongitude());
                mMap.addMarker(new MarkerOptions().position(myPosition).title(mContext.getString(R.string.sEventLocation)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,13.0f));
            }
        });

        eventViewHolder.imbOpts.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showPopupMenu(eventViewHolder.imbOpts,events.get(i));
            }
        });

    }

    @Override
    public int getItemCount() {
        return events.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void showPopupMenu(View view,Event event) {
        // inflate menu
        this.event = event;
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_event_my_events, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {


        public MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.act_not_participate:
                    Toast.makeText(mContext, mContext.getString(R.string.sBtnNotParticipate), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.act_edit:
                    Toast.makeText(mContext, mContext.getString(R.string.sBtnUpdate), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, EventActivity.class);
                    intent.putExtra("user", event.getCreator());
                    intent.putExtra("event",event);
                    intent.putExtra("events" , (Serializable) events);;
                    mContext.startActivity(intent);

                    return true;

                default:
            }
            return false;
        }
    }

}
