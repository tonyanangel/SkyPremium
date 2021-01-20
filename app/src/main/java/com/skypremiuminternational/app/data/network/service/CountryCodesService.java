package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by sandi on 11/12/17.
 */

public interface CountryCodesService {

  @GET(URL.GET_COUNTRY)
  Observable<List<CountryCode>> getCountryCodes();

  @GET(URL.GET_COUNTRY_CC)
  Observable<List<CountryCodeCC>> getCountryCodeCc(@Header("Authorization") String auth);
}
