package com.skypremiuminternational.app.data;

import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.nationality.Nationality;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.SkyPayload;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by hein on 5/16/17.
 *
 *  Task: To include the AWS Access Token and Keys on Cloudsearch APIs, add the variable "key"
 *        and "secret"
 *  Author: Levister Gutierrez
 *  Included: getHMSToken & getHMSSecret
 *  Date: 15 Apr 2019
 */



public interface InternalStorageManager {

  void clearCartCount();

  String getCartCount();

  void saveCartCount(String count);

  void clearAuthToken();

  void saveAdminAuthToken(String token);

  String getAdminAuthToken();

  void saveUserAuthToken(String token);

  String getUserAuthToken();

  void saveUserDetail(UserDetailResponse response);

  void saveListNotification(List<NotificationItem> response);

  UserDetailResponse getUserDetail();

  List<NotificationItem> getListNotification() throws FileNotFoundException;

  List<NotificationItem> getListNotificationByMemberNumber() throws FileNotFoundException;

  void saveCategories(CategoryResponse response);

  CategoryResponse getCategories();

  void saveCountryCodes(List<CountryCode> response);

  List<CountryCode> getCountryCodes();

  void savePhoneCodes(PhoneCode response);

  PhoneCode getPhoneCodes();

  void saveNationalities(List<Nationality> response);

  List<Nationality> getNationalities();

  String getCartId();

  void saveCartId(String cartId);

  void removeCartId();

  String getPromptedVersion();

  void savePromptedVersion(String version);

   void saveRatingOption(RatingOption response);

   RatingOption getRatingOption();

  /**
   * Levister
   * To get the HMS keys and Secret Keys
   */
  String getHMSToken();

  String getHMSSecret();

  void saveCRMToken(CrmTokenResponse response);

  CrmTokenResponse getCRMToken();

  void saveOpenNotification(boolean isOpen);

  boolean getOpenNotification();

  void saveTreeFilter(String stringTreeFilter);

  String getStringTreeFilterLocal();
}
