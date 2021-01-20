package com.skypremiuminternational.app.app.features.profile.manage_delivery_address;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;

import java.util.List;



public class ManageDeliveryAddressViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.txt_person_name)
  TextView txtPersonName;
  @BindView(R.id.txt_address)
  TextView txtAddress;
  @BindView(R.id.txt_edit)
  TextView txtEdit;
  @BindView(R.id.txt_phone)
  TextView txtPhone;
  @BindView(R.id.txt_company)
  TextView txtCompany;
  @BindView(R.id.img_checked)
  ImageView imgCheck;

  @BindView(R.id.layout_main)
  RelativeLayout layoutMain;


  public ManageDeliveryAddressViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  private String findCountry(String countryId,List<CountryCode> countryCodes) {
    for (int i = 0; i < countryCodes.size(); i++) {
      CountryCode countryCode = countryCodes.get(i);
      if (countryId.equalsIgnoreCase(countryCode.getId())) {
        return countryCode.getName();
      }
    }
    return "";
  }

  public void bind(Address address,List<CountryCode> countryCodes) {
    String stateName;
    String unitNumber = null;
    String countryName = null;

    if(address.isDefault()){
      imgCheck.setVisibility(View.VISIBLE);
      layoutMain.setBackground(itemView.getContext()
              .getResources()
              .getDrawable(R.drawable.booking_history_cancel_btn_bg_enable));
    }else{
      imgCheck.setVisibility(View.GONE);
      layoutMain.setBackground(itemView.getContext()
              .getResources()
              .getDrawable(R.drawable.white));
    }

    if (TextUtils.isEmpty(address.getCompany())) {
      txtCompany.setVisibility(View.GONE);
    } else {
      txtCompany.setVisibility(View.VISIBLE);
      txtCompany.setText(address.getCompany());
    }
    String name = String.format("%s %s", address.getFirstname(), address.getLastname());
    if (!TextUtils.isEmpty(address.getPrefix())) {
      name = address.getPrefix() + ". " + name;
    }
    txtPersonName.setText(name);
    //txtPhone.setText(String.format("+%s %s", address.getCountryId(), address.getTelephone()));
    txtPhone.setText(String.format("%s", address.getTelephone()));

    if (address.getCustomAttributes() != null) {
      if (address.getCustomAttributes().size() > 0) {
        for (CustomAttribute customAttribute : address.getCustomAttributes()) {
          if (customAttribute.getAttributeCode().equalsIgnoreCase(Constants.UNIT_NUMBER)) {
            unitNumber = customAttribute.getValue();
          }
        }
      }
    }


   // countryName = findCountry(address.getCountryId(),countryCodes);


    stateName = (String) address.getRegion().getRegion();

    StringBuilder streets = new StringBuilder();
    if (address.getStreet() != null) {
      if (address.getStreet().size() > 0) {
        for (String street : address.getStreet()) {
          streets.append(street);
          streets.append(", ");
        }
      }
    }

    if (streets.length() > 0) {
      String str = streets.substring(0, streets.lastIndexOf(","));
      String fullAdd;
      fullAdd = str;
      if (unitNumber != null && !TextUtils.isEmpty(unitNumber)) {
        fullAdd =  unitNumber+" "+ fullAdd;
      }
      if (address.getCity() != null && !TextUtils.isEmpty(address.getCity())) {
        fullAdd += "\n" + address.getCity();
      }
      if(address.getCountryId()!=null && !TextUtils.isEmpty(address.getCountryId())){
        fullAdd += "\n"+ CountryUtil.parseCountryName(countryCodes,address.getCountryId());
      }
      if (address.getPostcode() != null && !TextUtils.isEmpty(address.getPostcode())) {
        if (address.getCity() != null && !TextUtils.isEmpty(address.getCity())) {
          fullAdd += " " + address.getPostcode();
        } else {
          fullAdd += " " + address.getPostcode();
        }
      }
      if (stateName != null && !TextUtils.isEmpty(stateName)) {
        fullAdd += "\n" + stateName;
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
