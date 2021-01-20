package com.skypremiuminternational.app.app.features.profile.manage_delivery_address;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.UpdateUserDeliveryRequest;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodes;
import com.skypremiuminternational.app.domain.interactor.ean.GetISOCountryCodes;
import com.skypremiuminternational.app.domain.interactor.user.DeleteAddress;
import com.skypremiuminternational.app.domain.interactor.user.GetDeliveryAddress;
import com.skypremiuminternational.app.domain.interactor.user.UpdateUserAddress;
import com.skypremiuminternational.app.domain.interactor.user.UpdateUserDetail;
import com.skypremiuminternational.app.domain.models.checkout.AddressDeliveryRequest;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aeindraaung on 2/19/18.
 */

public class ManageDeliveryAddressPresenter extends BasePresenter<ManageDeliveryAddressView> {
  private final UpdateUserDetail updateUserDetail;
  private final UpdateUserAddress updateUserAddress;
  private final DeleteAddress deleteAddress;
  private GetDeliveryAddress getDeliveryAddress;
  private GetCountryCodes getCountryCodes;
  private final GetISOCountryCodes getISOCountryCodes;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  private UserDetailResponse userDetailResponse;

  @Inject
  ManageDeliveryAddressPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                                 GetDeliveryAddress getDeliveryAddress, UpdateUserDetail updateUserDetail,
                                 DeleteAddress deleteAddress, GetCountryCodes getCountryCodes,GetISOCountryCodes getISOCountryCodes,
                                 UpdateUserAddress updateUserAddress) {
    super(getAppVersion, internalStorageManager);
    this.getDeliveryAddress = getDeliveryAddress;
    this.deleteAddress = deleteAddress;
    this.updateUserDetail = updateUserDetail;
    this.getCountryCodes = getCountryCodes;
    this.getISOCountryCodes = getISOCountryCodes;
    this.updateUserAddress = updateUserAddress;
  }

  void getDeliveryAddresses() {
    getView().showLoading("Loading...");
    add(getDeliveryAddress.execute(new SingleSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse value) {
        ManageDeliveryAddressPresenter.this.userDetailResponse = value;
        getView().hideLoading();
        getView().render(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load addresses"));
      }
    } ));
  }

  void updateAddress(Address address, boolean addNewAddress,List<ISOCountry> isoCountryList,List<CountryCode> countryCodes) {

    getView().showLoading("Updating address...");

    /*20201707 - WIKI Viet Nguyen - fix bug set default delivery address
    UpdateUserRequest request = buildRequest(address,isoCountryList,countryCodes);
    */
    UpdateUserDeliveryRequest request = buildRequestParams(address,isoCountryList,countryCodes);


    add(updateUserAddress.execute(new SingleSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse value) {
        getView().hideLoading();
        getView().hideAddressDialog();
        getDeliveryAddresses();
      }

      @Override
      public void onError(Throwable error) {
        if(request.getMember_number().isEmpty()){
          getView().hideLoading();
           getView().render("Fail to load information due to missing Member Number");
        }else{
          getView().hideLoading();
           getView().render(error);
        }
      }
    }, new UpdateUserAddress.ParamsRequest(request, addNewAddress)));
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

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
        customAttributeList.add(new CustomAttribute("address_country", convertAddress(customAttribute.getValue(),isoCountryList,countryCodes)));
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
  }*/

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

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
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
  void deleteAddress(Address address) {
    getView().showLoading("Deleting delivery address...");
    add(deleteAddress.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean value) {
        getView().hideLoading();
        if (value) {
          getView().hideAddressDialog();
          getDeliveryAddresses();
        } else {
          getView().render(new Exception("Deleting delivery address failed!"));
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to delete address"));
      }
    }, new DeleteAddress.Params(address.getId())));
  }

  void addAddress() {
    getView().showLoading("Loading ...");
    add(getDeliveryAddress.execute(new SingleSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse value) {
        ManageDeliveryAddressPresenter.this.userDetailResponse = value;
        getView().hideLoading();
        getView().render(value);
        if (value.getAddresses() != null && value.getAddresses().size() >= 3) {
          getView().render(new Exception("Addresses are limited to a maximum of 3"));
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

  public void getCountryCodes() {
    getView().showLoading("Loading ...");
    add(getCountryCodes.execute(new SingleSubscriber<List<CountryCode>>() {
      @Override
      public void onSuccess(List<CountryCode> value) {

        add(getISOCountryCodes.execute(new SingleSubscriber<List<ISOCountry>>() {
          @Override
          public void onSuccess(List<ISOCountry> value1) {
            getView().hideLoading();
            getView().render(value,value1);

          }

          @Override
          public void onError(Throwable error) {
            getView().hideLoading();
            getView().render(new Exception("Failed to load country codes"));
          }
        } ));

      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
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
    request.setDefaultBilling(address.getDefaultBilling());
    request.setDefaultShipping(address.getDefaultShipping());
    request.setCustomerId(address.getCustomerId());
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
}
