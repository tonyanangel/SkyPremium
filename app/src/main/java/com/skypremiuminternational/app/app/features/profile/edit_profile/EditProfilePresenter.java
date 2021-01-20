package com.skypremiuminternational.app.app.features.profile.edit_profile;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.AddRenewalToCart;
import com.skypremiuminternational.app.domain.interactor.cart.GetRenewalProductPrice;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodes;
import com.skypremiuminternational.app.domain.interactor.ean.GetISOCountryCodes;
import com.skypremiuminternational.app.domain.interactor.nationality.GetNationalities;
import com.skypremiuminternational.app.domain.interactor.phone_code.GetPhoneCodes;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetailFromApi;
import com.skypremiuminternational.app.domain.interactor.user.UpdateUserDetail;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.nationality.Nationality;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class EditProfilePresenter extends BasePresenter<EditProfileView> {

  private final GetCountryCodes getCountryCodes;
  private final GetPhoneCodes getPhoneCodes;
  private final GetNationalities getNationalities;
  private final UpdateUserDetail updateUserDetail;
  private final GetISOCountryCodes getISOCountryCodes;
  private final AddRenewalToCart addRenewalToCart;
  private final GetRenewalProductPrice getRenewalProductPrice;
  private GetUserDetailFromApi getUserDetailFromApi;
  private EditProfileViewState viewState;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public EditProfilePresenter(GetRenewalProductPrice getRenewalProductPrice, GetAppVersion getAppVersion,
                              InternalStorageManager internalStorageManager,
                              GetUserDetailFromApi getUserDetailFromApi, AddRenewalToCart addRenewalToCart,
                              GetCountryCodes getCountryCodes, GetPhoneCodes getPhoneCodes,
                              GetNationalities getNationalities, UpdateUserDetail updateUserDetail,GetISOCountryCodes getISOCountryCodes) {
    super(getAppVersion, internalStorageManager);
    viewState = EditProfileViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .userDetail(null)
        .countryCodes(null)
        .phoneCodes(null)
        .nationalities(null)
        .build();
    this.getRenewalProductPrice = getRenewalProductPrice;
    this.addRenewalToCart = addRenewalToCart;
    this.getUserDetailFromApi = getUserDetailFromApi;
    this.getCountryCodes = getCountryCodes;
    this.getPhoneCodes = getPhoneCodes;
    this.getNationalities = getNationalities;
    this.updateUserDetail = updateUserDetail;
    this.getISOCountryCodes = getISOCountryCodes;
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }


  public void addRenewalToCart(boolean usePoints) {
    viewState = EditProfileViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(viewState.message())
        .userDetail(viewState.userDetail())
        .countryCodes(viewState.countryCodes())
        .phoneCodes(viewState.phoneCodes())
        .nationalities(viewState.nationalities())
        .build();
    getView().render(viewState);
    add(addRenewalToCart.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean ignored) {
        getView().goToShoppingCart();
      }

      @Override
      public void onError(Throwable error) {
        Timber.e(error);
        viewState = EditProfileViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(viewState.message())
            .userDetail(viewState.userDetail())
            .countryCodes(viewState.countryCodes())
            .phoneCodes(viewState.phoneCodes())
            .nationalities(viewState.nationalities())
            .build();
        getView().render(viewState);
      }
    }, new AddRenewalToCart.Params(usePoints)));
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void getUserDetailFromApi(final String messageType) {

    Subscription subscription =
        getUserDetailFromApi.execute(new BaseSubscriber<UserDetailResponse>() {
          @Override
          public void onSuccess(UserDetailResponse response) {
            viewState = EditProfileViewState.builder()
                .error(null)
                .isLoading(false)
                .isSuccess(true)
                .message(messageType)
                .userDetail(response)
                .countryCodes(viewState.countryCodes())
                .phoneCodes(viewState.phoneCodes())
                .nationalities(viewState.nationalities())
                .build();

            getView().render(viewState);
          }

          @Override
          public void onError(Throwable error) {
            viewState = EditProfileViewState.builder()
                .error(new Exception("Failed to load user detail"))
                .isLoading(false)
                .isSuccess(false)
                .message(null)
                .userDetail(null)
                .countryCodes(viewState.countryCodes())
                .phoneCodes(viewState.phoneCodes())
                .nationalities(viewState.nationalities())
                .build();

            getView().render(viewState);
          }
        } );

    compositeSubscription.add(subscription);
  }

  public void getCountryCodes() {

    onShowLoading();

    Subscription subscription = getCountryCodes.execute(new BaseSubscriber<List<CountryCode>>() {
      @Override
      public void onSuccess(List<CountryCode> response) {
        viewState = EditProfileViewState.builder()
            .error(viewState.error())
            .isLoading(viewState.isLoading())
            .isSuccess(viewState.isSuccess())
            .message(viewState.message())
            .userDetail(viewState.userDetail())
            .countryCodes(response)
            .phoneCodes(viewState.phoneCodes())
            .nationalities(viewState.nationalities())
            .build();
        getPhoneCodes();
      }

      @Override
      public void onError(Throwable error) {
        viewState = EditProfileViewState.builder()
            .error(new Exception("Failed to load country codes"))
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .userDetail(null)
            .countryCodes(null)
            .phoneCodes(null)
            .nationalities(null)
            .build();
        getView().render(viewState);
      }
    } );

    compositeSubscription.add(subscription);
  }

  public void getPhoneCodes() {

    Subscription subscription = getPhoneCodes.execute(new BaseSubscriber<PhoneCode>() {
      @Override
      public void onSuccess(PhoneCode response) {

        viewState = EditProfileViewState.builder()
            .error(viewState.error())
            .isLoading(viewState.isLoading())
            .isSuccess(viewState.isSuccess())
            .message(viewState.message())
            .userDetail(viewState.userDetail())
            .countryCodes(viewState.countryCodes())
            .phoneCodes(response)
            .nationalities(viewState.nationalities())
            .build();
        getNationalities();
      }

      @Override
      public void onError(Throwable error) {
        viewState = EditProfileViewState.builder()
            .error(new Exception("Failed to load phone codes"))
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .userDetail(null)
            .countryCodes(null)
            .phoneCodes(null)
            .nationalities(null)
            .build();
        getView().render(viewState);
      }
    } );

    compositeSubscription.add(subscription);
  }

  public void getNationalities() {

    Subscription subscription = getNationalities.execute(new BaseSubscriber<List<Nationality>>() {
      @Override
      public void onSuccess(List<Nationality> response) {

        viewState = EditProfileViewState.builder()
            .error(viewState.error())
            .isLoading(viewState.isLoading())
            .isSuccess(viewState.isSuccess())
            .message(viewState.message())
            .userDetail(viewState.userDetail())
            .countryCodes(viewState.countryCodes())
            .phoneCodes(viewState.phoneCodes())
            .nationalities(response)
            .build();

        getUserDetailFromApi("1"); // dont show toast
      }

      @Override
      public void onError(Throwable error) {
        viewState = EditProfileViewState.builder()
            .error(new Exception("Failed to load nationalities"))
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .userDetail(null)
            .countryCodes(null)
            .phoneCodes(null)
            .nationalities(viewState.nationalities())
            .build();
        getView().render(viewState);
      }
    } );

    compositeSubscription.add(subscription);
  }

  public void updateUserDetail(UpdateUserRequest request) {


    onShowLoading();

    Subscription subscription = updateUserDetail.execute(new BaseSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse response) {
        getUserDetailFromApi("2"); //show toast
      }

      @Override
      public void onError(Throwable error) {
        viewState = EditProfileViewState.builder()
            .error(new Exception("Failed to update user detail"))
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .userDetail(null)
            .countryCodes(null)
            .phoneCodes(null)
            .nationalities(null)
            .build();
        getView().render(viewState);
      }
    }, new UpdateUserDetail.Params(request, false));

    compositeSubscription.add(subscription);
  }

  public void onShowLoading() {
    viewState = EditProfileViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(viewState.message())
        .userDetail(viewState.userDetail())
        .countryCodes(viewState.countryCodes())
        .phoneCodes(viewState.phoneCodes())
        .nationalities(viewState.nationalities())
        .build();
    getView().render(viewState);
  }
  public void onLoadedSuccess(){
    viewState = EditProfileViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(viewState.message())
            .userDetail(viewState.userDetail())
            .countryCodes(viewState.countryCodes())
            .phoneCodes(viewState.phoneCodes())
            .nationalities(viewState.nationalities())
            .build();
    getView().render(viewState);
  }

  public void GetisoCountry(String id){
    add(getISOCountryCodes.execute(new SingleSubscriber<List<ISOCountry>>() {
      @Override
      public void onSuccess(List<ISOCountry> value1) {
        for(ISOCountry isoCountry: value1){
          if(isoCountry.country_code.equalsIgnoreCase(id)){
            //getView().render(isoCountry.country_name,id);
          }
        }
      }

      @Override
      public void onError(Throwable error) {
      }
    } ));
  }

  public void getRenewalProduct() {
    onShowLoading();
    add(getRenewalProductPrice.execute(new SingleSubscriber<Double>() {
      @Override
      public void onSuccess(Double renewalPrice) {
        viewState = EditProfileViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message("3")
            .renewalPrice(renewalPrice)
            .userDetail(viewState.userDetail())
            .countryCodes(viewState.countryCodes())
            .phoneCodes(viewState.phoneCodes())
            .nationalities(viewState.nationalities())
            .build();
        getView().render(viewState);
      }

      @Override
      public void onError(Throwable error) {
        viewState = EditProfileViewState.builder()
            .error(new Exception("Failed to load renewal product price"))
            .isLoading(false)
            .isSuccess(false)
            .message(viewState.message())
            .userDetail(viewState.userDetail())
            .countryCodes(viewState.countryCodes())
            .phoneCodes(viewState.phoneCodes())
            .nationalities(viewState.nationalities())
            .build();
        getView().render(viewState);
      }
    } ));
  }
}
