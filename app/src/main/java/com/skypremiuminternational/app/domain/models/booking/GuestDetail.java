package com.skypremiuminternational.app.domain.models.booking;

public class GuestDetail {

  public final String salutation;
  public final boolean isSmoking;
  public final String contactNumber;
  public final String specialRequest;
  public final String guestName;
  public final String roomName;
  public final String totalNights;
  public final double price;
  public final double feeNTaxes;
  public final boolean isEurope;

  public GuestDetail(String salutation, boolean isSmoking, String contactNumber,
                     String specialRequest, String guestName,
                     String roomName,
                     String totalNights, double price,
                     double feeNTaxes, boolean isEurope) {
    this.salutation = salutation;
    this.isSmoking = isSmoking;
    this.contactNumber = contactNumber;
    this.specialRequest = specialRequest;
    this.guestName = guestName;
    this.roomName = roomName;
    this.totalNights = totalNights;
    this.price = price;
    this.feeNTaxes = feeNTaxes;
    this.isEurope = isEurope;
  }
}
