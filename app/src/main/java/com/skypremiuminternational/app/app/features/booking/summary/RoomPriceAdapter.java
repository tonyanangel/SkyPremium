package com.skypremiuminternational.app.app.features.booking.summary;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.booking.GuestDetail;

import java.util.ArrayList;
import java.util.List;

public class RoomPriceAdapter extends RecyclerView.Adapter<RoomPriceViewHolder> {
  private List<GuestDetail> guestDetails = new ArrayList<>();

  private String night;

  @Override
  public RoomPriceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_room_price, viewGroup, false);
    return new RoomPriceViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RoomPriceViewHolder roomPriceViewHolder, int position) {
    roomPriceViewHolder.bind(guestDetails.get(position),night);
  }

  @Override
  public int getItemCount() {
    return guestDetails == null ? 0 : guestDetails.size();
  }

  public void setNight(String night){
    this.night = night;
  }
  public void setGuestDetails(List<GuestDetail> guestDetails) {
    this.guestDetails.clear();
    this.guestDetails.addAll(guestDetails);
    notifyDataSetChanged();
  }
}
