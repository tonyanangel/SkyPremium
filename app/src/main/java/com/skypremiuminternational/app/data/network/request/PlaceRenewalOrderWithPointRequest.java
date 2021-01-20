package com.skypremiuminternational.app.data.network.request;

import java.util.List;

public class PlaceRenewalOrderWithPointRequest {

  public final PaymentMethod paymentMethod;
  public final Address billing_address;

  public PlaceRenewalOrderWithPointRequest(PaymentMethod paymentMethod, Address billing_address) {
    this.paymentMethod = paymentMethod;
    this.billing_address = billing_address;
  }

  public static class PaymentMethod {
    public final String method;

    public PaymentMethod(String method) {
      this.method = method;
    }
  }

  public static class Address {
    public final String email;
    public final String firstname;
    public final String lastname;
    public final String region;
    public final String region_id;
    public final String region_code;
    public final String country_id;
    public final List<String> street;
    public final String postcode;
    public final String city;
    public final String telephone;

    public Address(String email, String firstname, String lastname, String region, String region_id,
                   String region_code, String country_id, List<String> street, String postcode, String city,
                   String telephone) {
      this.email = email;
      this.firstname = firstname;
      this.lastname = lastname;
      this.region = region;
      this.region_id = region_id;
      this.region_code = region_code;
      this.country_id = country_id;
      this.street = street;
      this.postcode = postcode;
      this.city = city;
      this.telephone = telephone;
    }
  }
}
