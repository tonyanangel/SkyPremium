package com.skypremiuminternational.app.app.features.notification_setting;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onesignal.OneSignal;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.domain.models.notification.AddtionalSettingData;
import com.skypremiuminternational.app.domain.models.notification.SettingDataRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;
import com.skypremiuminternational.app.push_notification.SkyNotificationReceivedHandler;

import org.json.JSONObject;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class NotificationSettingActivity extends BaseActivity<NotificationSettingPresenter>
    implements NotificationSettingView<NotificationSettingPresenter> {


  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitleToolbar;

  @BindView(R.id.swt_estore_deals)
  Switch swtEstoreDeals;
  @BindView(R.id.swt_wellness_deals)
  Switch swtWellnessDeals;
  @BindView(R.id.swt_wine_dine_deals)
  Switch swtWineDineDeals;
  @BindView(R.id.swt_travel_deals)
  Switch swtTravelDeals;
  @BindView(R.id.swt_shopping_deals)
  Switch swtShoppingDeals;
  @BindView(R.id.swt_maketing_alert)
  Switch swtMaketingAlert;
  @BindView(R.id.swt_event_invites_and_update)
  Switch swtEventInvitesAndIpdate;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;

  ProgressDialog progressDialog;

  AddtionalSettingData additionData ;
  public static void startMe(Context context) {
    Intent intent = new Intent(context, NotificationSettingActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_notification_setting);
    ButterKnife.bind(this);

    progressDialog = new ProgressDialog(this);
    progressDialog.setCanceledOnTouchOutside(false);


   // intitOneSignal();
    setToolbarTile(getResources().getString(R.string.notifications));
    initStateSetting();
    presenter.getSettingFromAPI();
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

 /* private void intitOneSignal() {
    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

    OneSignal.startInit(this)
        .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
        .unsubscribeWhenNotificationsAreDisabled(true)
        .setNotificationReceivedHandler(new SkyNotificationReceivedHandler(this,
            presenter.getPreferenceUtils(),presenter.getDataManager()))
        .init();

  }*/

  private void initStateSetting() {
    Map<String, Boolean> mapSetting = presenter.getSetting();

    for(String key : mapSetting.keySet()){
      switch (key) {
        case Constants.ESTORE_DEALS     :
          swtEstoreDeals.setChecked(mapSetting.get(key));
          break;
        case Constants.MARKETING_ALERT  :
          swtMaketingAlert.setChecked(mapSetting.get(key));
          break;
        case Constants.SHOPPING_DEALS  :
          swtShoppingDeals.setChecked(mapSetting.get(key));
          break;
        case Constants.TRAVEL_DEALS     :
          swtTravelDeals.setChecked(mapSetting.get(key));
          break;
        case Constants.WELLNESS_DEALS     :
          swtWellnessDeals.setChecked(mapSetting.get(key));
          break;
        case Constants.WINE_DINE_DEALS      :
          swtWineDineDeals.setChecked(mapSetting.get(key));
          break;
        case Constants.EVENT_INVITES_AND_UP :
          swtEventInvitesAndIpdate.setChecked(mapSetting.get(key));
          break;
      }
    }
  }

  private void setToolbarTile(String notifications) {
    tvTitleToolbar.setText(notifications);
  }

  @Inject
  @Override
  public void injectPresenter(NotificationSettingPresenter presenter) {
    super.injectPresenter(presenter);
  }


  @OnCheckedChanged({R.id.swt_estore_deals, R.id.swt_maketing_alert, R.id.swt_shopping_deals,
      R.id.swt_travel_deals, R.id.swt_wellness_deals, R.id.swt_wine_dine_deals,
      R.id.swt_event_invites_and_update})
  void onCheckChange(CompoundButton view, boolean value) {
    switch (view.getId()) {
      case R.id.swt_estore_deals:
        presenter.changeSetting(Constants.ESTORE_DEALS,value);
        break;
      case R.id.swt_maketing_alert:
        presenter.changeSetting(Constants.MARKETING_ALERT,value);
        break;
      case R.id.swt_shopping_deals:
        presenter.changeSetting(Constants.SHOPPING_DEALS,value);
        break;
      case R.id.swt_travel_deals:
        presenter.changeSetting(Constants.TRAVEL_DEALS,value);
        break;
      case R.id.swt_wellness_deals:
        presenter.changeSetting(Constants.WELLNESS_DEALS,value);
        break;
      case R.id.swt_wine_dine_deals:
        presenter.changeSetting(Constants.WINE_DINE_DEALS,value);
        break;
      case R.id.swt_event_invites_and_update:
        presenter.changeSetting(Constants.EVENT_INVITES_AND_UP,value);
        break;
    }
  }


  @OnClick(R.id.ivNavigation_toolbar)
  void onClickBack() {
    presenter.updateNotification();
  }

  @OnClick(R.id.ivCart_toolbar)
  public void onClickCartCount() {
    ShoppingCartActivity.start(this);
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
  }

  @Override
  public void showDialogLoading() {
    progressDialog.setMessage(getString(R.string.loading));
    progressDialog.show();
  }

  @Override
  public void hideDialogLoading() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
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
    initStateSetting();
  }


  @Override
  public void onBackPressed() {
    presenter.updateNotification();

  }

  private void sendTagsOneS() {
/*

    SettingDataRequest settingData = presenter.getSettingRequest();


    String tagsJson = new Gson().toJson(settingData);
    Log.d("SETTING_TAG" , "Content :" +tagsJson);
    OneSignal.deleteTag("test_tag");
    OneSignal.sendTags(tagsJson);*/

  }

  @Override
  public void renderSaveSetting() {
    finish();
  }
}
