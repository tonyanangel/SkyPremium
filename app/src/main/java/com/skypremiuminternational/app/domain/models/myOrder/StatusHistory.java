package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class StatusHistory implements Serializable {
  @SerializedName("comment")
  @Expose
  private String comment;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("entity_id")
  @Expose
  private Integer entityId;
  @SerializedName("entity_name")
  @Expose
  private String entityName;
  @SerializedName("is_customer_notified")
  @Expose
  private Object isCustomerNotified;
  @SerializedName("is_visible_on_front")
  @Expose
  private Integer isVisibleOnFront;
  @SerializedName("parent_id")
  @Expose
  private Integer parentId;
  @SerializedName("status")
  @Expose
  private String status;

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public Integer getEntityId() {
    return entityId;
  }

  public void setEntityId(Integer entityId) {
    this.entityId = entityId;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public Object getIsCustomerNotified() {
    return isCustomerNotified;
  }

  public void setIsCustomerNotified(Object isCustomerNotified) {
    this.isCustomerNotified = isCustomerNotified;
  }

  public Integer getIsVisibleOnFront() {
    return isVisibleOnFront;
  }

  public void setIsVisibleOnFront(Integer isVisibleOnFront) {
    this.isVisibleOnFront = isVisibleOnFront;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
