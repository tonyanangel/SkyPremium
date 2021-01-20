package com.skypremiuminternational.app.domain.exception.signin;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;

/**
 * Created by hein on 5/16/17.
 */

public class SessionTimeoutException extends Exception {

  public SessionTimeoutException() {
  }

  @Override
  public String getLocalizedMessage() {
    return App.getAppContext().getString(R.string.error_401);
  }
}
