package com.skypremiuminternational.app.app.features.travel.ean;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.ViewRatioUtils;
import com.skypremiuminternational.app.domain.models.ean.Property;

/**
 * Created by codigo on 19/4/18.
 */

public class PropertyViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.ly_root)
  ViewGroup lyRoot;
  @BindView(R.id.iv_property)
  ImageView ivProperty;
  @BindView(R.id.tv_property_name)
  TextView tvPropertyName;
  @BindView(R.id.tv_type)
  TextView tvType;

  public PropertyViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(Property property) {
    ViewRatioUtils.setProduct(lyRoot, ivProperty, itemView.getContext());
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.centerCrop();
    requestOptions.error(R.drawable.white);
    Glide.with(itemView.getContext())
        .load(property.image())
        .apply(requestOptions)
        .into(ivProperty);

    tvPropertyName.setText(property.name());
    String type = property.type() + " - " + property.city();
    tvType.setText(type);
  }
}
