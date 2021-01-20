package com.skypremiuminternational.app.app.utils;

import android.content.Context;

import com.skypremiuminternational.app.R;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.HttpException;
import timber.log.Timber;

/**
 * Factory used to save error messages from an Exception as a condition.
 */
@Singleton
public class ErrorMessageFactory {

  private Context context;

  @Inject
  public ErrorMessageFactory(Context context) {
    this.context = context;
  }

  public String getErrorMessage(Throwable throwable) {

    if (throwable instanceof UnknownHostException) {
      return context.getString(R.string.network_error);
    }

    if (throwable instanceof HttpException) {
      return getHttpErrorMessage((HttpException) throwable);
    }

    return getGeneralError(throwable.getLocalizedMessage());
  }

  private String getHttpErrorMessage(HttpException exception) {

    if (exception.code() == 500) {
      return context.getString(R.string.server_500);
    }

    String bodyString;
    try {
      bodyString = exception.response().errorBody().string();
    } catch (IOException e) {
      Timber.e(e);
      return getConversionError();
    }

    try {
      JSONArray jsonArray = new JSONArray(bodyString);
      if (jsonArray.length() > 0) {
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        if (jsonObject.has("message")) {
          return getGeneralError(jsonObject.getString("message"));
        }
      }
    } catch (JSONException e) {
      try {
        JSONObject jsonObject = new JSONObject(bodyString);
        if (jsonObject.has("message")) {
          return getGeneralError(jsonObject.getString("message"));
        }
      } catch (JSONException e1) {
        Timber.e(e);
        return getJsonSyntaxError();
      }
    }

    return getHttpError(exception);
  }

  private String getGeneralError(String message) {
    if (isDebugBuild()) {
      return message;
    }
    return context.getString(R.string.error_something_went_wrong);
  }

  private String getHttpError(HttpException exception) {
    if (isDebugBuild()) {
      return String.format(context.getString(R.string.http_error), exception.code(),
          exception.getMessage());
    }
    return context.getString(R.string.error_something_went_wrong);
  }

  private String getJsonSyntaxError() {
    if (isDebugBuild()) {
      return context.getString(R.string.error_json_syntax);
    }
    return context.getString(R.string.error_something_went_wrong);
  }

  private String getConversionError() {
    if (isDebugBuild()) {
      return context.getString(R.string.error_io);
    }
    return context.getString(R.string.error_something_went_wrong);
  }

  private boolean isDebugBuild() {
    return true;
    //return BuildConfig.BUILD_TYPE.equals("debug");
  }
}
