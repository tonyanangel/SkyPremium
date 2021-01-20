package com.skypremiuminternational.app.data.mapper;

import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.data.model.billingaddress.EditBillingAddressRequest;

import javax.inject.Inject;

public class EditBillingAddressRequestMapper {

  @Inject
  public EditBillingAddressRequestMapper() {

  }

  public EditBillingAddressRequest map(BillingAddress billingAddress) {
    EditBillingAddressRequest.BillingAddress address =
        new EditBillingAddressRequest.BillingAddress();
    address.setId(billingAddress.getId());
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
    EditBillingAddressRequest request = new EditBillingAddressRequest();
    request.setBillingAddress(address);
    return request;
  }
}
