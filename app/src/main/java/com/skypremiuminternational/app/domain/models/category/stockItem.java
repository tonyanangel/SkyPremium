package com.skypremiuminternational.app.domain.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class stockItem {
  
  @SerializedName("product_id")
  @Expose
  private Integer productId;
  @SerializedName("stock_id")
  @Expose
  private Integer stockId;
  @SerializedName("qty")
  @Expose
  private String qty;
  @SerializedName("is_in_stock")
  @Expose
  private Integer isInStock;
  @SerializedName("is_qty_decimal")
  @Expose
  private Integer isQtyDecimal;
  @SerializedName("show_default_notification_message")
  @Expose
  private Integer showDefaultNotificationMessage;
  @SerializedName("use_config_min_qty")
  @Expose
  private String useConfigMinQty;
  @SerializedName("min_qty")
  @Expose
  private String minQty;
  @SerializedName("use_config_min_sale_qty")
  @Expose
  private String useConfigMinSaleQty;
  @SerializedName("max_sale_qty")
  @Expose
  private Integer max_sale_qty;
  @SerializedName("use_config_backorders")
  @Expose
  private Integer use_config_backorders;
  @SerializedName("backorders")
  @Expose
  private String backorders;
  @SerializedName("use_config_notify_stock_qty")
  @Expose
  private Integer use_config_notify_stock_qty;
  @SerializedName("notify_stock_qty")
  @Expose
  private Integer notify_stock_qty;
  @SerializedName("use_config_qty_increments")
  @Expose
  private Integer use_config_qty_increments;
  @SerializedName("qty_increments")
  @Expose
  private String qty_increments;
  @SerializedName("use_config_enable_qty_inc")
  @Expose
  private String use_config_enable_qty_inc;
  @SerializedName("enable_qty_increments")
  @Expose
  private String enable_qty_increments;
  @SerializedName("use_config_manage_stock")
  @Expose
  private String use_config_manage_stock;
  @SerializedName("manage_stock")
  @Expose
  private Integer manage_stock;
  @SerializedName("low_stock_date")
  @Expose
  private Integer low_stock_date;
  @SerializedName("is_decimal_divided")
  @Expose
  private Integer is_decimal_divided;
  @SerializedName("stock_status_changed_auto")
  @Expose
  private String stock_status_changed_auto;
  
  
  public Integer getProductId() {
    return productId;
  }
  
  public void setProductId(Integer productId) {
    this.productId = productId;
  }
  
  public Integer getStockId() {
    return stockId;
  }
  
  public void setStockId(Integer stockId) {
    this.stockId = stockId;
  }
  
  public String getQty() {
    return qty;
  }
  
  public void setQty(String qty) {
    this.qty = qty;
  }
  
  public Integer getIsInStock() {
    return isInStock;
  }
  
  public void setIsInStock(Integer isInStock) {
    this.isInStock = isInStock;
  }
  
  public Integer getIsQtyDecimal() {
    return isQtyDecimal;
  }
  
  public void setIsQtyDecimal(Integer isQtyDecimal) {
    this.isQtyDecimal = isQtyDecimal;
  }
  
  public Integer getShowDefaultNotificationMessage() {
    return showDefaultNotificationMessage;
  }
  
  public void setShowDefaultNotificationMessage(Integer showDefaultNotificationMessage) {
    this.showDefaultNotificationMessage = showDefaultNotificationMessage;
  }
  
  public String getUseConfigMinQty() {
    return useConfigMinQty;
  }
  
  public void setUseConfigMinQty(String useConfigMinQty) {
    this.useConfigMinQty = useConfigMinQty;
  }
  
  public String getMinQty() {
    return minQty;
  }
  
  public void setMinQty(String minQty) {
    this.minQty = minQty;
  }
  
  public String getUseConfigMinSaleQty() {
    return useConfigMinSaleQty;
  }
  
  public void setUseConfigMinSaleQty(String useConfigMinSaleQty) {
    this.useConfigMinSaleQty = useConfigMinSaleQty;
  }
  
  public Integer getMax_sale_qty() {
    return max_sale_qty;
  }
  
  public void setMax_sale_qty(Integer max_sale_qty) {
    this.max_sale_qty = max_sale_qty;
  }
  
  public Integer getUse_config_backorders() {
    return use_config_backorders;
  }
  
  public void setUse_config_backorders(Integer use_config_backorders) {
    this.use_config_backorders = use_config_backorders;
  }
  
  public String getBackorders() {
    return backorders;
  }
  
  public void setBackorders(String backorders) {
    this.backorders = backorders;
  }
  
  public Integer getUse_config_notify_stock_qty() {
    return use_config_notify_stock_qty;
  }
  
  public void setUse_config_notify_stock_qty(Integer use_config_notify_stock_qty) {
    this.use_config_notify_stock_qty = use_config_notify_stock_qty;
  }
  
  public Integer getNotify_stock_qty() {
    return notify_stock_qty;
  }
  
  public void setNotify_stock_qty(Integer notify_stock_qty) {
    this.notify_stock_qty = notify_stock_qty;
  }
  
  public Integer getUse_config_qty_increments() {
    return use_config_qty_increments;
  }
  
  public void setUse_config_qty_increments(Integer use_config_qty_increments) {
    this.use_config_qty_increments = use_config_qty_increments;
  }
  
  public String getQty_increments() {
    return qty_increments;
  }
  
  public void setQty_increments(String qty_increments) {
    this.qty_increments = qty_increments;
  }
  
  public String getUse_config_enable_qty_inc() {
    return use_config_enable_qty_inc;
  }
  
  public void setUse_config_enable_qty_inc(String use_config_enable_qty_inc) {
    this.use_config_enable_qty_inc = use_config_enable_qty_inc;
  }
  
  public String getEnable_qty_increments() {
    return enable_qty_increments;
  }
  
  public void setEnable_qty_increments(String enable_qty_increments) {
    this.enable_qty_increments = enable_qty_increments;
  }
  
  public String getUse_config_manage_stock() {
    return use_config_manage_stock;
  }
  
  public void setUse_config_manage_stock(String use_config_manage_stock) {
    this.use_config_manage_stock = use_config_manage_stock;
  }
  
  public Integer getManage_stock() {
    return manage_stock;
  }
  
  public void setManage_stock(Integer manage_stock) {
    this.manage_stock = manage_stock;
  }
  
  public Integer getLow_stock_date() {
    return low_stock_date;
  }
  
  public void setLow_stock_date(Integer low_stock_date) {
    this.low_stock_date = low_stock_date;
  }
  
  public Integer getIs_decimal_divided() {
    return is_decimal_divided;
  }
  
  public void setIs_decimal_divided(Integer is_decimal_divided) {
    this.is_decimal_divided = is_decimal_divided;
  }
  
  public String getStock_status_changed_auto() {
    return stock_status_changed_auto;
  }
  
  public void setStock_status_changed_auto(String stock_status_changed_auto) {
    this.stock_status_changed_auto = stock_status_changed_auto;
  }
}
