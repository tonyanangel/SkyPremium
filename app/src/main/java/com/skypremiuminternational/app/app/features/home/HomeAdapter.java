package com.skypremiuminternational.app.app.features.home;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.home.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeCategoryViewHolder> {

  private List<Banner> dataList = new ArrayList<>();

  public static int VIEW_TYPE_MAIN = 1;

  private int height;

  public HomeAdapter() {
  }

  public List<Banner> getDataList() {
    return dataList;
  }

  public void setDataList(List<Banner> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  @Override
  public HomeCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_MAIN) {
      return new HomeCategoryViewHolder(LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_home_category_main, parent, false));
    } else {
      return new HomeCategoryViewHolder(LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_home_category, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(HomeCategoryViewHolder holder, int position) {
    holder.bind(getItem(position),getItemHeight(),getItemViewType(position));
  }

  public void setHeight(int height) {
    this.height = height;
    notifyDataSetChanged();
  }
  public int getHeight(){
    return this.height;
  }

  private int getItemHeight(){
    int itemHeight;

    itemHeight = this.height/6;

    return  itemHeight;

  }

  public Banner getItem(int position) {
    return dataList.get(position);
  }

  @Override
  public int getItemViewType(int position) {
    if (dataList.get(position).getIsMain().equalsIgnoreCase("1")) {
      return VIEW_TYPE_MAIN;
    }
    return super.getItemViewType(position);
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }
}
