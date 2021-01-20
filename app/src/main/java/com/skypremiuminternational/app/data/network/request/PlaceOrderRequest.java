package com.skypremiuminternational.app.data.network.request;

import java.util.List;

/**
 * Created by codigo on 28/2/18.
 */

public final class PlaceOrderRequest {

  public final PaymentMethod paymentMethod;
  public final Address billing_address;

  public PlaceOrderRequest(PaymentMethod paymentMethod, Address billing_address) {
    this.paymentMethod = paymentMethod;
    this.billing_address = billing_address;
  }

  public static class PaymentMethod {
    public final String method;
    public final AdditionalData additional_data;

    public PaymentMethod(String method, AdditionalData additional_data) {
      this.method = method;
      this.additional_data = additional_data;
    }
  }

  public static class AdditionalData {
    public final String sky_stripe_card_id;
    public final String cc_type;
    public final String cc_number;
    public final String expiration;
    public final String expiration_yr;
    public final String save_card;
    public final String cc_cid;
    public final String buildId;
    public final String chanel;

    public AdditionalData(String sky_stripe_card_id, String cc_type, String cc_number,
                          String expiration, String expiration_yr, String save_card, String cc_cid,String buildId,String chanel) {
      this.sky_stripe_card_id = sky_stripe_card_id;
      this.cc_type = cc_type;
      this.cc_number = cc_number;
      this.expiration = expiration;
      this.expiration_yr = expiration_yr;
      this.save_card = save_card;
      this.cc_cid = cc_cid;
      this.buildId = buildId;
      this.chanel = chanel;
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
