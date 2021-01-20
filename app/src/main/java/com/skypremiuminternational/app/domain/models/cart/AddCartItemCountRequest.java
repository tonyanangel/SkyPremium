package com.skypremiuminternational.app.domain.models.cart;

import androidx.annotation.Nullable;

/**
 * Created by codigo on 9/2/18.
 */

public class AddCartItemCountRequest {

  public final Cart cartItem;

  public AddCartItemCountRequest(Cart cartItem) {
    this.cartItem = cartItem;
  }

  public static class Cart {
    @Nullable
    public final Long item_id;
    public final String sku;
    public final int qty;
    public final String name;
    public final String product_type;
    public final String quote_id;

    public Cart(Long item_id, String sku, int qty, String name, String product_type,
                String quote_id) {
      this.item_id = item_id;
      this.sku = sku;
      this.qty = qty;
      this.name = name;
      this.product_type = product_type;
      this.quote_id = quote_id;
    }
  }
}
