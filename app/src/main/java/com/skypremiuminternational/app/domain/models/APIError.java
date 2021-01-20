package com.skypremiuminternational.app.domain.models;

import com.skypremiuminternational.app.data.network.AutoGson;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
@AutoGson
public abstract class APIError {

  @SerializedName("dateTime")
  public abstract String dateTime();

  @SerializedName("number")
  public abstract int number();

  @SerializedName("validationMessages")
  public abstract Object validationMessages();

  @SerializedName("exceptionID")
  public abstract int exceptionID();

  @SerializedName("source")
  public abstract String source();

  @SerializedName("message")
  public abstract String message();

  @SerializedName("type")
  public abstract String type();

  @SerializedName("info")
  public abstract String info();

  public static APIError create(String dateTime, int number, Object validationMessages,
                                int exceptionID, String source, String message, String type, String info) {
    return builder().dateTime(dateTime)
        .number(number)
        .validationMessages(validationMessages)
        .exceptionID(exceptionID)
        .source(source)
        .message(message)
        .type(type)
        .info(info)
        .build();
  }

  public static TypeAdapter<APIError> typeAdapter(Gson gson) {
    return new AutoValue_APIError.GsonTypeAdapter(gson);
  }

  public static Builder builder() {
    return new AutoValue_APIError.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder dateTime(String dateTime);

    public abstract Builder number(int number);

    public abstract Builder validationMessages(Object validationMessages);

    public abstract Builder exceptionID(int exceptionID);

    public abstract Builder source(String source);

    public abstract Builder message(String message);

    public abstract Builder type(String type);

    public abstract Builder info(String info);

    public abstract APIError build();
  }
}