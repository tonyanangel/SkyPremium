package com.skypremiuminternational.app.app.features.travel.ean;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.domain.models.ean.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codigo on 19/4/18.
 */

public class PropertyAdapter extends RecyclerView.Adapter<PropertyViewHolder> {
  private List<Property> properties = new ArrayList<>();
  private ProductActionListener<Property> actionListener;

  @Override
  public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property, parent, false);
    PropertyViewHolder viewHolder = new PropertyViewHolder(view);
    viewHolder.itemView.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onItemClicked(properties.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(PropertyViewHolder holder, int position) {
    holder.bind(properties.get(position));
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return properties == null ? 0 : properties.size();
  }

  public void setActionListener(ProductActionListener<Property> actionListener) {
    this.actionListener = actionListener;
  }
}
