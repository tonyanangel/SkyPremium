package com.skypremiuminternational.app.app.features.travel.booking;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.ean.Child;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class ChildViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.tv_child_name)
  TextView tvChildName;
  @BindView(R.id.tv_child_age)
  TextView tvChildAge;

  public ChildViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(final Child child) {
    tvChildAge.setText(child.age);
    tvChildName.setText(child.name);
  }
}
