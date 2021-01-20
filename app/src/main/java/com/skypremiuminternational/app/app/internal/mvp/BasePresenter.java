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

import androidx.annotation.NonNull;

import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.Version;

import rx.SingleSubscriber;
import rx.Subscription;
import timber.log.Timber;

public abstract class BasePresenter<T extends Viewable> implements Presentable<T> {

  private final GetAppVersion getAppVersion;
  private final InternalStorageManager internalStorageManager;
  private T viewable;

  public BasePresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager) {
    this.getAppVersion = getAppVersion;
    this.internalStorageManager = internalStorageManager;
  }

  protected void attachLoading(UseCase... useCases) {
    for (UseCase useCase : useCases) {
      useCase.onSubscribe(() -> getView().showLoading());
      useCase.onTerminate(() -> getView().hideLoading());
    }
  }

  @Override
  public void onStart() {
    // No-op by default
  }

  @Override
  public void onViewCreated() {
    // No-op by default
  }

  @Override
  public void onResume() {
    add(getAppVersion.execute(new SingleSubscriber<Version.AppVersion>() {
      @Override
      public void onSuccess(Version.AppVersion value) {
        if (shouldForceUpdate(value)) {
          getView().showForceUpdateNotice(value.force_update_message, value.url);
        } else if (shouldUpdate(value)) {
          internalStorageManager.savePromptedVersion(value.current_version);
          getView().showUpdateNotice(value.update_message, value.url);
        }
      }

      @Override
      public void onError(Throwable error) {
        Timber.e(error);
      }
    }));
  }

  private boolean shouldUpdate(Version.AppVersion value) {
    int localVersion[] = toIntArray(BuildConfig.VERSION_NAME.split("\\."));
    int remoteVersion[] = toIntArray(value.current_version.split("\\."));
    int promptedVersion[] = toIntArray(internalStorageManager.getPromptedVersion().split("\\."));
    if (compare(remoteVersion, localVersion) > 0 && compare(remoteVersion, promptedVersion) > 0) {
      return true;
    }
    return false;
  }

  private int[] toIntArray(String[] version) {
    int[] intVersion = new int[version.length];
    for (int i = 0; i < intVersion.length; i++) {
      intVersion[i] = Integer.parseInt(version[i]);
    }
    return intVersion;
  }

  /**
   * Compare app version of this format [0.0.0]
   *
   * @return Either of 1(first version is greater),0(versions are the same) or -1(first version is
   * smaller or versions are in invalid format)
   */
  private int compare(int[] firstVersion, int[] secondVersion) {
    if (firstVersion.length != 3) return -1;
    if (secondVersion.length != 3) return -1;

    if (firstVersion[0] == secondVersion[0]
        && firstVersion[1] == secondVersion[1]
        && firstVersion[2] == secondVersion[2]) {
      return 0;
    }

    if (firstVersion[0] > secondVersion[0]) return 1;
    if (firstVersion[0] < secondVersion[0]) return -1;

    if (firstVersion[1] > secondVersion[1]) return 1;
    if (firstVersion[1] < secondVersion[1]) return -1;

    if (firstVersion[2] > secondVersion[2]) return 1;
    if (firstVersion[2] < secondVersion[2]) return -1;

    return 0;
  }

  private boolean shouldForceUpdate(Version.AppVersion value) {
    int localVersion[] = toIntArray(BuildConfig.VERSION_NAME.split("\\."));
    int forceupdateVersion[] = toIntArray(value.force_update_version.split("\\."));

    return compare(forceupdateVersion, localVersion) > 0;
  }

  @Override
  public void onPause() {
    // No-op by default
  }

  @Override
  public void onStop() {
    // No-op by default
  }

  public abstract void add(Subscription subscription);

  @Override
  public void attachView(@NonNull T viewable) {
    this.viewable = viewable;
  }

  @Override
  public void detachView() {
    this.viewable = null;
  }

  @Override
  public T getView() {
    return viewable;
  }

  public void sendNotification(){

  };

}
