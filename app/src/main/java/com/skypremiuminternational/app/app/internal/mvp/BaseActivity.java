/*******************************************************************************
 * Copyright (c) 2017 Francisco Gonzalez-Armijo Riádigos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.skypremiuminternational.app.app.internal.mvp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.DataUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.utils.RxBus;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.impl.InternalStorageManagerImpl;
import com.skypremiuminternational.app.domain.exception.signin.SessionTimeoutException;

import org.jsoup.helper.DataUtil;

import java.net.HttpURLConnection;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity
    implements Viewable<T> {

  protected T presenter;
  private boolean isUpdateNoticeIsShowing = false;

  public boolean isUpdateNoticeIsShowing() {
    return isUpdateNoticeIsShowing;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(@StringRes int resource) {
    setTitle(getString(resource));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(@NonNull CharSequence msg) {
    setTitle(msg);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onStart() {
    super.onStart();
    getPresenter().onStart();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getPresenter().attachView(this);
    RxBus.get().register(this);
    App.isOpenningApp = true;
//    Log.e("FIREBASE TOKEN", CommonUtils.getFirebaseToken());
  }

  public ViewGroup getRootLayout() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onStop() {
    getPresenter().onStop();
    super.onStop();
    Log.d("ON_CIRCLE", "onStop");
  }

  @Override
  protected void onResume() {
    super.onResume();
    getPresenter().onResume();

    Log.d("ON_CIRCLE", "onResume");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onDestroy() {
    presenter = null;
    RxBus.get().unregister(this);
    super.onDestroy();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void displayError(String message) {
    View rootContent = ButterKnife.findById(this, android.R.id.content);
    Snackbar.make(rootContent, message, Snackbar.LENGTH_LONG).show();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void displayError(int messageId) {
    displayError(getString(messageId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showLoading() {
    // no-op by default
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hideLoading() {
    // no-op by default
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T getPresenter() {
    return presenter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void injectPresenter(T presenter) {
    this.presenter = presenter;
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }

  @Subscribe
  public void onSessionTimeout(SessionTimeoutException e) {
  }

  @Override
  public void showForceUpdateNotice(String forceUpdateMessage, String url) {
    if (isDestroyed()) return;
    isUpdateNoticeIsShowing = true;
    new AlertDialog.Builder(this).setMessage(forceUpdateMessage)
        .setCancelable(false)
        .setPositiveButton(R.string.label_update_now, (dialog, which) -> openPlayStoreLink())
        .show();
  }

  private void openPlayStoreLink() {
    final String appPackageName =
        getPackageName(); // getPackageName() from Context or Activity object
    try {
      startActivity(
          new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
    } catch (android.content.ActivityNotFoundException anfe) {
      startActivity(new Intent(Intent.ACTION_VIEW,
          Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
    }
  }

  @Override
  public void showUpdateNotice(String updateMessage, String url) {
    if (isDestroyed()) return;
    isUpdateNoticeIsShowing = true;
    new AlertDialog.Builder(this).setMessage(updateMessage)
        .setCancelable(false)
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> openPlayStoreLink())
        .setNegativeButton(R.string.btn_label_cancel, (dialog, which) -> {
          dialog.dismiss();
          isUpdateNoticeIsShowing = false;
          onClickedCancelUpdate();
        })
        .show();
  }

  public void onClickedCancelUpdate() {

  }

  public void hideKeyboard() {
    View view = this.getCurrentFocus();
    if (view != null) {
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }


  @Override
  protected void onRestart() {
    super.onRestart();
     Log.d("ON_CIRCLE", "onRestart");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d("ON_CIRCLE", "onPause");
  }
}
