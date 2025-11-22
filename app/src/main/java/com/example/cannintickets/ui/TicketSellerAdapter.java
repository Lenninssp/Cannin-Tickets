package com.example.cannintickets.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.models.tickets.get.GetTicketResponseModel;

import java.util.ArrayList;
import java.util.List;

public class TicketSellerAdapter extends RecyclerView.Adapter<TicketSellerAdapter.ViewHolder> {

    private final List<GetTicketResponseModel> tickets = new ArrayList<>();

    public interface OnDeleteClickListener {
        void onDeleteClick(GetTicketResponseModel ticket, int position);
    }

    private OnDeleteClickListener deleteListener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }

    public TicketSellerAdapter(List<GetTicketResponseModel> initialTickets) {
        if (initialTickets != null) {
            tickets.addAll(initialTickets);
        }
    }

    public void setTickets(List<GetTicketResponseModel> newTickets) {
        tickets.clear();
        if (newTickets != null) {
            tickets.addAll(newTickets);
        }
        notifyDataSetChanged();
    }

    public GetTicketResponseModel getTicketAt(int position) {
        if (position < 0 || position >= tickets.size()) return null;
        return tickets.get(position);
    }

    public void removeTicketAt(int position) {
        if (position < 0 || position >= tickets.size()) return;
        tickets.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public TicketSellerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketSellerAdapter.ViewHolder holder, int position) {
        GetTicketResponseModel ticket = tickets.get(position);

        holder.ticketName.setText(ticket.getName());
        holder.ticketPrice.setText(String.valueOf(ticket.getPrice()));

        holder.ticketQuantity.setText(String.valueOf(ticket.getCapacity()));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ticketName;
        TextView ticketPrice;
        TextView ticketQuantity;
        Button ticketDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ticketName     = itemView.findViewById(R.id.ticket_name);
            ticketPrice    = itemView.findViewById(R.id.ticket_price);
            ticketQuantity = itemView.findViewById(R.id.ticket_quantity);
            ticketDelete   = itemView.findViewById(R.id.ticket_delete);

            ticketDelete.setOnClickListener(v -> {
                if (deleteListener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        deleteListener.onDeleteClick(tickets.get(position), position);
                    }
                }
            });
        }
    }
}
