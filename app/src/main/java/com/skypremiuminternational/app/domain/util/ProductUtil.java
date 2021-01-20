package com.skypremiuminternational.app.domain.util;

import android.text.TextUtils;

import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateParser;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by codigo on 21/3/18.
 */

public class ProductUtil {

  @Inject
  public ProductUtil() {

  }

  public DetailsItem flatInfo(DetailsItem item) {
    String newFromDate = "";
    String newToDate = "";
    String currentServerTime = "";
    String specialFromDate = "";
    String specialToDate = "";
    String specialPrice = "";
    // 20200406 - WIKI Toan Tran - add new dealValue
    String dealValue = "";
    for (CustomAttribute attribute : item.getCustomAttributes()) {
      if (attribute.getAttributeCode().equalsIgnoreCase("deal_status")) {
        item.setDealStatus(attribute.getValue().equals(Constants.DEAL_STATUS_ENABLE));
      } else if (attribute.getAttributeCode().equalsIgnoreCase("deal_discount_type")) {
        item.setDiscountType((String) attribute.getValue());
      } else if (attribute.getAttributeCode().equalsIgnoreCase("deal_discount_percentage")) {
        item.setDiscountPercentage((String) attribute.getValue());
      } else if (attribute.getAttributeCode().equalsIgnoreCase("special_price")) {
        try {
          item.setSpecialPrice((String) attribute.getValue());
        }catch (ClassCastException ex){
          item.setSpecialPrice(""+((Double) attribute.getValue()));
        }
      } else if (attribute.getAttributeCode().equalsIgnoreCase("deal_from_date")) {
        item.setDealFromDate((String) attribute.getValue());
      } else if (attribute.getAttributeCode().equalsIgnoreCase("deal_to_date")) {
        item.setDealToDate((String) attribute.getValue());
      } else if (attribute.getAttributeCode().equalsIgnoreCase("current_server_time")) {
        item.setCurrentServerTime((String) attribute.getValue());
        currentServerTime = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("news_from_date")
          && attribute.getValue() != null) {
        newFromDate = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("news_to_date")
          && attribute.getValue() != null) {
        newToDate = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("special_from_date")
          && attribute.getValue() != null) {
        specialFromDate = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("special_to_date")
          && attribute.getValue() != null) {
        specialToDate = attribute.getValue().toString();
      }else if (attribute.getAttributeCode().equalsIgnoreCase("special_price")
          && attribute.getValue() != null) {
        specialPrice = attribute.getValue().toString();
      }
      // 20200406 - WIKI Toan Tran - add new dealValue
      else if (attribute.getAttributeCode().equalsIgnoreCase("deal_value")
              && attribute.getValue() != null) {
        dealValue = attribute.getValue().toString();
      }
    }
    item.setSpecialFormDate(specialFromDate);
    item.setSpecialToDate(specialToDate);
    item.setWholeSale(isActiveDetailSale(specialFromDate, specialToDate, currentServerTime,item.getSpecialPrice()));
    item.setNew(isNew(newFromDate, newToDate, currentServerTime));
    // 20200406 - WIKI Toan Tran - add new dealValue
    item.setDealValue(dealValue);
    return item;
  }

  public ItemsItem flatInfo(ItemsItem item) {
    String newFromDate = "";
    String newToDate = "";
    String currentServerTime = "";
    String specialFromDate = "";
    String specialToDate = "";
    String minialprice = "";
    String specialPrice = "";

    String dealToDate = "";
    String dealFromDate = "";
    // 20200406 - WIKI Toan Tran - add new dealValue
    String dealValue = "";
    for (CustomAttributesItem attribute : item.getCustomAttributes()) {
      if (attribute.getAttributeCode().equalsIgnoreCase("deal_status")) {
        item.setDealStatus(attribute.getValue().equals(Constants.DEAL_STATUS_ENABLE));
      } else if (attribute.getAttributeCode().equalsIgnoreCase("deal_discount_type")) {
        item.setDiscountType((String) attribute.getValue());
      } else if (attribute.getAttributeCode().equalsIgnoreCase("deal_discount_percentage")) {
        item.setDiscountPercentage((String) attribute.getValue());
      } else if (attribute.getAttributeCode().equalsIgnoreCase("special_price")) {
        item.setSpecialPrice((String) attribute.getValue());
      } else if (attribute.getAttributeCode().equalsIgnoreCase("deal_from_date")) {
        item.setDealFromDate((String) attribute.getValue());
        dealFromDate = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("deal_to_date")) {
        item.setDealToDate((String) attribute.getValue());
        dealToDate = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("current_server_time")) {
        item.setCurrentServerTime((String) attribute.getValue());
        currentServerTime = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("news_from_date")
          && attribute.getValue() != null) {
        newFromDate = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("news_to_date")
          && attribute.getValue() != null) {
        newToDate = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("special_from_date")
          && attribute.getValue() != null) {
        specialFromDate = attribute.getValue().toString();
      } else if (attribute.getAttributeCode().equalsIgnoreCase("special_to_date")
          && attribute.getValue() != null) {
        specialToDate = attribute.getValue().toString();
      }else if (attribute.getAttributeCode().equalsIgnoreCase("minimal_price")
          && attribute.getValue() != null) {
        minialprice = attribute.getValue().toString();
      }else if (attribute.getAttributeCode().equalsIgnoreCase("special_price")
          && attribute.getValue() != null) {
        specialPrice = attribute.getValue().toString();
      }
      // 20200406 - WIKI Toan Tran - add new dealValue
      else if (attribute.getAttributeCode().equalsIgnoreCase("deal_value")
              && attribute.getValue() != null) {
        dealValue = attribute.getValue().toString();
      }
    }
    item.setSpecialFromDate(specialFromDate);
    item.setSpecialToDate(specialToDate);
    item.setWholeSale(isActiveSale(specialFromDate, specialToDate, currentServerTime,item.getSpecialPrice()));
    item.setNew(isNew(newFromDate, newToDate, currentServerTime));
    item.setMinimalprice(minialprice);
    // 20200406 - WIKI Toan Tran - add new dealValue

    item.setDealValue(dealValue);
    return item;
  }



  public static boolean isActiveDetail(String rawFromDate, String rawToDate, String currentServerTime) {
    Calendar calendar = Calendar.getInstance();
    DateParser dateParser = DateParser.with(Constants.PATTERN_DATE_TIME);
    DateParser currentDateParser = DateParser.with(Constants.PATTERN_DATE_TIME);

    if (isValid(rawFromDate) && isValid(rawToDate)) {
      try {
        Date fromDate = dateParser.parse(rawFromDate);
        //Date toDate = dateParser.parse(rawToDate);
        Date toDate = dateParser.parse(rawToDate);
        calendar.setTime(toDate);
        calendar.add(Calendar.DATE, 1);
        toDate = calendar.getTime();
        Date currentDate = currentDateParser.parse(currentServerTime);
        return currentDate.after(fromDate) && currentDate.before(toDate);
      } catch (ParseException e) {
        Timber.e(e);
        return false;
      }
    } else if (isValid(rawFromDate) && !isValid(rawToDate)) {
      try {
        Date fromDate = dateParser.parse(rawFromDate);
        Date currentDate = currentDateParser.parse(currentServerTime);
        return currentDate.after(fromDate);
      } catch (ParseException e) {
        Timber.e(e);
        return false;
      }
    } else if (!isValid(rawFromDate) && isValid(rawToDate)) {
      return false;
    } else {
      return false;
    }
  }
  public static boolean isActiveDetailSale(String rawFromDate, String rawToDate, String currentServerTime,String specialPrice) {
    if(!isValid(currentServerTime)){
      return false;
    }
    Calendar calendar = Calendar.getInstance();
    DateParser dateParser = DateParser.with(Constants.PATTERN_DATE_TIME);
    DateParser currentDateParser = DateParser.with(Constants.PATTERN_CURRENT_SERVER_TIME);
    if (isValid(rawFromDate) && isValid(rawToDate)) {
      if(!isValid(specialPrice)){
        return true;
      }else {
        try {
          Date fromDate = dateParser.parse(rawFromDate);
          //Date toDate = dateParser.parse(rawToDate);
          Date toDate = dateParser.parse(rawToDate);
          /*calendar.setTime(toDate);
          calendar.add(Calendar.DATE, 1);
          toDate = calendar.getTime();*/
          Date currentDate = currentDateParser.parse(currentServerTime);
          return currentDate.after(fromDate) && currentDate.before(toDate);
        } catch (ParseException e) {
          Timber.e(e);
          return false;
        }
      }
    }else {
      return true;
    }
  }


  public static boolean isActive(String rawFromDate, String rawToDate, String currentServerTime) {
    Calendar calendar = Calendar.getInstance();
    DateParser dateParser = DateParser.with(Constants.PATTERN_DATE_TIME);
    DateParser currentDateParser = DateParser.with(Constants.PATTERN_CURRENT_SERVER_TIME);



    if (isValid(rawFromDate) && isValid(rawToDate)) {
      try {
        Date fromDate = dateParser.parse(rawFromDate);
        //Date toDate = dateParser.parse(rawToDate);
        Date toDate = dateParser.parse(rawToDate);
        /*calendar.setTime(toDate);
        calendar.add(Calendar.DATE, 1);
        toDate = calendar.getTime();*/
        Date currentDate = currentDateParser.parse(currentServerTime);
        return currentDate.after(fromDate) && currentDate.before(toDate);
      } catch (ParseException e) {
        Timber.e(e);
        return false;
      }
    } else if (isValid(rawFromDate) && !isValid(rawToDate)) {
      try {
        Date fromDate = dateParser.parse(rawFromDate);
        Date currentDate = currentDateParser.parse(currentServerTime);
        return currentDate.after(fromDate);
      } catch (ParseException e) {
        Timber.e(e);
        return false;
      }
    } else if (!isValid(rawFromDate) && isValid(rawToDate)) {
      return false;
      //try {
      //  Date toDate = dateParser.parse(rawToDate);
      //  calendar.setTime(toDate);
      //  calendar.add(Calendar.DATE, 1);
      //  toDate = calendar.getTime();
      //  Date currentDate = currentDateParser.parse(currentServerTime);
      //  return currentDate.before(toDate);
      //} catch (ParseException e) {
      //  Timber.e(e);
      //  return false;
      //}
    } else {
      return false;
    }
  }


  public static boolean isActiveSale(String rawFromDate, String rawToDate, String currentServerTime,String specialPrice) {
    if(!isValid(currentServerTime)){
      return false;
    }


    Calendar calendar = Calendar.getInstance();
    DateParser dateParser = DateParser.with(Constants.PATTERN_DATE_TIME);
    DateParser currentDateParser = DateParser.with(Constants.PATTERN_CURRENT_SERVER_TIME);
    if (isValid(rawFromDate) && isValid(rawToDate)) {
      if(!isValid(specialPrice)){
        return true;
      }else {
        try {
          Date fromDate = dateParser.parse(rawFromDate);
          //Date toDate = dateParser.parse(rawToDate);
          Date toDate = dateParser.parse(rawToDate);
          /*calendar.setTime(toDate);
          calendar.add(Calendar.DATE, 1);
          toDate = calendar.getTime();*/
          Date currentDate = currentDateParser.parse(currentServerTime);
          return currentDate.after(fromDate) && currentDate.before(toDate);
        } catch (ParseException e) {
          Timber.e(e);
          return false;
        }
      }
    }else {
      return true;
    }
  }

  /**
   *
   * 20200414 WIKI - Toan Tran - update condition for is New
   * @param rawFromDate
   * @param rawToDate
   * @param currentServerTime
   * @return
   */
  public static boolean isNew(String rawFromDate, String rawToDate, String currentServerTime) {
    Calendar calendar = Calendar.getInstance();
    DateParser dateParser = DateParser.with(Constants.PATTERN_DATE_TIME);
    DateParser currentDateParser = DateParser.with(Constants.PATTERN_CURRENT_SERVER_TIME);



    if (isValid(rawFromDate) && isValid(rawToDate)) {
      try {
        Date fromDate = dateParser.parse(rawFromDate);
        //Date toDate = dateParser.parse(rawToDate);
        Date toDate = dateParser.parse(rawToDate);
        calendar.setTime(toDate);
        calendar.add(Calendar.DATE, 1);
        toDate = calendar.getTime();
        Date currentDate = currentDateParser.parse(currentServerTime);
        return currentDate.after(fromDate) && currentDate.before(toDate);
      } catch (ParseException e) {
        Timber.e(e);
        return false;
      }
    } else if (isValid(rawFromDate) && !isValid(rawToDate)) {
      try {
        Date fromDate = dateParser.parse(rawFromDate);
        Date currentDate = currentDateParser.parse(currentServerTime);
        return currentDate.after(fromDate);
      } catch (ParseException e) {
        Timber.e(e);
        return false;
      }
    } else if (!isValid(rawFromDate) && isValid(rawToDate)) {
      try {
        Date toDate = dateParser.parse(rawToDate);
        Date currentDate = currentDateParser.parse(currentServerTime);
        return currentDate.before(toDate);
      } catch (ParseException e) {
        Timber.e(e);
        return false;
      }
    } else {
      return false;
    }
  }


  public static boolean compareDate(String pattern,String rawFromDate , String rawCurrentDate) {
    DateParser dateParser = DateParser.with(Constants.PATTERN_DATE_TIME);
    DateParser currentDateParser = DateParser.with(pattern);

    try {
      Date fromDate = dateParser.parse(rawFromDate);
      Date currentDate = currentDateParser.parse(rawCurrentDate);
      if(fromDate.before(currentDate)){
        return true;
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean isValid(String rawDate) {
    return rawDate != null && !TextUtils.isEmpty(rawDate);
  }
}
