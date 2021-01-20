package com.skypremiuminternational.app.app.features.profile.my_sky_dollar;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.crm.CreateCrmToken;
import com.skypremiuminternational.app.domain.interactor.crm.GetSkyDollarHistory;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetail;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.crm.SkyDollarHistoryResponse;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MySkyDollarPresenter extends BasePresenter<MySkyDollarView> {


  private final CreateCrmToken createCrmToken;
  private final GetSkyDollarHistory getSkyDollarHistory;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public MySkyDollarPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                          GetUserDetail getUserDetail , CreateCrmToken createCrmToken,
                          GetSkyDollarHistory getSkyDollarHistory) {
    super(getAppVersion, internalStorageManager);

    this.createCrmToken = createCrmToken;
    this.getSkyDollarHistory = getSkyDollarHistory;
    attachLoading(getUserDetail);
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void getSkyHistory(String page , String limit){
    createCrmToken(page, limit);
  }

  public void createCrmToken(String page, String limit){
    getView().showLoading();
    compositeSubscription.add(createCrmToken.execute(new SingleSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse value) {
        getView().hideLoading();
        getView().renderCreatedToken(value);
        getSkyDollarHistory(value.getAccessToken(),page,limit);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().renderError(error);
      }
    } ));
  }
  public void createCrmToken(){
    getView().showLoading();
    compositeSubscription.add(createCrmToken.execute(new SingleSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse value) {
        getView().hideLoading();
        getView().renderCreatedToken(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().renderError(error);
      }
    } ));
  }
  public void getSkyDollarHistory(String oauth,String page, String limit){
    getView().showLoading();

    GetSkyDollarHistory.Params params = new GetSkyDollarHistory.Params(oauth, page , limit);

    compositeSubscription.add(getSkyDollarHistory.execute(new SingleSubscriber<SkyDollarHistoryResponse>() {
      @Override
      public void onSuccess(SkyDollarHistoryResponse value) {
        getView().hideLoading();
        getView().renderSkyDollarHistory(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().renderError(error);
      }
    },params));
  }
}
