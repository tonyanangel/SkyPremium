package com.skypremiuminternational.app.app.features.profile.manage_credit_card;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class CreditCardViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.tvCardNumber)
  TextView tvCardNumber;
  @BindView(R.id.tvCardDate)
  TextView tvCardDate;
  @BindView(R.id.txt_edit)
  TextView txtEdit;
  @BindView(R.id.img_card_brand)
  ImageView imgCardBrand;

  public CreditCardViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(CreditCardResponse item) {
    if (item.getBrand() != null) {
      if (item.getBrand().equalsIgnoreCase(Constants.VISA_BRAND)) {
        imgCardBrand.setImageResource(R.drawable.ic_visa_blue_background);
      } else if (item.getBrand().equalsIgnoreCase(Constants.MASTER_BRAND)) {
        imgCardBrand.setImageResource(R.drawable.ic_master_card_with_text);
      } else {
        imgCardBrand.setImageResource(R.drawable.ic_american_express_blue_background);
      }
    } else {
      imgCardBrand.setImageResource(R.drawable.ic_logo_visa);
    }
    tvCardNumber.setText("XXXX XXXX XXXX " + item.getLast4());
    tvCardDate.setText(item.getExpMonth() + "/" + item.getExpYear());
  }
}
