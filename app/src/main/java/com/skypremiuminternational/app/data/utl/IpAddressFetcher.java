package com.skypremiuminternational.app.data.utl;

import android.util.Log;

import com.skypremiuminternational.app.app.utils.Validator;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.inject.Inject;

public class IpAddressFetcher {

  private String ipAddress;

  @Inject
  public IpAddressFetcher() {

  }

  public String fetch() throws SocketException {
    if (!Validator.isTextValid(ipAddress)) {
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
           en.hasMoreElements(); ) {
        NetworkInterface networkInterface = en.nextElement();
        for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses();
             enumIpAddr.hasMoreElements(); ) {
          InetAddress inetAddress = enumIpAddr.nextElement();
          if (!inetAddress.isLoopbackAddress()) {
            if (validIP(inetAddress.getHostAddress())) {
              ipAddress = inetAddress.getHostAddress();
              return ipAddress;
            }
          }
        }
      }
    }
    return ipAddress;
  }

  public static boolean validIP(String ip) {
    try {
      if (ip == null || ip.isEmpty()) {
        return false;
      }

      String[] parts = ip.split("\\.");
      if (parts.length != 4) {
        return false;
      }

      for (String s : parts) {
        int i = Integer.parseInt(s);
        if ((i < 0) || (i > 255)) {
          return false;
        }
      }
      if (ip.endsWith(".")) {
        return false;
      }

      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }
}
