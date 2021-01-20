package com.skypremiuminternational.app.app.features.profile;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.booking.history.BookingsHistoryActivity;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.demo3_activity.DemoThreeActivity;
import com.skypremiuminternational.app.app.features.demo_four_activity.DemoFourActivity;
import com.skypremiuminternational.app.app.features.demo_two_activity.DemoTwoActivity;
import com.skypremiuminternational.app.app.features.hunry_go_where.my_reservation.MyResevationsActivity;
import com.skypremiuminternational.app.app.features.landing.popup.FragmentVideo;
import com.skypremiuminternational.app.app.features.landing.popup.FragmentVimeo;
import com.skypremiuminternational.app.app.features.landing.popup.FragmentYoutube;
import com.skypremiuminternational.app.app.features.landing.popup.SlideAdapter;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.features.notification_setting.NotificationSettingActivity;
import com.skypremiuminternational.app.app.features.profile.billingaddress.ManageBillingAddressActivity;
import com.skypremiuminternational.app.app.features.profile.edit_profile.EditProfileActivity;
import com.skypremiuminternational.app.app.features.profile.invite_friend.InviteFriendActivity;
import com.skypremiuminternational.app.app.features.profile.manage_credit_card.ManageCreditCardActivity;
import com.skypremiuminternational.app.app.features.profile.manage_delivery_address.ManageDeliveryAddressActivity;
import com.skypremiuminternational.app.app.features.profile.my_favourites.MyFavouritesActivity;
import com.skypremiuminternational.app.app.features.profile.my_orders.MyOrderActivity;
import com.skypremiuminternational.app.app.features.profile.my_sky_dollar.MySkyDollarActivity;
import com.skypremiuminternational.app.app.features.signin.SignInActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.AspectRatioImageView;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateFormatter;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.util.ProductUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import dagger.android.AndroidInjection;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import timber.log.Timber;

public class ProfileActivity extends BaseActivity<ProfilePresenter>
    implements ProfileView<ProfilePresenter> {

  @BindView(R.id.tvManageDeliveryAddress)
  TextView tvManageDeliveryAddress;
  @BindView(R.id.tvManageCreditCards)
  TextView tvManageCreditCards;
  @BindView(R.id.tvMyOrders)
  TextView tvMyOrders;
  @BindView(R.id.tvMyReservation)
  TextView tvMyReservation;
  @BindView(R.id.tvName)
  TextView tvName;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.ivNavigation_toolbar)
  ImageView ivHumberger;
  @BindView(R.id.img_avatar)
  ImageView img_avatar;
  @BindView(R.id.txt_amtloyalty)
  TextView txtAmtLoyalty;
  @BindView(R.id.tvLoyaltyExpireDate)
  TextView tvLoyaltyExpireDate;
  @BindView(R.id.layout_profile)
  LinearLayout layoutProfile;
  @BindView(R.id.tvMyFavourites)
  TextView tvFavourites;
  @BindView(R.id.layout_upload_photo)
  RelativeLayout layoutUploadPhoto;
  @BindView(R.id.img_upload_photo)
  AspectRatioImageView imgUploadPhoto;
  @BindView(R.id.layout_cart_icon)
  RelativeLayout layoutCartIcon;
  @BindView(R.id.lyCartCount)
  FrameLayout lyCartCount;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;
  @BindView(R.id.tvMemberNumber)
  TextView tvMemberNumber;
  @BindView(R.id.tvMemberLabel)
  TextView tvMemberLabel;
  @BindView(R.id.tvOpenPopup)
  TextView tvOpenPopup;

  @BindView(R.id.frm_popup)
  FrameLayout frmPopup;
  @BindView(R.id.pagePopup)
  ViewPager pagePopup;
  @BindView(R.id.cons_popup)
  LinearLayout consPopup;
  @BindView(R.id.fpi)
  FlycoPageIndicaor fpi;
  @BindView(R.id.img_close)
  ImageView imgClose;
  @BindView(R.id.tvMySkyDollar)
  TextView tvMySkyDollar;

  @Inject
  ErrorMessageFactory errorMessageFactory;
  boolean isExpire = false;
  private ProgressDialog progressDialog;
  private ProgressDialog progressDialogLoading;
  private String uploadImageUrl, imageName, imageType;
  private SlideAdapter slideAdapter;

  private int currentPos;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, ProfileActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  public static void startMe(Context context, boolean isExpire) {
    Intent intent = new Intent(context, ProfileActivity.class);
    intent.putExtra("IsExpire", isExpire);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    ButterKnife.bind(this);
    tvTitle_toolbar.setText(getResources().getString(R.string.profile));

    this.isExpire = getIntent().getBooleanExtra("IsExpire", false);
    if (isExpire) {
      ivHumberger.setVisibility(View.INVISIBLE);
    }

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);

    presenter.getUserDetail();
  }

  @Override
  public void onPause() {
    super.onPause();
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Inject
  @Override
  public void injectPresenter(ProfilePresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    if (!isExpire) {
      NavigationDialogFragment.newInstance(tvName.getText().toString())
          .show(getSupportFragmentManager(), "Navigation");
    }
  }

  @OnClick(R.id.tvEditProfile)
  void onClickEditProfile() {
    EditProfileActivity.startMe(this);
  }

  @OnClick(R.id.tvInviteFriend)
  void onClickInviteFriend() {
    presenter.getReferralCodeAndDescription();
  }

  @OnClick(R.id.tvNotificationSetting)
  void onClicktvNotificationSetting() {
    NotificationSettingActivity.startMe(ProfileActivity.this);
  }

  @OnClick(R.id.tvManageCreditCards)
  void onClickManageCreditCard() {
    ManageCreditCardActivity.startMe(this);
  }

  @OnClick(R.id.tvManageDeliveryAddress)
  void onClickManageDeliveryAddress() {
    ManageDeliveryAddressActivity.startMe(this);
  }

  @OnClick(R.id.tvManageBillingAddress)
  void onClickManageBillingAddress() {
    ManageBillingAddressActivity.start(this);
  }

  @OnClick(R.id.tvMyOrders)
  void onClickMyOrders() {
    MyOrderActivity.startMe(this);
  }

  @OnClick(R.id.tvMyFavourites)
  void onClickMyFavourites() {
    MyFavouritesActivity.startMe(this);
  }

  @OnClick(R.id.tvDemo)
  void onClickDemo() {
    DemoTwoActivity.startMe(this);
  }

  @OnClick(R.id.tvOpenPopup)
  void onClickPopup() {

    progressDialog.setMessage(getResources().getString(R.string.loading));
    presenter.getFristTimePopup();
  }
  @OnClick(R.id.img_close)
  void onClickClosePopup() {

    frmPopup.setVisibility(View.GONE);

    if (slideAdapter.getItem(currentPos) instanceof FragmentYoutube) {
      if (((FragmentYoutube) slideAdapter.getItem(currentPos)).getYouTubePlayerView() != null) {
        ((FragmentYoutube) slideAdapter.getItem(currentPos)).getYouTubePlayerView().pause();
      }
    } else if (slideAdapter.getItem(currentPos) instanceof FragmentVideo) {
      if (((FragmentVideo) slideAdapter.getItem(currentPos)).getVideoView() != null) {

        ((FragmentVideo) slideAdapter.getItem(currentPos)).pauseVideo();

      }
    } else if (slideAdapter.getItem(currentPos) instanceof FragmentVimeo) {
      if (((FragmentVimeo) slideAdapter.getItem(currentPos)).getVimeoPlayer() != null) {

        ((FragmentVimeo) slideAdapter.getItem(currentPos)).getVimeoPlayer().pause();

      }
    }
  }

  @OnClick(R.id.tvMySkyDollar)
  void onClickMySkyDollar() {
    MySkyDollarActivity.startMe(this);
  }

  @OnClick(R.id.tvLogOut)
  void onClickLogout() {
    presenter.onLogout();
    finish();
    Intent mIntent = new Intent(this, SignInActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(mIntent);
  }

  @OnClick(R.id.layout_avatar)
  void onClickAvatar() {
    startCropImage(null);
  }

  @Override
  public void render(ProfileViewState viewState) {

    if (viewState.isLoading()) {
      if (viewState.txtLoading() != null) {
        progressDialog.setMessage(getString(R.string.uploading_profile_image));
      } else {
        progressDialog.setMessage(getString(R.string.getting_user_detail));
      }
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess() && viewState.message() != null) {
        UserDetailResponse response = viewState.message();
        if (response.getGroupId() == Constants.GROUP_ID_EXPIRED) {
          isExpire = true;
          updateViewForExpired();
          //showMembershipDialog(getString(R.string.expired_membership));
        } else if (response.getGroupId() == Constants.GROUP_ID_AWAITING_RENEWAL) {
          isExpire = true;
          updateViewForExpired();
          //showMembershipDialog(getString(R.string.awaiting_renewal_membership));
        } else if (response.getGroupId() != Constants.GROUP_ID_ACTIVE) {
          SignInActivity.startMe(this, response.getGroupId());
        }
        tvMemberNumber.setText(getMemberNumber(response));
        //if (!response.isActive()) {
        //  hideFieldsSpecificToActiveUser();
        //}
        String name = "";
        name += getSalutation(response);

        if (response.getFirstname() != null) {
          name += " " + response.getFirstname();
        }

        if (response.getLastname() != null) {
          name += " " + response.getLastname();
        }
        tvName.setText(name);

        //avatar
        Timber.i("Avatar" + response.getAvatar());
        if (response.getAvatar() != null) {
          RequestOptions requestOptions = new RequestOptions();
          requestOptions.placeholder(R.drawable.human);
          requestOptions.dontAnimate();
          requestOptions.centerCrop();
          requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
          requestOptions.error(R.drawable.human);
          Glide.with(getApplicationContext())
              .asBitmap()
              .load(response.getAvatar())
              .apply(requestOptions)
              .into(new BitmapImageViewTarget(img_avatar) {
                @Override
                protected void setResource(Bitmap resource) {
                  RoundedBitmapDrawable circularBitmapDrawable =
                      RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(),
                          resource);
                  circularBitmapDrawable.setCircular(true);
                  img_avatar.setImageDrawable(circularBitmapDrawable);
                }
              });
        }
        if (response.getLoyaltyPoints() != null) {
          txtAmtLoyalty.setText(
              DecimalUtil.roundTowDecimalWithoutBrace(response.getLoyaltyPoints()));
        } else {
          txtAmtLoyalty.setText(DecimalUtil.roundTwoDecimals(0.0));
        }
        if (ProductUtil.isValid(response.getLoyaltyPointExpiryDate())) {
          String dateFormat = "yyyy-MM-dd";

          DateFormat formatter = new SimpleDateFormat(dateFormat);
          Date date = null;
          try {
            date = (Date)formatter.parse(response.getLoyaltyPointExpiryDate());
          } catch (ParseException e) {
            e.printStackTrace();
          }
          SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy");
          String finalString = newFormat.format(date);

          tvLoyaltyExpireDate.setText("("+getString(R.string.sky_dollar_exp_on)+" "+finalString+")");
        } else {
          tvLoyaltyExpireDate.setText("");
        }
      } else {
        Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
            Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void updateCartCount(String count) {
    if (count == null && TextUtils.isEmpty(count)) {
      lyCartCount.setVisibility(View.INVISIBLE);
      tvCartCount.setVisibility(View.INVISIBLE);
    } else {
      if (!count.equalsIgnoreCase("0")) {
        lyCartCount.setVisibility(View.VISIBLE);
        tvCartCount.setVisibility(View.VISIBLE);
        tvCartCount.setText(count);
      } else {
        lyCartCount.setVisibility(View.INVISIBLE);
        tvCartCount.setVisibility(View.INVISIBLE);
      }
    }
    if (isExpire) {
      layoutCartIcon.setVisibility(View.INVISIBLE);
    }
  }

  private void showMembershipDialog(String message) {
    AlertDialog.Builder builder;
    builder = new AlertDialog.Builder(ProfileActivity.this);
    builder
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .show();
  }

  @Override
  public void goToInviteFriend(String code, String salutation, String firstname, String lastName,
                               String description,String imageBannerUrl) {

    //20200403 WIKI Toan Tran - update  image url
    InviteFriendActivity.startMe(this, code, salutation, firstname, lastName, description,imageBannerUrl);
  }

  @Override
  public void render(Throwable error) {
    AlertDialog.Builder builder;
    builder = new AlertDialog.Builder(getApplicationContext());
    builder
        .setMessage(errorMessageFactory.getErrorMessage(error))
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss())
        .show();
  }

  @Override
  public void renderFirstTimePopup(FirstTimePopup firstTimePopup) {
    if(firstTimePopup.getPopupItem()==null||firstTimePopup.getPopupItem().size()<=0){
      return;
    }
    frmPopup.setVisibility(View.VISIBLE);
    if (slideAdapter == null) {
      pagePopup.setBackgroundColor(getResources().getColor(R.color.black));
      slideAdapter = new SlideAdapter(getSupportFragmentManager(), this, firstTimePopup.getPopupItem());
      pagePopup.setAdapter(slideAdapter);
      fpi.setViewPager(pagePopup);
    }
    pagePopup.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      int pos = 0;
      int pos2 = 0;

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


      }

      @Override
      public void onPageSelected(int position) {
        for (int i = 0; i < slideAdapter.getCount(); i++) {
          if (slideAdapter.getItem(i) instanceof FragmentYoutube) {
            if (((FragmentYoutube) slideAdapter.getItem(i)).getYouTubePlayerView() != null) {
              ((FragmentYoutube) slideAdapter.getItem(i)).getYouTubePlayerView().pause();
            }
          } else if (slideAdapter.getItem(i) instanceof FragmentVideo) {
            if (((FragmentVideo) slideAdapter.getItem(i)).getVideoView() != null) {

              ((FragmentVideo) slideAdapter.getItem(i)).pauseVideo();

            }
          }
        }
        if (position != pos) {
          if (slideAdapter.getItem(pos) instanceof FragmentVimeo) {
            if (((FragmentVimeo) slideAdapter.getItem(pos)).getVimeoPlayer() != null) {

              ((FragmentVimeo) slideAdapter.getItem(pos)).getVimeoPlayer().pause();

            }
          }
        }
        pos = position;
        currentPos = position;
        exitFullscreen();
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }

    });
  }

  public void showFullscreen() {
    pagePopup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    fpi.setVisibility(View.GONE);
    imgClose.setVisibility(View.GONE);
  }

  public void exitFullscreen() {
    pagePopup.setLayoutParams(new LinearLayout.LayoutParams(MetricsUtils.convertDpToPixel(350f, this), MetricsUtils.convertDpToPixel(200f, this)));
    consPopup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    fpi.setVisibility(View.VISIBLE);
    imgClose.setVisibility(View.VISIBLE);
  }

  @Override
  public void renderFirstTimePopupFailed(Throwable throwable) {

  }

  private void updateViewForExpired() {
    layoutCartIcon.setVisibility(View.INVISIBLE);
    ivHumberger.setVisibility(View.INVISIBLE);

    tvManageDeliveryAddress.setTextColor(
        ContextCompat.getColor(getApplicationContext(), R.color.textColorGrey));
    tvManageDeliveryAddress.setClickable(false);

    tvFavourites.setTextColor(
        ContextCompat.getColor(getApplicationContext(), R.color.textColorGrey));
    tvFavourites.setClickable(false);

    tvMyOrders.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorGrey));
    tvMyOrders.setClickable(false);

    tvManageCreditCards.setTextColor(
        ContextCompat.getColor(getApplicationContext(), R.color.textColorGrey));
    tvManageCreditCards.setClickable(false);
  }

  private String getSalutation(UserDetailResponse response) {
    for (CustomAttribute attributes : response.getCustomAttributes()) {
      if (attributes.getAttributeCode().equals("salutation")) {
        return attributes.getValue() + ".";
      }
    }
    return "";
  }


  private String getMemberNumber(UserDetailResponse response) {
    for (CustomAttribute attributes : response.getCustomAttributes()) {
      if (attributes.getAttributeCode().equals("member_number")) {
        return attributes.getValue() ;
      }
    }
    return "";
  }

  private void startCropImage(Uri uri) {
    CropImage.activity(uri)
        //.setMinCropResultSize(500, 500)
        .setAspectRatio(500, 500)
        .setGuidelines(CropImageView.Guidelines.ON)
        .setMultiTouchEnabled(true)
        .start(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // handle result of CropImage
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == RESULT_OK) {
        //image type
        if (result.getOriginalUri().getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
          ContentResolver cr = getApplicationContext().getContentResolver();
          imageType = cr.getType(result.getOriginalUri());
        } else {
          String extension =
              MimeTypeMap.getFileExtensionFromUrl(result.getOriginalUri().toString());
          if (extension != null) {
            imageType =
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension).toLowerCase();
          }
        }

        //file Name
        imageName = result.getUri().getLastPathSegment();

        final InputStream imageStream;
        try {
          imageStream = getContentResolver().openInputStream(result.getUri());
          final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
          byte[] b = baos.toByteArray();
          uploadImageUrl = Base64.encodeToString(b, Base64.DEFAULT);

          presenter.uploadImageToServer(uploadImageUrl, imageType, imageName);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        Toast.makeText(this, "Upload Fail", Toast.LENGTH_SHORT).show();
      }
    }
  }

  @OnClick(R.id.ivCart_toolbar)
  public void onClickCart() {
    ShoppingCartActivity.start(this);
  }

  @OnClick(R.id.tvMyBooking)
  public void onClickMyBooking() {
    BookingsHistoryActivity.start(this);
  }

  @OnClick(R.id.tvMyReservation)
  public void onClickMyReservation() {
    MyResevationsActivity.startMe(this);
  }




  @Override
  public void showLoading() {
    if (isDestroyed()) return;
    progressDialog.show();
  }

  @Override
  public void hideLoading() {
    if (isDestroyed()) return;
    progressDialog.dismiss();
  }

}
