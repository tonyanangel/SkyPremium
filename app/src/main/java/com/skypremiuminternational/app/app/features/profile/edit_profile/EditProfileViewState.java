package com.skypremiuminternational.app.app.features.profile.edit_profile;

import androidx.annotation.Nullable;

import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.nationality.Nationality;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class EditProfileViewState {

  @Nullable
  abstract Double renewalPrice();

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  @Nullable
  abstract UserDetailResponse userDetail();

  @Nullable
  abstract List<CountryCode> countryCodes();

  @Nullable
  abstract PhoneCode phoneCodes();

  @Nullable
  abstract List<Nationality> nationalities();



  public static EditProfileViewState create(Double renewalPrice, Throwable error, boolean isLoading,
                                            boolean isSuccess, String message, UserDetailResponse userDetail,
                                            List<CountryCode> countryCodes, PhoneCode phoneCodes,
                                            List<Nationality> nationalities) {
    return builder()
        .renewalPrice(renewalPrice)
        .error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .userDetail(userDetail)
        .countryCodes(countryCodes)
        .phoneCodes(phoneCodes)
        .nationalities(nationalities)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_EditProfileViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract Builder userDetail(UserDetailResponse userDetailResponse);

    public abstract Builder countryCodes(List<CountryCode> countryCodes);

    public abstract Builder phoneCodes(PhoneCode phoneCodes);

    public abstract Builder nationalities(List<Nationality> nationalities);

    public abstract Builder renewalPrice(Double renewalPrice);

    public abstract EditProfileViewState build();
  }
}
