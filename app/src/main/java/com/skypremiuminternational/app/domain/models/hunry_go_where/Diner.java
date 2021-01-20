package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Diner {

  @SerializedName("firstName")
  String firstName;
  @SerializedName("lastName")
  String lastName;
  @SerializedName("name")
  String fullName;
  @SerializedName("phones")
  List<ReservePhone> phones;
  @SerializedName("emails")
  List<ReserveEmail> emails;



  public ReservePhone getPrimaryPhone(){
    for(ReservePhone phone  :  getPhones()){
      if(phone.isPrimary()){
        return phone;
      }
    }
    return null;
  }


  public ReserveEmail getPrimaryEmail(){
    for(ReserveEmail email  :  getEmails()){
      if(email.isPrimary()){
        return email;
      }
    }
    return null;
  }

  public List<ReservePhone> getPhones() {
    return phones;
  }

  public void setPhones(List<ReservePhone> phones) {
    this.phones = phones;
  }

  public List<ReserveEmail> getEmails() {
    return emails;
  }

  public void setEmails(List<ReserveEmail> emails) {
    this.emails = emails;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
