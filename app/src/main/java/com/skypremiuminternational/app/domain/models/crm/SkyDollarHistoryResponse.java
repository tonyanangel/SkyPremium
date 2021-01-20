package com.skypremiuminternational.app.domain.models.crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SkyDollarHistoryResponse {

  @SerializedName("code")
  @Expose
  String code;
  @SerializedName("member_value")
  @Expose
  String memberValue;
  @SerializedName("record_count")
  @Expose
  String recordCount;
  @SerializedName("records")
  @Expose
  List<HistoryRecord> records;
  @SerializedName("error_msg")
  @Expose
  String errorMsg;


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMemberValue() {
    return memberValue;
  }

  public void setMemberValue(String memberValue) {
    this.memberValue = memberValue;
  }

  public String getRecordCount() {
    return recordCount;
  }

  public void setRecordCount(String recordCount) {
    this.recordCount = recordCount;
  }

  public List<HistoryRecord> getRecords() {
    return records;
  }

  public void setRecords(List<HistoryRecord> records) {
    this.records = records;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
}
