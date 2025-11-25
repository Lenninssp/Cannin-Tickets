package com.example.cannintickets.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.events.GetEventsController;
import com.example.cannintickets.models.events.get.GetEventResponseModel;
import com.example.cannintickets.models.orders.OrderResponseModel;
import com.example.cannintickets.models.usertickets.UserTicketsResponseModel;

import java.util.ArrayList;
import java.util.List;

public class UserTicketAdapter extends RecyclerView.Adapter<UserTicketAdapter.viewholder>{


    List<UserTicketsResponseModel> tickets = new ArrayList<>();






    public UserTicketAdapter(List<UserTicketsResponseModel> tickets) {
        this.tickets = tickets;
    }








    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_ticket_item,parent,false);


        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        UserTicketsResponseModel ticket = tickets.get(position);


        Context context = holder.ticketStatus.getContext();


        holder.eventName.setText(ticket.getEventName());
        holder.eventDate.setText(ticket.getEventDate());
        holder.eventLocation.setText(ticket.getLocation());
        holder.ticketName.setText(ticket.getTicketName());
        holder.ticketId.setText(ticket.getId());
        if (ticket.getChecked()) {
            holder.ticketStatus.setText("USED");
            holder.ticketStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.ticketStatus.setText("ACTIVE");
            holder.ticketStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        }








    }

    @Override
    public int getItemCount() {
        if (tickets == null) {
            return 0;
        } else {
            return tickets.size();
        }
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView eventName, ticketName, eventDate, eventLocation, ticketId, ticketStatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventLocation = itemView.findViewById(R.id.event_location);
            ticketName = itemView.findViewById(R.id.ticket_name);
            ticketId = itemView.findViewById(R.id.ticket_id);
            ticketStatus = itemView.findViewById(R.id.ticket_status);






        }
    }




}
