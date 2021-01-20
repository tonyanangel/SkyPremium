package com.skypremiuminternational.app.app.features.booking.summary;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.booking.GuestDetail;

import static com.skypremiuminternational.app.app.utils.DecimalUtil.roundTwoDecimals;

public class RoomPriceViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.tv_name)
  TextView tvName;
  @BindView(R.id.tv_night_count)
  TextView tvNightCount;
  @BindView(R.id.tv_price)
  TextView tvPrice;
  @BindView(R.id.tv_fee_tax)
  TextView tvFeeTax;
  @BindView(R.id.tv_fee_tax_label)
  TextView tvFeeTaxLabel;

  public RoomPriceViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(GuestDetail guestDetail,String night) {
    tvName.setText(guestDetail.roomName);
    tvNightCount.setText(night);
    tvPrice.setText(String.format("S$%s", roundTwoDecimals(guestDetail.price)));
    tvFeeTax.setText(String.format("S$%s", roundTwoDecimals(guestDetail.feeNTaxes)));
    if (guestDetail.isEurope) {
      tvFeeTaxLabel.setText(R.string.tax_notice_europe);
    } else {
      tvFeeTaxLabel.setText(R.string.tax_notice_non_europe);
    }
  }
}
