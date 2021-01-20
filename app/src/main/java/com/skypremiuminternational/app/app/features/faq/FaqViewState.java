package com.skypremiuminternational.app.app.features.faq;

import androidx.annotation.Nullable;

import com.skypremiuminternational.app.domain.models.faq.FaqItem;
import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class FaqViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  @Nullable
  abstract List<FaqItem> dataList();

  public static FaqViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                    String message, List<FaqItem> dataList) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .dataList(dataList)
        .build();
  }

  public static FaqViewState.Builder builder() {
    return new AutoValue_FaqViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract Builder dataList(List<FaqItem> dataList);

    public abstract FaqViewState build();
  }
}
