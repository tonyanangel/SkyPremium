package com.skypremiuminternational.app.app.features.checkout.room.stepone;

import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.model.BookerInfo;
import com.skypremiuminternational.app.app.utils.BookerInfoValidator;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.utl.OccupancyArranger;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class CheckoutGuestDetailPresenter extends BaseFragmentPresenter<CheckoutGuestDetailView> {

  private final OccupancyArranger occupancyArranger;
  private final BookerInfoValidator bookerInfoValidator;
  private int roomCount;
  private int adultCount;
  private List<Child> children;
  private UserDetailResponse userDetailResponse;
  private PhoneCode phoneCode;

  @Inject
  public CheckoutGuestDetailPresenter(OccupancyArranger occupancyArranger,
                                      BookerInfoValidator bookerInfoValidator) {
    this.occupancyArranger = occupancyArranger;
    this.bookerInfoValidator = bookerInfoValidator;
  }

  public void setValues(CheckoutGuestDetailFragment.Params params) {
    this.userDetailResponse = params.userDetailResponse;
    this.roomCount = params.roomCount;
    this.adultCount = params.adultCount;
    this.children = params.children;
    this.phoneCode = params.phoneCode;

    if (params.bookerInfos != null && params.bookerInfos.size() > 0) {
      getView().render(params.bookerInfos);
    } else {
      getView().render(buildDefBookerInfos());
    }
  }

  private List<BookerInfo> buildDefBookerInfos() {
    int userPhoneCodePosition = 0;
    String userPhoneCode = phoneCode.getPhoneCodes().get(userPhoneCodePosition).getDiallingCode();

    String phoneNumber = "";
    String countryCode = "";
    int salutationPosition = -1;

    int defPhoneCodePosition = 0;
    String defPhoneCode = userPhoneCode;

    for (int i = 0; i < Constants.SALUTATIONS.length; i++) {
      String salutation = Constants.SALUTATIONS[i];
      if (salutation.equalsIgnoreCase(userDetailResponse.getSalutation() + ".")) {
        salutationPosition = i;
      }
    }

    if (userDetailResponse.getCustomAttributes() != null) {
      for (CustomAttribute customAttribute : userDetailResponse.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equals("contact_country_code")) {
          countryCode = customAttribute.getValue();
        } else if (customAttribute.getAttributeCode().equals("contact_number")) {
          phoneNumber = customAttribute.getValue();
        }
      }
    }
    for (int i = 0; i < phoneCode.getPhoneCodes().size(); i++) {
      PhoneCode.PhoneCode_ phoneCode_ = phoneCode.getPhoneCodes().get(i);
      if (String.valueOf(phoneCode_.getDiallingCode()).equals(countryCode)) {
        userPhoneCodePosition = i;
        userPhoneCode = phoneCode_.getDiallingCode();
      }

      if (String.valueOf(phoneCode_.getDiallingCode()).equals("65")) {
        defPhoneCodePosition = i;
        defPhoneCode = phoneCode_.getDiallingCode();
      }
    }

    List<String> occupancies =
        occupancyArranger.arrangeChildAges(roomCount, adultCount, children);

    List<List<Integer>> childAges =
        occupancyArranger.arrangeForChildAge(roomCount, adultCount, children);

    List<int[]> guestOccupancies =
        occupancyArranger.arrangeForCount(roomCount, adultCount, children.size());

    List<BookerInfo> bookerInfos = new ArrayList<>();

    for (int i = 0; i < childAges.size(); i++) {
      String occupancy = occupancies.get(i);
      if (i == 0) {
        bookerInfos.add(BookerInfo.builder()
            .setRoomName("Room #" + (i + 1) + ": " + occupancy)
            .setNumberOfAdult(guestOccupancies.get(i)[0])
            .setPhoneNumber(phoneNumber)
            .setPhoneCode(userPhoneCode)
            .setPhoneCodePosition(userPhoneCodePosition)
            .setChildAges(childAges.get(i))
            .setSalutation(userDetailResponse.getSalutation())
            .setFirstName(userDetailResponse.getFirstname())
            .setLastName(userDetailResponse.getLastname())
            .setSalutationPosition(salutationPosition)
            .build());
      } else {
        bookerInfos.add(BookerInfo.builder()
            .setRoomName("Room #" + (i + 1) + ": " + occupancy)
            .setNumberOfAdult(guestOccupancies.get(i)[0])
            .setPhoneNumber("")
            .setPhoneCode(defPhoneCode)
            .setPhoneCodePosition(defPhoneCodePosition)
            .setChildAges(childAges.get(i))
            .setSalutation("")
            .setFirstName("")
            .setLastName("")
            .setSalutationPosition(-1)
            .build());
      }
    }
    return bookerInfos;
  }

  public void validateGuestDetail(String email,
                                  List<BookerInfo> bookerInfos, String bedGroup) {
    boolean isEmailValid = true;
    boolean isAllMandatoryFieldsFilled = true;

    if (!Validator.isTextValid(email)) {
      isAllMandatoryFieldsFilled = false;
    }
    if (!Validator.isEmailValid(email)) {
      getView().renderEmailValidationError(false);
      isEmailValid = false;
    }

    if (!Validator.isTextValid(bedGroup)) {
      isAllMandatoryFieldsFilled = false;
      getView().renderBedGroupValidationError();
    }

    for (BookerInfo bookerInfo : bookerInfos) {
      if (!bookerInfoValidator.validate(bookerInfo)) {
        isAllMandatoryFieldsFilled = false;
      }
    }
    if (isAllMandatoryFieldsFilled && isEmailValid) {
      getView().goToStepTwo(email, bedGroup);
    }
    if (!isAllMandatoryFieldsFilled) {
      getView().renderBookerValidationError();
    }

    if (isAllMandatoryFieldsFilled && !isEmailValid) {
      getView().renderEmailValidationError(true);
    }
  }

  public void clearDefaultEntries() {
    getView().render(buildEmptyBookerInfos());
  }

  public void populateDefaultEntries() {
    getView().render(buildDefBookerInfos());
  }

  private List<BookerInfo> buildEmptyBookerInfos() {
    int defPhoneCodePosition = -1;
    String defPhoneCode = "";

    List<String> occupancies =
        occupancyArranger.arrangeChildAges(roomCount, adultCount, children);

    List<List<Integer>> childAges =
        occupancyArranger.arrangeForChildAge(roomCount, adultCount, children);

    List<int[]> guestOccupancies =
        occupancyArranger.arrangeForCount(roomCount, adultCount, children.size());

    List<BookerInfo> bookerInfos = new ArrayList<>();
    for (int i = 0; i < childAges.size(); i++) {
      String occupancy = occupancies.get(i);
      bookerInfos.add(BookerInfo.builder()
          .setRoomName("Room #" + (i + 1) + ": " + occupancy)
          .setNumberOfAdult(guestOccupancies.get(i)[0])
          .setPhoneNumber("")
          .setPhoneCode(defPhoneCode)
          .setPhoneCodePosition(defPhoneCodePosition)
          .setChildAges(childAges.get(i))
          .setSalutation("")
          .setFirstName("")
          .setLastName("")
          .setSalutationPosition(-1)
          .build());
    }
    return bookerInfos;
  }
}
