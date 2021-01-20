package com.skypremiuminternational.app.app.features.estore.detail.review.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.detail.review.ImageReviewDialogFragment;
import com.skypremiuminternational.app.domain.models.comment_rating.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewImageViewHolder extends RecyclerView.ViewHolder {



  @BindView(R.id.img)
  ImageView imagePage;

  Context context;
  FragmentManager fragmentManager;

  public void bind(String url, ViewPager vpgImageComment, int position, Comment comment){

    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.error(R.drawable.white);
    Glide.with(context).load(url)
        .apply(requestOptions)
        .into(imagePage);
    imagePage.setOnClickListener(v -> {
      if(vpgImageComment!=null){
        vpgImageComment.setCurrentItem(position);
      }else {
        if(comment.getImage().size()>0){
          ImageReviewDialogFragment fragment = ImageReviewDialogFragment.newInstance(position,comment);
          fragment.show(fragmentManager,"IMG_REVIEW");
        }
      }
    });
  }


  public ReviewImageViewHolder(@NonNull View itemView, FragmentManager fragmentManager) {
    super(itemView);
    ButterKnife.bind(this,itemView);
    context = itemView.getContext();
    this.fragmentManager = fragmentManager;
  }


}
