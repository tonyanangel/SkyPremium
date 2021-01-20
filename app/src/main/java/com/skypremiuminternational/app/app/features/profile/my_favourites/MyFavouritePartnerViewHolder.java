package com.skypremiuminternational.app.app.features.profile.my_favourites;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.utils.ViewRatioUtils;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.Product;
import com.skypremiuminternational.app.domain.util.ProductUtil;

import java.text.DecimalFormat;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  20200413 WIKI Toan Tran -  create new layout for partner
 */

public class MyFavouritePartnerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ll_favourite_partner)
    LinearLayout linearLayout;
    @BindView(R.id.iv_favourite_product)
    ImageView iv;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.tv_sub_category)
    TextView tvSubCategory;
    @BindView(R.id.ivFav_favourite_product)
    ImageView ivFav;
    @BindView(R.id.iv_new)
    ImageView ivNew;



    public MyFavouritePartnerViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final FavouriteListResponse item) {
        ViewRatioUtils.setProduct(linearLayout, iv, itemView.getContext());
        Product product = item.getProduct();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.dontAnimate();
        requestOptions.centerCrop();
        requestOptions.error(R.drawable.white);
        Glide.with(itemView.getContext())
                .load(URLUtils.formatImageURL(product.getThumbnail()))
                .apply(requestOptions)
                .into(iv);
        tvCategory.setText(item.getPillarName());
        tvSubCategory.setText(item.getCategoryName());

        tvProduct.setText(product.getName());
        if(ProductUtil.isValid(item.getProduct().getCurrentServerTime())) {
            if (ProductUtil.isNew(item.getProduct().getNewsFromDate(), item.getProduct().getNewsToDate(), item.getProduct().getCurrentServerTime())) {
                ivNew.setVisibility(View.VISIBLE);
            } else {
                ivNew.setVisibility(View.GONE);
            }
        }else{
            ivNew.setVisibility(View.GONE);
        }
    }

    private String roundDecimal(String price) {
        if (price != null && !TextUtils.isEmpty(price)) {
            return new DecimalFormat("#.00").format(Double.parseDouble(price));
        } else {
            return "0.00";
        }
    }
}
