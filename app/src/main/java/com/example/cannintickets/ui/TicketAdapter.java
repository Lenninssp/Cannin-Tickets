package com.example.cannintickets.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.models.tickets.get.GetTicketResponseModel;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.viewholder>{

    List<GetTicketResponseModel> tickets = new ArrayList<>();
    List<Integer> quantities = new ArrayList<>();

    public TicketAdapter(List<GetTicketResponseModel> tickets) {
        setTickets(tickets);
    }

    public void setTickets(List<GetTicketResponseModel> newTickets) {
        this.tickets.clear();
        this.tickets.addAll(newTickets);

        quantities.clear();
        for (int i = 0; i < this.tickets.size(); i++) {
            quantities.add(0);
        }

        notifyDataSetChanged();
    }

    public List<GetTicketResponseModel> getTickets() {
        return tickets;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_ticket_item, parent, false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        GetTicketResponseModel ticket = tickets.get(position);

        holder.ticketname.setText(ticket.getName());
        holder.ticketprice.setText(Double.toString(ticket.getPrice()));

        int qty = quantities.get(position);
        holder.ticketCount.setText(String.valueOf(qty));
    }

    @Override
    public int getItemCount() {
        return tickets == null ? 0 : tickets.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView ticketname;
        TextView ticketprice;
        TextView ticketCount;
        TextView ticketCountPlus;
        TextView ticketCountMinus;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            ticketname = itemView.findViewById(R.id.ticket_name);
            ticketprice = itemView.findViewById(R.id.ticket_price);
            ticketCount = itemView.findViewById(R.id.ticket_count);
            ticketCountPlus = itemView.findViewById(R.id.ticket_count_plus);
            ticketCountMinus = itemView.findViewById(R.id.ticket_count_minus);

            ticketCountPlus.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position == RecyclerView.NO_POSITION) return;

                int current = quantities.get(position);
                current++;
                quantities.set(position, current);
                ticketCount.setText(String.valueOf(current));
            });

            ticketCountMinus.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position == RecyclerView.NO_POSITION) return;

                int current = quantities.get(position);
                if (current > 0) {
                    current--;
                    quantities.set(position, current);
                    ticketCount.setText(String.valueOf(current));
                }
            });
        }
    }
}
