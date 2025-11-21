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
import com.example.cannintickets.models.orders.OrderResponseModel;
import com.example.cannintickets.models.usertickets.UserTicketsResponseModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class ManageTicketAdapter extends RecyclerView.Adapter<ManageTicketAdapter.viewholder>{


    List<UserTicketsResponseModel> tickets = new ArrayList<>();


    public interface OnTicketToggleListener {
        void onTicketToggle(UserTicketsResponseModel ticket, boolean isChecked, int position);
    }

    private final OnTicketToggleListener toggleListener;



    public ManageTicketAdapter(List<UserTicketsResponseModel> tickets,
                               OnTicketToggleListener toggleListener) {
        this.tickets = tickets;
        this.toggleListener = toggleListener;
    }



    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_ticket_item,parent,false);


        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        UserTicketsResponseModel ticket = tickets.get(position);




        holder.userEmail.setText(ticket.getUserEmail());
        holder.ticketId.setText(ticket.getId());

        holder.switchMaterial.setChecked(ticket.getChecked());

        holder.switchMaterial.setOnCheckedChangeListener(null); // important for recycling
        holder.switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int adapterPosition = holder.getBindingAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && toggleListener != null) {
                toggleListener.onTicketToggle(ticket, isChecked, adapterPosition);
            }
        });
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
        TextView userEmail, ticketId;
        SwitchMaterial switchMaterial;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            userEmail = itemView.findViewById(R.id.user_email);
            ticketId = itemView.findViewById(R.id.ticket_id);
            switchMaterial = itemView.findViewById(R.id.material_switch);


        }
    }




}
