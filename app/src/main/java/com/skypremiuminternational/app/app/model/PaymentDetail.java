package com.skypremiuminternational.app.app.model;

import com.skypremiuminternational.app.domain.models.booking.TourismFee;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetail {

  public String cardType = "";
  public String cardNumber = "";
  public String expiryMonth = "";
  public String expiryYear = "";
  public String securityCode = "";
  public String firstName = "";
  public String lastName = "";
  public String emailAddress = "";
  public String phoneCode = "";
  public String phoneNumber = "";
  public String streetAddress = "";
  public String unitNumber = "";
  public String postalCode = "";
  public String city = "";
  public String state = "";
  public String country = "";
  public boolean hasReadAndAcceptRules = true;
  public int cardPosition = -1;
  public int phoneCodePosition = 0;
  public int countryPosition = 0;
  public String grandTotal = "";
  public List<TourismFee> fees = new ArrayList<>();
  public String countryCode = "";
  public String cardTypeAbbr = "";
  public String processingCountry = "";
  public String cancellationPolicy = "";
}
