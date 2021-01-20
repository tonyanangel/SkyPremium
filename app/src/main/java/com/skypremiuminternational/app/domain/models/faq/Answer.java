package com.skypremiuminternational.app.domain.models.faq;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sandi on 10/1/17.
 */

public class Answer implements Parcelable {

  String name;

  public Answer(String name) {
    this.name = name;
  }

  protected Answer(Parcel in) {
    name = in.readString();
  }

  public String getName() {
    return name;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Answer> CREATOR = new Creator<Answer>() {
    @Override
    public Answer createFromParcel(Parcel in) {
      return new Answer(in);
    }

    @Override
    public Answer[] newArray(int size) {
      return new Answer[size];
    }
  };
}
