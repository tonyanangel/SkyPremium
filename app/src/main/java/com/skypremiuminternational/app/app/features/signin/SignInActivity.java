package com.skypremiuminternational.app.app.features.signin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.andrognito.kerningview.KerningTextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onesignal.OSDevice;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalAPIClient;
import com.onesignal.OneSignalApiResponseHandler;
import com.onesignal.OneSignalNotificationManager;
import com.onesignal.OneSignalRemoteParams;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.forgot_password.ForgotPasswordActivity;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.features.skytextinputlayout.SkyTextInputLayout;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.PasswordTransformationMethod;
import com.skypremiuminternational.app.domain.exception.signin.InvalidEmailException;
import com.skypremiuminternational.app.domain.exception.signin.InvalidPasswordException;
import com.skypremiuminternational.app.domain.models.notification.AddtionalSettingData;
import com.skypremiuminternational.app.domain.models.notification.SettingDataRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import dagger.android.AndroidInjection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public class SignInActivity extends BaseActivity<SignInPresenter>
    implements SignInView<SignInPresenter> {

  //@BindView(R.id.llContent) LinearLayout llContent;
  @BindView(R.id.stilEmail)
  SkyTextInputLayout stilEmail;
  @BindView(R.id.stilPassword)
  SkyTextInputLayout stilPassword;
  @BindView(R.id.tilCountry)
  TextInputLayout tilCountry;
  @BindView(R.id.tvSignIn)
  KerningTextView tvSignIn;

  ImageView imageView;

  boolean isFirstLogin = false;


  @Inject
  ErrorMessageFactory errorMessageFactory;

  private ProgressDialog progressDialog;

  private ProgressDialog progressDialog2;

  private long back_pressed;
  private int groupId = -1;

  AddtionalSettingData additionData;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, SignInActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  public static void startMe(Context context, int groupId) {
    Intent intent = new Intent(context, SignInActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra("group_id", groupId);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signin);
    //set background image url
    imageView = (ImageView)findViewById(R.id.img);
    getIMGurl();



    ButterKnife.bind(this);
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage(getString(R.string.loading));

    progressDialog2  = new ProgressDialog(this);
    progressDialog2.setCanceledOnTouchOutside(false);

    if (getIntent() != null && getIntent().getExtras() != null) {
      groupId = getIntent().getExtras().getInt("group_id");
    }

    if (groupId != -1) {
      if (groupId == 14) {
        showAlertDialog(getString(R.string.terminate_membership));
      } else {
        showAlertDialog(getString(R.string.notice_need_more_permissions));
      }
    }
    if (tvSignIn != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        tvSignIn.setLetterSpacing(0.0f);
      } else {
        tvSignIn.setKerningFactor(0.0f);
      }
    }
    stilPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    stilPassword.setTransformationMethod(new PasswordTransformationMethod());
  }

  private void loadImagefromUrl(String url){
    Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.img_signin2)
            .into(imageView);

  }
  private void getIMGurl(){

      String url = BuildConfig.BASE_URL+BuildConfig.API+"/skypremium/dynamicimages/device/android";
      RequestQueue requestQueue = Volley.newRequestQueue(this);


      JsonObjectRequest objectRequest = new JsonObjectRequest(
              Request.Method.GET,
              url,
              null,
              new Response.Listener<JSONObject>() {
                  @Override
                  public void onResponse(JSONObject jsonObject) {
                    String response;
                    response =  jsonObject.toString();
                    try {
                      JSONObject filedata = new JSONObject(response);
                     String urlimg = (String)filedata.get("url");
                      loadImagefromUrl(urlimg);
                    } catch (JSONException e) {
                       e.printStackTrace();
                    }
                  }
              },
              new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError volleyError) {
                      Log.e("login image url error:" ,volleyError.toString());
                    Picasso.get()
                            .load(R.drawable.img_signin2)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.img_signin2)
                            .into(imageView);

                  }
              }
      );
      requestQueue.add(objectRequest);

  }

  @Override
  public void onBackPressed() {
    if (isTaskRoot()) {
      if (back_pressed + 2000 > System.currentTimeMillis()) {
        finish();
      } else {
        Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
      }
      back_pressed = System.currentTimeMillis();
    } else {
      finish();
    }
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
  public void injectPresenter(SignInPresenter presenter) {
    super.injectPresenter(presenter);
  }

  /*@OnClick(R.id.btnShowPassword) void onShowHidePassword() {
    passwordIsVisible = !passwordIsVisible;
    if (passwordIsVisible) {
      btnShowPassword.setText("Hide Password");
      tilPassword.getEditText().setTransformationMethod(null);
    } else {
      btnShowPassword.setText("Show Password");
      tilPassword.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    tilPassword.getEditText().setSelection(tilPassword.getEditText().getText().length());
  }*/

  @OnClick(R.id.tvForgotPassword)
  void onClickForgotPassword() {
    ForgotPasswordActivity.startMe(this);
  }

  @OnClick(R.id.vCountry)
  void onClickCountry() {
    final String[] countries = {"Singapore", "China", "Myanmar"};
    new AlertDialog.Builder(this).setTitle("Choose country")
        .setItems(countries, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            tilCountry.getEditText().setText(countries[item]);
          }
        })
        .show();
  }

  @OnClick(R.id.tvSignIn)
  void onSignIn() {
    stilEmail.hideError();
    stilPassword.hideError();
    presenter.onSignIn(stilEmail.getText(), stilPassword.getText());
    //presenter.onSignIn("ryan.villanueva@monimedia.com", "test1234");
    //presenter.getUserDatasFromApi();
  }

  @Override
  public void render(SignInViewState viewState) {
    if (viewState.isLoading()) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        if (viewState.groupId() == Constants.GROUP_ID_ACTIVE) {
          presenter.sendExtenalUserId();
         /* presenter.getSettingFromAPI();
          presenter.updateMappVersion();
          presenter.checkFirstLogin();*/
          //presenter.getDeviceInfo();

          OneSignal.setSubscription(true);
          finish();
          LandingActivity.startMe(this);
        } else if (viewState.groupId() == Constants.GROUP_ID_EXPIRED || viewState.groupId() == Constants.GROUP_ID_AWAITING_RENEWAL) {
          finish();
          ProfileActivity.startMe(this);
        } else if (viewState.groupId() == Constants.GROUP_ID_TERMINATED) {
          showAlertDialog(getString(R.string.terminate_membership));
        } else {
          showAlertDialog(getString(R.string.notice_need_more_permissions));
        }
      } else {
        progressDialog.dismiss();
        stilEmail.showError();
        stilPassword.showError();
        if (viewState.error() instanceof InvalidEmailException) {
          Toast.makeText(this, "Invalid Email!", Toast.LENGTH_SHORT).show();
        } else if (viewState.error() instanceof InvalidPasswordException) {
          Toast.makeText(this, "Invalid Password!", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
              Toast.LENGTH_SHORT).show();
        }
      }
    }
  }

  private void showAlertDialog(String message) {
    AlertDialog.Builder builder;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      builder = new AlertDialog.Builder(SignInActivity.this, android.R.style.Theme_Material_Dialog_Alert);
    } else {
      builder = new AlertDialog.Builder(SignInActivity.this);
    }
    builder
        .setMessage(message)
        .setCancelable(false).setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
        .show();
  }

  private void dateDifference(String expiredDate) {
    String expireDate = expiredDate;
    Date date = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
    String currentDateFormat = dateFormat.format(date);
    try {
      Date dateCurrent = dateFormat.parse(currentDateFormat);
      Date dateExpired = dateFormat.parse(expireDate);
      if (dateCurrent.before(dateExpired)) {
        finish();
        LandingActivity.startMe(this);
      } else {
        finish();
        ProfileActivity.startMe(this);
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /*@Override public void render(SignInViewState viewState) {

    if (viewState.isLoading()) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        if (viewState.message().equals("SuccessGettingData")) {

        } else {
          int date = Integer.parseInt("-20");
          if(date <= 0) {
            finish();
            ProfileActivity.startMe(this);
          } else {
            finish();
            LandingActivity.startMe(this);
          }
        }
      } else {
        progressDialog.dismiss();
        if (viewState.isSuccess()) {
          finish();
          LandingActivity.startMe(this);
        } else {
          stilEmail.showError();
          stilPassword.showError();
          if (viewState.error() instanceof InvalidEmailException) {
            Toast.makeText(this, "Invalid Email!", Toast.LENGTH_SHORT).show();
          } else if (viewState.error() instanceof InvalidPasswordException) {
            Toast.makeText(this, "Invalid Password!", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
                Toast.LENGTH_SHORT).show();
          }
        }
      }
    }
  }*/

  @Override
  public void renderSetting(SettingNotificationResponse response) {
    additionData = new AddtionalSettingData();
    additionData = response.getData().toAdditionData();


    presenter.changeSetting(Constants.ESTORE_DEALS,additionData.isEstoreDeals());
    presenter.changeSetting(Constants.MARKETING_ALERT,additionData.isMarketingAlerts());
    presenter.changeSetting(Constants.SHOPPING_DEALS,additionData.isShoppingDeals());
    presenter.changeSetting(Constants.TRAVEL_DEALS,additionData.isTravelDeals());
    presenter.changeSetting(Constants.WELLNESS_DEALS,additionData.isWellnessDeals());
    presenter.changeSetting(Constants.WINE_DINE_DEALS,additionData.isWineAndDineDeals());
    presenter.changeSetting(Constants.EVENT_INVITES_AND_UP,additionData.isEventInvitesAndUpdate());

    //presenter.checkFirstLogin();
  }

  private void sendTagsOneS(boolean isFirstLogin) {
    if(!isFirstLogin){
      SettingDataRequest settingData = new SettingDataRequest();

      settingData.setEstore(additionData.isEstoreDeals());
      settingData.setMarketing(additionData.isMarketingAlerts());
      settingData.setShopping(additionData.isShoppingDeals());
      settingData.setTravel(additionData.isTravelDeals());
      settingData.setWellness(additionData.isWellnessDeals());
      settingData.setWinedine(additionData.isWineAndDineDeals());
      settingData.setEvent(additionData.isEventInvitesAndUpdate());


    }

  }

  @Override
  public void renderGotoLanding() {
    hideDialogLoading();
    finish();
    LandingActivity.startMe(this,false);
  }

  @Override
  public void showDialogLoading(String msg) {
    progressDialog2.setMessage(msg);
    progressDialog2.show();
  }

  @Override
  public void hideDialogLoading() {
    if (progressDialog2 != null && progressDialog2.isShowing()) {
      progressDialog2.dismiss();
    }
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }

  }

  @Override
  public void renderGotoLanding(boolean isFLogin) {

    hideDialogLoading();
    finish();
    LandingActivity.startMe(this,isFLogin);
  }
}
