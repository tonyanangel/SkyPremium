package com.skypremiuminternational.app.domain.models.myOrder.detail;

import com.google.gson.annotations.SerializedName;

public class StatusHistoriesItem {

  @SerializedName("is_visible_on_front")
  private int isVisibleOnFront;

  @SerializedName("entity_name")
  private String entityName;

  @SerializedName("parent_id")
  private int parentId;

  @SerializedName("created_at")
  private String createdAt;

  @SerializedName("comment")
  private String comment;

  @SerializedName("is_customer_notified")
  private Object isCustomerNotified;

  @SerializedName("entity_id")
  private int entityId;

  @SerializedName("status")
  private String status;

  public void setIsVisibleOnFront(int isVisibleOnFront) {
    this.isVisibleOnFront = isVisibleOnFront;
  }

  public int getIsVisibleOnFront() {
    return isVisibleOnFront;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }

  public int getParentId() {
    return parentId;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getComment() {
    return comment;
  }

  public void setIsCustomerNotified(Object isCustomerNotified) {
    this.isCustomerNotified = isCustomerNotified;
  }

  public Object getIsCustomerNotified() {
    return isCustomerNotified;
  }

  public void setEntityId(int entityId) {
    this.entityId = entityId;
  }

  public int getEntityId() {
    return entityId;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return
        "StatusHistoriesItem{" +
            "is_visible_on_front = '" + isVisibleOnFront + '\'' +
            ",entity_name = '" + entityName + '\'' +
            ",parent_id = '" + parentId + '\'' +
            ",created_at = '" + createdAt + '\'' +
            ",comment = '" + comment + '\'' +
            ",is_customer_notified = '" + isCustomerNotified + '\'' +
            ",entity_id = '" + entityId + '\'' +
            ",status = '" + status + '\'' +
            "}";
  }
}