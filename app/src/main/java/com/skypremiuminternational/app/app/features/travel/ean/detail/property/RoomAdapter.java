package com.skypremiuminternational.app.app.features.travel.ean.detail.property;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.ean.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codigo on 23/4/18.
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomViewHolder> {
  private List<Room> rooms = new ArrayList<>();

  private ActionListener actionListener;

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  @Override
  public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
    RoomViewHolder viewHolder = new RoomViewHolder(view);

    viewHolder.tvShowRoomDetails.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onShowDetailClicked(rooms.get(viewHolder.getAdapterPosition()));
      }
    });

    viewHolder.tvShowMoreAmenities.setOnClickListener(v -> {
      // TODO: 5/9/2018
    });

    viewHolder.tvCancellationPolicy.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onShowCancellationPolicyClicked(
            rooms.get(viewHolder.getAdapterPosition()));
      }
    });
    viewHolder.tvBookNow.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onBookNowClicked(rooms.get(viewHolder.getAdapterPosition()));
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RoomViewHolder holder, int position) {
    holder.bind(rooms.get(position), getItemCount());
  }

  public void setRooms(List<Room> rooms) {
    this.rooms = rooms;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return rooms == null ? 0 : rooms.size();
  }

  public interface ActionListener {
    void onShowDetailClicked(Room room);

    void onBookNowClicked(Room room);

    void onShowCancellationPolicyClicked(Room room);
  }
}
