package com.skypremiuminternational.app.app.features.profile.manage_credit_card;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.listener.ItemEditClickListener;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardViewHolder> {

  private List<CreditCardResponse> dataList = new ArrayList<>();
  private ItemEditClickListener<CreditCardResponse> itemEditClickListener;

  public void setDataList(List<CreditCardResponse> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  public void setItemEditClickListener(ItemEditClickListener<CreditCardResponse> itemEditClickListener) {
    this.itemEditClickListener = itemEditClickListener;
  }

  @Override
  public CreditCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_credit_card, parent, false);
    final CreditCardViewHolder holder = new CreditCardViewHolder(view);

    holder.txtEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (itemEditClickListener != null
            && holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          itemEditClickListener.onItemEditClicked(dataList.get(holder.getAdapterPosition()));
        }
      }
    });
    return holder;
  }

  @Override
  public void onBindViewHolder(CreditCardViewHolder holder, int position) {
    holder.bind(dataList.get(position));
  }

  @Override
  public int getItemCount() {
    return dataList != null ? dataList.size() : 0;
  }
}
