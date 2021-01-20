package com.skypremiuminternational.app.app.features.profile.order.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.checkout.steptwo.CheckoutReviewViewHolder;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.domain.models.myOrder.detail.ItemsItem;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailItemAdapter  extends RecyclerView.Adapter<OrderDetailItemViewHolder> {
  private List<ItemsItem> dataList;
  FragmentManager fragmentManager;

  String status = Constants.ORDER_PROCESSING;

  public OrderDetailItemAdapter(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
    dataList = new ArrayList<>();
  }

  public void setDataList(List<ItemsItem> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  @Override public OrderDetailItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_order_detail, parent, false);
    final OrderDetailItemViewHolder holder = new OrderDetailItemViewHolder(view);
    return holder;
  }

  @Override public void onBindViewHolder(OrderDetailItemViewHolder holder, int position) {
    holder.bind(fragmentManager,dataList.get(position), position,this.status);
  }

  @Override public int getItemCount() {
    return dataList != null ? dataList.size() : 0;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
