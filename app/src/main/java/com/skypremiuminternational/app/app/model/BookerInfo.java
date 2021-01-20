package com.skypremiuminternational.app.app.model;

import android.text.TextUtils;

import java.util.List;

public final class BookerInfo {

  public final String roomName;
  public String salutation;
  public String firstName;
  public String lastName;
  public String phoneCode;
  public String phoneNumber;
  public boolean isSmoking;
  public String specialRequest;
  public int phoneCodePosition;

  public boolean salutationHasError;
  public boolean firstNameHasError;
  public boolean lastNameHasError;
  public boolean phoneNumberHasError;
  public boolean phoneCodeHasError;
  public int numberOfAdult;
  public List<Integer> childAges;
  public int salutationPosition;

  private BookerInfo(String roomName) {
    this.roomName = roomName;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    public int numberOfAdult = 0;
    public int salutationPosition;
    private String roomName;
    private String salutation;
    private String firstName;
    private String lastName;
    private String phoneCode;
    private String phoneNumber;
    private boolean isSmoking;
    private String specialRequest;
    private int phoneCodePosition;
    private List<Integer> childAges;

    public Builder setNumberOfAdult(int numberOfAdult) {
      this.numberOfAdult = numberOfAdult;
      return this;
    }

    public Builder setRoomName(String roomName) {
      if (roomName == null || TextUtils.isEmpty(roomName)) {
        throw new IllegalArgumentException("roomName is not set");
      }
      this.roomName = roomName;
      return this;
    }

    public Builder setSalutation(String salutation) {
      this.salutation = emptyIfNull(salutation);
      return this;
    }

    public Builder setFirstName(String firstName) {
      this.firstName = emptyIfNull(firstName);
      return this;
    }

    public Builder setLastName(String lastName) {
      this.lastName = emptyIfNull(lastName);
      return this;
    }

    public Builder setPhoneCode(String phoneCode) {
      this.phoneCode = emptyIfNull(phoneCode);
      return this;
    }

    public Builder setPhoneNumber(String phoneNumber) {
      this.phoneNumber = emptyIfNull(phoneNumber);
      return this;
    }

    public Builder setSmoking(boolean smoking) {
      isSmoking = smoking;
      return this;
    }

    public Builder setSpecialRequest(String specialRequest) {
      this.specialRequest = emptyIfNull(specialRequest);
      return this;
    }

    public Builder setPhoneCodePosition(int phoneCodePosition) {
      this.phoneCodePosition = phoneCodePosition;
      return this;
    }

    public Builder setSalutationPosition(int salutationPosition) {
      this.salutationPosition = salutationPosition;
      return this;
    }

    public Builder setChildAges(List<Integer> childAges) {
      this.childAges = childAges;
      return this;
    }

    public BookerInfo build() {
      BookerInfo bookerInfo = new BookerInfo(roomName);
      bookerInfo.salutation = emptyIfNull(salutation);
      bookerInfo.firstName = emptyIfNull(firstName);
      bookerInfo.lastName = emptyIfNull(lastName);
      bookerInfo.phoneCode = emptyIfNull(phoneCode);
      bookerInfo.phoneNumber = emptyIfNull(phoneNumber);
      bookerInfo.isSmoking = isSmoking;
      bookerInfo.specialRequest = emptyIfNull(specialRequest);
      bookerInfo.phoneCodePosition = phoneCodePosition;
      bookerInfo.numberOfAdult = numberOfAdult;
      bookerInfo.childAges = childAges;
      bookerInfo.salutationPosition = salutationPosition;
      return bookerInfo;
    }

    private String emptyIfNull(String text) {
      return text == null ? "" : text;
    }
  }
}
