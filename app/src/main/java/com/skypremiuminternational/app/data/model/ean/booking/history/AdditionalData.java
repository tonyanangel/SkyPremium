package com.skypremiuminternational.app.data.model.ean.booking.history;

import java.util.List;

public class AdditionalData {

  public final String affiliate_reference_id;
  public final List<Payment> payments;
  public final List<Room> rooms;

  public AdditionalData(
      String affiliate_reference_id, List<Payment> payments,
      List<Room> rooms) {
    this.affiliate_reference_id = affiliate_reference_id;
    this.payments = payments;
    this.rooms = rooms;
  }

  public static class Payment {
    public final String type;
    public final String card_type;
    public final String number;
    public final String security_code;
    public final String expiration_month;
    public final String expiration_year;
    public final BillingContact billing_contact;

    public Payment(String type, String card_type, String number, String security_code,
                   String expiration_month, String expiration_year, BillingContact billing_contact) {
      this.type = type;
      this.card_type = card_type;
      this.number = number;
      this.security_code = security_code;
      this.expiration_month = expiration_month;
      this.expiration_year = expiration_year;
      this.billing_contact = billing_contact;
    }
  }

  public static class BillingContact {
    public final String given_name;
    public final String family_name;
    public final String email;
    public final String phone;
    public final Address address;

    public BillingContact(String given_name, String family_name, String email, String phone, Address address) {
      this.given_name = given_name;
      this.family_name = family_name;
      this.email = email;
      this.phone = phone;
      this.address = address;
    }
  }

  public static class Address {
    public final String city;
    public final String country_code;
    public final String line_1;
    public final String line_2;
    public final String line_3;
    public final String postal_code;
    public final String state_province_code;

    public Address(String city, String country_code, String line_1, String line_2,
                   String line_3, String postal_code, String state_province_code) {
      this.city = city;
      this.country_code = country_code;
      this.line_1 = line_1;
      this.line_2 = line_2;
      this.line_3 = line_3;
      this.postal_code = postal_code;
      this.state_province_code = state_province_code;
    }
  }

  public static class Room {
    public final List<Integer> child_ages;
    public final String email;
    public final String family_name;
    public final String given_name;
    public final int number_of_adults;
    public final String phone;
    public final boolean smoking;
    public final String special_request;
    public final String title;

    public Room(List<Integer> child_ages, String email, String family_name, String given_name,
                int number_of_adults, String phone, boolean smoking, String special_request, String title) {
      this.child_ages = child_ages;
      this.email = email;
      this.family_name = family_name;
      this.given_name = given_name;
      this.number_of_adults = number_of_adults;
      this.phone = phone;
      this.smoking = smoking;
      this.special_request = special_request;
      this.title = title;
    }
  }
}
