package com.skypremiuminternational.app.app.utils;

import android.view.View;

/**
 * Created by sandi on 11/2/16.
 */
public class PasswordTransformationMethod extends android.text.method.PasswordTransformationMethod {
  @Override
  public CharSequence getTransformation(CharSequence source, View view) {
    return new PasswordCharSequence(source);
  }

  private class PasswordCharSequence implements CharSequence {
    private CharSequence mSource;

    public PasswordCharSequence(CharSequence source) {
      mSource = source; // Store char sequence
    }

    public char charAt(int index) {
      return '•'; // This is the important part
    }

    public int length() {
      return mSource.length(); // Return default
    }

    public CharSequence subSequence(int start, int end) {
      return mSource.subSequence(start, end); // Return default
    }
  }
};
