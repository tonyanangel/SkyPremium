package com.skypremiuminternational.app.app.features.booking.summary;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codigo on 22/6/18.
 */

public class TourismFeeRecyclerAdapter extends RecyclerView.Adapter<TourismFeeViewHolder> {

  private List<TourismFee> fees = new ArrayList<>();

  public void setTourismFees(List<TourismFee> fees) {
    this.fees.clear();
    this.fees.addAll(fees);
    notifyDataSetChanged();
  }

  @Override
  public TourismFeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_tourism_fees, parent, false);
    return new TourismFeeViewHolder(view);
  }

  @Override
  public void onBindViewHolder(TourismFeeViewHolder holder, int position) {
    holder.bind(fees.get(position));
  }

  @Override
  public int getItemCount() {
    return fees.size();
  }
}
