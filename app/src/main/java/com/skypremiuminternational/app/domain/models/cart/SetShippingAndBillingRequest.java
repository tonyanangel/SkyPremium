package com.skypremiuminternational.app.domain.models.cart;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by codigo on 7/3/18.
 */

public class SetShippingAndBillingRequest {

  public final AddressInformation addressInformation;

  public SetShippingAndBillingRequest(AddressInformation addressInformation) {
    this.addressInformation = addressInformation;
  }

  @AutoValue
  public static abstract class AddressInformation {
    public abstract Address shipping_address();

    public abstract Address billing_address();

    public abstract String shipping_carrier_code();

    public abstract String shipping_method_code();

    public static AddressInformation create(Address shipping_address, Address billing_address,
                                            String shipping_carrier_code, String shipping_method_code) {
      return builder().shipping_address(shipping_address)
          .billing_address(billing_address)
          .shipping_carrier_code(shipping_carrier_code)
          .shipping_method_code(shipping_method_code)
          .build();
    }

    public static Builder builder() {
      return new AutoValue_SetShippingAndBillingRequest_AddressInformation.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder shipping_address(Address shipping_address);

      public abstract Builder billing_address(Address billing_address);

      public abstract Builder shipping_carrier_code(String shipping_carrier_code);

      public abstract Builder shipping_method_code(String shipping_method_code);

      public abstract AddressInformation build();
    }
  }

  @AutoValue
  public static abstract class Address {
    public abstract String email();

    public abstract String firstname();

    public abstract String lastname();

    @Nullable
    public abstract String region();

    @Nullable
    public abstract String company();

    @Nullable
    public abstract String region_id();

    @Nullable
    public abstract String region_code();

    public abstract String country_id();

    public abstract List<String> street();

    public abstract String postcode();

    public abstract String city();

    public abstract String telephone();

    public abstract String unit_number();

    public static Address create(String email, String firstname, String lastname, String region,
                                 String region_id, String region_code, String country_id, List<String> street,
                                 String postcode, String city, String telephone,String unit_number,String company) {
      return builder().email(email)
          .firstname(firstname)
          .lastname(lastname)
          .region(region)
          .company(company)
          .region_id(region_id)
          .region_code(region_code)
          .country_id(country_id)
          .street(street)
          .postcode(postcode)
          .city(city)
          .telephone(telephone)
          .unit_number(unit_number)
          .build();
    }

    public static Builder builder() {
      return new AutoValue_SetShippingAndBillingRequest_Address.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder email(String email);

      public abstract Builder firstname(String firstname);

      public abstract Builder lastname(String lastname);

      public abstract Builder region(String region);

      public abstract Builder company(String company);

      public abstract Builder region_id(String region_id);

      public abstract Builder region_code(String region_code);

      public abstract Builder country_id(String country_id);

      public abstract Builder street(List<String> street);

      public abstract Builder postcode(String postcode);

      public abstract Builder city(String city);

      public abstract Builder telephone(String telephone);

      public abstract Builder unit_number(String unit_number);

      public abstract Address build();
    }
  }
}
