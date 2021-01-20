package com.skypremiuminternational.app.app.features.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.home.Banner;

import java.util.ArrayList;
import java.util.List;

import shivam.developer.featuredrecyclerview.FeatureRecyclerViewAdapter;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class HomeCategoryAdapter extends FeatureRecyclerViewAdapter<HomeCategoryViewHolder> {

  private List<Banner> dataList = new ArrayList<>();

  int VIEW_TYPE_MAIN = 1;

  public HomeCategoryAdapter() {
  }

  public List<Banner> getDataList() {
    return dataList;
  }

  public void setDataList(List<Banner> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  @Override
  public HomeCategoryViewHolder onCreateFeaturedViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_MAIN) {
      return new HomeCategoryViewHolder(LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_home_category_main, parent, false));
    } else {
      return new HomeCategoryViewHolder(LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_home_category, parent, false));
    }
  }

  @Override
  public void onBindFeaturedViewHolder(HomeCategoryViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  public Banner getItem(int position) {
    return dataList.get(position);
  }

  @Override
  public int getFeaturedItemsCount() {
    return dataList.size();
  }

  @Override
  public void onSmallItemResize(HomeCategoryViewHolder holder, int position, float offset) {
    //holder.tvHeading.setAlpha(offset / 100f);
  }

  @Override
  public void onBigItemResize(HomeCategoryViewHolder holder, int position, float offset) {
    //holder.tvHeading.setAlpha(offset / 100f);
  }

  @Override
  public int getItemViewType(int position) {
    if (dataList.get(position).getIsMain().equalsIgnoreCase("1")) {
      return VIEW_TYPE_MAIN;
    }
    return super.getItemViewType(position);
  }
}
