package com.skypremiuminternational.app.app.features.checkout.room.steptwo;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.model.PaymentDetail;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.model.ean.payment.CardOption;
import com.skypremiuminternational.app.domain.interactor.ean.GetPaymentOptions;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.util.CreditNumberValidator;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.utils.Validator.*;

public class CheckoutPaymentReviewPresenter
    extends BaseFragmentPresenter<CheckoutPaymentReviewView> {

  public static final String COUNTRY_UNITED_STATES = "United States";
  private final CreditNumberValidator creditNumberValidator;

  @Inject
  public CheckoutPaymentReviewPresenter(CreditNumberValidator creditNumberValidator) {
    this.creditNumberValidator = creditNumberValidator;
  }

  public void validate(PaymentDetail paymentDetail) {
    ValidationResult result = new ValidationResult();
    result.cardTypeHasError = !isTextValid(paymentDetail.cardType);
    result.cardNumberHasError = !creditNumberValidator.validate(paymentDetail.cardNumber);
    result.expMonthHasError =
        !validateExpiryMonth(paymentDetail.expiryMonth, paymentDetail.expiryYear);
    result.expYearHasError =
        !validateExpiryYear(paymentDetail.expiryYear, paymentDetail.expiryMonth);

    result.securityCodeHasError = !isTextValid(paymentDetail.securityCode);

   /* result.securityCodeHasError =
        !validateSecurity(paymentDetail.securityCode, paymentDetail.cardTypeAbbr); */
    result.firstNameHasError = !isTextValid(paymentDetail.firstName);
    result.lastNameHasError = !isTextValid(paymentDetail.lastName);
    result.phoneNumberHasError = !isTextValid(paymentDetail.phoneNumber);
    result.streetHasError = !isUnitNumberValid(paymentDetail.streetAddress);
    result.postalCodeHasError = !isTextValid(paymentDetail.postalCode);
    result.phoneCodeHasError = !isTextValid(paymentDetail.phoneCode);
    result.cityHasError = !isTextValid(paymentDetail.city);
    result.countryHasError = !isTextValid(paymentDetail.country);
    result.unitNumberHasError = !isUnitNumberValid(paymentDetail.unitNumber);
    if (paymentDetail.country != null && paymentDetail.country.equalsIgnoreCase(
        COUNTRY_UNITED_STATES)) {
      result.stateHasError = !isTextValid(paymentDetail.state);
    } else {
      result.stateHasError = false;
    }

    result.emailHasError = !isEmailValid(paymentDetail.emailAddress);

    result.isAcknowledged = paymentDetail.hasReadAndAcceptRules;

    boolean isAllMandatoryFieldsFilled = isAllMandatoryFieldsFilled(paymentDetail);

    if (!isAllMandatoryFieldsFilled) {
      getView().showMandatoryNotFilledError();
    }
    if (!isValid(result)) {
      getView().renderError(result, isAllMandatoryFieldsFilled);
    }

    if (isValid(result)) {
      getView().startBooking(paymentDetail);
    }
  }

  private boolean isAllMandatoryFieldsFilled(PaymentDetail paymentDetail) {
    return isTextValid(paymentDetail.cardType) &&
        isTextValid(paymentDetail.cardNumber) &&
        isTextValid(paymentDetail.expiryMonth) &&
        isTextValid(paymentDetail.expiryYear) &&
        isTextValid(paymentDetail.securityCode) &&
        isTextValid(paymentDetail.firstName) &&
        isTextValid(paymentDetail.lastName) &&
        isTextValid(paymentDetail.emailAddress) &&
        isTextValid(paymentDetail.phoneCode) &&
        isTextValid(paymentDetail.phoneNumber) &&
        isTextValid(paymentDetail.streetAddress) &&
        isTextValid(paymentDetail.unitNumber) &&
        isTextValid(paymentDetail.postalCode) &&
        isTextValid(paymentDetail.city);
  }

  private boolean validateExpiryMonth(String expiryMonth, String expiryYear) {
    if (!Validator.isTextValid(expiryMonth)) return false;

    if (Validator.isTextValid(expiryYear)) {
      if (!Validator.isTextValid(expiryMonth)) {
        return false;
      } else {
        int expYear = Integer.parseInt(expiryYear);
        int expMonth = Integer.parseInt(expiryMonth);

        Calendar now = Calendar.getInstance();
        if (expYear == now.get(Calendar.YEAR)) {
          if (expMonth < (now.get(Calendar.MONTH) + 1)) {
            return false;
          }
        }
      }
    } else {
      return Validator.isTextValid(expiryMonth);
    }
    return true;
  }

  private boolean validateExpiryYear(String expiryYear, String expiryMonth) {
    if (!Validator.isTextValid(expiryYear)) return false;
    if (!Validator.isTextValid(expiryMonth)) return false;
    int expYear = Integer.parseInt(expiryYear);
    int expMonth = Integer.parseInt(expiryMonth);

    Calendar now = Calendar.getInstance();

    if (expYear < now.get(Calendar.YEAR)) {
      return false;
    }

    if (expYear == now.get(Calendar.YEAR)) {
      if (expMonth < (now.get(Calendar.MONTH) + 1)) {
        return false;
      }
    }
    return true;
  }

  private boolean validateSecurity(String securityCode, String cardTypeAbbr) {
    if (!isTextValid(cardTypeAbbr)) {
      return false;
    }
   /* if (cardTypeAbbr.equalsIgnoreCase(GetPaymentOptions.CARD_TYPE_VISA)
        || cardTypeAbbr.equalsIgnoreCase(GetPaymentOptions.CARD_TYPE_MASTER_CARD)) {
      return securityCode.length() == 3;
    } else { */
      return securityCode.length() == 4;
    //}
  }

  private boolean isValid(ValidationResult result) {
    return !result.isPhoneCodeHasError() &&
        !result.cardTypeHasError &&
        !result.cardNumberHasError &&
        !result.expMonthHasError &&
        !result.expYearHasError &&
        !result.securityCodeHasError &&
        !result.firstNameHasError &&
        !result.lastNameHasError &&
        !result.phoneNumberHasError &&
        !result.streetHasError &&
        !result.unitNumberHasError &&
        !result.postalCodeHasError &&
        !result.cityHasError &&
        !result.stateHasError &&
        !result.countryHasError &&
        !result.emailHasError &&
        result.isAcknowledged;
  }

  public static class ValidationResult {
    private boolean cardTypeHasError;
    private boolean cardNumberHasError;
    private boolean expMonthHasError;
    private boolean expYearHasError;
    private boolean securityCodeHasError;
    private boolean firstNameHasError;
    private boolean lastNameHasError;
    private boolean emailHasError;
    private boolean phoneNumberHasError;
    private boolean streetHasError;
    private boolean postalCodeHasError;
    private boolean cityHasError;
    private boolean stateHasError;
    private boolean countryHasError;
    private boolean unitNumberHasError;
    private boolean isAcknowledged;
    private boolean phoneCodeHasError;

    public boolean isPhoneCodeHasError() {
      return phoneCodeHasError;
    }

    public boolean isCardTypeHasError() {
      return cardTypeHasError;
    }

    public boolean isCardNumberHasError() {
      return cardNumberHasError;
    }

    public boolean isExpMonthHasError() {
      return expMonthHasError;
    }

    public boolean isExpYearHasError() {
      return expYearHasError;
    }

    public boolean isSecurityCodeHasError() {
      return securityCodeHasError;
    }

    public boolean isFirstNameHasError() {
      return firstNameHasError;
    }

    public boolean isLastNameHasError() {
      return lastNameHasError;
    }

    public boolean isEmailHasError() {
      return emailHasError;
    }

    public boolean isPhoneNumberHasError() {
      return phoneNumberHasError;
    }

    public boolean isStreetHasError() {
      return streetHasError;
    }

    public boolean isPostalCodeHasError() {
      return postalCodeHasError;
    }

    public boolean isCityHasError() {
      return cityHasError;
    }

    public boolean isStateHasError() {
      return stateHasError;
    }

    public boolean isCountryHasError() {
      return countryHasError;
    }

    public boolean isUnitNumberHasError() {
      return unitNumberHasError;
    }

    public boolean isAcknowledged() {
      return isAcknowledged;
    }
  }

  @AutoValue
  public static abstract class Params {

    @Nullable
    public abstract String checkInInstructions();

    @Nullable
    public abstract String specialCheckInInStructions();

    public abstract List<ISOCountry> countryCodes();

    public abstract List<CardOption> cardOptions();

    public abstract PhoneCode phoneCode();

    public abstract double grandTotal();

    public abstract List<TourismFee> fees();

    public abstract CancelPenalty cancelPenalty();

    public abstract String propertyName();

    public static Params create(String checkInInstructions, String specialCheckInInStructions,
                                List<ISOCountry> countryCodes, List<CardOption> cardOptions,
                                PhoneCode phoneCode, double grandTotal, CancelPenalty cancelPenalty,
                                String propertyName,List<TourismFee> fees) {
      return builder()
          .checkInInstructions(checkInInstructions)
          .specialCheckInInStructions(specialCheckInInStructions)
          .countryCodes(countryCodes)
          .cardOptions(cardOptions)
          .phoneCode(phoneCode)
          .grandTotal(grandTotal)
          .fees(fees)
          .cancelPenalty(cancelPenalty)
          .propertyName(propertyName)
          .build();
    }


    public static Builder builder() {
      return new AutoValue_CheckoutPaymentReviewPresenter_Params.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder countryCodes(List<ISOCountry> countryCodes);

      public abstract Builder cardOptions(List<CardOption> cardOptions);

      public abstract Builder phoneCode(PhoneCode phoneCode);

      public abstract Builder grandTotal(double grandTotal);

      public abstract Builder fees(List<TourismFee> fees);

      public abstract Builder cancelPenalty(CancelPenalty cancelPenalty);

      public abstract Builder propertyName(String propertyName);

      public abstract Builder checkInInstructions(String checkInInstructions);

      public abstract Builder specialCheckInInStructions(String specialCheckInInStructions);

      public abstract Params build();
    }
  }
}
