package com.skypremiuminternational.app.app.features.checkout.stepone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.user.Address;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class BillingAddressAdapter extends RecyclerView.Adapter<BillingAddressViewHolder> {
  private final boolean highlightDefault;
  private List<BillingAddress> billingAddresses = new ArrayList<>();
  private List<CountryCode> countryCodes;
  private ActionListener actionListener;
  private int lastPosition = 0;

  public BillingAddressAdapter(boolean highlightDefault) {
    this.highlightDefault = highlightDefault;
  }

  public void setPositionSelected(int lastPosition){
    this.lastPosition = lastPosition;
  }

  @Override
  public BillingAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_billing_address, parent, false);
    BillingAddressViewHolder viewHolder = new BillingAddressViewHolder(view);
    viewHolder.itemView.setOnClickListener(v -> {
      final int position = viewHolder.getAdapterPosition();
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onItemClicked(billingAddresses.get(viewHolder.getAdapterPosition()));
      }
      if (position != RecyclerView.NO_POSITION) {
        changeItemSelected(position);
        App.idBillingAddress = billingAddresses.get(position).getId();
      }
    });

    viewHolder.txtEdit.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onEditClicked(billingAddresses.get(viewHolder.getAdapterPosition()));
      }
    });
    return viewHolder;
  }

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  @Override
  public void onBindViewHolder(BillingAddressViewHolder holder, int position) {
    holder.bind(billingAddresses.get(position), countryCodes, highlightDefault);
  }

  @Override
  public int getItemCount() {
    return billingAddresses == null ? 0 : billingAddresses.size();
  }

  public void setBillingAddresses(List<BillingAddress> billingAddresses) {
    this.billingAddresses = billingAddresses;
    notifyDataSetChanged();
  }

  public void setCountryCodes(List<CountryCode> countryCodes) {
    this.countryCodes = countryCodes;
  }

  public interface ActionListener {
    void onEditClicked(BillingAddress address);

    void onItemClicked(BillingAddress address);
  }

  private void changeItemSelected(int position){
    try{
      billingAddresses.get(lastPosition).setBillingAddressSelected(false);
    }catch (IndexOutOfBoundsException ex){}
    billingAddresses.get(position).setBillingAddressSelected(true);
    lastPosition = position;
    notifyDataSetChanged();
  }
  public boolean isSelectedDefault(){
    for(BillingAddress address : billingAddresses){
      if(address.isBillingAddressSelected()){
        return true;
      }
    }
    return false;
  }

}
