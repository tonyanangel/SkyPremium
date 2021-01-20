package com.skypremiuminternational.app.app.features.checkout.room.stepthree;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.booking.GuestDetail;

import java.util.ArrayList;
import java.util.List;

public class GuestDetailAdapter extends RecyclerView.Adapter<GuestDetailViewHolder> {
  private List<GuestDetail> guestDetails = new ArrayList<>();

  @Override
  public GuestDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_guest_detail, viewGroup, false);
    return new GuestDetailViewHolder(view);
  }

  @Override
  public void onBindViewHolder(GuestDetailViewHolder guestDetailViewHolder, int position) {
    guestDetailViewHolder.bind(guestDetails.get(position));
  }

  public void setGuestDetails(List<GuestDetail> guestDetails) {
    this.guestDetails.clear();
    this.guestDetails.addAll(guestDetails);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return guestDetails != null ? guestDetails.size() : 0;
  }
}
