package com.skypremiuminternational.app.app.features.checkout.stepone;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.utils.listener.ItemClickListener;
import com.skypremiuminternational.app.app.utils.listener.ItemEditClickListener;

import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.user.Address;

import java.util.List;

/**
 * Created by aeindraaung on 1/29/18.
 */

public class CheckoutDeliveryAddressAdapter
    extends RecyclerView.Adapter<CheckoutDeliveryAddressViewHolder> {
  private List<Address> deliveryAddressList;
  private ItemClickListener<Address> itemClickListener;
  private ItemEditClickListener<Address> itemEditClickListener;
  private List<CountryCode> countryCodeList;
  private int lastPosition = 0;

  public void setDeliveryAddress(List<Address> deliveryAddressList) {
    this.deliveryAddressList = deliveryAddressList;
    notifyDataSetChanged();
  }

  public void setIdTemp(int lastPosition){
    this.lastPosition = lastPosition;
  }

  public void setItemClickListener(ItemClickListener<Address> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public void setItemEditClickListener(ItemEditClickListener<Address> itemEditClickListener) {
    this.itemEditClickListener = itemEditClickListener;
  }

  @Override
  public CheckoutDeliveryAddressViewHolder onCreateViewHolder(ViewGroup parent,
                                                              final int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_choose_address, parent, false);
    final CheckoutDeliveryAddressViewHolder viewHolder =
        new CheckoutDeliveryAddressViewHolder(view);
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final int position = viewHolder.getAdapterPosition();
        if (itemClickListener != null
            && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          itemClickListener.onItemClicked(deliveryAddressList.get(viewHolder.getAdapterPosition()));
        }
        if (position != RecyclerView.NO_POSITION) {
          changeItemSelected(position);
          App.idDeliveryAddress = String.valueOf(deliveryAddressList.get(position).getId());
        }
      }
    });

    viewHolder.txtEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (itemEditClickListener != null
            && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          itemEditClickListener.onItemEditClicked(
              deliveryAddressList.get(viewHolder.getAdapterPosition()));
        }
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(final CheckoutDeliveryAddressViewHolder holder, int position) {
    holder.bind(deliveryAddressList.get(position), countryCodeList);
  }

  @Override
  public int getItemCount() {
    return deliveryAddressList != null ? deliveryAddressList.size() : 0;
  }

  public void setCountryCodes(List<CountryCode> countryCodes) {
    countryCodeList = countryCodes;
  }

  private void changeItemSelected(int position){
    try{
      deliveryAddressList.get(lastPosition).setDeliveryAddressSelected(false);
    }catch (IndexOutOfBoundsException ex){}
    deliveryAddressList.get(position).setDeliveryAddressSelected(true);
    lastPosition = position;
    notifyDataSetChanged();
  }

  public List<Address> getData(){
    return  this.deliveryAddressList;
  }
  public boolean isSelectedDefault(){
    for(Address address : deliveryAddressList){
      if(address.isDeliveryAddressSelected()){
        return true;
      }
    }
    return false;
  }
}
