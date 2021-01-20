package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReserveTime {


  @SerializedName("status")
  Integer status;
  @SerializedName("startRow")
  Integer startRow;
  @SerializedName("endRow")
  Integer endRow;
  @SerializedName("totalRows")
  Integer totalRows;
  @SerializedName("data")
  List<ReserveTimeSlotItem> data;


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getStartRow() {
    return startRow;
  }

  public void setStartRow(Integer startRow) {
    this.startRow = startRow;
  }

  public Integer getEndRow() {
    return endRow;
  }

  public void setEndRow(Integer endRow) {
    this.endRow = endRow;
  }

  public Integer getTotalRows() {
    return totalRows;
  }

  public void setTotalRows(Integer totalRows) {
    this.totalRows = totalRows;
  }

  public List<ReserveTimeSlotItem> getData() {
    return data;
  }

  public void setData(List<ReserveTimeSlotItem> data) {
    this.data = data;
  }
}
