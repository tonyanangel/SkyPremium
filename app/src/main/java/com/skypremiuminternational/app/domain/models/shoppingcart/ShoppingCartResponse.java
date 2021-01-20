package com.skypremiuminternational.app.domain.models.shoppingcart;

/**
 * Created by aeindraaung on 1/27/18.
 */

public class ShoppingCartResponse {
  private int id;
  private int img;
  private String productName;
  private String kindOfProduct;
  private int productCost;
  private int productQty;
  private int totalAmount;

  public ShoppingCartResponse(int id, int img, String productName, String kindOfProduct,
                              int productCost, int productQty, int totalAmount) {
    this.id = id;
    this.img = img;
    this.productName = productName;
    this.kindOfProduct = kindOfProduct;
    this.productCost = productCost;
    this.productQty = productQty;
    this.totalAmount = totalAmount;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getImg() {
    return img;
  }

  public void setImg(int img) {
    this.img = img;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getKindOfProduct() {
    return kindOfProduct;
  }

  public void setKindOfProduct(String kindOfProduct) {
    this.kindOfProduct = kindOfProduct;
  }

  public int getProductCost() {
    return productCost;
  }

  public void setProductCost(int productCost) {
    this.productCost = productCost;
  }

  public int getProductQty() {
    return productQty;
  }

  public void setProductQty(int productQty) {
    this.productQty = productQty;
  }

  public int getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(int totalAmount) {
    this.totalAmount = totalAmount;
  }
}
