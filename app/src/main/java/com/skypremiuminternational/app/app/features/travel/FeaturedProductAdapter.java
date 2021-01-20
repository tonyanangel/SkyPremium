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

public class FeaturedProductAdapter
    extends RecyclerView.Adapter<TravelProductFeaturedViewHolder> {

  private List<ItemsItem> dataList = new ArrayList<>();
  private ProductActionListener<ItemsItem> actionListener;

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

  @Override
  public TravelProductFeaturedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_travel_product_featured, parent, false);
    final TravelProductFeaturedViewHolder viewHolder = new TravelProductFeaturedViewHolder(view);
    viewHolder.itemView.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onItemClicked(dataList.get(viewHolder.getAdapterPosition()),
            viewHolder.getAdapterPosition());
      }
    });

    viewHolder.ivFav.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onFavItemClicked(dataList.get(viewHolder.getAdapterPosition()),
            viewHolder.getAdapterPosition());
      }
    });
    return viewHolder;
  }

  private ItemsItem getItem(int position) {
    return dataList.get(position);
  }

  @Override
  public void onBindViewHolder(TravelProductFeaturedViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }

  public void addItem(ItemsItem dataObj, int index) {
    dataList.add(dataObj);
    notifyItemInserted(index);
  }

  public void addAll(List<ItemsItem> dataObj) {
    dataList.addAll(dataObj);
    notifyDataSetChanged();
  }

  public void deleteItem(int index) {
    dataList.remove(index);
    notifyItemRemoved(index);
  }

  public void replaceAll(List<ItemsItem> dataObj) {
    dataList.clear();
    dataList.addAll(dataObj);
    notifyDataSetChanged();
  }
}
