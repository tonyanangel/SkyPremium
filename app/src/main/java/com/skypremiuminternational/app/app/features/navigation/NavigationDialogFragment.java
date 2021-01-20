package com.skypremiuminternational.app.app.features.navigation;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.EstoreActivity;
import com.skypremiuminternational.app.app.features.faq.FaqActivity;
import com.skypremiuminternational.app.app.features.info.InfoActivity;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.memership_services.MembershipActivity;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.features.profile.my_favourites.MyFavouritesActivity;
import com.skypremiuminternational.app.app.features.signin.SignInActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.RxBus;
import com.skypremiuminternational.app.domain.models.navigation.NavigationEvent;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import dagger.android.support.AndroidSupportInjection;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import retrofit2.HttpException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class NavigationDialogFragment extends BaseDialogFragment<NavigationPresenter>
    implements NavigationView<NavigationPresenter> {
  // 20200529 WIKI - Toan Tran - disable code for backup
  /*private static final String LINK_ABOUT = "https://www.skypremium.com.sg/sg/about?source=mobile";
  private static final String LINK_BUZZ = "https://www.skypremium.com.sg/sg/buzz?source=mobile";
  private static final String LINK_LEGAL = "https://www.skypremium.com.sg/sg/legal?source=mobile";
  private static final String LINK_MEMBER_BENEFITS = "https://www.skypremium.com.sg/sg/sky-premium-exclusive-privileges?source=mobile";
  private static final String LINK_HOW_IT_WORKS = "https://www.skypremium.com.sg/sg/how-it-works?source=mobile";*/

  public static final String LINK_ABOUT = BuildConfig.CWEB_DOMAIN+"/sg/about?source=mobile";
  public static final String LINK_BUZZ = BuildConfig.CWEB_DOMAIN+"/sg/buzz?source=mobile";
  public static final String LINK_LEGAL = BuildConfig.CWEB_DOMAIN+"/sg/legal?source=mobile";
  public static final String LINK_MEMBER_BENEFITS = BuildConfig.CWEB_DOMAIN+"/sg/sky-premium-exclusive-privileges?source=mobile";
  public static final String LINK_HOW_IT_WORKS = BuildConfig.CWEB_DOMAIN+"/sg/how-it-works?source=mobile";

  @BindView(R.id.tvVersion)
  TextView tvVersion;
  @BindView(R.id.tvName)
  TextView tvName;
  @BindView(R.id.tvLogOut)
  TextView tvLogOut;
  @BindView(R.id.imgAvatar)
  ImageView imgAvatar;
  @BindView(R.id.tvPoints)
  TextView txtAmtLoyalty;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private String name, uploadImageUrl, imageName, imageType;
  private ProgressDialog progressDialog;

  public static NavigationDialogFragment newInstance() {
    NavigationDialogFragment fragment = new NavigationDialogFragment();
    return fragment;
  }

  public static NavigationDialogFragment newInstance(String name) {
    NavigationDialogFragment fragment = new NavigationDialogFragment();
    fragment.name = name;
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
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

    if (name != null || !TextUtils.isEmpty(name)) {
      tvName.setText(name);
    }

    String versionName = "v" + BuildConfig.VERSION_NAME + "." + BuildConfig.BUILD_TIME
        + BuildConfig.APP_TYPE;
    tvVersion.setText(versionName);
    presenter.getUserDetailFromApi();
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
  }

  @OnClick(R.id.imgClose)
  void onClickContinue() {
    dismiss();
  }

  @OnClick(R.id.tvHome)
  void onClickHome() {
    navigateToLandingActivity(Constants.DEEP_LINK_LANDING_HOME);
  }

  @OnClick(R.id.tvProfile)
  void onClickProfile() {
    dismiss();
    if (getActivity().getClass()
        .getSimpleName()
        .equalsIgnoreCase(LandingActivity.class.getSimpleName())) {
      ProfileActivity.startMe(getContext());
    } else if (!getActivity().getClass()
        .getSimpleName()
        .equalsIgnoreCase(ProfileActivity.class.getSimpleName())) {
      getActivity().finish();
      ProfileActivity.startMe(getContext());
    }
  }

  @OnClick(R.id.tvFavourites)
  void onClickFavourites() {
    dismiss();
    if (!(getActivity() instanceof MyFavouritesActivity)) {
      MyFavouritesActivity.startMe(getActivity());
    }
  }

  @OnClick(R.id.tvTravel)
  void onClickTravel() {
    navigateToLandingActivity(Constants.DEEP_LINK_LANDING_TRAVEL);
  }

  @OnClick(R.id.tvWine)
  void onClickWine() {
    navigateToLandingActivity(Constants.DEEP_LINK_LANDING_WINE);
  }

  @OnClick(R.id.tvShopping)
  void onClickShopping() {
    navigateToLandingActivity(Constants.DEEP_LINK_LANDING_SHOPPING);
  }

  @OnClick(R.id.tvWellness)
  void onClickWellness() {
    navigateToLandingActivity(Constants.DEEP_LINK_LANDING_WELLNESS);
  }

  @OnClick(R.id.tvFaq)
  void onClickFaq() {
    dismiss();
    if (getActivity().getClass()
        .getSimpleName()
        .equalsIgnoreCase(LandingActivity.class.getSimpleName())) {
      FaqActivity.startMe(getContext());
    } else if (!getActivity().getClass()
        .getSimpleName()
        .equalsIgnoreCase(FaqActivity.class.getSimpleName())) {
      getActivity().finish();
      FaqActivity.startMe(getContext());
    }
  }

  @OnClick(R.id.tvAbout)
  void onClickAbout() {
    showInfo(LINK_ABOUT, "About");
  }

  @OnClick(R.id.tvBuzz)
  void onClickBuzz() {
    showInfo(LINK_BUZZ, "Buzz");
  }

  @OnClick(R.id.tvLegal)
  void onClickLegal() {
    showInfo(LINK_LEGAL, "Legal");
  }

  @OnClick(R.id.tvMemberBenefits)
  void onClickMemberBenefits() {
    showInfo(LINK_MEMBER_BENEFITS, "Member Benefits");
  }

  @OnClick(R.id.tvHowItWorks)
  void onClickHowItWorks() {
    showInfo(LINK_HOW_IT_WORKS, "How it works");
  }

  @OnClick(R.id.tvMembership)
  void onClickMembership() {
    dismiss();
    if (getActivity().getClass()
        .getSimpleName()
        .equalsIgnoreCase(LandingActivity.class.getSimpleName())) {
      MembershipActivity.startMe(getContext());
    } else if (!getActivity().getClass()
        .getSimpleName()
        .equalsIgnoreCase(MembershipActivity.class.getSimpleName())) {
      getActivity().finish();
      MembershipActivity.startMe(getContext());
    }
  }

  @OnClick(R.id.tvLogOut)
  void onClickLogout() {
    presenter.onLogout();
    NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    manager.cancelAll();
    dismiss();
    getActivity().finish();
    Intent mIntent = new Intent(getActivity(), SignInActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(mIntent);
  }

  @OnClick(R.id.imgFacebook)
  void onClickFacebook() {
    String url = "https://www.facebook.com/SkyPremiumSG/";
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(url));
    startActivity(i);
  }

  @OnClick(R.id.imgInstagram)
  void onClickInstagram() {
    String url = "https://www.instagram.com/skypremiumsg/";
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(url));
    startActivity(i);
  }

  @OnClick(R.id.imgAvatar)
  public void onClickAvatar() {
    startCropImage(null);
  }

  private void startCropImage(Uri uri) {
    CropImage.activity(uri)
        //.setMinCropResultSize(500, 500)
        .setAspectRatio(500, 500)
        .setGuidelines(CropImageView.Guidelines.ON)
        .setMultiTouchEnabled(true)
        .start(getContext(), this);
  }

  private void navigateToLandingActivity(int deeplink) {
    dismiss();
    if (!getActivity().getClass()
        .getSimpleName()
        .equalsIgnoreCase(LandingActivity.class.getSimpleName())) {
      getActivity().finish();
      LandingActivity.startMe(getActivity(), deeplink);
    }
    RxBus.get().post(new NavigationEvent(deeplink));
  }

  @Inject
  @Override
  public void injectPresenter(NavigationPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_fragment_navigation;
  }

  @Override
  public void render(NavigationViewState viewState) {
    if (viewState.isLoading()) {
      if (viewState.txtLoading() != null) {
        progressDialog.setMessage(getString(R.string.uploading_profile_image));

        progressDialog.show();
      } else {
        //progressDialog.setMessage(getString(R.string.getting_user_detail));
      }
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        UserDetailResponse response = viewState.message();
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
        if (viewState.avatarLink() != null) {
          RequestOptions requestOptions = new RequestOptions();
          requestOptions.placeholder(R.drawable.human);
          requestOptions.dontAnimate();
          requestOptions.centerCrop();
          requestOptions.error(R.drawable.human).diskCacheStrategy(DiskCacheStrategy.NONE);
          Glide.with(getActivity())
              .asBitmap()
              .load(viewState.avatarLink())
              .apply(requestOptions)
              .into(new BitmapImageViewTarget(imgAvatar) {
                @Override
                protected void setResource(Bitmap resource) {
                  if (getActivity() != null) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imgAvatar.setImageDrawable(circularBitmapDrawable);
                  }
                }
              });
        }

        //loyalty amount
        if (viewState.amtLoyalty() != null && !TextUtils.isEmpty(viewState.amtLoyalty())) {
          txtAmtLoyalty.setText(
              DecimalUtil.roundTowDecimalWithoutBrace(Double.parseDouble(viewState.amtLoyalty())));
        } else {
          txtAmtLoyalty.setText(DecimalUtil.roundTowDecimalWithoutBrace(0.0));
        }
      } else {
        Toast.makeText(getContext(), errorMessageFactory.getErrorMessage(viewState.error()),
            Toast.LENGTH_SHORT).show();
        if (viewState.error() instanceof HttpException) {
          HttpException httpException = (HttpException) viewState.error();
          if (httpException.code() == 401) {
            onClickLogout();
          }
        }
      }
    }
  }

  private String getSalutation(UserDetailResponse response) {
    for (CustomAttribute attributes : response.getCustomAttributes()) {
      if (attributes.getAttributeCode().equals("salutation")) {
        return attributes.getValue() + ".";
      }
    }
    return "";
  }

  private void showInfo(String link, String title) {

    if (getActivity().getClass()
        .getSimpleName()
        .equalsIgnoreCase(LandingActivity.class.getSimpleName())) {
      InfoActivity.start(getActivity(), link, title);
    } else {
      getActivity().finish();
      InfoActivity.start(getActivity(), link, title);
    }
    dismiss();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // handle result of CropImage
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == RESULT_OK) {
        //image type
        if (result.getOriginalUri().getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
          ContentResolver cr = getActivity().getContentResolver();
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
          imageStream = getActivity().getContentResolver().openInputStream(result.getUri());
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
        Toast.makeText(getActivity(), "Upload Fail", Toast.LENGTH_SHORT).show();
      }
    }
  }

  @OnClick(R.id.tvEStore)
  void onClickEstore() {
    if (getActivity() instanceof EstoreActivity) {
      dismiss();
    } else {
      EstoreActivity.startMe(getContext());
    }
  }
}
