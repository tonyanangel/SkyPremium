package com.skypremiuminternational.app.app.features.profile.manage_delivery_address;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.listener.ItemEditClickListener;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aeindraaung on 2/19/18.
 */

public class ManageDeliveryAddressAdapter
    extends RecyclerView.Adapter<ManageDeliveryAddressViewHolder> {
  private List<Address> addressList = new ArrayList<>();
  private ItemEditClickListener<Address> itemEditClickListener;
  private List<CountryCode> countryCodeList;
  private List<ISOCountry> countryListiso;

  public void setData(List<Address> addressList) {
    this.addressList = addressList;
    notifyDataSetChanged();
  }

  public void setItemEditClickListener(ItemEditClickListener<Address> itemEditClickListener) {

    this.itemEditClickListener = itemEditClickListener;
  }

  @Override
  public ManageDeliveryAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_delivery_address, parent, false);
    final ManageDeliveryAddressViewHolder holder = new ManageDeliveryAddressViewHolder(view);
    holder.txtEdit.setOnClickListener(v -> {
      if (itemEditClickListener != null
          && holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        itemEditClickListener.onItemEditClicked(addressList.get(holder.getAdapterPosition()));
      }
    });
    return holder;
  }

  @Override
  public void onBindViewHolder(ManageDeliveryAddressViewHolder holder, int position) {
    holder.bind(addressList.get(position), countryCodeList);
  }

  @Override
  public int getItemCount() {
    return addressList != null ? addressList.size() : 0;
  }

  public void setCountryCodes(List<CountryCode> countryCodes) {
    countryCodeList = countryCodes;
  }
}
