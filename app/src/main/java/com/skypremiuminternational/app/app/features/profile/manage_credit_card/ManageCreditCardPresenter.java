package com.skypremiuminternational.app.app.features.profile.manage_credit_card;

import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.AddCreditCardRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCreditCardRequest;
import com.skypremiuminternational.app.domain.interactor.auth.CheckAddCardFirstTime;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodeCC;
import com.skypremiuminternational.app.domain.interactor.user.AddCreditCard;
import com.skypremiuminternational.app.domain.interactor.user.AddCreditCardV2;
import com.skypremiuminternational.app.domain.interactor.user.DeleteCreditCard;
import com.skypremiuminternational.app.domain.interactor.user.GetCreditCardInfo;
import com.skypremiuminternational.app.domain.interactor.user.GetCreditCards;
import com.skypremiuminternational.app.domain.interactor.user.GetCreditCardsV2;
import com.skypremiuminternational.app.domain.interactor.user.GetDeliveryAddress;
import com.skypremiuminternational.app.domain.interactor.user.UpdateCreditCard;
import com.skypremiuminternational.app.domain.interactor.user.UpdateCreditCardV2;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.user.CardInfomationResponse;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class ManageCreditCardPresenter extends BasePresenter<ManageCreditCardView> {

  private GetCreditCards getCreditCards;
  private GetCreditCardsV2 getCreditCardsV2;
  private GetDeliveryAddress getDeliveryAddress;
  private AddCreditCard addCreditCard;
  private AddCreditCardV2 addCreditCardV2;
  private UpdateCreditCard updateCreditCard;
  private UpdateCreditCardV2 updateCreditCardV2;
  private DeleteCreditCard deleteCreditCard;
  private CheckAddCardFirstTime checkAddCardFirstTime;
  private GetCountryCodeCC getCountryCodeCC;
  private GetCreditCardInfo getCreditCardInfo;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  private UserDetailResponse userDetailResponse;

  @Inject
  public ManageCreditCardPresenter(GetAppVersion getAppVersion,
                                   InternalStorageManager internalStorageManager,
                                   GetCreditCards getCreditCards, GetDeliveryAddress getDeliveryAddress,
                                   AddCreditCard addCreditCard, UpdateCreditCard updateCreditCard,
                                   DeleteCreditCard deleteCreditCard, CheckAddCardFirstTime checkAddCardFirstTime,
                                   GetCountryCodeCC getCountryCodeCC, AddCreditCardV2 addCreditCardV2,
                                   GetCreditCardsV2 getCreditCardsV2, GetCreditCardInfo getCreditCardInfo,
                                   UpdateCreditCardV2 updateCreditCardV2) {
    super(getAppVersion, internalStorageManager);
    this.getCreditCards = getCreditCards;
    this.getDeliveryAddress = getDeliveryAddress;
    this.addCreditCard = addCreditCard;
    this.updateCreditCard = updateCreditCard;
    this.deleteCreditCard = deleteCreditCard;
    this.checkAddCardFirstTime = checkAddCardFirstTime;
    this.getCountryCodeCC = getCountryCodeCC;
    this.addCreditCardV2 = addCreditCardV2;
    this.getCreditCardsV2 = getCreditCardsV2;
    this.getCreditCardInfo = getCreditCardInfo;
    this.updateCreditCardV2 = updateCreditCardV2;
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  private void getDeliveryAddress() {
    getView().showLoading("Loading ...");
    add(getDeliveryAddress.execute(new SingleSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse value) {
        getView().hideLoading();
        ManageCreditCardPresenter.this.userDetailResponse = value;
        checkAddCardFirstTime();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load addresses"));
      }
    } ));
  }

  private void getCardInfo(String cardId) {
    getView().showLoading("Getting Credit Cards...");
    add(getCreditCardInfo.execute(new SingleSubscriber<CardInfomationResponse>() {

      @Override
      public void onSuccess(CardInfomationResponse value) {
        getView().hideLoading();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
      }
    } ));
  }

  public void getCreditCardsV2() {
    getView().showLoading("Getting Credit Cards...");
    add(getCreditCardsV2.execute(new SingleSubscriber<List<CreditCardResponse>>() {

      @Override
      public void onSuccess(List<CreditCardResponse> value) {
        getView().hideLoading();
        getView().render(value);
        getCountryList();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load credit cards"));
      }
    } ));
  }

  public void getCreditCards() {
    getView().showLoading("Getting Credit Cards...");
    add(getCreditCards.execute(new SingleSubscriber<List<CreditCardResponse>>() {

      @Override
      public void onSuccess(List<CreditCardResponse> value) {
        getView().hideLoading();
        getView().render(value);
        getCountryList();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load credit cards"));
      }
    } ));
  }

  public void getCountryList() {
    getView().showLoading("Loading...");
    add(getCountryCodeCC.execute(new SingleSubscriber<List<CountryCodeCC>>() {

      @Override
      public void onSuccess(List<CountryCodeCC> response) {
        getView().hideLoading();
        getView().initCountryCode(response);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
      }
    } ));
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void checkAddCardFirstTime() {
    getView().showLoading("Loading ...");
    add(checkAddCardFirstTime.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean value) {
        getView().hideLoading();
        if (value) {
          addCreditCardFirstTime();
        } else {
          addCreditCardV2();
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
      }
    } ));

  }

  public void addCreditCard() {
    if (userDetailResponse != null) {
      String city = "";
      String name = "";
      String street = "";
      String postCode = "";
      String countryCode = "";
      /*if (userDetailResponse.getAddresses().size() > 0) {
        for (Address address : userDetailResponse.getAddresses()) {
          if (address.getDefaultShipping() != null) {
            if (address.getDefaultShipping() == true) {
              city = address.getCity();
              name = address.getFirstname() + " " + address.getLastname();
              street = address.getStreet().get(0);
              postCode = address.getPostcode();
              countryCode = address.getCountryId();
            }
          }
        }
        if (city != null && street != null && postCode != null && countryCode != null) {
          if (!TextUtils.isEmpty(city)
              && !TextUtils.isEmpty(street)
              && !TextUtils.isEmpty(postCode)
              && !TextUtils.isEmpty(countryCode)) {
            CreditCardResponse creditCard =
                CreditCardResponse.skeletonCreditCard(city, name, street, postCode, countryCode);
            getView().showAddCreditDialog(creditCard);
          } else {
            getView().showWarning("You need to set default delivery address.");
          }
        } else {
          getView().showWarning("You need to set default delivery address.");
        }
      } else {
        getView().showWarning("You need to add delivery address.");
      }*/
      CreditCardResponse creditCard =
          CreditCardResponse.skeletonCreditCard(city, name, street, postCode, countryCode);
      getView().showAddCreditDialog(creditCard);
    }
  }

  public void addCreditCardV2() {
    CreditCardResponse creditCard = new CreditCardResponse();
    getView().showAddCreditDialogV2(creditCard);
  }

  public void addCreditCardFirstTime() {
    getView().showAddCreditDialogFirstTime();
  }

  public void updateCreditCard(CreditCardResponse creditCard, String message,
                               boolean setAsDefault) {
    getView().showLoading("Updating Credit Card ...");

    if (message.equalsIgnoreCase("add_credit_card")) {
      AddCreditCardRequest addRequest = buildRequestCreditCard(creditCard);

      add(addCreditCard.execute(new SingleSubscriber<UpdateUserResponse>() {
        @Override
        public void onSuccess(UpdateUserResponse value) {
          if (value.getSuccess().equalsIgnoreCase("true")) {
            getView().hideAddressDialog();
            getCreditCards();
          } else {
            getView().hideLoading();
            getView().showWarning(value.getMessage());
          }
        }

        @Override
        public void onError(Throwable error) {
          getView().hideLoading();
          getView().render(error);
        }
      }, addRequest));
    } else {
      UpdateCreditCardRequest updateRequest = buildRequestUpdateCredit(creditCard);

      add(updateCreditCard.execute(new SingleSubscriber<UpdateUserResponse>() {
        @Override
        public void onSuccess(UpdateUserResponse value) {
          if (value.getSuccess().equalsIgnoreCase("true")) {
            getView().hideAddressDialog();
            getCreditCards();
          } else {
            getView().hideLoading();
            getView().showWarning(value.getMessage());
          }
        }

        @Override
        public void onError(Throwable error) {
          getView().hideLoading();
          getView().render(error);
        }
      }, new UpdateCreditCard.Params(updateRequest, setAsDefault)));
    }
  }

  public void updateCreditCardV2(CreditCardResponse creditCard, String message,
                                 boolean setAsDefault) {
    getView().showLoading("Updating Credit Card ...");

    if (message.equalsIgnoreCase("add_credit_card")) {
      AddCreditCardRequest addRequest = buildRequestCreditCardV2(creditCard);

      add(addCreditCardV2.execute(new SingleSubscriber<UpdateUserResponse>() {
        @Override
        public void onSuccess(UpdateUserResponse value) {
          if (value.getSuccess().equalsIgnoreCase("true")) {
            getView().hideAddressDialog();
            getCreditCardsV2();
          } else {
            getView().hideLoading();
            getView().showWarning(value.getMessage());
          }
        }

        @Override
        public void onError(Throwable error) {
          getView().hideLoading();
          getView().render(error);
        }
      }, addRequest));
    } else {
      UpdateCreditCardRequest updateRequest = buildRequestUpdateCredit(creditCard);

      add(updateCreditCardV2.execute(new SingleSubscriber<UpdateUserResponse>() {
        @Override
        public void onSuccess(UpdateUserResponse value) {
          if (value.getSuccess().equalsIgnoreCase("true")) {
            getView().hideAddressDialog();
            getCreditCardsV2();
          } else {
            getView().hideLoading();
            getView().showWarning(value.getMessage());
          }
        }

        @Override
        public void onError(Throwable error) {
          getView().hideLoading();
          getView().render(error);
        }
      }, new UpdateCreditCardV2.Params(updateRequest, setAsDefault)));
    }
  }

  private UpdateCreditCardRequest buildRequestUpdateCredit(CreditCardResponse creditCard) {
    UpdateCreditCardRequest.CreditCardItem creditCardItem =
        new UpdateCreditCardRequest.CreditCardItem(
            creditCard.getId(), creditCard.getExpMonth(), creditCard.getExpYear(),
            creditCard.getName(), creditCard.getAddressLine1(), creditCard.getAddressLine2(),
            creditCard.getAddressState(),
            creditCard.getAddressZip(), creditCard.getAddressCity(),
            creditCard.getAddressCountry(), BuildConfig.BuildId, BuildConfig.Chanel);
    return new UpdateCreditCardRequest(creditCardItem);
  }

  private UpdateCreditCardRequest buildRequestUpdateCreditV2(CreditCardResponse creditCard) {
    UpdateCreditCardRequest.CreditCardItem creditCardItem =
        new UpdateCreditCardRequest.CreditCardItem(
            creditCard.getId(), creditCard.getExpMonth(), creditCard.getExpYear(),
            creditCard.getName(), creditCard.getAddressLine1(), creditCard.getAddressLine2(),
            creditCard.getAddressState(), creditCard.getAddressZip(), creditCard.getAddressCity(),
            creditCard.getAddressCountry(), BuildConfig.BuildId, BuildConfig.Chanel);
    return new UpdateCreditCardRequest(creditCardItem);
  }

  private AddCreditCardRequest buildRequestCreditCard(CreditCardResponse creditCard) {
    AddCreditCardRequest.CreditCardItemToken creditCardItemToken =
        new AddCreditCardRequest.CreditCardItemToken(creditCard.getCardNumber(),
            creditCard.getExpMonth(), creditCard.getExpYear(), (String) creditCard.getCvcCheck(),
            creditCard.getName(), creditCard.getAddressLine1(),
            "Singapore", creditCard.getAddressZip(), creditCard.getAddressCity(),
            creditCard.getAddressCountry(), BuildConfig.BuildId, BuildConfig.Chanel);
    return new AddCreditCardRequest(creditCardItemToken);
  }

  private AddCreditCardRequest buildRequestCreditCardV2(CreditCardResponse creditCard) {
    AddCreditCardRequest.CreditCardItemToken creditCardItemToken =
        new AddCreditCardRequest.CreditCardItemToken(creditCard.getCardNumber(),
            creditCard.getExpMonth(), creditCard.getExpYear(), (String) creditCard.getCvcCheck(),
            creditCard.getName(), creditCard.getAddressLine1(),
            creditCard.getAddressState(),
            creditCard.getAddressZip(), creditCard.getAddressCity(),
            creditCard.getCountry(), BuildConfig.BuildId, BuildConfig.Chanel, creditCard.getPhoneNumber());
    return new AddCreditCardRequest(creditCardItemToken);
  }

  public void deleteCreditCard(CreditCardResponse creditCard) {
    getView().showLoading("Deleting Credit Card ...");
    add(deleteCreditCard.execute(new SingleSubscriber<UpdateUserResponse>() {
      @Override
      public void onSuccess(UpdateUserResponse value) {
        getView().hideLoading();
        if (value.getSuccess().equalsIgnoreCase("true")) {
          getView().hideAddressDialog();
          getCreditCardsV2();
        } else {
          getView().hideLoading();
          getView().showWarning(value.getMessage());
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    }, new DeleteCreditCard.Params(creditCard.getId())));
  }

  public void checkCardCounts() {
    getView().showLoading("Loading ...");
    add(getCreditCardsV2.execute(new SingleSubscriber<List<CreditCardResponse>>() {
      @Override
      public void onSuccess(List<CreditCardResponse> value) {
        getView().hideLoading();
        getView().render(value);
        if (value.size() >= 3) {
          getView().render(new Exception("Credit cards are limited to a maximum of 3"));
        } else {
          getDeliveryAddress();
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load cards"));
      }
    } ));
  }
}
