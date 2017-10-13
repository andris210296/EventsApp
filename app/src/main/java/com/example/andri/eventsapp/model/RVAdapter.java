package com.example.andri.eventsapp.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andri.eventsapp.R;

import java.util.List;

/**
 * Created by andri on 31/08/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.EventViewHolder> {


    List<Event> events;

    public RVAdapter(List<Event> events) {
        this.events = events;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNameEvent;
        TextView txtDateEvent;
        TextView txtTimeEvent;
        ImageView imvEvent;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtNameEvent = (TextView) itemView.findViewById(R.id.txtNameEvent);
            txtDateEvent = (TextView) itemView.findViewById(R.id.txtDateEvent);
            txtTimeEvent = (TextView) itemView.findViewById(R.id.txtTimeEvent);
            imvEvent = (ImageView) itemView.findViewById(R.id.imvEvent);
        }
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        eventViewHolder.txtNameEvent.setText(events.get(i).getName());
        eventViewHolder.txtDateEvent.setText(events.get(i).getEventDate());
        eventViewHolder.txtTimeEvent.setText(events.get(i).getTime());
        eventViewHolder.imvEvent.setImageResource(R.mipmap.ic_launcher);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
