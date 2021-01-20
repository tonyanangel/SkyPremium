package com.skypremiuminternational.app.app.features.info;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;



public class InfoActivity extends AppCompatActivity {

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle;
  @BindView(R.id.webContent)
  WebView webContent;
  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  public static void start(Context context, String link, String title) {
    Intent starter = new Intent(context, InfoActivity.class);
    starter.putExtra("link", link);
    starter.putExtra("title", title);
    context.startActivity(starter);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info);
    ButterKnife.bind(this);

    if (getIntent().getExtras() != null) {
      String link = getIntent().getExtras().getString("link");
      String title = getIntent().getExtras().getString("title");

      WebSettings webSettings = webContent.getSettings();
      webSettings.setJavaScriptEnabled(true);
      webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
      tvTitle.setText(title);
      webContent.getSettings().setDomStorageEnabled(true);
      webContent.setWebViewClient(new WebViewClient(){


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

          handler.cancel();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

          String refinedUrl;
          if (!url.contains("source=mobile")) {
            refinedUrl = url + "?source=mobile";
          } else {
            refinedUrl = url;
          }
          view.loadUrl(refinedUrl);
          return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
          if (!isDestroyed()) {
            progressBar.setVisibility(View.VISIBLE);
            webContent.setVisibility(View.GONE);
          }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
          if (!isDestroyed()) {
            progressBar.setVisibility(View.GONE);
            webContent.setVisibility(View.VISIBLE);
          }
        }
      });
      webContent.loadUrl(link);

    }
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    NavigationDialogFragment.newInstance().show(getSupportFragmentManager(), "Navigation");
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }


  @Override
  public void onBackPressed() {
    if (webContent.canGoBack()) {
      webContent.goBack();
    } else {
      super.onBackPressed();
    }
  }
}
