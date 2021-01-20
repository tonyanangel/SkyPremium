package com.skypremiuminternational.app.app.view;

import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skypremiuminternational.app.R;

import org.jsoup.nodes.Element;

/**
 * Created by angebagui on 06/08/2016.
 */

public class IFrameView extends ElementView {

  private static final String TAG = IFrameView.class.getSimpleName();

  public IFrameView(Context context, Element element) {
    super(context, element);
  }

  public IFrameView(Context context, AttributeSet attrs, Element element) {
    super(context, attrs, element);
  }

  @Override
  public void render() {
    setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    setPadding(0, 0, 0, 0);
    WebView webView = new WebView(getContext());
    WebViewClient webViewClient = new WebViewClient(){
      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

         handler.cancel();
      }
    };
    webView.setWebViewClient(webViewClient);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
      webView.getSettings().setPluginState(WebSettings.PluginState.ON);
    }
    webView.getSettings().setJavaScriptEnabled(true);
    webView.loadDataWithBaseURL("file:///android_asset/", getElement().toString(), "text/html",
        "UTF-8", null);
    webView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    webView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.background));
    addView(webView);
  }

  public String getLink() {
    if (getElement() == null) {
      return null;
    }
    return getElement().attr("abs:src");
  }
}
