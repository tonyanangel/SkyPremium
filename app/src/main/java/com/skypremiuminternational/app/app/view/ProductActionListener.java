package com.skypremiuminternational.app.app.view;

import com.skypremiuminternational.app.app.features.travel.TravelProductViewHolder;

/**
 * Created by codigo on 27/1/18.
 */

public interface ProductActionListener<T> {
  void onItemClicked(T item, int position);

  void onFavItemClicked(T item, int position);

  void onReserveButton(T item, int position, TravelProductViewHolder holder);
}
