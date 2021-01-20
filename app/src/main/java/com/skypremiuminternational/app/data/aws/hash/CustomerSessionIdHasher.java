package com.skypremiuminternational.app.data.aws.hash;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

public class CustomerSessionIdHasher {
  private static final Charset UTF_8 = Charset.forName("UTF-8");

  @Inject
  public CustomerSessionIdHasher() {

  }

  public String hash(String userToken, String userId) {
    try {
      return Sha256.get(userToken + userId + "-android", UTF_8);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return "";
    }
  }
}
