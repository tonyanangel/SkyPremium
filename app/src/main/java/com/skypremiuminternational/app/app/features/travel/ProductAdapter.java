package com.skypremiuminternational.app.app.features.travel;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<TravelProductViewHolder> {

  private List<ItemsItem> dataList = new ArrayList<>();
  private ProductActionListener<ItemsItem> actionListener;
  private boolean fromTravel = false;
  private String pillar = "";


  public void setFromTravel(boolean fromTravel){
    this.fromTravel = fromTravel;
  }
  public void setPillar(String pillar){
    this.pillar = pillar;
  }
  public void setActionListener(ProductActionListener<ItemsItem> actionListener) {
    this.actionListener = actionListener;
  }

  public List<ItemsItem> getDataList() {
    return dataList;
  }

  public void setDataList(List<ItemsItem> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  public ItemsItem getItem(int position) {
    return dataList.get(position);
  }

  @Override
  public TravelProductViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_travel_product, parent, false);
    final TravelProductViewHolder viewHolder = new TravelProductViewHolder(view,fromTravel,pillar);
    viewHolder.itemView.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onItemClicked(dataList.get(viewHolder.getAdapterPosition()),
            viewHolder.getAdapterPosition());
      }
    });
    viewHolder.tvReserve.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onReserveButton(dataList.get(viewHolder.getAdapterPosition()),
            viewHolder.getAdapterPosition(),viewHolder);
      }
    });

    viewHolder.ivFavourite.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onFavItemClicked(dataList.get(viewHolder.getAdapterPosition()),
            viewHolder.getAdapterPosition());
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(TravelProductViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }

  public List<ItemsItem> getData(){
    return this.dataList;
  }

  public void addAll(List<ItemsItem> dataObj) {
    dataList.addAll(dataObj);
    notifyDataSetChanged();
  }
}
