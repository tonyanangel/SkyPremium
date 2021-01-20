package com.skypremiuminternational.app.domain.models.cart;

/**
 * Created by aeindraaung on 2/22/18.
 */

public class UpdateItemCountRequest {
  public final UpdateItemCountRequest.Cart cartItem;

  public UpdateItemCountRequest(UpdateItemCountRequest.Cart cartItem) {
    this.cartItem = cartItem;
  }

  public static class Cart {
    public final String sku;
    public  int qty;
    public final String name;
    public final String price;
    public final String product_type;
    public final String quote_id;

    public Cart(String sku, int qty, String name, String price, String product_type, String quote_id) {
      this.sku = sku;
      this.qty = qty;
      this.name = name;
      this.price = price;
      this.product_type = product_type;
      this.quote_id = quote_id;
    }
  }
}
