package com.skypremiuminternational.app.app.features.profile.order.detail.review;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.profile.ProfileViewState;
import com.skypremiuminternational.app.app.features.profile.order.detail.OrderDetailsActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.model.ImageViewParce;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.ImageDataUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.utils.RatingUtils;
import com.skypremiuminternational.app.app.view.VerticalScrollview;
import com.skypremiuminternational.app.data.network.request.AddCommentRequest;
import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
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
import java.net.URLConnection;
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

public class ReviewDialogFragment  extends BaseDialogFragment<ReviewPresenter>
    implements ReviewView<ReviewPresenter> {

  static  final  int MAX_CONTENT = 1000;
  static  final  int MIN_CONTENT = 25;
  @BindView(R.id.imgThumbnail)
  ImageView imgThumbnail;
  @BindView(R.id.rvListImage)
  RecyclerView rvListImage;
  @BindView(R.id.tvProductName)
  TextView tvProductName;
  @BindView(R.id.tvLimitText)
  TextView tvLimitText;
  @BindView(R.id.tvLimitNote)
  TextView tvLimitNote;
  @BindView(R.id.tvReviewComment)
  TextView tvReviewComment;
  @BindView(R.id.tvLimitTextSuccess)
  TextView tvLimitTextSuccess;
  @BindView(R.id.tvBotCaption)
  TextView tvBotCaption;
  @BindView(R.id.tv_save)
  TextView tvSave;
  @BindView(R.id.lbLimitFile)
  TextView lbLimitFile;
  @BindView(R.id.tvThankYou)
  TextView tvThankYou;
  @BindView(R.id.tvAttachment)
  TextView tvAttachment;
  @BindView(R.id.tvTopTitle)
  TextView tvTopTitle;
  @BindView(R.id.lbComment)
  TextView lbComment;
  @BindView(R.id.layoutContentComment)
  ConstraintLayout layoutContentComment;
  @BindView(R.id.scrollView)
  VerticalScrollview nsv;
  @BindView(R.id.editComment)
  EditText editComment;
  @BindView(R.id.rtStar)
  ScaleRatingBar rtStar;


  ImageAdapter adapter;
  RatingOption ratingOption;
  String ratingId;
  AddCommentRequest addCommentRequest;

  OrderDetailsActivity activity ;

  private ProgressDialog progressDialog;
  List<ImageViewParce> listImageParce = new ArrayList<>();
  List<ImageView> listImage = new ArrayList<>();
  List<Bitmap> listBitmap = new ArrayList<>();
  String[] listString = {"image-0","image-1","image-2","image-3","image-4","image-5","image-6"};
  String url;
  List<Uri> mSelected;

  int PICK_IMAGE_MULTIPLE = 1;
  String imageEncoded;
  List<String> imagesEncodedList;


  @Inject
  ErrorMessageFactory errorMessageFactory;

  private String name, uploadImageUrl, imageName, imageType;
  private String productName;
  private String sku;
  private String nameCustomer="";
  private String idProduct;
  private String idOrder;

  public static ReviewDialogFragment newInstance() {
    ReviewDialogFragment fragment = new ReviewDialogFragment();
    return fragment;
  }

  public static ReviewDialogFragment newInstance(String name) {
    ReviewDialogFragment fragment = new ReviewDialogFragment();
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
    //return super.onCreateDialog(savedInstanceState);
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    //dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);
    this.setRetainInstance( true );
    activity = (OrderDetailsActivity) getActivity();
    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);
    Picasso.get().load(url)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(imgThumbnail);
    tvProductName.setText(productName);
    setCommentChange();
    setOnClickRating();
    setRecyclerView();
    presenter.getUserDetail();
    tvSave.setOnClickListener(v -> {
      if(rtStar.getRating()<=0f){
        Toast.makeText(getContext(),"Please rating",Toast.LENGTH_SHORT).show();
        return;
      }
      if(editComment.getText().length()<=0){
        addCommentRequest= new AddCommentRequest();
        addCommentRequest.setNickName(nameCustomer);
        addCommentRequest.setDetail("&nbsp;");
        addCommentRequest.setOrderId(idOrder);
        addCommentRequest.setProductId(idProduct);
        addCommentRequest.setTitle(TITILE);
        addCommentRequest.setRating("20");
        addCommentRequest.setListBase64(getListImageBase64());
        addCommentRequest.setRating(ratingId);
        presenter.submitComment(addCommentRequest);
      }else if(isValidate()){

        addCommentRequest= new AddCommentRequest();
        addCommentRequest.setNickName(nameCustomer);
        addCommentRequest.setDetail(editComment.getText().toString());
        addCommentRequest.setOrderId(idOrder);
        addCommentRequest.setProductId(idProduct);
        addCommentRequest.setTitle(TITILE);
        addCommentRequest.setRating("20");
        addCommentRequest.setListBase64(getListImageBase64());
        addCommentRequest.setRating(ratingId);
        presenter.submitComment(addCommentRequest);
      }
    });
    Log.d("IMG-LIST",""+adapter.getItemCount());

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
        tvLimitText.setText(s.length()+"/"+MAX_CONTENT);
      }
    });
  }

  void setOnClickRating(){
    presenter.rating();
    rtStar.setOnRatingChangeListener((ratingBar, rating, fromUser) -> {
      presenter.rating();
    });
  }


  void setRecyclerView(){
    adapter = new ImageAdapter(this);
    adapter.setData(ImageDataUtils.getFileImage(getActivity()));
    adapter.setIsEdit(true);
    rvListImage.setLayoutManager(new GridLayoutManager(getActivity(),9,RecyclerView.VERTICAL,false));

    rvListImage.setAdapter(adapter);
  }

  @Override
  public void onDismiss(DialogInterface dialog) {

  }

  @OnClick(R.id.imgClose)
  void onClickContinue() {
    dismiss();
    OrderDetailsActivity activity = (OrderDetailsActivity) getActivity();
    activity.getPresenter().getOrderDetail(Integer.parseInt(idOrder));
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
     if (requestCode == ImageAdapter.REQUEST_SELECT_PICTURE && resultCode == RESULT_OK
          && null != data) {
        if(data.getAction()!=null&&data.getAction().equalsIgnoreCase("inline-data")) {
          Bitmap photo = (Bitmap) data.getExtras().get("data");
          Uri tempUri = getImageUri(getContext(), photo);
          int pos = ImageDataUtils.getCountFile(getActivity());
          if(pos<7)
            ImageDataUtils.saveImageByPath(getActivity(),tempUri,listString[pos]);
          adapter.setData(ImageDataUtils.getFileImage(getActivity()));
        }
        if(data.getData()!=null){

          Uri mImageUri=data.getData();

          int pos = ImageDataUtils.getCountFile(getActivity());
          if(pos<7)
            ImageDataUtils.saveImageByPath(getActivity(),mImageUri,listString[pos]);
          adapter.setData(ImageDataUtils.getFileImage(getActivity()));
        } else {
          if (data.getClipData() != null) {
            ClipData mClipData = data.getClipData();
            ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
            for (int i = 0; i < mClipData.getItemCount(); i++) {

              ClipData.Item item = mClipData.getItemAt(i);
              Uri uri = item.getUri();
              mArrayUri.add(uri);
                int pos = ImageDataUtils.getCountFile(getActivity());
                if(pos<7)
                  ImageDataUtils.saveImageByPath(getActivity(),uri,listString[pos]);
            }
            adapter.setData(ImageDataUtils.getFileImage(getActivity()));
            Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
          }
        }
      } else if (requestCode==ImageAdapter.REQUEST_TAKE_PHOTO) {
       int pos = ImageDataUtils.getCountFile(getActivity());
       int scaleValue = 100;
        if(pos<7){

          BitmapFactory.Options options = new BitmapFactory.Options();
          options.inPreferredConfig = Bitmap.Config.ARGB_8888;
          Log.d("INTENT-TEST","TEST "+adapter.getPhotoFilePath());
          PreferenceUtils preferenceUtils = new PreferenceUtils(getContext());

          Bitmap bitmap = BitmapFactory.decodeFile(preferenceUtils.get("imageURL",""), options);
          ContextWrapper cw = new ContextWrapper(getContext().getApplicationContext());
          File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
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
              }

              fos.flush();
              fos.close();
            } catch (java.io.IOException e) {
              e.printStackTrace();
            }
          }
          adapter.setData(ImageDataUtils.getFileImage(getContext()));
        }
      } else {
        Toast.makeText(getActivity(), "You haven't picked Image",LENGTH_LONG).show();
      }
  }
  public Uri getImageUri(Context inContext, Bitmap inImage) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
    return Uri.parse(path);
  }

  public String getRealPathFromURI(Uri uri) {
    String path = "";
    if (getContext().getContentResolver() != null) {
      Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
      if (cursor != null) {
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        path = cursor.getString(idx);
        cursor.close();
      }
    }
    return path;
  }
  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Inject
  @Override
  public void injectPresenter(ReviewPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_fragment_review;
  }



  @Override
  public void render() {

  }

  @Override
  public void renderSubmitSuccess(RatingResult addResult) {
    //set gone
    editComment.setVisibility(View.GONE);
    tvLimitNote.setVisibility(View.GONE);
    tvLimitText.setVisibility(View.GONE);
    lbLimitFile.setVisibility(View.GONE);
    tvSave.setVisibility(View.GONE);
    tvSave.setEnabled(false);
    //set visible

    tvReviewComment.setVisibility(View.VISIBLE);
    tvLimitTextSuccess.setVisibility(View.VISIBLE);
    tvBotCaption.setVisibility(View.VISIBLE);
    tvThankYou.setVisibility(View.VISIBLE);

    rtStar.setClickable(false);
    rtStar.setScrollable(false);
    adapter.setIsEdit(false);

    Toast.makeText(getContext(),""+addResult.getStatus()+" ! "+addResult.getMessage(),Toast.LENGTH_SHORT).show();
    ReviewDetailRequest request = new ReviewDetailRequest();
    request.setProductId(addCommentRequest.getProductId());
    request.setOrderId(addCommentRequest.getOrderId());
    tvTopTitle.setText(getString(R.string.review_submission_successful));
    hideLoading();
    presenter.getReviewDetail(request);
  }

  @Override
  public void renderSubmitFailed(RatingResult value) {
    Toast.makeText(getContext(),""+value.getStatus()+" ! "+value.getMessage(),Toast.LENGTH_SHORT).show();
  }

  @Override
  public void renderSubmitError() {

  }

  @Override
  public void renderGetUserDetailSuccess(UserDetailResponse response) {
    nameCustomer = "";

    if (response.getFirstname() != null) {
      nameCustomer += " " + response.getFirstname();
    }

    if (response.getLastname() != null) {
      nameCustomer += " " + response.getLastname();
    }

  }

  @Override
  public void renderGetUserDetailFail() {

  }

  @Override
  public void renderRatingStar(RatingOption ratingOption) {
    if(RatingUtils.rating(rtStar.getRating(),ratingOption)!=null){
      Log.d("Rating","Rating :" +RatingUtils.rating(rtStar.getRating(),ratingOption));
      ratingId = RatingUtils.rating(rtStar.getRating(),ratingOption);
    }
  }

  @Override
  public void renderReviewDetail(ReviewDetailResponse reviewDetailResponse) {


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
        editComment.setVisibility(View.GONE);
      }else {
        tvReviewComment.setText(reviewDetailResponse.getDetail());
        lbComment.setVisibility(View.VISIBLE);
      }
    }else {
      tvReviewComment.setVisibility(View.GONE);
      editComment.setVisibility(View.GONE);
    }

    tvLimitText.setVisibility(View.GONE);
    tvLimitTextSuccess.setVisibility(View.GONE);
    hideLoading();
    hideKeyboard(getActivity());
    nsv.scrollTo(0, 0);

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
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    Intent viewIntent = new Intent(Intent.ACTION_GET_CONTENT);
    Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    viewIntent.setType("image/*");
    Intent chooserIntent = Intent.createChooser(viewIntent, "Open in...");
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { camIntent });
    viewIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    startActivityForResult(Intent.createChooser(chooserIntent,"Select Picture"), 1);
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


  private boolean isValidate(){
    if(editComment.getText().toString().length()<MIN_CONTENT||editComment.getText().toString().length()>MAX_CONTENT){
      Toast.makeText(getContext(),"25 - 1000 characters",Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }

  private List<String> getListImageBase64(){


    List<String> listBase64 = new ArrayList<>();
    String base64;


    for(File file  : ImageDataUtils.getFileImage(getActivity())){
      listBase64.add(covertToBase64(file));
    }
     /*
    //get image
    imageName = result.getUri().getLastPathSegment();

    final InputStream imageStream;
    try {
      imageStream = getActivity().getContentResolver().openInputStream(result.getUri());
      final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
      byte[] b = baos.toByteArray();
      base64 = Base64.encodeToString(b, Base64.DEFAULT);
      listBase64.add(base64);

      // upload to server
     // presenter.uploadImageToServer(uploadImageUrl, imageType, imageName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/
    return listBase64;
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

  }
