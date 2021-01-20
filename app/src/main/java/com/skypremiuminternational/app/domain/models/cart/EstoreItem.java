package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by codigo on 6/3/18.
 */

@DatabaseTable(tableName = EstoreItem.TABLE_NAME)
public class EstoreItem implements Serializable {

  public static final String TABLE_NAME = "estore_item";
  public static final String FIELD_NAME_SKU = "sku";
  public static final String FIELD_NAME_IMAGE_URL = "image_url";
  public static final String FIELD_NAME_CATEGORY = "category";

  @DatabaseField(columnName = FIELD_NAME_SKU, id = true, generatedId = false)
  private String sku;

  @DatabaseField(columnName = FIELD_NAME_IMAGE_URL)
  @SerializedName("image_url")
  private String imageUrl;

  @DatabaseField(columnName = FIELD_NAME_CATEGORY)
  private String category;

  public EstoreItem() {

  }

  public EstoreItem(String sku, String imageUrl, String category) {
    this.sku = sku;
    this.imageUrl = imageUrl;
    this.category = category;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getSku() {
    return sku;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return category;
  }
}
