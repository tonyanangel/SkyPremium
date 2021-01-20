package com.skypremiuminternational.app.app.utils;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.skypremiuminternational.app.app.features.shopping.detail.ShoppingDetailActivity;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

/**
 * Created by codigo on 26/1/18.
 */

public final class ActivityChooser {

  private ActivityChooser() {

  }

  public static void startMe(Fragment context, ItemsItem itemsItem, String category,
                             boolean fromTravel, int requestCode) {
    double longitude = 0.0;
    double latitude = 0.0;
    for (CustomAttributesItem customAttributesItem : itemsItem.getCustomAttributes()) {
      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("longitude")) {
        longitude = Double.parseDouble(customAttributesItem.getValue().toString());
      } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("latitude")) {
        latitude = Double.parseDouble(customAttributesItem.getValue().toString());
      }
    }
    if (fromTravel) {
      TravelDetailActivity.startMe(context, itemsItem, category, true, requestCode);
    } else {
      if (longitude == 0.0 && latitude == 0.0) {
        ShoppingDetailActivity.startMe(context, itemsItem, category, requestCode);
      } else {
        TravelDetailActivity.startMe(context, itemsItem, category, false, requestCode);
      }
    }
  }

  public static void startMe(Activity context, ItemsItem itemsItem, String category,
                             boolean fromTravel, int requestCode) {
    double longitude = 0.0;
    double latitude = 0.0;
    for (CustomAttributesItem customAttributesItem : itemsItem.getCustomAttributes()) {
      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("longitude")) {
        longitude = Double.parseDouble(customAttributesItem.getValue().toString());
      } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("latitude")) {
        latitude = Double.parseDouble(customAttributesItem.getValue().toString());
      }
    }

    if (fromTravel) {
      TravelDetailActivity.startMe(context, itemsItem, category, true, requestCode);
    } else {
      if (longitude == 0.0 && latitude == 0.0) {
        ShoppingDetailActivity.startMe(context, itemsItem, category, requestCode);
      } else {
        TravelDetailActivity.startMe(context, itemsItem, category, false, requestCode);
      }
    }
  }
}
