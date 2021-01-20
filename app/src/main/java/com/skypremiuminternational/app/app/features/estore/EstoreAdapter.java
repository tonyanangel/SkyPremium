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

public class EstoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final CompositeSubscription compositeSubscription = new CompositeSubscription();
  private List<ItemsItem> itemsItems = new ArrayList<>();
  private ProductActionListener<ItemsItem> actionListener;
  private  IOnClickAddToCart iOnClickAddToCart;
  private  IOnClickBuyNow iOnClickBuyNow;

  public void setActionListener(ProductActionListener<ItemsItem> actionListener) {
    this.actionListener = actionListener;
  }

  public EstoreAdapter (IOnClickAddToCart iOnClickAddToCart, IOnClickBuyNow iOnClickBuyNow){
    this.iOnClickAddToCart = iOnClickAddToCart;
    this.iOnClickBuyNow = iOnClickBuyNow;
  }


  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;
    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estore, parent, false);
    final EstoreViewHolder vh = new EstoreViewHolder(view , itemsItems,this.iOnClickAddToCart,this.iOnClickBuyNow);
    vh.itemView.setOnClickListener(v -> {
      if (actionListener != null && vh.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onItemClicked(itemsItems.get(vh.getAdapterPosition()), vh.getAdapterPosition());
      }
    });
    vh.ivFav.setOnClickListener(v -> {
      if (actionListener != null && vh.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onFavItemClicked(itemsItems.get(vh.getAdapterPosition()), vh.getAdapterPosition());
      }
    });
    return vh;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Subscription subscription = ((EstoreViewHolder) holder).bind(itemsItems.get(position));
    if (subscription != null) {
      compositeSubscription.add(subscription);
    }
  }

  @Override
  public int getItemCount() {
    return itemsItems == null ? 0 : itemsItems.size();
  }

  public void setData(List<ItemsItem> items) {
    itemsItems = items;
    notifyDataSetChanged();
  }
  public List<ItemsItem> getData() {
    return itemsItems;
  }

  public void unSubscribe() {
    if (compositeSubscription.hasSubscriptions()) {
      compositeSubscription.clear();
    }
  }

}
