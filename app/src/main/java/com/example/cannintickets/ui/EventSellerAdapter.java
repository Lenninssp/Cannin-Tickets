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

public class EventSellerAdapter extends RecyclerView.Adapter<EventSellerAdapter.viewholder>{


    List<GetEventResponseModel> events = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);

    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public EventSellerAdapter(List<GetEventResponseModel> events) {
        this.events = events;
    }






    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_seller_item,parent,false);


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
        TextView deleteEvent;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            eventname = itemView.findViewById(R.id.event_name);
            eventdescription = itemView.findViewById(R.id.event_date);
            imageview = itemView.findViewById(R.id.event_image);
            deleteEvent = itemView.findViewById(R.id.delete_event);


            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

            deleteEvent.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });


        }
    }




}
