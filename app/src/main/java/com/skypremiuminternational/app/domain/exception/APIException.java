package com.skypremiuminternational.app.domain.exception;

import com.skypremiuminternational.app.domain.models.APIError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by hein on 6/5/17.
 */

public class APIException extends HttpException {

  List<APIError> apiErrors;

  public APIException(Response<?> response) {
    super(response);

    try {
      Type type = new TypeToken<List<APIError>>() {
      }.getType();
      apiErrors = new Gson().fromJson(response.errorBody().string(), type);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getLocalizedMessage() {
    return super.getLocalizedMessage();
  }
}
