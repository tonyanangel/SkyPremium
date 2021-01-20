package com.skypremiuminternational.app.domain.models.membership;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sandi on 10/1/17.
 */

public class Description implements Parcelable {

  String description;

  public Description(String description) {
    this.description = description;
  }

  protected Description(Parcel in) {
    description = in.readString();
  }

  public String getDescription() {
    return description;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(description);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Description> CREATOR = new Creator<Description>() {
    @Override
    public Description createFromParcel(Parcel in) {
      return new Description(in);
    }

    @Override
    public Description[] newArray(int size) {
      return new Description[size];
    }
  };
}
