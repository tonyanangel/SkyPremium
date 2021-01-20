package com.skypremiuminternational.app.app.features.checkout.stepone;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.utils.listener.ItemClickListener;
import com.skypremiuminternational.app.app.utils.listener.ItemEditClickListener;

import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

import java.util.List;

/**
 * Created by aeindraaung on 1/29/18.
 */

public class CheckoutVisaCardAdapter extends RecyclerView.Adapter<CheckoutVisaCardViewHolder> {
  private List<CreditCardResponse> visaCartList;
  private ItemClickListener<CreditCardResponse> itemClickListener;
  private ItemEditClickListener<CreditCardResponse> itemEditClickListener;
  private int lastPosition = 0;

  public void setItemClickListener(ItemClickListener<CreditCardResponse> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public void setItemEditClickListener(ItemEditClickListener<CreditCardResponse> itemEditClickListener) {
    this.itemEditClickListener = itemEditClickListener;
  }

  public void setVisaList(List<CreditCardResponse> visaCartList) {
    this.visaCartList = visaCartList;
    notifyDataSetChanged();
  }

  public void setItemVisaTemp(int lastPosition) {
    this.lastPosition = lastPosition;
  }

  @Override
  public CheckoutVisaCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_credit_card, parent, false);
    final CheckoutVisaCardViewHolder viewHolder = new CheckoutVisaCardViewHolder(view);
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position = viewHolder.getAdapterPosition();
        if (itemClickListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          itemClickListener.onItemClicked(visaCartList.get(viewHolder.getAdapterPosition()));
        }
        if (position != RecyclerView.NO_POSITION){
          changeItemVisaSelected(position);
          App.idCard = visaCartList.get(position).getId();
        }
      }
    });
    viewHolder.txtEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (itemEditClickListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          itemEditClickListener.onItemEditClicked(visaCartList.get(viewHolder.getAdapterPosition()));
        }
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(final CheckoutVisaCardViewHolder holder, int position) {
    holder.bind(visaCartList.get(position));
  }

  @Override
  public int getItemCount() {
    return visaCartList != null ? visaCartList.size() : 0;
  }

  private void changeItemVisaSelected(int position){
    visaCartList.get(lastPosition).setVisaSelected(false);
    visaCartList.get(position).setVisaSelected(true);
    lastPosition = position;
    notifyDataSetChanged();
  }

  public List<CreditCardResponse> getVisaCartList(){
    return this.visaCartList;
  }

}
