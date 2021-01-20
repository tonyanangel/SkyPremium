package com.skypremiuminternational.app.app.features.estore;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by codigo on 4/2/18.
 */

public class FeaturedEstoreAdapter extends RecyclerView.Adapter<FeaturedEstoreViewHolder> {

  private final CompositeSubscription compositeSubscription = new CompositeSubscription();
  private List<ItemsItem> itemsItems = new ArrayList<>();
  private ProductActionListener<ItemsItem> actionListener;

  public void setActionListener(ProductActionListener<ItemsItem> actionListener) {
    this.actionListener = actionListener;
  }

  @Override
  public FeaturedEstoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_featured_estore, parent, false);

    final FeaturedEstoreViewHolder viewHolder = new FeaturedEstoreViewHolder(view);
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          actionListener.onItemClicked(itemsItems.get(viewHolder.getAdapterPosition()),
              viewHolder.getAdapterPosition());
        }
      }
    });

    viewHolder.ivFav.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          actionListener.onFavItemClicked(itemsItems.get(viewHolder.getAdapterPosition()),
              viewHolder.getAdapterPosition());
        }
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(FeaturedEstoreViewHolder holder, int position) {
    Subscription subscription = holder.bind(itemsItems.get(position));
    if (subscription != null) {
      compositeSubscription.add(subscription);
    }
  }

  @Override
  public int getItemCount() {
    return itemsItems != null ? itemsItems.size() : 0;
  }

  public void setData(List<ItemsItem> featureItems) {
    itemsItems = featureItems;
    notifyDataSetChanged();
  }

  public void unSubscribe() {
    if (compositeSubscription.hasSubscriptions()) {
      compositeSubscription.clear();
    }
  }
}
