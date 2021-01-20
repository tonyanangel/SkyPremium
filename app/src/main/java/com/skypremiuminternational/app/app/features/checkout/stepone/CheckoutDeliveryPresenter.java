package com.skypremiuminternational.app.app.features.checkout.stepone;

import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.data.network.request.AddCreditCardRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCreditCardRequest;
import com.skypremiuminternational.app.data.network.request.UpdateUserDeliveryRequest;
import com.skypremiuminternational.app.domain.interactor.auth.CheckAddCardFirstTime;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodeCC;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodes;
import com.skypremiuminternational.app.domain.interactor.ean.GetISOCountryCodes;
import com.skypremiuminternational.app.domain.interactor.user.AddCreditCard;
import com.skypremiuminternational.app.domain.interactor.user.AddCreditCardV2;
import com.skypremiuminternational.app.domain.interactor.user.DeleteAddress;
import com.skypremiuminternational.app.domain.interactor.user.DeleteCreditCard;
import com.skypremiuminternational.app.domain.interactor.user.GetCreditCards;
import com.skypremiuminternational.app.domain.interactor.user.GetCreditCardsV2;
import com.skypremiuminternational.app.domain.interactor.user.GetDeliveryAddress;
import com.skypremiuminternational.app.domain.interactor.user.SetDefaultCreditCard;
import com.skypremiuminternational.app.domain.interactor.user.UpdateCreditCard;
import com.skypremiuminternational.app.domain.interactor.user.UpdateCreditCardV2;
import com.skypremiuminternational.app.domain.interactor.user.UpdateUserAddress;
import com.skypremiuminternational.app.domain.interactor.user.UpdateUserDetail;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.AddBillingAddress;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.BillingAddressPayment;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.DeleteBillingAddress;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.EditBillingAddress;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.GetBillingAddresses;
import com.skypremiuminternational.app.domain.models.checkout.AddressDeliveryRequest;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.models.user.BillingAddressPayment.BillingAddressPaymentRespond;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.RequestBody;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aeindraaung on 2/4/18.
 */

public class CheckoutDeliveryPresenter extends BasePresenter<CheckoutDeliveryView> {
  private final UpdateUserDetail updateUserDetail;
  private final UpdateUserAddress updateUserAddress;
  private final AddCreditCard addCreditCard;
  private final UpdateCreditCard updateCreditCard;
  private final DeleteAddress deleteAddress;
  private final DeleteCreditCard deleteCreditCard;
  private final GetBillingAddresses getBillingAddresses;
  private final EditBillingAddress editBillingAddress;
  private final AddBillingAddress addBillingAddress;
  private final DeleteBillingAddress deleteBillingAddress;
  private GetDeliveryAddress getDeliveryAddress;
  private GetCreditCards getCreditCards;
  private GetCountryCodeCC getCountryCodeCC;
  private GetCreditCardsV2 getCreditCardsV2;
  private UpdateCreditCardV2 updateCreditCardV2;
  private AddCreditCardV2 addCreditCardV2;
  private GetCountryCodes getCountryCodes;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  private SetDefaultCreditCard setDefaultCreditCard;
  private UserDetailResponse userDetailResponse;
  private List<CreditCardResponse> creditCardResponse;
  private List<CountryCode> countryCodeList;
  private List<BillingAddress> billingAddress;
  private final GetISOCountryCodes getISOCountryCodes;
  private final BillingAddressPayment billingAddressPayment;
  private final CheckAddCardFirstTime checkAddCardFirstTime;
  @Inject
  public CheckoutDeliveryPresenter(GetAppVersion getAppVersion,
                                   InternalStorageManager internalStorageManager, UpdateUserDetail updateUserDetail,
                                   DeleteAddress deleteAddress, AddBillingAddress addBillingAddress,
                                   EditBillingAddress editBillingAddress, DeleteBillingAddress deleteBillingAddress,
                                   GetDeliveryAddress getDeliveryAddress, GetCreditCards getCreditCards,
                                   AddCreditCard addCreditCard, UpdateCreditCard updateCreditCard,
                                   GetBillingAddresses getBillingAddresses,
                                   DeleteCreditCard deleteCreditCard, SetDefaultCreditCard setDefaultCreditCard,
                                   GetCountryCodes getCountryCodes,GetISOCountryCodes getISOCountryCodes,
                                   UpdateUserAddress updateUserAddress,
                                   BillingAddressPayment billingAddressPayment,GetCreditCardsV2 getCreditCardsV2,
                                   CheckAddCardFirstTime checkAddCardFirstTime, AddCreditCardV2 addCreditCardV2,
                                   UpdateCreditCardV2 updateCreditCardV2 , GetCountryCodeCC getCountryCodeCC) {
    super(getAppVersion, internalStorageManager);
    this.editBillingAddress = editBillingAddress;
    this.addBillingAddress = addBillingAddress;
    this.deleteBillingAddress = deleteBillingAddress;
    this.getBillingAddresses = getBillingAddresses;
    this.updateUserDetail = updateUserDetail;
    this.deleteAddress = deleteAddress;
    this.getDeliveryAddress = getDeliveryAddress;
    this.getCreditCards = getCreditCards;
    this.addCreditCard = addCreditCard;
    this.updateCreditCard = updateCreditCard;
    this.deleteCreditCard = deleteCreditCard;
    this.setDefaultCreditCard = setDefaultCreditCard;
    this.getCountryCodes = getCountryCodes;
    this.getISOCountryCodes = getISOCountryCodes;
    this.updateUserAddress = updateUserAddress;
    this.billingAddressPayment = billingAddressPayment;
    this.getCreditCardsV2 = getCreditCardsV2;
    this.checkAddCardFirstTime = checkAddCardFirstTime;
    this.addCreditCardV2 = addCreditCardV2;
    this.updateCreditCardV2 = updateCreditCardV2;
    this.getCountryCodeCC = getCountryCodeCC;
  }

  public void getDeliveryAddress() {
    getView().showLoading("Loading ...");
    add(getDeliveryAddress.execute(new SingleSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse value) {
        CheckoutDeliveryPresenter.this.userDetailResponse = value;
        getCreditCards();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load addresses"));
      }
    } ));
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void addAddress() {

    getView().showLoading("Loading ...");
    add(getDeliveryAddress.execute(new SingleSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse value) {
        CheckoutDeliveryPresenter.this.userDetailResponse = value;
        getView().hideLoading();
        getView().render(value);
        if (value.getAddresses() != null && value.getAddresses().size() >= 3) {
          getView().render(new Exception("Shipping addresses are limited to a maximum of 3"));
        } else {
          Address address = Address.skeletonAddress(userDetailResponse.getId());
          getView().showAddAddressDialog(address);
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load address count"));
      }
    } ));
  }



  public void updateAddress(Address address, boolean addNewAddress,List<ISOCountry> isoCountryList,List<CountryCode> countryCodes) {
    getView().showLoading("Updating address...");

/*  20201707 - WIKI Viet Nguyen - fix bug set default delivery address
    UpdateUserRequest request = buildRequest(address,isoCountryList,countryCodes);
*/

    UpdateUserDeliveryRequest deliveryRequest = buildRequestParams(address, isoCountryList, countryCodes);

    add(updateUserAddress.execute(new SingleSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse value) {
        getView().hideLoading();
        getView().hideAddressDialog();
        getDeliveryAddress();
      }

      @Override
      public void onError(Throwable error) {
        if(deliveryRequest.getMember_number().isEmpty()){
          getView().hideLoading();
          getView().render("Fail to load information due to missing Member Number");
        }else{
          getView().hideLoading();
          getView().render(error);
        }
      }
    }, new UpdateUserAddress.ParamsRequest(deliveryRequest, addNewAddress)));
  }

  /**<<START>> Nhat Nguyen - 25032020 : fix can not update delivery address <<END>> **/
  public String convertAddress(String value,List<ISOCountry> isoCountryList,List<CountryCode> countryCodes){

    String addresscountry = value;
    for(ISOCountry isoCountry : isoCountryList){

      for (CountryCode countryCode: countryCodes){
        if(value.equalsIgnoreCase(isoCountry.country_code) && isoCountry.country_name.equalsIgnoreCase(countryCode.getName())){
          addresscountry = String.valueOf(countryCode.getId()) ;
        }
      }

    }
    return addresscountry;
  }

  /**<<END>> Nhat Nguyen - 25032020 : fix can not update delivery address <<END>> **/
/*  private UpdateUserRequest buildRequest(Address address,List<ISOCountry> isoCountryList,List<CountryCode> countryCodes) {
    String member_number = "";
    List<CustomAttribute> customAttributeList = new ArrayList<>();

    for (CustomAttribute customAttribute : userDetailResponse.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equals("language")) {
        customAttributeList.add(new CustomAttribute("language", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("currency")) {
        customAttributeList.add(new CustomAttribute("currency", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("member_number")) {
        member_number = customAttribute.getValue();
        customAttributeList.add(new CustomAttribute("member_number", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contract_status")) {
        customAttributeList.add(new CustomAttribute("contract_status", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("nationality")) {
        customAttributeList.add(new CustomAttribute("nationality", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contact_country_code")) {
        customAttributeList.add(
            new CustomAttribute("contact_country_code", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("address_country")) {
        customAttributeList.add(new CustomAttribute("address_country",  convertAddress(customAttribute.getValue(),isoCountryList,countryCodes)));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("salutation")) {
        customAttributeList.add(new CustomAttribute("salutation", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contact_number")) {
        customAttributeList.add(new CustomAttribute("contact_number", customAttribute.getValue()));
      }
    }

    UpdateUserRequest.Customer customer = new UpdateUserRequest.Customer(userDetailResponse.getId(),
        userDetailResponse.getWebsiteId(), userDetailResponse.getFirstname(),
        userDetailResponse.getLastname(), userDetailResponse.getEmail(), customAttributeList);
    List<Address> addressList = new ArrayList<>();
    if (address.getId() != null) {
      for (Address address1 : userDetailResponse.getAddresses()) {
        if (address1.getCustomAttributes() == null) {
          List<CustomAttribute> list = new ArrayList<>();
          address1.setCustomAttributes(list);
        }
        String id = address1.getCountryId();
        address1.setCountryId(convertAddress(id,isoCountryList,countryCodes));
        addressList.add(address1);
      }
    } else {
      addressList.add(address);
      for (Address address1 : userDetailResponse.getAddresses()) {
        if (address1.getCustomAttributes() == null) {
          List<CustomAttribute> list = new ArrayList<>();
          address1.setCustomAttributes(list);
        }
        String id = address1.getCountryId();
        address1.setCountryId(convertAddress(id,isoCountryList,countryCodes));
        addressList.add(address1);
      }
    }
    customer.setAddresses(addressList);
    return new UpdateUserRequest(customer, member_number);
  } */

/*20201707 - WIKI Viet Nguyen - fix bug set default delivery address*/
  private UpdateUserDeliveryRequest buildRequestParams(Address address,List<ISOCountry> isoCountryList,List<CountryCode> countryCodes) {
    String member_number = "";
    List<CustomAttribute> customAttributeList = new ArrayList<>();

    for (CustomAttribute customAttribute : userDetailResponse.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equals("language")) {
        customAttributeList.add(new CustomAttribute("language", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("currency")) {
        customAttributeList.add(new CustomAttribute("currency", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("member_number")) {
        member_number = customAttribute.getValue();
        customAttributeList.add(new CustomAttribute("member_number", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contract_status")) {
        customAttributeList.add(new CustomAttribute("contract_status", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("nationality")) {
        customAttributeList.add(new CustomAttribute("nationality", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contact_country_code")) {
        customAttributeList.add(
                new CustomAttribute("contact_country_code", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("address_country")) {
        customAttributeList.add(new CustomAttribute("address_country",  convertAddress(customAttribute.getValue(),isoCountryList,countryCodes)));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("salutation")) {
        customAttributeList.add(new CustomAttribute("salutation", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contact_number")) {
        customAttributeList.add(new CustomAttribute("contact_number", customAttribute.getValue()));
      }
    }

    UpdateUserDeliveryRequest.Customer customer = new UpdateUserDeliveryRequest.Customer(userDetailResponse.getId(),
            userDetailResponse.getWebsiteId(), userDetailResponse.getFirstname(),
            userDetailResponse.getLastname(), userDetailResponse.getEmail(), customAttributeList);
    List<Address> addressList = new ArrayList<>();
    List<AddressDeliveryRequest> addressDeliveryRequests = new ArrayList<>();
    if (address.getId() != null) {
      for (Address address1 : userDetailResponse.getAddresses()) {
        if (address1.getCustomAttributes() == null) {
          List<CustomAttribute> list = new ArrayList<>();
          address1.setCustomAttributes(list);
        }
        String id = address1.getCountryId();
        address1.setCountryId(convertAddress(id,isoCountryList,countryCodes));
        addressList.add(address1);
      }
    } else {
      addressList.add(address);
      for (Address address1 : userDetailResponse.getAddresses()) {
        if (address1.getCustomAttributes() == null) {
          List<CustomAttribute> list = new ArrayList<>();
          address1.setCustomAttributes(list);
        }
        String id = address1.getCountryId();
        address1.setCountryId(convertAddress(id,isoCountryList,countryCodes));
        addressList.add(address1);
      }
    }
    for (int i = 0; i < addressList.size(); i++){
      addressDeliveryRequests.add(addressDeliveryRequest(addressList.get(i)));
    }
    customer.setAddresses(addressDeliveryRequests);
    return new UpdateUserDeliveryRequest(customer, member_number);
  }

  public void deleteAddress(Address address) {
    getView().showLoading("Deleting address...");
    add(deleteAddress.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean value) {
        getView().hideLoading();
        if (value) {
          getView().hideAddressDialog();
          getDeliveryAddress();
        } else {
          getView().render(new Exception("Filed to delete address"));
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Filed to delete address"));
      }
    }, new DeleteAddress.Params(address.getId())));
  }

  public void getCreditCards() {
    add(getCreditCardsV2.execute(new SingleSubscriber<List<CreditCardResponse>>() {

      @Override
      public void onSuccess(List<CreditCardResponse> value) {
        CheckoutDeliveryPresenter.this.creditCardResponse = value;
        getView().hideLoading();
        getView().renderCountryCode(countryCodeList);
        getView().render(userDetailResponse);
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

  void checkCardsCount() {
    getView().showLoading("Loading ...");
    add(getCreditCardsV2.execute(new SingleSubscriber<List<CreditCardResponse>>() {
      @Override
      public void onSuccess(List<CreditCardResponse> value) {
        getView().hideLoading();
        getView().render(value);
        if (value.size() >= 3) {
          getView().render(new Exception("Credit cards are limited to a maximum of 3"));
        } else {
          checkAddCardFirstTime();
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load cards"));
      }
    } ));
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

  public void addCreditCardFirstTime() {
    getView().showAddCreditDialogFirstTime();
  }
  private void addCreditCard() {

    if (userDetailResponse != null) {
      String city = "";
      String name = "";
      String street = "";
      String postCode = "";
      String countryCode = "";
      /*if (userDetailResponse.getAddresses().size() > 0) {
        for (Address address : userDetailResponse.getAddresses()) {
          if (address.isDefault()) {
            city = address.getCity();
            name = address.getFirstname() + " " + address.getLastname();
            street = address.getStreet().get(0);
            postCode = address.getPostcode();
            countryCode = address.getCountryId();
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
  private void addCreditCardV2() {
    CreditCardResponse creditCard = new CreditCardResponse();
    getView().showAddCreditDialog(creditCard);
  }

  public void updateCreditCard(CreditCardResponse creditCard, String message) {
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
          getView().render(new Exception("Failed to update credit card"));
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
      }, new UpdateCreditCard.Params(updateRequest, creditCard.isSetAsDefault())));
    }
  }

  private UpdateCreditCardRequest buildRequestUpdateCredit(CreditCardResponse creditCard) {
    UpdateCreditCardRequest.CreditCardItem creditCardItem =
        new UpdateCreditCardRequest.CreditCardItem(
            creditCard.getId(), creditCard.getExpMonth(), creditCard.getExpYear(),
            creditCard.getName(), creditCard.getAddressLine1(), creditCard.getAddressLine2(),
            creditCard.getAddressState(),
            creditCard.getAddressZip(), creditCard.getAddressCity(),
            creditCard.getAddressCountry(),BuildConfig.BuildId,BuildConfig.Chanel);
    return new UpdateCreditCardRequest(creditCardItem);
  }

  private AddCreditCardRequest buildRequestCreditCard(CreditCardResponse creditCard) {
    AddCreditCardRequest.CreditCardItemToken creditCardItemToken =
        new AddCreditCardRequest.CreditCardItemToken(creditCard.getCardNumber(),
            creditCard.getExpMonth(), creditCard.getExpYear(), (String) creditCard.getCvcCheck(),
            creditCard.getName(), creditCard.getAddressLine1(),
            "Singapore", creditCard.getAddressZip(), creditCard.getAddressCity(),
            creditCard.getAddressCountry(), BuildConfig.BuildId,BuildConfig.Chanel);
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
          getCreditCards();
        } else {
          getView().hideLoading();
          getView().showWarning(value.getMessage());
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to delete credit card"));
      }
    }, new DeleteCreditCard.Params(creditCard.getId())));
  }

  public void setDefaultCreditCard(String id) {
    getView().showLoading("Changing Default Credit Card ...");
    add(setDefaultCreditCard.execute(new SingleSubscriber<UpdateUserResponse>() {
      @Override
      public void onSuccess(UpdateUserResponse value) {
        if (value.getSuccess().equalsIgnoreCase("true")) {
          getCreditCards();
        } else {
          getView().hideLoading();
          getView().showWarning(value.getMessage());
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to change default credit card"));
      }
    }, new SetDefaultCreditCard.Params(id)));
  }

  void validate(int checkoutType,boolean isSelectDelivery,boolean isSelectBilling ,List<CreditCardResponse> creditCardList, Boolean isFullRedemption) {
    if (userDetailResponse == null
        || userDetailResponse.getAddresses() == null
        || userDetailResponse.getAddresses().size() <= 0) {
      getView().showWarning("Please add address to proceed");
      return;
    } //20200720 - WIKI Toan Tran -disable old condition
   /* boolean hasDefaultAddress = false;
    for (Address address : userDetailResponse.getAddresses()) {
      if (address.isDefault()) {
        hasDefaultAddress = true;
      }
    }

    if (!hasDefaultAddress) {
      getView().showWarning("Please select a delivery address to proceed");
      return;
    }*/


    //20200720 - WIKI Toan Tran -disable old condition
   /* boolean hasDefaultBillingAddress = false;
    if (billingAddress != null && billingAddress.size() > 0) {
      for (BillingAddress billingAddress : billingAddress) {
        if (billingAddress.isDefaultBilling()) {
          hasDefaultBillingAddress = true;
        }
      }
    }

    if (!hasDefaultBillingAddress) {
      getView().showWarning("Please set default billing address to proceed");
      return;
    }*/

    if(!isSelectBilling){
      getView().showWarning("Please select a billing address to proceed");
      return;
    }

    if(!isSelectDelivery){
      getView().showWarning("Please select a delivery address to proceed");
      return;
    }


    if (checkoutType
        == ShoppingCartPresenter.CHECKOUT_TYPE_RENEWAL_WITH_POINTS) { // no need to check credit for this case
      getView().proceedToNextStep();
      return;
    }

    if ((creditCardResponse == null || creditCardResponse.size() <= 0) && (checkoutType
            != ShoppingCartPresenter.CHECKOUT_TYPE_RENEWAL_WITH_POINTS)) {
      if(!isFullRedemption){
        getView().showWarning("Please add credit card to proceed");
        return;
      }else {
        getView().proceedToNextStep();
        return;
      }

    }
    if(creditCardList != null){
      boolean hasSelectedCard = false;
      for(CreditCardResponse item : creditCardList){
        if(item.getVisaSelected()){
          hasSelectedCard = true;
        }
      }
      if(!hasSelectedCard){
        if(!isFullRedemption){
          getView().showWarning("Please add credit card to proceed");
          return;
        }else {
          getView().proceedToNextStep();
          return;
        }
      }
    }
    //Disable checking default creditCard
   /* boolean hasDefaultCard = false;
    for (CreditCardResponse response : creditCardResponse) {
      if (response.getCartDefault()) {
        hasDefaultCard = true;
      }
    }

    if (!hasDefaultCard) {
      getView().showWarning("Please set a default credit card to proceed");
      return;
    }*/

    getView().proceedToNextStep();
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

      add(updateCreditCardV2.execute(new SingleSubscriber<UpdateUserResponse>() {
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
      }, new UpdateCreditCardV2.Params(updateRequest, setAsDefault)));
    }
  }
  public void getBillingAddress() {
    add(getBillingAddresses.execute(new SingleSubscriber<List<BillingAddress>>() {
      @Override
      public void onSuccess(List<BillingAddress> value) {
        CheckoutDeliveryPresenter.this.billingAddress = value;
        getView().renderBillingAddress(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().render(new Exception("Failed to load billing addresses"));
      }
    } ));
  }

  public void getCountryCodes() {
    add(getCountryCodes.execute(new SingleSubscriber<List<CountryCode>>() {
      @Override
      public void onSuccess(List<CountryCode> value) {
        CheckoutDeliveryPresenter.this.countryCodeList = value;
        getDeliveryAddress();
      }

      @Override
      public void onError(Throwable error) {
        getView().render(new Exception("Failed to load country codes"));
      }
    } ));
  }

  public void setDefaultBillingAddress(BillingAddress address) {
    address.setDefaultBilling(true);
    editBillingAddress(address);
  }

  public void collectCountryCodesAndProceedToEdit(BillingAddress address) {
    if (countryCodeList != null) {
      getView().showEditBillingAddressDialog(countryCodeList, address);
    }
  }

  public void editBillingAddress(BillingAddress address) {
    getView().showLoading("Loading ...");
    add(editBillingAddress.execute(new SingleSubscriber<List<BillingAddress>>() {
      @Override
      public void onSuccess(List<BillingAddress> value) {
        CheckoutDeliveryPresenter.this.billingAddress = value;
        getView().hideLoading();
        getView().renderBillingAddress(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to edit address"));
      }
    }, new EditBillingAddress.Params(address)));
  }
  public void getCountryList() {
    getView().showLoading("Loading...");
    add(getCountryCodeCC.execute(new SingleSubscriber<List<CountryCodeCC>>() {

      @Override
      public void onSuccess(List<CountryCodeCC> response) {
        getView().hideLoading();
        getView().initCountryCodeCc(response);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
      }
    } ));
  }
  public void deleteBillingAddress(BillingAddress address) {
    getView().showLoading("Loading ...");
    add(deleteBillingAddress.execute(new SingleSubscriber<List<BillingAddress>>() {
      @Override
      public void onSuccess(List<BillingAddress> value) {
        CheckoutDeliveryPresenter.this.billingAddress = value;
        getView().hideLoading();
        getView().renderBillingAddress(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to delete address"));
      }
    }, new DeleteBillingAddress.Params(address.getId())));
  }

  public void addBillingAddress(BillingAddress address) {
    getView().showLoading("Loading ...");
    add(addBillingAddress.execute(new SingleSubscriber<List<BillingAddress>>() {
      @Override
      public void onSuccess(List<BillingAddress> value) {
        CheckoutDeliveryPresenter.this.billingAddress = value;
        getView().hideLoading();
        getView().renderBillingAddress(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to add Address"));
      }
    }, new AddBillingAddress.Params(address)));
  }

  public void collectCountryCodesAndProceedToAdd() {
    if (countryCodeList != null) {
      getView().showLoading("Loading ...");
      add(getBillingAddresses.execute(new SingleSubscriber<List<BillingAddress>>() {
        @Override
        public void onSuccess(List<BillingAddress> value) {
          getView().hideLoading();
          getView().renderBillingAddress(value);
          if (value.size() >= 3) {
            getView().render(new Exception("Billing addresses are limited to a maximum of 3"));
          } else {
            getView().showAddBillingAddressDialog(countryCodeList);
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
  public void getCountry() {
    add(getISOCountryCodes.execute(new SingleSubscriber<List<ISOCountry>>() {
      @Override
      public void onSuccess(List<ISOCountry> value) {

        getView().render(value,true);
      }

      @Override
      public void onError(Throwable error) {
        getView().render(new Exception("Failed to load country codes"));
      }
    } ));
  }

  private AddressDeliveryRequest addressDeliveryRequest(Address address){
    AddressDeliveryRequest request = new AddressDeliveryRequest();
    request.setCity(address.getCity());
    request.setCompany(address.getCompany());
    request.setContactCountryCode(address.getContactCountryCode());
    request.setCountryId(address.getCountryId());
    request.setCustomAttributes(address.getCustomAttributes());
    request.setCustomerId(address.getCustomerId());
    request.setDefaultBilling(address.getDefaultBilling());
    request.setDefaultShipping(address.getDefaultShipping());
    request.setFirstname(address.getFirstname());
    request.setLastname(address.getLastname());
    request.setId(address.getId());
    request.setPostcode(address.getPostcode());
    request.setPrefix(address.getPrefix());
    request.setRegion(address.getRegion());
    request.setRegionId(address.getRegionId());
    request.setStreet(address.getStreet());
    request.setTelephone(address.getTelephone());
    return request;
  }


  /*20201707 - WIKI Viet Nguyen - fix bug set default billing address*/
  public void requestBillingAddressPayment(RequestBody params) {
    add(billingAddressPayment.execute(new SingleSubscriber<BillingAddressPaymentRespond>() {
      @Override
      public void onSuccess(BillingAddressPaymentRespond respond) {
        if (respond != null){
          if (respond.getStatus()){
            getView().requestBillingAddressSuccess();
          } else {
            getView().requestBillingAddressError(respond.getMessage());
          }
        }
      }
      @Override
      public void onError(Throwable error) {
        getView().render(new Exception(error));
      }
    },params));
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
}
