/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2016 the original author or authors.
 */
package com.skypremiuminternational.app.data.aws.hash;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Richard Lucas
 */
public final class Sha256 {

  private static final String SHA_256 = "SHA-256";
  private static final String ZERO = "0";
  private static final char[] hexDigits = "0123456789abcdef".toCharArray();

  private Sha256() {
    // hide default constructor
  }

  public static String get(String value, Charset charset) throws NoSuchAlgorithmException {
    MessageDigest md;
    md = MessageDigest.getInstance(SHA_256);
    byte[] bytes = value.getBytes(charset);
    md.update(bytes);
    int b = md.getDigestLength();
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
