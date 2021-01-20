package com.skypremiuminternational.app.data.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DataUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.data.InternalStorageManager;
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
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Created by johnsonmaung on 7/11/17.
 *
 *  Author: Levister Gutierrez
 *  Task: To include the AWS Access Token and Keys on Cloudsearch APIs, add the variable "key"
 *        and "secret"
 *  Included: saveUserAuthToken & getHMSToken & getHMSSecret
 *  Date: 15 Apr 2019
 */
@Singleton
public class InternalStorageManagerImpl implements InternalStorageManager {

  private static final String KEY_CART_ID = "cart_id";
  private final PreferenceUtils preferenceUtils;
  private final DataUtils dataUtils;

  @Inject
  public InternalStorageManagerImpl(PreferenceUtils preferenceUtils, DataUtils dataUtils) {
    this.preferenceUtils = preferenceUtils;
    this.dataUtils = dataUtils;
  }

  @Override
  public void saveAdminAuthToken(String token) {
    String separated = token.substring(1, token.length() - 1);
    Timber.e(separated);
    preferenceUtils.save("AdminToken", separated);
  }

  @Override
  public String getAdminAuthToken() {
    return preferenceUtils.get("AdminToken", "");
  }

  @Override
  public void clearAuthToken() {
    preferenceUtils.remove(BuildConfig.USER_TOKEN);
  }



  @Override
  public String getUserAuthToken() {
    return preferenceUtils.get(BuildConfig.USER_TOKEN, "");
  }

  @Override
  public String getHMSToken() {
    return preferenceUtils.get(Constants.HMS_TOKEN_KEY, "");
  }

  @Override
  public String getHMSSecret() {
    return preferenceUtils.get(Constants.HMS_SECRET_KEY, "");
  }

  @Override
  public void saveCRMToken(CrmTokenResponse response) {

    dataUtils.writeObject("CRM_TOKEN", response);
  }

  /*
  @Override
  public void saveUserAuthToken(String token) {
    String separated = token.substring(1, token.length() - 1);
    Timber.e(separated);
    preferenceUtils.save(com.skypremiuminternational.app.app.utils.Constants.USER_TOKEN, separated);
  }
  */

  @Override
  public void saveUserAuthToken(String token) {
    String separated = token.substring(1, token.length() - 1);
    Timber.e(separated);
    String[] parts = separated.split("\\&",-1);
    preferenceUtils.save(BuildConfig.USER_TOKEN, parts[0]);
    preferenceUtils.save(com.skypremiuminternational.app.app.utils.Constants.HMS_TOKEN_KEY, parts[1]);
    preferenceUtils.save(com.skypremiuminternational.app.app.utils.Constants.HMS_SECRET_KEY, parts[2]);
  }

  @Override
  public void saveUserDetail(UserDetailResponse response) {
    dataUtils.writeObject("UserDetail", response);
  }

  @Override
  public void saveListNotification(List<NotificationItem> listNotification) {
    dataUtils.writeObject("ListNotification", listNotification);
  }


  @Override
  public UserDetailResponse getUserDetail() {
    return new Gson().fromJson(dataUtils.readObject("UserDetail"), UserDetailResponse.class);
  }
  @Override
  public CrmTokenResponse getCRMToken() {
    return new Gson().fromJson(dataUtils.readObject("CRM_TOKEN"), CrmTokenResponse.class);
  }

  @Override
  public List<NotificationItem> getListNotification() throws FileNotFoundException {
    return new Gson().fromJson(dataUtils.readObject("ListNotification")
        , new TypeToken< List<NotificationItem>>() {}.getType());
  }

  @Override
  public void saveRatingOption(RatingOption ratingOption) {
    dataUtils.writeObject("RatingOption", ratingOption);
  }

  @Override
  public RatingOption getRatingOption() {
    return new Gson().fromJson(dataUtils.readObject("RatingOption"), RatingOption.class);
  }


  @Override
  public void saveCategories(CategoryResponse response) {
    dataUtils.writeObject("Categories", response);
  }

  @Override
  public CategoryResponse getCategories() {
    return new Gson().fromJson(dataUtils.readObject("Categories"), CategoryResponse.class);
  }

  @Override
  public void saveCountryCodes(List<CountryCode> response) {
    dataUtils.writeObject("CountryCode", response);
  }

  @Override
  public List<CountryCode> getCountryCodes() {
    return new Gson().fromJson(dataUtils.readObject("CountryCode"),
        new TypeToken<List<CountryCode>>() {
        }.getType());
  }

  @Override
  public void savePhoneCodes(PhoneCode response) {
    dataUtils.writeObject("PhoneCode", response);
  }

  @Override
  public PhoneCode getPhoneCodes() {
    return new Gson().fromJson(dataUtils.readObject("PhoneCode"), PhoneCode.class);
  }

  @Override
  public void saveNationalities(List<Nationality> response) {
    dataUtils.writeObject("Nationality", response);
  }

  @Override
  public List<Nationality> getNationalities() {
    return new Gson().fromJson(dataUtils.readObject("Nationality"),
        new TypeToken<List<Nationality>>() {
        }.getType());
  }

  @Override
  public String getCartId() {
    return preferenceUtils.get(KEY_CART_ID, "");
  }

  @Override
  public void saveCartId(String cartId) {
    preferenceUtils.save(KEY_CART_ID, cartId);
  }

  @Override
  public void removeCartId() {
    preferenceUtils.remove(KEY_CART_ID);
  }

  @Override
  public String getPromptedVersion() {
    return preferenceUtils.get("app_version", "0.0.0");
  }

  @Override
  public void savePromptedVersion(String version) {
    preferenceUtils.save("app_version", version);
  }

  @Override
  public void clearCartCount() {
    preferenceUtils.remove(Constants.CART_COUNT);
  }

  @Override
  public String getCartCount() {
    return preferenceUtils.get(Constants.CART_COUNT, "");
  }

  @Override
  public void saveCartCount(String count) {
    preferenceUtils.save(Constants.CART_COUNT, count);
  }

  @Override
  public List<NotificationItem> getListNotificationByMemberNumber() throws FileNotFoundException {
    List<NotificationItem> list = new Gson().fromJson(dataUtils.readObject("ListNotification")
        , new TypeToken< List<NotificationItem>>() {}.getType());

    String memberNumber = getUserDetail().getMemberNumber();

    List<NotificationItem> notificationItemList = new ArrayList<>();
    if(list!=null) {
      for (NotificationItem item : list) {
        if (!TextUtils.isEmpty(item.getMemberNumber()) && item.getMemberNumber().equals(memberNumber)) {
          notificationItemList.add(item);
        }
      }
    }
    return notificationItemList;
  }

  @Override
  public void saveOpenNotification(boolean isOpen) {
    preferenceUtils.save("isOpenNotification", isOpen);
  }

  @Override
  public boolean getOpenNotification() {
    return preferenceUtils.get("isOpenNotification",false);
  }

  @Override
  public void saveTreeFilter(String stringTreeFilter) {
    dataUtils.writeObject("treeFilter",stringTreeFilter);
  }

  @Override
  public String getStringTreeFilterLocal() {
    return dataUtils.readObject("treeFilter");
  }

  /*@Override public String getDeviceID() {
    return deviceId;
  }

  @Override public void clearAuthToken() {
    //preferenceUtils.remove("Token");
    dataUtils.deleteObject(Constants.STAFF_FILE_NAME);
  }

  @Override public boolean checkUserLoggedIn() {
    return dataUtils.isExist(Constants.STAFF_FILE_NAME);
  }

  @Override public void saveStaff(Staff staff) {
    dataUtils.writeObject(Constants.STAFF_FILE_NAME, staff);
  }

  @Override public Staff getStaff() {
    return new Gson().fromJson(dataUtils.readObject(Constants.STAFF_FILE_NAME), Staff.class);
  }

  @Override public void savePointSettings(PointSettings pointSettings) {
    dataUtils.writeObject(Constants.POINT_SETTINGS_FILE_NAME, pointSettings);
  }

  @Override public PointSettings getPointSettings() {
    return new Gson().fromJson(dataUtils.readObject(Constants.POINT_SETTINGS_FILE_NAME),
        PointSettings.class);
  }*/

}
