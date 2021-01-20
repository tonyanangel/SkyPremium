package com.skypremiuminternational.app.app.features.checkout.steptwo;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.AspectRatioImageView;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.domain.models.myOrder.detail.ItemsItem;

/**
 * Created by aeindraaung on 2/4/18.
 */

public class CheckoutReviewViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.img_product) AspectRatioImageView imgProduct;
  @BindView(R.id.txt_product_name) TextView txtProductName;
  @BindView(R.id.txt_kind_of_product) TextView txtKindOfProduct;
  @BindView(R.id.txt_product_amount) TextView txtProductAmount;
  @BindView(R.id.img_delete) ImageView imgDelete;
  @BindView(R.id.eidt_qty) EditText editQty;
  @BindView(R.id.txt_amount) TextView txtAmount;
  @BindView(R.id.layout_main) LinearLayout layoutMain;
  @BindView(R.id.tv_error_message) TextView tvErrorMessage;
  @BindView(R.id.tv_loyaltyValue) TextView tvLoyaltyValue;

  public CheckoutReviewViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(ItemsItem response, int position) {
    imgDelete.setVisibility(View.GONE);
    if ((position % 2) != 0) {
      layoutMain.setBackgroundColor(
          ContextCompat.getColor(layoutMain.getContext(), R.color.white));
    } else {
      layoutMain.setBackgroundColor(
          ContextCompat.getColor(layoutMain.getContext(), R.color.background));
    }

    tvErrorMessage.setVisibility(View.GONE);
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.centerCrop();
    requestOptions.error(R.drawable.white);
    Glide.with(itemView.getContext())
        .load(URLUtils.formatImageURL(response.getExtensionAttributes().getThumbnailImageUrl()))
        .apply(requestOptions)
        .into(imgProduct);
    if(response.getExtensionAttributes().getCategoryName()!=null){
      txtKindOfProduct.setText(response.getExtensionAttributes().getCategoryName());
    }else {
      txtKindOfProduct.setText("");
    }
    txtProductName.setText(response.getName());
    if (response.getPrice() != null && !TextUtils.isEmpty(response.getPrice())) {
      txtProductAmount.setText(
          String.format("S$%s", DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getPrice()))));
    }
    editQty.setText(String.valueOf(response.getQtyOrdered()));
    String amount =
        DecimalUtil.roundTwoDecimals(response.getQtyOrdered() * Double.parseDouble(response.getPrice()));
    txtAmount.setText(String.format("S$%s", amount));
    editQty.setEnabled(false);
    if(response.getLoyaltyValue() != null && !response.getLoyaltyValue().equals("0")){
      tvLoyaltyValue.setVisibility(View.VISIBLE);
      tvLoyaltyValue.setText(String.format("Earn %s.00 Sky Dollar", response.getLoyaltyValue()));
      tvLoyaltyValue.setAllCaps(false);
    }
  }
}
