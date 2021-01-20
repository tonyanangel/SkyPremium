package com.skypremiuminternational.app.app.features.profile.order.detail.see_review;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.profile.order.detail.OrderDetailsActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.model.ImageViewParce;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.ImageDataUtils;
import com.skypremiuminternational.app.app.utils.RatingUtils;
import com.skypremiuminternational.app.data.network.request.AddCommentRequest;
import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static com.skypremiuminternational.app.app.utils.Constants.TITILE;

public class ReviewContentDialogFragment  extends BaseDialogFragment<ReviewContentPresenter>
    implements ReviewView<ReviewContentPresenter> {

  static  final  int MAX_CONTENT = 1000;
  static  final  int MIN_CONTENT = 25;
  @BindView(R.id.imgThumbnail)
  ImageView imgThumbnail;
  @BindView(R.id.rvListImage)
  RecyclerView rvListImage;
  @BindView(R.id.tvProductName)
  TextView tvProductName;
  @BindView(R.id.tvReviewComment)
  TextView tvReviewComment;
  @BindView(R.id.lbComment)
  TextView lbComment;
  @BindView(R.id.tvBotCaption)
  TextView tvBotCaption;
  @BindView(R.id.tvThankYou)
  TextView tvThankYou;
  @BindView(R.id.tvAttachment)
  TextView tvAttachment;
  @BindView(R.id.layoutContentComment)
  ConstraintLayout layoutContentComment;
  @BindView(R.id.rtStar)
  ScaleRatingBar rtStar;


  ImageAdapter adapter;
  RatingOption ratingOption;
  String ratingId;
  AddCommentRequest addCommentRequest;

  String reviewId;
  List<ImageViewParce> listImageParce = new ArrayList<>();
  List<ImageView> listImage = new ArrayList<>();
  List<Bitmap> listBitmap = new ArrayList<>();
  String[] listString = {"image-0","image-1","image-2","image-3","image-4","image-5","image-6"};
  String url;
  List<Uri> mSelected;

  int PICK_IMAGE_MULTIPLE = 1;
  String imageEncoded;
  List<String> imagesEncodedList;

  OrderDetailsActivity activity ;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private String name, uploadImageUrl, imageName, imageType;
  private String productName;
  private String sku;
  private String nameCustomer="";
  private String idProduct;
  private String idOrder;
  private ProgressDialog progressDialog;
  public static ReviewContentDialogFragment newInstance() {
    ReviewContentDialogFragment fragment = new ReviewContentDialogFragment();
    return fragment;
  }

  public static ReviewContentDialogFragment newInstance(String name) {
    ReviewContentDialogFragment fragment = new ReviewContentDialogFragment();
    fragment.name = name;
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = this.getArguments();
    if (bundle != null) {
     url = (String) bundle.getSerializable("imageUrl");
     productName = (String) bundle.getSerializable("nameProduct");
     sku = (String) bundle.getSerializable("sku");
     idOrder = (String) bundle.getSerializable("idOrder");
     idProduct = (String) bundle.getSerializable("idProduct");
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    return dialog;
  }

  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      dialog.getWindow()
          .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
  }

  @Override
  public void showLoading() {
    if (!getActivity().isDestroyed()) {
      progressDialog.setMessage("Loading...");
      progressDialog.show();
    }
  }

  @Override
  public void hideLoading() {
    if (!getActivity().isDestroyed() && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);

    this.setRetainInstance( true );


    activity = ((OrderDetailsActivity)getActivity());
    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);
    Picasso.get().load(url)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(imgThumbnail);
    tvProductName.setText(productName);
    setRecyclerView();


    tvReviewComment.setVisibility(View.VISIBLE);
    tvBotCaption.setVisibility(View.VISIBLE);
    tvThankYou.setVisibility(View.VISIBLE);

    rtStar.setClickable(false);
    rtStar.setScrollable(false);


    ReviewDetailRequest request = new ReviewDetailRequest();
    request.setProductId(idProduct);
    request.setOrderId(idOrder);
    presenter.getReviewDetail(request);


  }



  void setRecyclerView(){
    adapter = new ImageAdapter(this);
    rvListImage.setLayoutManager(new GridLayoutManager(getActivity(),9,RecyclerView.VERTICAL,false));

    rvListImage.setAdapter(adapter);
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
  }




  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Inject
  @Override
  public void injectPresenter(ReviewContentPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_fragment_see_review;
  }



  @Override
  public void render() {

  }
  @OnClick(R.id.imgClose)
  void onClose(){
    dismiss();
  }

  @OnClick(R.id.ivGarbage)
  void deleteReview(){
    new AlertDialog.Builder(getContext())
        .setMessage(R.string.ask_delete_comment)
        .setNegativeButton(R.string.negative_answer,(dialog, which) -> {
        }).setPositiveButton(R.string.positive_answer,(dialog, which) -> {
      activity.showLoading("Loading...");
      presenter.deleteReview(reviewId);
    }).show();

  }

  @Override
  public void renderReviewDetail(ReviewDetailResponse reviewDetailResponse) {

    reviewId = reviewDetailResponse.getReviewId();
    if(reviewDetailResponse.getImage()!=null){
      if(reviewDetailResponse.getImage().size() ==0 ){
        tvAttachment.setVisibility(View.GONE);
      }
    }else {

      tvAttachment.setVisibility(View.GONE);
      rvListImage.setVisibility(View.GONE);
    }
    if(!TextUtils.isEmpty(reviewDetailResponse.getDetail())){
      if(reviewDetailResponse.getDetail().equalsIgnoreCase("&nbsp;")){
        tvReviewComment.setText("");
        lbComment.setVisibility(View.GONE);
      }else {
        tvReviewComment.setText(reviewDetailResponse.getDetail());
        lbComment.setVisibility(View.VISIBLE);
      }
    }else {
      tvReviewComment.setVisibility(View.GONE);
      lbComment.setVisibility(View.GONE);
    }
    adapter.setData(reviewDetailResponse.getFullLinkImage());
    rtStar.setRating((Float.parseFloat(reviewDetailResponse.getRatingValue())/2f));
    if(reviewDetailResponse.getFullLinkImage().size()<=0){
      rvListImage.setVisibility(View.GONE);
      tvAttachment.setVisibility(View.GONE);
    }
  }

  @Override
  public void renderDelete(RatingResult ratingResult) {
    Toast.makeText(getContext(), ratingResult.getMessage()+"",Toast.LENGTH_SHORT).show();
    if(ratingResult.getStatus().equalsIgnoreCase("success")){
      dismiss();
    }
    hideLoading();

    OrderDetailsActivity activity = (OrderDetailsActivity) getActivity();
    activity.getPresenter().getOrderDetail(Integer.parseInt(idOrder));
  }
}
