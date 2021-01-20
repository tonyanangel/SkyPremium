package com.skypremiuminternational.app.data.utl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class EanAuthHeaderGenerator {

  public String generate(final String apiKey, final String sharedSecret) {
    Date date = new java.util.Date();
    Long timestamp = (date.getTime() / 1000);
    String signature = null;
    try {
      String toBeHashed = apiKey + sharedSecret + timestamp;
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      byte[] bytes = md.digest(toBeHashed.getBytes("UTF-8"));
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      signature = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "EAN APIKey=" + apiKey + ",Signature=" + signature + ",timestamp=" + timestamp;
  }
}
