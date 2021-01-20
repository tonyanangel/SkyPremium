package com.skypremiuminternational.app.app.features.checkout.steptwo;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.myOrder.detail.ItemsItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aeindraaung on 2/4/18.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<CheckoutReviewViewHolder> {
  private List<ItemsItem> dataList;

  public OrderItemAdapter() {
    dataList = new ArrayList<>();
  }

  public void setDataList(List<ItemsItem> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  @Override public CheckoutReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_checkout_order_placed, parent, false);
    final CheckoutReviewViewHolder holder = new CheckoutReviewViewHolder(view);
    return holder;
  }

  @Override public void onBindViewHolder(CheckoutReviewViewHolder holder, int position) {
    holder.bind(dataList.get(position), position);
  }

  @Override public int getItemCount() {
    return dataList != null ? dataList.size() : 0;
  }
}
