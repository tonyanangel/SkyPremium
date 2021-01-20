package com.skypremiuminternational.app.app.features.profile.my_orders;

import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderItem;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderSubItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.skypremiuminternational.app.app.utils.DecimalUtil.roundTwoDecimals;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class MyOrderViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.tvId)
  TextView tvId;
  @BindView(R.id.tvDate)
  TextView tvDate;
  @BindView(R.id.tvCost)
  TextView tvCost;
  @BindView(R.id.tvStatus)
  TextView tvStatus;
  @BindView(R.id.tvSkyEarned)
  TextView tvSkyEarned;
  @BindView(R.id.tvSkyRedeemed)
  TextView getTvSkyRedeemed;

  private MyOrderItem myOrder;

  public MyOrderViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(final MyOrderItem item) {
    this.myOrder = item;
    tvId.setText(String.format("Order ID: %s", item.getIncrementId()));
     tvDate.setText(parseDateFormat(item.getUpdatedAt()));
    tvCost.setText(
        String.format(itemView.getContext().getResources().getString(R.string.label_amount_format),
            roundTwoDecimals(Double.parseDouble(item.getGrandTotal()))));
    // Get loyalty points redeemed from MyOrderItem.java
    getTvSkyRedeemed.setText(roundTwoDecimals(Double.valueOf(item.getExtensionAttributes().getTotalloyalty()) ));

    if (item.getStatus().equalsIgnoreCase(Constants.ORDER_PENDING)) {
      tvStatus.setText(R.string.txt_delivering);
    } else if (item.getStatus().equalsIgnoreCase(Constants.ORDER_COMPLETE)) {
      tvStatus.setText(R.string.txt_collected);
    } else if (item.getStatus().equalsIgnoreCase(Constants.ORDER_HOLDED)) {
      tvStatus.setText(R.string.txt_holded);
    } else if (item.getStatus().equalsIgnoreCase(Constants.ORDER_PROCESSING)) {
      tvStatus.setText(R.string.txt_processing);
    } else if (item.getStatus().equalsIgnoreCase(Constants.ORDER_CLOSED)) {
      tvStatus.setText(R.string.txt_closed);
    } else if (item.getStatus().equalsIgnoreCase(Constants.ORDER_FAIL)) {
      tvStatus.setText(R.string.txt_fail);
    } else if (item.getStatus().equalsIgnoreCase(Constants.ORDER_SHIPPING)) {
      tvStatus.setText(R.string.txt_shipping);
    } else if (item.getStatus().equalsIgnoreCase(Constants.ORDER_CANCELLED)) {
      tvStatus.setText(R.string.txt_cancelled);
    }


    /*if(item.getExtensionAttributes().getTotalloyalty()!=null
        &&!TextUtils.isEmpty(item.getExtensionAttributes().getTotalloyalty())
        &&Double.parseDouble(item.getExtensionAttributes().getTotalloyalty())>0){
      tvSkyEarned.setText("0.00");
    }else{
      tvSkyEarned.setText(DecimalUtil.roundTwoDecimals(item.getExtensionAttributes().getTotalSkyDollarEarn()));
    }*/
    // Calculate combined loyalty points earned from MyOrderSubItem.java
    List<MyOrderSubItem> subItems = item.getItems();
    int totalLoyaltyValue = 0;
    for (MyOrderSubItem subItem : subItems) {
      if (subItem.getLoyaltyValue() != null && !subItem.getLoyaltyValue().equals("0")) {
        totalLoyaltyValue += Integer.valueOf(subItem.getLoyaltyValue());
      }
    }
    tvSkyEarned.setText(String.valueOf(totalLoyaltyValue) + ".00");

  }

  private String parseDateFormat(String time) {
    String inputPattern = "yyyy-MM-dd HH:mm:ss";
    String outputPattern = "EEE, dd MMM yyyy";
    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
    Date date = null;
    String str = null;

    try {
      date = inputFormat.parse(time);
      str = outputFormat.format(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return str;
  }
}
