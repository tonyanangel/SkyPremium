package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantMessage{

  @SerializedName("startRow")
  String  startRow;
  @SerializedName("endRow")
  String  endRow;
  @SerializedName("totalRows")
  String  totalRows;
  @SerializedName("data")
  List<DataMessage> data;


  public String getStartRow() {
    return startRow;
  }

  public void setStartRow(String startRow) {
    this.startRow = startRow;
  }

  public String getEndRow() {
    return endRow;
  }

  public void setEndRow(String endRow) {
    this.endRow = endRow;
  }

  public String getTotalRows() {
    return totalRows;
  }

  public void setTotalRows(String totalRows) {
    this.totalRows = totalRows;
  }

  public List<DataMessage> getData() {
    return data;
  }

  public void setData(List<DataMessage> data) {
    this.data = data;
  }
}
