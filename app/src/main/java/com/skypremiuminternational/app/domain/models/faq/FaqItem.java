package com.skypremiuminternational.app.domain.models.faq;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by sandi on 11/15/17.
 */

@DatabaseTable(tableName = FaqItem.TABLE_NAME)
public class FaqItem implements Serializable {

  public static final String TABLE_NAME = "Faq";
  public static final String FIELD_NAME_FAQ_ID = "faq_id";
  public static final String FIELD_NAME_TITLE = "title";
  public static final String FIELD_NAME_CONTENT = "content";
  public static final String FIELD_NAME_STATUS = "status";
  public static final String FIELD_NAME_CREATED_TIME = "created_time";
  public static final String FIELD_NAME_SORT_ORDER = "sort_order";
  public static final String FIELD_NAME_URL_KEY = "url_key";

  @DatabaseField(columnName = FIELD_NAME_FAQ_ID, id = true, generatedId = false)
  @SerializedName("faq_id")
  @Expose
  private String faqId;
  @DatabaseField(columnName = FIELD_NAME_TITLE)
  @SerializedName("title")
  @Expose
  private String
      title;
  @SerializedName("category_ids")
  @Expose
  private String categoryIds;
  @DatabaseField(columnName = FIELD_NAME_CONTENT)
  @SerializedName("content")
  @Expose
  private String
      content;
  @DatabaseField(columnName = FIELD_NAME_STATUS)
  @SerializedName("status")
  @Expose
  private String
      status;
  @DatabaseField(columnName = FIELD_NAME_CREATED_TIME)
  @SerializedName("created_time")
  @Expose
  private String createdTime;
  @SerializedName("update_time")
  @Expose
  private Object updateTime;
  @DatabaseField(columnName = FIELD_NAME_SORT_ORDER)
  @SerializedName("sort_order")
  @Expose
  private String sortOrder;
  @DatabaseField(columnName = FIELD_NAME_URL_KEY)
  @SerializedName("url_key")
  @Expose
  private String
      urlKey;
  @SerializedName("most_frequently")
  @Expose
  private String mostFrequently;
  @SerializedName("tag")
  @Expose
  private Object tag;
  @SerializedName("meta_keywords")
  @Expose
  private Object metaKeywords;
  @SerializedName("meta_description")
  @Expose
  private Object metaDescription;

  public FaqItem() {
  }

  public String getFaqId() {
    return faqId;
  }

  public void setFaqId(String faqId) {
    this.faqId = faqId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCategoryIds() {
    return categoryIds;
  }

  public void setCategoryIds(String categoryIds) {
    this.categoryIds = categoryIds;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
  }

  public Object getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Object updateTime) {
    this.updateTime = updateTime;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getUrlKey() {
    return urlKey;
  }

  public void setUrlKey(String urlKey) {
    this.urlKey = urlKey;
  }

  public String getMostFrequently() {
    return mostFrequently;
  }

  public void setMostFrequently(String mostFrequently) {
    this.mostFrequently = mostFrequently;
  }

  public Object getTag() {
    return tag;
  }

  public void setTag(Object tag) {
    this.tag = tag;
  }

  public Object getMetaKeywords() {
    return metaKeywords;
  }

  public void setMetaKeywords(Object metaKeywords) {
    this.metaKeywords = metaKeywords;
  }

  public Object getMetaDescription() {
    return metaDescription;
  }

  public void setMetaDescription(Object metaDescription) {
    this.metaDescription = metaDescription;
  }
}
