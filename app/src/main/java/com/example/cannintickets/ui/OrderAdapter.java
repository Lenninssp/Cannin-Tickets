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

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewholder>{


    List<OrderResponseModel> orders = new ArrayList<>();






    public OrderAdapter(List<OrderResponseModel> orders) {
        this.orders = orders;
    }








    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);


        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        OrderResponseModel order = orders.get(position);




        holder.eventName.setText(order.getUserEmail());
        holder.createdAt.setText(String.valueOf(order.getCreatedAt()));
        holder.ticketType.setText(order.getTicketName());
        holder.ticketPrice.setText(String.valueOf(order.getTicketPrice()));
        holder.ticketQuantity.setText(String.valueOf(order.getQuantity()));
        holder.totalPrice.setText(String.valueOf(order.getTotal()));






    }

    @Override
    public int getItemCount() {
        if (orders == null) {
            return 0;
        } else {
            return orders.size();
        }
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView eventName, createdAt, ticketType, ticketPrice, ticketQuantity, totalPrice;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            createdAt = itemView.findViewById(R.id.order_date);
            ticketType = itemView.findViewById(R.id.ticket_type);
            ticketPrice = itemView.findViewById(R.id.ticket_price);
            ticketQuantity = itemView.findViewById(R.id.ticket_quantity);
            totalPrice = itemView.findViewById(R.id.order_total);






        }
    }




}
