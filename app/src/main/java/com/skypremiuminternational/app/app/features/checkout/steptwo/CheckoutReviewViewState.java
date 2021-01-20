package com.skypremiuminternational.app.app.features.checkout.steptwo;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by aeindraaung on 2/4/18.
 */

@AutoValue
public abstract class CheckoutReviewViewState {
    public static CheckoutReviewViewState create(Throwable error, boolean isLoading, boolean isSuccess) {
        return builder()
                .error(error)
                .isLoading(isLoading)
                .isSuccess(isSuccess)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_CheckoutReviewViewState.Builder();
    }

    @Nullable
    abstract Throwable error();

    abstract boolean isLoading();

    abstract boolean isSuccess();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder error(Throwable error);

        public abstract Builder isLoading(boolean isLoading);

        public abstract Builder isSuccess(boolean isSuccess);

        public abstract CheckoutReviewViewState build();
    }
}
