package com.skypremiuminternational.app.app.features.profile.billingaddress;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;

import java.util.ArrayList;
import java.util.List;

public class BillingAddressAdapter extends RecyclerView.Adapter<BillingAddressViewHolder> {
  private final boolean highlightDefault;
  private List<BillingAddress> billingAddresses = new ArrayList<>();
  private List<CountryCode> countryCodes;
  private ActionListener actionListener;

  public BillingAddressAdapter(boolean highlightDefault) {
    this.highlightDefault = highlightDefault;
  }

  @Override
  public BillingAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_billing_address, parent, false);
    BillingAddressViewHolder viewHolder = new BillingAddressViewHolder(view);
    viewHolder.itemView.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onItemClicked(billingAddresses.get(viewHolder.getAdapterPosition()));
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
}
