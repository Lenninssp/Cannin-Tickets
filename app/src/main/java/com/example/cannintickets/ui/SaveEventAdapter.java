package com.example.cannintickets.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.models.events.get.GetEventResponseModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SaveEventAdapter extends RecyclerView.Adapter<SaveEventAdapter.viewholder> {

    List<GetEventResponseModel> events = new ArrayList<>();

    private final Set<String> likedEventIds = new HashSet<>();

    public interface OnLikeClickListener {
        void onLikeClick(int position);
    }

    private OnLikeClickListener likeListener;

    public void setOnLikeClickListener(OnLikeClickListener likeListener) {
        this.likeListener = likeListener;
    }

    public SaveEventAdapter(List<GetEventResponseModel> events) {
        this.events = events;
    }

    public void setAllLiked() {
        likedEventIds.clear();
        for (GetEventResponseModel e : events) {
            likedEventIds.add(e.getId());
        }
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_event_item, parent, false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        GetEventResponseModel event = events.get(position);

        holder.eventName.setText(event.getName());
        holder.eventDate.setText(event.getEventDate());

        if (event.getCoverImage() != null && event.getCoverImage().exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(event.getCoverImage().getAbsolutePath());
            holder.eventImage.setImageBitmap(bitmap);
        }

        boolean liked = likedEventIds.contains(event.getId());
        int colorRes = liked ? R.color.orange : R.color.white;

        holder.likeButton.setColorFilter(
                ContextCompat.getColor(holder.itemView.getContext(), colorRes)
        );
    }

    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView eventName, eventDate;
        ImageView eventImage;
        ImageButton likeButton;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventImage = itemView.findViewById(R.id.event_image);
            likeButton = itemView.findViewById(R.id.save_button);

            likeButton.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position == RecyclerView.NO_POSITION) return;

                GetEventResponseModel event = events.get(position);
                String id = event.getId();

                if (likedEventIds.contains(id)) {
                    likedEventIds.remove(id);
                } else {
                    likedEventIds.add(id);
                }
                notifyItemChanged(position);

                if (likeListener != null) {
                    likeListener.onLikeClick(position);
                }
            });
        }
    }
}
