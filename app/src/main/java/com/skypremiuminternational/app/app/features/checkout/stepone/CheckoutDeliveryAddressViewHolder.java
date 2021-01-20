package com.skypremiuminternational.app.app.features.checkout.stepone;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.CountryUtil;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;

import java.util.List;

/**
 * Created by aeindraaung on 1/30/18.
 */

public class CheckoutDeliveryAddressViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.layout_main)
  RelativeLayout layoutMain;
  @BindView(R.id.txt_person_name)
  TextView txtPersonName;
  @BindView(R.id.txt_address)
  TextView txtAddress;
  @BindView(R.id.txt_edit)
  TextView txtEdit;
  @BindView(R.id.img_checked)
  ImageView imgChecked;
  @BindView(R.id.txt_phone)
  TextView txtPhone;
  @BindView(R.id.txt_company)
  TextView txtCompany;

  public CheckoutDeliveryAddressViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(Address item, List<CountryCode> countryCodeList) {
    String unitNumber = null;
    String countryName = null;
    String stateName;


/*  20201707 - WIKI Viet Nguyen - fix bug set default delivery address
    if (item.isDefault()) {
      highlightBackground();
    } else {
      unHighlightBackground();
    }*/
    if (item.isDeliveryAddressSelected()) {
      highlightBackground();
    } else {
      unHighlightBackground();
    }

    if (TextUtils.isEmpty(item.getCompany())) {
      txtCompany.setVisibility(View.GONE);
    } else {
      txtCompany.setVisibility(View.VISIBLE);
      txtCompany.setText(item.getCompany());
    }

    txtPersonName.setText(String.format("%s. %s %s", item.getPrefix(), item.getFirstname(), item.getLastname()));
    txtPhone.setText(String.format("%s", item.getTelephone()));

    if (item.getCustomAttributes() != null && item.getCustomAttributes().size() > 0) {
      for (CustomAttribute customAttribute : item.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equalsIgnoreCase(Constants.UNIT_NUMBER)) {
          unitNumber = customAttribute.getValue();
          break;
        }
      }
    }

    if (countryCodeList != null && countryCodeList.size() > 0) {
      for (CountryCode countryCode : countryCodeList) {
        /*if (countryCode.getId() == item.getContactCountryCode()) {
          countryName = countryCode.getName();
          break;
        }*/
      }
    }

    stateName = (String) item.getRegion().getRegion();

    StringBuilder streets = new StringBuilder();
    if (item.getStreet() != null && item.getStreet().size() > 0) {
      for (String street : item.getStreet()) {
        streets.append(street);
        streets.append(", ");
      }

      if (streets.length() > 0) {
        String str = streets.substring(0, streets.lastIndexOf(","));
        String fullAdd;
        fullAdd = str;
        if (unitNumber != null && !TextUtils.isEmpty(unitNumber)) {
          fullAdd =  unitNumber+" "+ fullAdd;
        }
        if (item.getCity() != null && !TextUtils.isEmpty(item.getCity())) {
          fullAdd += "\n" + item.getCity();
        }
        if (stateName != null && !TextUtils.isEmpty(stateName)) {
          fullAdd += " " + stateName;
        }
        if(item.getCountryId()!=null && !TextUtils.isEmpty(item.getCountryId())){
          fullAdd += "\n"+ CountryUtil.parseCountryName(countryCodeList,item.getCountryId());
        }
        if (item.getPostcode() != null && !TextUtils.isEmpty(item.getPostcode())) {
          if (item.getCity() != null && !TextUtils.isEmpty(item.getCity())) {
            fullAdd += " " + item.getPostcode();
          } else {
            fullAdd += " " + item.getPostcode();
          }
        }
        if (countryName != null && !TextUtils.isEmpty(countryName)) {
          if (stateName != null && !TextUtils.isEmpty(stateName)) {
            fullAdd += ", " + countryName;
          } else {
            fullAdd += "\n" + countryName;
          }
        }
        txtAddress.setText(fullAdd);
      }
    }
  }

  private void unHighlightBackground() {
    layoutMain.setBackgroundColor(
        ContextCompat.getColor(itemView.getContext(), R.color.white));
    imgChecked.setVisibility(View.GONE);
  }

  private void highlightBackground() {
    layoutMain.setBackground(itemView.getContext()
        .getResources()
        .getDrawable(R.drawable.booking_history_cancel_btn_bg_enable));
    imgChecked.setVisibility(View.VISIBLE);
  }
}
