package com.skypremiuminternational.app.app.features.estore.detail.review;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.detail.review.adapter.ReviewImageAdapter;
import com.skypremiuminternational.app.app.features.profile.order.detail.review.ReviewPresenter;
import com.skypremiuminternational.app.app.features.shopping.detail.ImagePagerAdapter;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.domain.models.comment_rating.Comment;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewImage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class ImageReviewDialogFragment  extends BaseDialogFragment<ImageReviewPresenter>
    implements ImageReviewView<ImageReviewPresenter>{

  @BindView(R.id.vpgImageComment)
  ViewPager vpgImageComment;
  @BindView(R.id.rvImagePage)
  RecyclerView rvImagePage;

  ImagePagerAdapter imagePagerAdapter;
  ReviewImageAdapter reviewImagePagerAdapter;
  Integer postition;
  Comment comment;


  ImageReviewPresenter presenter;

  public static ImageReviewDialogFragment newInstance() {
    ImageReviewDialogFragment fragment = new ImageReviewDialogFragment();
    return fragment;
  }

  public static ImageReviewDialogFragment newInstance(Integer postition, Comment comment) {
    ImageReviewDialogFragment fragment = new ImageReviewDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putInt("position",postition);
    bundle.putSerializable("comment",comment);
    fragment.setArguments(bundle);
    return fragment;
  }
  public static ImageReviewDialogFragment newInstance( Comment comment) {
    ImageReviewDialogFragment fragment = new ImageReviewDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putSerializable("comment",comment);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      dialog.getWindow()
          .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);
    List<String> listData = new ArrayList<>();
/*
    listData.add("https://d2h2e5qsoix1ya.cloudfront.net/s3fs-public/styles/privilegest/public/travel.jpg");
    listData.add("https://d2h2e5qsoix1ya.cloudfront.net/s3fs-public/styles/privilegest/public/dining.jpg");
    listData.add("https://d2h2e5qsoix1ya.cloudfront.net/s3fs-public/styles/privilegest/public/shopping.jpg");
    listData.add("https://d2h2e5qsoix1ya.cloudfront.net/s3fs-public/styles/privilegest/public/travel.jpg");
    listData.add("https://d2h2e5qsoix1ya.cloudfront.net/s3fs-public/styles/privilegest/public/dining.jpg");
    listData.add("https://d2h2e5qsoix1ya.cloudfront.net/s3fs-public/styles/privilegest/public/shopping.jpg");
    listData.add("https://d2h2e5qsoix1ya.cloudfront.net/s3fs-public/styles/privilegest/public/travel.jpg");
    listData.add("https://d2h2e5qsoix1ya.cloudfront.net/s3fs-public/styles/privilegest/public/dining.jpg");
    listData.add("https://d2h2e5qsoix1ya.cloudfront.net/s3fs-public/styles/privilegest/public/shopping.jpg");*/






    reviewImagePagerAdapter = new ReviewImageAdapter(getContext(),vpgImageComment,getChildFragmentManager());
    rvImagePage.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    rvImagePage.setAdapter(reviewImagePagerAdapter);
    try {
      this.postition = this.getArguments().getInt("position");
      this.comment = (Comment) this.getArguments().getSerializable("comment");
    }catch (NullPointerException ex){}
    if(comment!=null){
      imagePagerAdapter =new ImagePagerAdapter(getActivity(),getChildFragmentManager(),comment.getFullImagerURL());
      reviewImagePagerAdapter.setData(comment);
    }
    vpgImageComment.setAdapter(imagePagerAdapter);

    if(postition!=null){
      vpgImageComment.setCurrentItem(postition);
    }
  }

  @OnClick(R.id.imgToRight)
  void clickToRight(){
      vpgImageComment.setCurrentItem(vpgImageComment.getCurrentItem()+1);
  }
  @OnClick(R.id.imgToLeft)
  void clickToLeft(){
      vpgImageComment.setCurrentItem(vpgImageComment.getCurrentItem()-1);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_fragment_image_review;
  }

  @Inject
  @Override
  public void injectPresenter(ImageReviewPresenter presenter) {
    super.injectPresenter(presenter);
  }


  @OnClick(R.id.img_close)
  void clickToClose(){
    dismiss();
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }
}
