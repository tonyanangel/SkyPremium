package com.skypremiuminternational.app.app.utils;

import com.hwangjr.rxbus.Bus;

/**
 * Created by hein on 5/18/17.
 */
public final class RxBus {
  private static Bus sBus;

  public static synchronized Bus get() {
    if (sBus == null) {
      sBus = new Bus();
    }
    return sBus;
  }
}