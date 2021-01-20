package com.skypremiuminternational.app.domain.models.crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryRecord {

  public static final String PATTERN_DATE = "yyyy-MM-dd";



  @SerializedName("name")
  @Expose
  String name;
  @SerializedName("sales_order_no")
  @Expose
  String salesOrderNo;
  @SerializedName("points_type")
  @Expose
  String pointsType;
  @SerializedName("points")
  @Expose
  String points;
  @SerializedName("trx_date")
  @Expose
  String trxDate;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSalesOrderNo() {
    return salesOrderNo;
  }

  public void setSalesOrderNo(String salesOrderNo) {
    this.salesOrderNo = salesOrderNo;
  }

  public String getPointsType() {
    return pointsType;
  }

  public void setPointsType(String pointsType) {
    this.pointsType = pointsType;
  }

  public String getPoints() {
    return points;
  }

  public void setPoints(String points) {
    this.points = points;
  }

  public String getTrxDate() {
    return trxDate;
  }

  public void setTrxDate(String trxDate) {
    this.trxDate = trxDate;
  }
}