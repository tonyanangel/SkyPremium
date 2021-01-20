package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.nationality.Nationality;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by sandi on 11/12/17.
 */

public interface NationalitiesService {

  @GET(URL.GET_NATIONALITIES)
  Observable<List<Nationality>> getNationalities();
}
