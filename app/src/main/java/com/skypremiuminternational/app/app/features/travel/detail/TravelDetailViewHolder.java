package com.skypremiuminternational.app.app.features.travel.detail;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

import com.skypremiuminternational.app.domain.models.travel.TravelProduct;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class TravelDetailViewHolder extends RecyclerView.ViewHolder {

  private TravelProduct travelProduct;

  public TravelDetailViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(final TravelProduct item) {
    this.travelProduct = item;
  }
}
