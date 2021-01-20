package com.skypremiuminternational.app.app.features.profile.my_sky_dollar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.j256.ormlite.stmt.query.In;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.booking.history.BookingsHistoryActivity;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.features.profile.ProfileViewState;
import com.skypremiuminternational.app.app.features.profile.billingaddress.ManageBillingAddressActivity;
import com.skypremiuminternational.app.app.features.profile.edit_profile.EditProfileActivity;
import com.skypremiuminternational.app.app.features.profile.invite_friend.InviteFriendActivity;
import com.skypremiuminternational.app.app.features.profile.manage_credit_card.ManageCreditCardActivity;
import com.skypremiuminternational.app.app.features.profile.manage_delivery_address.ManageDeliveryAddressActivity;
import com.skypremiuminternational.app.app.features.profile.my_favourites.MyFavouritesActivity;
import com.skypremiuminternational.app.app.features.profile.my_orders.MyOrderActivity;
import com.skypremiuminternational.app.app.features.signin.SignInActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.AspectRatioImageView;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.crm.SkyDollarHistoryResponse;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.util.ProductUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MySkyDollarActivity extends BaseActivity<MySkyDollarPresenter>
    implements MySkyDollarView<MySkyDollarPresenter> {

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitleToolbar;
  @BindView(R.id.rvSkyDollar)
  RecyclerView rvSkyDollar;




  @Inject
  ErrorMessageFactory errorMessageFactory;



  boolean isExpire = false;
  private ProgressDialog progressDialog;
  private String uploadImageUrl, imageName, imageType;

  SkyDollarAdapter adapter;

  public static void startMe(Context context) {
    Intent intent = new Intent(context,MySkyDollarActivity.class);
    context.startActivity(intent);
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_sky_dollar);
    ButterKnife.bind(this);

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    //init
    setTitleToolbar();
    setRecyclerView();  
    
    //===
    presenter.createCrmToken();
  }

  private void setTitleToolbar() {
    tvTitleToolbar.setText(R.string.my_sky_dollar);
  }

  private void setRecyclerView() {
    adapter = new SkyDollarAdapter();
    rvSkyDollar.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    rvSkyDollar.setAdapter(adapter);
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
  public void injectPresenter(MySkyDollarPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  protected void onResume() {
    super.onResume();
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

  @Override
  public void renderError(Throwable error) {
      Toast.makeText(this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
  }

  @Override
  public void renderCreatedToken(CrmTokenResponse value) {

    Toast.makeText(this,""+value.getAccessToken(),Toast.LENGTH_SHORT).show();
    presenter.getSkyDollarHistory(value.getAccessToken(),"1","50");
  }

  @Override
  public void renderSkyDollarHistory(SkyDollarHistoryResponse value) {
    adapter.setData(value.getRecords());
  }


  @OnClick(R.id.ivNavigation_toolbar)
  public void onClickBack(){
    finish();
  }

}
