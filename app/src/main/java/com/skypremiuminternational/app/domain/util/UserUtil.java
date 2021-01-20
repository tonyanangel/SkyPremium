package com.skypremiuminternational.app.domain.util;

import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateParser;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by codigo on 30/3/18.
 */

public class UserUtil {

  public static final long DEFAULT_DAYS_LEFT = Long.MIN_VALUE;

  @Inject
  public UserUtil() {

  }

  public UserDetailResponse flatInfo(UserDetailResponse response) {
    String memberExpiryPrompt = "";
    String loyaltyPoints = "";
    String avatar = "";
    String memeberExpiryDate = "";
    String referralCode = "";
    String loyaltyPointExpiryDate = "";
    if (response.getCustomAttributes() != null) {
      for (CustomAttribute customAttribute : response.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equalsIgnoreCase("member_loyalty_point")) {
          loyaltyPoints = customAttribute.getValue();
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("referral_code")) {
          referralCode = customAttribute.getValue();
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("avatar")) {
          avatar = customAttribute.getValue();
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("salutation")) {
          response.setSalutation(customAttribute.getValue());
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("member_expiry_date")) {
          memeberExpiryDate = customAttribute.getValue();
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("member_expiry_prompt")) {
          memberExpiryPrompt = customAttribute.getValue();
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("loyalty_point_expiry_date")) {
          loyaltyPointExpiryDate = customAttribute.getValue();
        }
      }
    }
    response.setExpiryDate(memeberExpiryDate);
    response.setDaysLeft(calculateDaysLeft(memeberExpiryDate));
    response.setReferralCode(referralCode);
    response.setAvatar(avatar);
    response.setShowMemberExpiryPrompt("1".equalsIgnoreCase(memberExpiryPrompt));
    response.setLoyaltyPoints(parseDouble(loyaltyPoints));
    response.setLoyaltyPointExpiryDate(loyaltyPointExpiryDate);
    return response;
  }

  private Double parseDouble(String rawDouble) {
    try {
      return Double.parseDouble(rawDouble);
    } catch (Exception e) {
      return 0d;
    }
  }

  private long calculateDaysLeft(String rawExpiryDate) {
    long days = DEFAULT_DAYS_LEFT;
    try {
      Date currentDate = new Date();
      Date expiryDate = DateParser.with(Constants.PATTERN_DATE_TIME).parse(rawExpiryDate);
      long diffDays = expiryDate.getTime() - currentDate.getTime();
      days = TimeUnit.DAYS.convert(diffDays, TimeUnit.MILLISECONDS);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return days;
  }
}
