package com.skypremiuminternational.app.data.aws.hash;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha128 {

  private static final String SHA_128 = "SHA-1";
  private static final char[] hexDigits = "0123456789abcdef".toCharArray();

  private Sha128() {
    // hide default constructor
  }

  public static String get(String value, Charset charset) throws NoSuchAlgorithmException {
    MessageDigest md;
    md = MessageDigest.getInstance(SHA_128);
    byte[] bytes = value.getBytes(charset);
    md.update(bytes);
    md.getDigestLength();
    return bytesToHex(md.digest());
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder(2 * bytes.length);
    for (byte b : bytes) {
      sb.append(hexDigits[(b >> 4) & 0xf]).append(hexDigits[b & 0xf]);
    }
    return sb.toString();
  }
}
