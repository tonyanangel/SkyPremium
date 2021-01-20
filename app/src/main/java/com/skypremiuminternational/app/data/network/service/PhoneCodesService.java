package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by sandi on 11/12/17.
 */

public interface PhoneCodesService {

  @GET(URL.GET_PHONE_CODE)
  Observable<PhoneCode> getPhoneCodes();
}
