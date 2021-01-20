package com.skypremiuminternational.app.app.features.demo_two_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.domain.models.demo.Item;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.ArrayList;
import java.util.List;

public class DemoTwoAdapter extends RecyclerView.Adapter<DemoViewHolder>{
    private List<Item> itemList = new ArrayList<>();
    private ProductActionListener<Item> actionListener;

    public void setActionListener(ProductActionListener<Item> actionListener) {
        this.actionListener = actionListener;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.demo_two_checkbox,parent,false);
        DemoViewHolder holder = new DemoViewHolder(view);

        holder.lnlLayout.setOnClickListener(v -> {
            if (actionListener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                actionListener.onItemClicked(itemList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DemoViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        if(itemList!=null&&itemList.size()>0)
            return itemList.size();
        return 0;
    }

}
