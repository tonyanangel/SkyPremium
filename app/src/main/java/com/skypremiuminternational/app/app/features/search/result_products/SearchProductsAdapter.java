package com.skypremiuminternational.app.app.features.search.result_products;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class SearchProductsAdapter extends RecyclerView.Adapter<SearchProductsViewHolder> {

  private List<ItemsItem> dataList = new ArrayList<>();
  private String categoryID;
  private FragmentManager fm;

  public SearchProductsAdapter(String categoryID, FragmentManager fm) {
    this.categoryID = categoryID;
    this.fm = fm;
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
  public SearchProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new SearchProductsViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_shopping_product, parent, false), fm);
  }

  @Override
  public void onBindViewHolder(SearchProductsViewHolder holder, int position) {
    holder.bind(getItem(position), categoryID);
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }

  public void addAll(List<ItemsItem> dataObj) {
    dataList.addAll(dataObj);
    notifyDataSetChanged();
  }
}
