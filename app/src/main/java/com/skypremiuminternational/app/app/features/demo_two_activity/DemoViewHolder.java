package com.skypremiuminternational.app.app.features.demo_two_activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;

public class DemoViewHolder  extends RecyclerView.ViewHolder {

    View lnlLayout;

    public DemoViewHolder(@NonNull View itemView) {
        super(itemView);
        lnlLayout = itemView.findViewById(R.id.lnl_item_view);

    }
}
