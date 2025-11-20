package com.example.cannintickets.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.events.GetEventsController;
import com.example.cannintickets.models.events.get.GetEventResponseModel;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.viewholder>{


    List<GetEventResponseModel> events = new ArrayList<>();

    public EventAdapter(List<GetEventResponseModel> events) {
        this.events = events;
    }






    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);


        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        GetEventResponseModel event = events.get(position);
        holder.eventname.setText(event.getName());
        holder.eventdescription.setText(event.getEventDate());
        if (event.getCoverImage().exists()) {
            holder.imageview.setImageBitmap(BitmapFactory.decodeFile(event.getCoverImage().getAbsolutePath()));
        }



    }

    @Override
    public int getItemCount() {
        if (events == null) {
            return 0;
        } else {
            return events.size();
        }
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView eventname;
        TextView eventdescription;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            eventname = itemView.findViewById(R.id.event_name);
            eventdescription = itemView.findViewById(R.id.event_date);
            imageview = itemView.findViewById(R.id.event_image);

        }
    }
}
