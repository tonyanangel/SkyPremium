package com.skypremiuminternational.app.app.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnsonmaung on 5/16/17.
 */

public class Validator {

  private static final String EMAIL_PATTERN =
      "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
  private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);
  private static final String APHALBE_NUMBER_PATTERN ="[A-Za-z0-9 ]+";
  private static Pattern patternAphalbeNumber = Pattern.compile(APHALBE_NUMBER_PATTERN);
  private static int password_length = 8;
  private static Matcher matcher;
  private static Matcher matcherAphalbeNumber ;

  public static boolean isUsernameValid(String username) {
    return username != null && username.trim().length() > 0;
  }

  public static boolean isTextValid(String text) {
    return text != null && text.trim().length() > 0;
  }
  public static boolean isUnitNumberValid(String text) {
    matcherAphalbeNumber = patternAphalbeNumber.matcher(text);
    return matcherAphalbeNumber.matches();
  }

  public static boolean isEmailValid(String email) {
    matcher = pattern.matcher(email);
    return matcher.matches();
  }

  public static boolean isPasswordValid(String password) {
    return password.length() >= password_length;
  }

  public static boolean isPhoneValid(String phone) {
    return phone.length() != 0;
  }

  public static boolean isCodeValid(String code) {
    return code.length() == 9;
  }

  public static boolean isCustomerValid(String s) {
    return !TextUtils.isEmpty(s);
  }

  public static boolean isBillVaild(String s) {
    if (s.length() == 15) return true;
    return false;
  }
}
