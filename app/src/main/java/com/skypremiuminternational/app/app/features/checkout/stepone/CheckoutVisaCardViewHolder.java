package com.skypremiuminternational.app.app.features.checkout.stepone;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skypremiuminternational.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

/**
 * Created by aeindraaung on 1/29/18.
 */
class CheckoutVisaCardViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.txt_edit)
  TextView txtEdit;
  @BindView(R.id.txt_visa_number)
  TextView txtVisaNumber;
  @BindView(R.id.tv_delivery_date)
  TextView txtDate;
  @BindView(R.id.layout_parent)
  RelativeLayout layoutParent;
  @BindView(R.id.isVisa)
  ImageView imgIsVisa;
  @BindView(R.id.img_visa)
  ImageView imgVisa;

  public CheckoutVisaCardViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(CreditCardResponse item) {

/* 20201707 - WIKI Viet Nguyen - fix bug set default visa
    if (item.getCartDefault()) {
      layoutParent.setBackground(itemView.getContext()
          .getResources()
          .getDrawable(R.drawable.booking_history_cancel_btn_bg_enable));
      imgIsVisa.setVisibility(View.VISIBLE);
    } else {
      layoutParent.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
      imgIsVisa.setVisibility(View.GONE);
    }*/

    if (item.getVisaSelected()) {
      layoutParent.setBackground(itemView.getContext()
              .getResources()
              .getDrawable(R.drawable.booking_history_cancel_btn_bg_enable));
      imgIsVisa.setVisibility(View.VISIBLE);
    } else {
      layoutParent.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
      imgIsVisa.setVisibility(View.GONE);
    }

    if (item.getBrand() != null) {
      if (item.getBrand().equalsIgnoreCase(Constants.VISA_BRAND)) {
        imgVisa.setImageResource(R.drawable.ic_visa_blue_background);
      } else if (item.getBrand().equalsIgnoreCase(Constants.MASTER_BRAND)) {
        imgVisa.setImageResource(R.drawable.ic_master_card_with_text);
      } else {
        imgVisa.setImageResource(R.drawable.ic_american_express_blue_background);
      }
    } else {
      imgVisa.setImageResource(R.drawable.ic_visa_blue_background);
    }
    txtVisaNumber.setText("XXXX XXXX XXXX " + item.getLast4());
    txtDate.setText(item.getExpMonth() + "/" + item.getExpYear());
  }
}
