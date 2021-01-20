package com.skypremiuminternational.app.domain.exception.signin;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;

/**
 * Created by hein on 5/16/17.
 */

public class InvalidEmailException extends Exception {

  public InvalidEmailException() {
  }

  @Override
  public String getLocalizedMessage() {
    return App.getAppContext().getString(R.string.error_invalid_email);
  }
}
