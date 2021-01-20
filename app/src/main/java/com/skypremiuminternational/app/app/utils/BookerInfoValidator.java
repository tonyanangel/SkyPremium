package com.skypremiuminternational.app.app.utils;

import com.skypremiuminternational.app.app.model.BookerInfo;

import java.util.List;

import javax.inject.Inject;

public class BookerInfoValidator {

  @Inject
  public BookerInfoValidator() {

  }

  public boolean validate(BookerInfo bookerInfo) {
    bookerInfo.salutationHasError = !Validator.isTextValid(bookerInfo.salutation);

    bookerInfo.firstNameHasError = !Validator.isTextValid(bookerInfo.firstName);

    bookerInfo.lastNameHasError = !Validator.isTextValid(bookerInfo.lastName);

    bookerInfo.phoneNumberHasError = !Validator.isTextValid(bookerInfo.phoneNumber);

    bookerInfo.phoneCodeHasError = !Validator.isTextValid(bookerInfo.phoneCode);

    return !bookerInfo.phoneNumberHasError
        && !bookerInfo.firstNameHasError
        && !bookerInfo.lastNameHasError
        && !bookerInfo.salutationHasError
        && !bookerInfo.phoneCodeHasError;
  }
}
