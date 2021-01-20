package com.skypremiuminternational.app.app.features.travel.detail;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.travel.TravelProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class TravelDetailAdapter extends RecyclerView.Adapter<TravelDetailViewHolder> {

  private List<TravelProduct> dataList = new ArrayList<>();

  public TravelDetailAdapter() {
  }

  public List<TravelProduct> getDataList() {
    return dataList;
  }

  public void setDataList(List<TravelProduct> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  public TravelProduct getItem(int position) {
    return dataList.get(position);
  }

  @Override
  public TravelDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TravelDetailViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_travel_detail, parent, false));
  }

  @Override
  public void onBindViewHolder(TravelDetailViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }

  public void addAll(List<TravelProduct> dataObj) {
    dataList.addAll(dataObj);
    notifyDataSetChanged();
  }
}
