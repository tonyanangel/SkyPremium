package com.skypremiuminternational.app.data.network.request;

import android.provider.ContactsContract;

import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;

import java.util.List;

public class BookRoomRequest {

  public String token;

  public RequestData request_data;

  public static class Room {
    public String title;
    public String given_name;
    public String family_name;
    public String email;
    public String phone;
    public boolean smoking;
    public String special_request;
    public int number_of_adults;
    public List<Integer> child_ages;
  }

  public static class Address {
    public String line_1;
    public String line_2;
    public String line_3;
    public String city;
    public String state_province_code;
    public String postal_code;
    public String country_code;
  }

  public static class BillingContact {
    public String given_name;
    public String family_name;
    public String email;
    public String phone;
    public Address address;
  }

  public static class Payment {
    public String type;
    public String card_type;
    public String number;
    public String security_code;
    public String expiration_month;
    public String expiration_year;

    public BillingContact billing_contact;
  }

  public static class RequestData {
    public int affiliate_reference_id;
    public String email;
    public Phone phone;
    public List<Room> rooms;
    public List<Payment> payments;
    public String affiliate_metadata;
    public CustomerData customer_data;
  }

  public static class Phone {

    public String country_code;
    public String number;

  }

  public static class CustomerData {
    public String customer_session_id;
    public String customer_ip;
    public String email_address;
    public boolean book_for_so;
    public String check_in;
    public String check_out;
    public String property_id;
    public String bed_group_selected;
    public String bed_groups_of_room;
    public String cancellation_policy;
    public String country_payment_will_be_processed;
    public String sky_dollar;
  }
}
