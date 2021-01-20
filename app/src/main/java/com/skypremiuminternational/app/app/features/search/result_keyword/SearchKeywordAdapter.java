package com.skypremiuminternational.app.app.features.search.result_keyword;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandi on 1/16/17.
 */

public class SearchKeywordAdapter extends RecyclerView.Adapter<SearchKeywordViewHolder> {

  private List<String> dataList = new ArrayList<>();

  public SearchKeywordAdapter() {
  }

  public List<String> getDataList() {
    return dataList;
  }

  public void setDataList(List<String> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  public String getItem(int position) {
    return dataList.get(position);
  }

  @Override
  public SearchKeywordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new SearchKeywordViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_search_keyword, parent, false));
  }

  @Override
  public void onBindViewHolder(SearchKeywordViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }

  public void addAll(List<String> dataObj) {
    dataList.addAll(dataObj);
    notifyDataSetChanged();
  }
}

