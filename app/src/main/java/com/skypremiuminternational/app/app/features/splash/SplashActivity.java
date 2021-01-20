package com.skypremiuminternational.app.app.features.splash;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalNotificationManager;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.notifications.NotificationActivity;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.features.signin.SignInActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DataUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.utils.listener.MovePillarListener;
import com.skypremiuminternational.app.domain.exception.signin.UserNotFoundException;
import com.skypremiuminternational.app.domain.models.notification.AddtionalSettingData;
import com.skypremiuminternational.app.domain.models.notification.SettingDataRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;
import com.skypremiuminternational.app.push_notification.ChangeNotificationListener;
import com.skypremiuminternational.app.push_notification.SkyNotificationReceivedHandler;

import java.util.HashMap;

import dagger.android.AndroidInjection;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.App.getAppContext;
import static com.skypremiuminternational.app.app.App.isSendingNotification;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class SplashActivity extends BaseActivity<SplashPresenter>
    implements SplashView<SplashPresenter> , MovePillarListener, ChangeNotificationListener {

  @BindView(R.id.img)
  ImageView img;
  private SplashViewState viewState;
  boolean isFistLogin = false;
  AddtionalSettingData additionData;

  public static void startMe(Context context) {
    Intent intent = new Intent(context, NotificationActivity.class);
    context.startActivity(intent);
  }
  public static Intent getIntentPush(Context activity) {
    Intent intent = new Intent(activity, SplashActivity.class);
    return intent;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    App.isInApp = false;
    checkVersion();
    Animation animFadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
    img.startAnimation(animFadein);
    presenter.intitOneSignal(getAppContext(),this,this);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        //presenter.checkUserLoggedIn();
        presenter.getCategories();

        /*if(!isSendingNotification){
        presenter.saveNotificarion();
        }*/

        //goout();
      }
    }, 1000);
  }



  private void checkVersion() {
    DataUtils dataUtils = new DataUtils(this);
    final String APPV ="AppVersion";
    String versionName = "v" + BuildConfig.VERSION_NAME;
    if(dataUtils.isExist(APPV)){
      String oldVersion = dataUtils.readObject(APPV);
      oldVersion = oldVersion.substring(1,oldVersion.length()-1);
      if(!oldVersion.equalsIgnoreCase(versionName)){
        PreferenceUtils preferenceUtils = new PreferenceUtils(this);
        preferenceUtils.clearAllPrefs();
        DataUtils.deleteAllData(this);

        dataUtils.writeObject(APPV,versionName);
      }
    }else{
      dataUtils.writeObject(APPV,versionName);
    }



  }
  @Inject
  @Override
  public void injectPresenter(SplashPresenter presenter) {
    super.injectPresenter(presenter);
  }

  void goout() {
    finish();
    SignInActivity.startMe(this);
  }

  @Override
  public void onClickedCancelUpdate() {
    if (viewState != null) {
      render(viewState);
    }
  }

  @Override
  public void render(SplashViewState viewState) {
    this.viewState = viewState;
    if (viewState.isSuccess()) {
      if (!isUpdateNoticeIsShowing()) {

        if (viewState.isUserLoggedIn()) {
          int groupId = viewState.groupId();
          if (groupId == Constants.GROUP_ID_EXPIRED || groupId == Constants.GROUP_ID_AWAITING_RENEWAL) {
            finish();
            ProfileActivity.startMe(this);
          } else if (groupId == Constants.GROUP_ID_ACTIVE) {

           /* presenter.sendExtenalUserId();
            presenter.getSettingFromAPI();
            presenter.updateMappVersion();
            presenter.checkFirstLogin();*/
            //presenter.getDeviceInfo();

            OneSignal.setSubscription(true);
            if(presenter.isOpenNotification()){
              presenter.saveOpenNotification(false);
              finish();
              NotificationActivity.startMe(this,"notification");
            }else {
              finish();
              LandingActivity.startMe(this);
            }
          } else {
            finish();
            SignInActivity.startMe(this, groupId);
          }
        } else {
          finish();
          SignInActivity.startMe(this);
        }

      }
    } else {
      if (viewState.error() instanceof UserNotFoundException) {
        if (!isUpdateNoticeIsShowing()) {
          finish();
          SignInActivity.startMe(this);
        }
      } else {



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connection error");
        builder.setMessage(
            "Unable to connect with the server. Check your internet connection and try again.");
        builder.setPositiveButton("Try again", null);
        builder.setOnDismissListener(dialog -> presenter.getCategories());
        builder.show();
      }
    }
  }


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

  private void sendTagsOneS(boolean isFistLogin) {
    if(!isFistLogin){
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
    finish();
    LandingActivity.startMe(this);
  }


  @Override
  public void renderGotoLanding(boolean isFLogin) {
    finish();
    LandingActivity.startMe(this,isFLogin);
  }

  @Override
  public void movePillar(String position) {

  }

  @Override
  public void movePillar(String pillar, String filter, String from, String sortBy) {

  }


  @Override
  public void renderNumberNotification() {

  }

  @Override
  public void renderListNotification() {

  }
}
