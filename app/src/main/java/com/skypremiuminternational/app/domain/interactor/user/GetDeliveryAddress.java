package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 2/20/18.
 */

public class GetDeliveryAddress extends UseCase<UserDetailResponse, Void> {
  @Inject
  protected GetDeliveryAddress(DataManager dataManager,
                               ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<UserDetailResponse> provideObservable(Void aVoid) {
    return getDataManager().getUserDetailFromAPI().flatMap(response -> {
      response.setAddresses(filterAddress(response.getAddresses()));
      return Observable.just(response);
    });
  }

  public static List<Address> filterAddress(List<Address> addresses) {
    List<Address> filtered = new ArrayList<>();
    if (addresses != null) {
      for (Address address : addresses) {
        if (address.getCustomAttributes() != null) {
          boolean isPostalAddress = false;
          for (CustomAttribute attribute : address.getCustomAttributes()) {
            if (attribute.getValue().equalsIgnoreCase("postal_address")) {
              isPostalAddress = true;
            }
          }
          if (!isPostalAddress) {
            //filtered.add(address);  //fix not show full delivery address
          }
          filtered.add(address);
        }
      }
    }
    return filtered;
  }
}
