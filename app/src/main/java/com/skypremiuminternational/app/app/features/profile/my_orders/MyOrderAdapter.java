package com.skypremiuminternational.app.app.features.profile.my_orders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.listener.ItemClickListener;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderViewHolder> {

  private List<MyOrderItem> dataList = new ArrayList<>();
  private ItemClickListener<MyOrderItem> itemClickListener;

  public void setDataList(List<MyOrderItem> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  public void setItemClickListener(ItemClickListener<MyOrderItem> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  @Override
  public MyOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_order, parent, false);
    final MyOrderViewHolder viewHolder = new MyOrderViewHolder(view);
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (itemClickListener != null
            && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          itemClickListener.onItemClicked(dataList.get(viewHolder.getAdapterPosition()));
        }
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(MyOrderViewHolder holder, final int position) {
    holder.bind(dataList.get(position));
  }

  @Override
  public int getItemCount() {
    return dataList != null ? dataList.size() : 0;
  }
}
