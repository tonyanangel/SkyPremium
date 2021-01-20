package com.skypremiuminternational.app.app.features.profile.order.detail.edit_review;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.profile.order.detail.OrderDetailsActivity;
import com.skypremiuminternational.app.app.features.profile.order.detail.edit_review.AsyncTask.DownloadAsyncTask;
import com.skypremiuminternational.app.app.features.profile.order.detail.review.ImageAdapter;
import com.skypremiuminternational.app.app.features.profile.order.detail.review.ReviewDialogFragment;
import com.skypremiuminternational.app.app.features.profile.order.detail.review.ReviewPresenter;
import com.skypremiuminternational.app.app.features.profile.order.detail.review.ReviewView;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.model.ImageFile;
import com.skypremiuminternational.app.app.utils.ImageDataUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.utils.RatingUtils;
import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCommentRequest;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

public class EditReviewDialogFragment extends BaseDialogFragment<EditReviewPresenter>
    implements EditReviewView<EditReviewPresenter> {

  @BindView(R.id.ivGarbage)
  ImageView ivGarbage;
  @BindView(R.id.ivClose)
  ImageView ivClose;
  @BindView(R.id.editComment)
  EditText editComment;
  @BindView(R.id.tvLimitText)
  TextView tvLimitText;
  @BindView(R.id.tvProductName)
  TextView tvProductName;
  @BindView(R.id.tvLimitNote)
  TextView tvLimitNote;
  @BindView(R.id.tv_save)
  TextView tv_save;
  @BindView(R.id.lbLimitFile)
  TextView lbLimitFile;
  @BindView(R.id.tvBotCaption)
  TextView tvBotCaption;
  @BindView(R.id.tvTopTitle)
  TextView tvTopTitle;
  @BindView(R.id.imgThumbnail)
  ImageView imgThumbnail;
  @BindView(R.id.rtStar)
  ScaleRatingBar rtStar;
  @BindView(R.id.rvListImage)
  RecyclerView rvListImage;

  @BindView(R.id.lbAttachment)
  TextView tvAttachment;
  @BindView(R.id.tvReviewComment)
  TextView tvReviewComment;
  @BindView(R.id.lbComment)
  TextView lbComment;

  String name;
  private int MAX_CONTENT = 1000;
  private int MIN_CONTENT = 25;

  String reviewId;

  ImageEditAdapter adapter;

  private String uploadImageUrl, imageName, imageType;
  private String productName;
  private String sku;
  private String customerName="";
  private String idProduct;
  private String idOrder;
  private String url;
  String ratingId;
  private  ProgressDialog progressDialog;
  private  ReviewDetailResponse reviewDetailResponse;
  String[] listString = {"images-edit-0","images-edit-1","images-edit-2","images-edit-3","images-edit-4","images-edit-5","images-edit-6"};
  int currentLenth = 0;
  int PICK_IMAGE_MULTIPLE = 1;
  ReviewDetailRequest request;
  OrderDetailsActivity activity ;
  List<ImageFile> listImageFile;

  public static EditReviewDialogFragment newInstance() {
    EditReviewDialogFragment fragment = new EditReviewDialogFragment();
    return fragment;
  }

  public static EditReviewDialogFragment newInstance(String name) {
    EditReviewDialogFragment fragment = new EditReviewDialogFragment();
    fragment.name = name;
    return fragment;
  }

  @Inject
  @Override
  public void injectPresenter(EditReviewPresenter presenter) {
    super.injectPresenter(presenter);
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
  public void onCreate(Bundle savedInstanceState) {
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

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);
    activity = ((OrderDetailsActivity)getActivity());
    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);
    setCommentChange();
    request = new ReviewDetailRequest();
    request.setCustomerId("");
    request.setOrderId(idOrder);
    request.setProductId(idProduct);

    presenter.getUserDetail();
    if(savedInstanceState!=null){
      if(savedInstanceState.getInt("STATE")!=1){
        presenter.getReviewDetail(request);
      }
      reviewId =  savedInstanceState.getString("REVIEW_ID");
      ArrayList<String> arrayList =  savedInstanceState.getStringArrayList("LIST_IMG");
      listImageFile = new ArrayList<>();
      int pos = 0 ;
      if(arrayList!=null){
        for(String url  : arrayList){
          ImageFile imageFile = new ImageFile();
          imageFile.setUrl(url);
          listImageFile.add(imageFile);
          pos++;
          Log.d("ITEM-LOG-Create",""+url);
        }
      }
      ArrayList<String> nameList =  savedInstanceState.getStringArrayList("LIST_NAME");
      int i = 0 ;
      if(nameList!=null){
        for(String name  : nameList){
          listImageFile.get(i).setName(name);
          i++;
          Log.d("ITEM-LOG-Create",""+name);
        }
      }
    }else {
      presenter.getReviewDetail(request);
    }


    setOnClickRating();
    setRecyclerView();
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    outState.putInt("STATE",1);
    outState.putString("REVIEW_ID",reviewId);

    List<String> urlList = new ArrayList<>();
    if(listImageFile!=null&&listImageFile.size()>0) {
      for (ImageFile file : listImageFile) {
        if(file.getFile()==null){
          urlList.add(file.getUrl());
          Log.d("ITEM-LOG",""+file.getUrl());
        }
      }
      outState.putStringArrayList("LIST_IMG", (ArrayList<String>) urlList);

      List<String> nameList = new ArrayList<>();
      for (ImageFile file : listImageFile) {
        if(file.getFile()==null){
          nameList.add(file.getName());
          Log.d("ITEM-LOG",""+file.getUrl());
        }
      }
      outState.putStringArrayList("LIST_NAME", (ArrayList<String>) nameList);
    }
    super.onSaveInstanceState(outState);

  }

  @OnClick(R.id.ivClose)
  void closeDialogFragment(){
    dismiss();
    OrderDetailsActivity activity = (OrderDetailsActivity) getActivity();
    activity.getPresenter().getOrderDetail(Integer.parseInt(idOrder));
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

  @OnClick(R.id.tv_save)
  void submitEditReview(){
    UpdateCommentRequest request  = new UpdateCommentRequest();
    request.setRating("20");
    request.setItemId(reviewId);
    request.setNickName(customerName);
    request.setTitle(TITILE);
    request.setRating(ratingId);
    request.setListBase64(getListImageBase64());
    request.setNameImage(getImage());
    if(rtStar.getRating()<=0f){
      Toast.makeText(getContext(),"Please rating",Toast.LENGTH_SHORT).show();
      return;
    }
    if(currentLenth<=0) {
      showLoading();
      request.setDetail("&nbsp;");
      presenter.updateReview(request);
    }else if(isValidate()) {
      showLoading();
      request.setDetail(editComment.getText().toString());
      presenter.updateReview(request);
    }

  }
  void setOnClickRating(){
    presenter.rating();
    rtStar.setOnRatingChangeListener((ratingBar, rating, fromUser) -> {
      presenter.rating();
    });
  }
  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
  }
  private boolean isValidate(){
    if(editComment.getText().toString().length()<MIN_CONTENT||editComment.getText().toString().length()>MAX_CONTENT){
      Toast.makeText(getContext(),"25 - 1000 characters",Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }
  void setCommentChange(){
    editComment.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        currentLenth = s.length();
        tvLimitText.setText(currentLenth+"/"+MAX_CONTENT);
      }
    });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_fragment_edit_review;
  }

  private List<String> getListImageBase64(){


    List<String> listBase64 = new ArrayList<>();
    String base64;

    if(adapter.getData()!=null){
      for(ImageFile file  : adapter.getData()){
        if(file.getFile()!=null){
          listBase64.add(covertToBase64(file.getFile()));
        }
      }
    }

    return listBase64;
  }
  private List<String> getImage(){


    List<String> listName = new ArrayList<>();

    if(adapter.getData()!=null) {
      for (ImageFile file : adapter.getData()) {
        if (file.getName() != null) {
          listName.add(file.getName());
        }
      }
    }

    return listName;
  }

  private String covertToBase64(File fileOriginal){
    InputStream finput = null;
    String type="";
    try {
      finput = new FileInputStream(fileOriginal);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    byte[] imageBytes = new byte[(int)fileOriginal.length()];
    try {
      finput.read(imageBytes, 0, imageBytes.length);

      type =  fileOriginal.toURL().openConnection().getContentType();
      finput.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String imageStr = Base64.encodeToString(imageBytes,Base64.DEFAULT);
    imageStr = "data:"+type+";base64,"+imageStr;
    return  imageStr;
  }

  @Override
  public void renderGetUserDetailSuccess(UserDetailResponse response) {
    customerName = "";

    if (response.getFirstname() != null) {
      customerName += " " + response.getFirstname();
    }

    if (response.getLastname() != null) {
      customerName += " " + response.getLastname();
    }

  }

  @Override
  public void renderEditReviewSuccess(RatingResult result) {
    Toast.makeText(getContext(),""+result.getStatus()+" ! "+result.getMessage(),Toast.LENGTH_SHORT).show();
    presenter.getReviewDetailAfterSubmit(request);
  }

  @Override
  public void renderEditReviewFail(RatingResult result) {

    Toast.makeText(getContext(),""+result.getStatus()+" ! "+result.getMessage(),Toast.LENGTH_SHORT).show();
  }

  @Override
  public void renderRatingStar(RatingOption ratingOption) {
    rtStar.setRating(Float.parseFloat(RatingUtils.rating(Float.parseFloat(reviewDetailResponse.getRatingValue()),ratingOption )));
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

  @Override
  public void renderReviewDetailAfterSubmit(ReviewDetailResponse reviewDetailResponse) {
    if(reviewDetailResponse.getImage()!=null){
      if(reviewDetailResponse.getImage().size() ==0 ){
        tvAttachment.setVisibility(View.GONE);
        rvListImage.setVisibility(View.GONE);

      }
    }else {
      tvAttachment.setVisibility(View.GONE);
    }
    if(!TextUtils.isEmpty(reviewDetailResponse.getDetail())){
      if(reviewDetailResponse.getDetail().equalsIgnoreCase("&nbsp;")){
        tvReviewComment.setText("");
        lbComment.setVisibility(View.GONE);
        editComment.setVisibility(View.GONE);
      }else {
        tvReviewComment.setText(reviewDetailResponse.getDetail());
        tvReviewComment.setVisibility(View.VISIBLE);
        lbComment.setVisibility(View.VISIBLE);
        editComment.setVisibility(View.GONE);
      }
    }else {
      tvReviewComment.setVisibility(View.GONE);
      editComment.setVisibility(View.GONE);
    }
    rtStar.setEnabled(false);
    rtStar.setClickable(false);
    rtStar.setScrollable(false);
    tvLimitText.setVisibility(View.GONE);
    tvLimitNote.setVisibility(View.GONE);
    tv_save.setVisibility(View.GONE);
    lbLimitFile.setVisibility(View.GONE);
    tvTopTitle.setText(R.string.review_submission_successful);
    tvBotCaption.setVisibility(View.VISIBLE);
    adapter.setIsEdit(false);
    hideLoading();
    hideKeyboard(getActivity());
  }
  public static void hideKeyboard(Activity activity) {
    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    //Find the currently focused view, so we can grab the correct window token from it.
    View view = activity.getCurrentFocus();
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
      view = new View(activity);
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
  @Override
  public void renderReviewDetail(ReviewDetailResponse reviewDetailResponse) {

    this.reviewDetailResponse = reviewDetailResponse;
    if(reviewDetailResponse.getDetail().equalsIgnoreCase("&nbsp;")){
      editComment.setText("");
    }else {
      editComment.setText(reviewDetailResponse.getDetail());
    }
    Picasso.get().load(url)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(imgThumbnail);
    reviewId = reviewDetailResponse.getReviewId();
    tvProductName.setText(productName);
    presenter.rating();
    OrderDetailsActivity activity = (OrderDetailsActivity) getActivity();
    rtStar.setRating((Float.parseFloat(reviewDetailResponse.getRatingValue())/2f));
    String urlImage[]  = new String[reviewDetailResponse.getFullLinkImage().size()];
    reviewDetailResponse.getFullLinkImage().toArray(urlImage);

    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
       new DownloadAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,urlImage);
    } else {
      new DownloadAsyncTask(this).execute(urlImage);
    }*/
    if(reviewDetailResponse.getFullLinkImage()!=null){
      if(reviewDetailResponse.getFullLinkImage().size()>0){

       listImageFile  = new ArrayList<>();

        int pos =0;
        for(String url : reviewDetailResponse.getFullLinkImage()){
          ImageFile imageFile = new ImageFile();
          imageFile.setUrl(url);
          imageFile.setName(reviewDetailResponse.getImage().get(pos));
          listImageFile.add(imageFile);
          pos++;
        }

        Log.d("ARRAY-SIZE","size: "+reviewDetailResponse.getFullLinkImage().size());


        adapter.setData(listImageFile);
      }
    }

    activity.hideLoading();
  }


  public void renderListImage(){
   // adapter.setData(ImageDataUtils.getFileImageEditZone(getActivity()));
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ImageAdapter.REQUEST_SELECT_PICTURE && resultCode == RESULT_OK
        && null != data) {
      if(data.getAction()!=null&&data.getAction().equalsIgnoreCase("inline-data")) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        Uri tempUri = getImageUri(getContext(), photo);
        int pos = ImageDataUtils.getCountFileEditZone(getActivity());
        if(adapter.getCountItem()+pos<7)
          ImageDataUtils.saveImageByEditPath(getActivity(),tempUri,listString[pos]);
        //adapter.setData(ImageDataUtils.getFileImageEditZone(getActivity()));
        adapter.addFile(ImageDataUtils.getFileImageEditZone(getActivity()));
      }
      if(data.getData()!=null){

        Uri mImageUri=data.getData();
        int pos = ImageDataUtils.getCountFileEditZone(getActivity());
        if(adapter.getCountItem()+pos<7)
          ImageDataUtils.saveImageByEditPath(getActivity(),mImageUri,listString[pos]);
       // adapter.setData(ImageDataUtils.getFileImageEditZone(getActivity()));
        adapter.addFile(ImageDataUtils.getFileImageEditZone(getActivity()));
      } else {
        if (data.getClipData() != null) {
          ClipData mClipData = data.getClipData();
          ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
          for (int i = 0; i < mClipData.getItemCount(); i++) {

            ClipData.Item item = mClipData.getItemAt(i);
            Uri uri = item.getUri();
            mArrayUri.add(uri);
            int pos = ImageDataUtils.getCountFileEditZone(getActivity());
            if(adapter.getCountItem()+pos<7)
              ImageDataUtils.saveImageByEditPath(getActivity(),uri,listString[pos]);
          }
          //adapter.setData(ImageDataUtils.getFileImageEditZone(getActivity()));
          adapter.addFile(ImageDataUtils.getFileImageEditZone(getActivity()));
        }
      }
    }else if (requestCode==ImageAdapter.REQUEST_TAKE_PHOTO) {
      int pos = ImageDataUtils.getCountFileEditZone(getActivity());
      int scaleValue = 100;

      if(adapter.getCountItem()+pos<7){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        PreferenceUtils preferenceUtils = new PreferenceUtils(getContext());

        Bitmap bitmap = BitmapFactory.decodeFile(preferenceUtils.get("imageURL",""), options);
        ContextWrapper cw = new ContextWrapper(getContext().getApplicationContext());
        File directory = cw.getDir("imageDirEdit", Context.MODE_PRIVATE);
        File file = new File(directory, listString[pos]+ ".jpeg");

        if(ImageDataUtils.sizeOf(bitmap)/(1024*1024)>2){
          int percent =  ImageDataUtils.sizeOf(bitmap)/((1024*1024)*2);
          scaleValue = scaleValue / percent+1;
          if(scaleValue<30){
            scaleValue =30;
          }
        }
        if (!file.exists()) {
          Log.d("path", file.toString());
          FileOutputStream fos = null;
          try {
            fos = new FileOutputStream(file);
            try {
              bitmap.compress(Bitmap.CompressFormat.JPEG, scaleValue, fos);
            }catch (NullPointerException e){
              Toast.makeText(getContext(),"You haven't picked Image",Toast.LENGTH_SHORT).show();
              fos.flush();
              fos.close();
              return;
            }

            fos.flush();
            fos.close();
          } catch (java.io.IOException e) {
            e.printStackTrace();
          }
        }
        adapter.setData(listImageFile);
        adapter.addFile(ImageDataUtils.getFileImageEditZone(getContext()));

      }
    }  else {
      Toast.makeText(getActivity(), "You haven't picked Image",LENGTH_LONG).show();
    }
    renderListImage();
    Picasso.get().load(url)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(imgThumbnail);
    tvProductName.setText(productName);
  }
  public Uri getImageUri(Context inContext, Bitmap inImage) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
    return Uri.parse(path);
  }
  void setRecyclerView(){
    adapter = new ImageEditAdapter(this);
    adapter.setData(listImageFile);
    adapter.setIsEdit(true);
    rvListImage.setLayoutManager(new GridLayoutManager(getActivity(),9, RecyclerView.VERTICAL,false));

    rvListImage.setAdapter(adapter);
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
  public void renderRatingStarChoose(RatingOption ratingOption) {
    if(RatingUtils.rating(rtStar.getRating(),ratingOption)!=null){
      Log.d("Rating","Rating :" +RatingUtils.rating(rtStar.getRating(),ratingOption));
      ratingId = RatingUtils.rating(rtStar.getRating(),ratingOption);
    }
  }
}
