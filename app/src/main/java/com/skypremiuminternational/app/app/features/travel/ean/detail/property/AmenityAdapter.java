package com.skypremiuminternational.app.app.features.travel.ean.detail.property;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.data.model.ean.content.Amenities;
import com.skypremiuminternational.app.domain.models.ean.Amenity;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.Room;

import java.util.ArrayList;
import java.util.List;

public class AmenityAdapter extends RecyclerView.Adapter<AmenityViewHolder> {
  private List<Amenity> amenities = new ArrayList<>();

  public Room room;
  public List<CancelsPenalties> cancelsPenaltiesList;
  public List<CancelPenalty> cancelPenaltyList;

  private ActionListener actionListener;

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  @Override
  public AmenityViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_amenity, viewGroup, false);
    AmenityViewHolder viewHolder = new AmenityViewHolder(view);
    viewHolder.tvAmenity.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {

        if(viewHolder.getAdapterPosition() == (amenities.size()-1)) {
          actionListener.onClicked(amenities.get(amenities.size() - 1));
        }

      }
    });


    return viewHolder;
  }

  @Override
  public void onBindViewHolder(AmenityViewHolder amenityViewHolder, int position) {

      amenityViewHolder.bind(amenities.get(position), cancelsPenaltiesList, room, amenities.size(), position,cancelPenaltyList);

  }

  public void setAmenities(List<Amenity> amenities) {
    this.amenities.clear();
    this.amenities.addAll(amenities);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return amenities != null ? amenities.size() : 0;
  }

  public interface ActionListener {
    void onClicked(Amenity amenity);
  }
}
