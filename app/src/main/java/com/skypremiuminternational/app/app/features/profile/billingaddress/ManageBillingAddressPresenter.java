package com.skypremiuminternational.app.app.features.profile.billingaddress;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodes;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.AddBillingAddress;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.DeleteBillingAddress;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.EditBillingAddress;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.GetBillingAddresses;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ManageBillingAddressPresenter extends BasePresenter<ManageBillingAddressView> {

  private final GetBillingAddresses getBillingAddresses;
  private final AddBillingAddress addBillingAddress;
  private final EditBillingAddress editBillingAddress;
  private final DeleteBillingAddress deleteBillingAddress;
  private final GetCountryCodes getCountryCodes;
  private CompositeSubscription compositeSubscription;
  private List<CountryCode> countryCodes;

  @Inject
  public ManageBillingAddressPresenter(AddBillingAddress addBillingAddress,
                                       EditBillingAddress editBillingAddress,
                                       GetCountryCodes getCountryCodes, GetAppVersion getAppVersion,
                                       DeleteBillingAddress deleteBillingAddress,
                                       InternalStorageManager internalStorageManager,
                                       GetBillingAddresses getBillingAddresses) {
    super(getAppVersion, internalStorageManager);
    this.getCountryCodes = getCountryCodes;
    this.addBillingAddress = addBillingAddress;
    this.editBillingAddress = editBillingAddress;
    this.deleteBillingAddress = deleteBillingAddress;
    this.getBillingAddresses = getBillingAddresses;
    compositeSubscription = new CompositeSubscription();
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  @Override
  public void onStop() {
    super.onStop();
    compositeSubscription.clear();
  }

  public void getCountryCodes() {
    getView().showLoading();
    add(getCountryCodes.execute(new SingleSubscriber<List<CountryCode>>() {
      @Override
      public void onSuccess(List<CountryCode> countryCodes) {
        ManageBillingAddressPresenter.this.countryCodes = countryCodes;
        getView().setCountryCodes(countryCodes);
        getBillingAddresses();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    } ));
  }

  public void getBillingAddresses() {
    add(getBillingAddresses.execute(new SingleSubscriber<List<BillingAddress>>() {
      @Override
      public void onSuccess(List<BillingAddress> value) {
        getView().hideLoading();
        getView().render(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    } ));
  }

  public void addAddress(BillingAddress address) {

    getView().showLoading();
    add(addBillingAddress.execute(new SingleSubscriber<List<BillingAddress>>() {
      @Override
      public void onSuccess(List<BillingAddress> value) {
        getView().hideLoading();
        getView().render(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    }, new AddBillingAddress.Params(address)));
  }


  public void collectCountryCodesAndProceedToAdd() {
    if (countryCodes != null) {
      getView().showLoading();
      add(getBillingAddresses.execute(new SingleSubscriber<List<BillingAddress>>() {
        @Override
        public void onSuccess(List<BillingAddress> value) {
          getView().hideLoading();
          getView().render(value);
          if (value != null && value.size() >= 3) {
            getView().render(new Exception("Billing addresses are limited to a maximum of 3"));
          } else {
            getView().showAddAddressDialog(countryCodes);
          }
        }

        @Override
        public void onError(Throwable error) {
          getView().hideLoading();
          getView().render(new Exception("Failed to load billing addresses"));
        }
      } ));
    }
  }

  public void deleteAddress(BillingAddress address) {
    getView().showLoading();
    add(deleteBillingAddress.execute(new SingleSubscriber<List<BillingAddress>>() {
      @Override
      public void onSuccess(List<BillingAddress> value) {
        getView().hideLoading();
        getView().render(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    }, new DeleteBillingAddress.Params(address.getId())));
  }

  public void collectCountryCodesAndProceedToEdit(BillingAddress address) {
    if (countryCodes != null) {
      getView().showEditAddressDialog(countryCodes, address);
    }
  }

  public void editAddress(BillingAddress address) {
    getView().showLoading();
    add(editBillingAddress.execute(new SingleSubscriber<List<BillingAddress>>() {
      @Override
      public void onSuccess(List<BillingAddress> value) {
        getView().hideLoading();
        getView().render(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    }, new EditBillingAddress.Params(address)));
  }
}
