package com.skypremiuminternational.app.data.model.ean.booking.book;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.data.model.ean.booking.history.AdditionalData;

import java.util.List;

public class BookResponse {

  @SerializedName("additional_data")
  private List<AdditionalData> additionalData;

  @SerializedName("ean_result")
  private List<EanResult> eanResult;

  public List<EanResult> getEanResult() {
    return eanResult;
  }

  public void setEanResult(
      List<EanResult> eanResult) {
    this.eanResult = eanResult;
  }

  public List<AdditionalData> getAdditionalData() {
    return additionalData;
  }

  public void setAdditionalData(
      List<AdditionalData> additionalData) {
    this.additionalData = additionalData;
  }

  public static final class Error {
    public final String type;
    public final String message;
    public final List<Field> fields;

    public Error(String type, String message, List<Field> fields) {
      this.type = type;
      this.message = message;
      this.fields = fields;
    }
  }

  public static final class Field {
    public final String name;
    public final String type;
    public final String value;

    public Field(String name, String type, String value) {
      this.name = name;
      this.type = type;
      this.value = value;
    }
  }
}