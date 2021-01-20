package com.skypremiuminternational.app.data.mapper;

import com.skypremiuminternational.app.data.model.billingaddress.AddBillingAddressRequest;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;

import javax.inject.Inject;

public class AddBillingAddressRequestMapper {

  @Inject
  public AddBillingAddressRequestMapper() {

  }

  public AddBillingAddressRequest map(BillingAddress billingAddress) {
    AddBillingAddressRequest.BillingAddress address = new AddBillingAddressRequest.BillingAddress();
    address.setFirstname(billingAddress.getFirstname());
    address.setLastname(billingAddress.getLastname());
    address.setRegionId(billingAddress.getRegionId());
    address.setPostcode(billingAddress.getPostcode());
    address.setTelephone(billingAddress.getTelephone());
    address.setUnitNumber(billingAddress.getUnitNumber());
    address.setStreet(billingAddress.getStreet());
    address.setCompany(billingAddress.getCompany());
    address.setSalutation(billingAddress.getSalutation());
    String region = "";
    if (billingAddress.getRegion() != null && billingAddress.getRegion().size() > 0) {
      region = billingAddress.getRegion().get(0).getRegion();
    }
    address.setRegion(region);
    address.setDefaultBilling(billingAddress.isDefaultBilling());
    address.setCountryId(billingAddress.getCountryId());
    address.setCity(billingAddress.getCity());
    AddBillingAddressRequest request = new AddBillingAddressRequest();
    request.setBillingAddress(address);
    return request;
  }
}
