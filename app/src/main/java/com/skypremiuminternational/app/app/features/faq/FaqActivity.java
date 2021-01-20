package com.skypremiuminternational.app.app.features.faq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.domain.models.faq.FaqItem;

import dagger.android.AndroidInjection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class FaqActivity extends BaseActivity<FaqPresenter> implements FaqView<FaqPresenter> {

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.edtSearch)
  EditText edtSearch;
  @BindView(R.id.elw)
  ExpandableListView elw;
  @BindView(R.id.llMessage)
  LinearLayout llMessage;
  @BindView(R.id.tvMessage)
  TextView tvMessage;
  @BindView(R.id.tvMessageInfo)
  TextView tvMessageInfo;
  @BindView(R.id.ly_cart)
  RelativeLayout lyCart;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private int lastExpandedPosition = -1;
  private ProgressDialog progressDialog;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, FaqActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_faqs);
    ButterKnife.bind(this);

    tvTitle_toolbar.setText(getResources().getString(R.string.faq));

    elw.setGroupIndicator(null);
    elw.setChildIndicator(null);
    elw.setChildDivider(getResources().getDrawable(R.color.white));
    elw.setDivider(getResources().getDrawable(R.color.dividerColor));
    elw.setDividerHeight(1);
    elw.setOnGroupExpandListener(groupPosition -> {
      if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
        elw.collapseGroup(lastExpandedPosition);
      }
      lastExpandedPosition = groupPosition;
    });

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.loading));

    edtSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      private Timer timer = new Timer();
      private final long DELAY = 500;

      @Override
      public void afterTextChanged(Editable s) {
        final String keyword = s.toString();
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            runOnUiThread(() -> presenter.getFaqItemByKeyword(keyword));
          }
        }, DELAY);
      }
    });

    lyCart.setVisibility(View.VISIBLE);

    presenter.getCartCount();
    presenter.getFaq();
  }

  @Override
  public void onPause() {
    super.onPause();
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @Override
  public void updateCartCount(String count) {
    if (count == null || TextUtils.isEmpty(count)) {
      lyCartCount.setVisibility(View.INVISIBLE);
      tvCartCount.setVisibility(View.INVISIBLE);
    } else {
      if (!count.equalsIgnoreCase("0")) {
        lyCartCount.setVisibility(View.VISIBLE);
        tvCartCount.setVisibility(View.VISIBLE);
        tvCartCount.setText(count);
      } else {
        lyCartCount.setVisibility(View.INVISIBLE);
        tvCartCount.setVisibility(View.INVISIBLE);
      }
    }
  }

  @Inject
  @Override
  public void injectPresenter(FaqPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    NavigationDialogFragment.newInstance().show(getSupportFragmentManager(), "Navigation");
  }

  /*@OnClick(R.id.ivSearch_toolbar) void onClickSearch() {
    SearchActivity.startMe(this);
  }*/

  @Override
  public void render(FaqViewState viewState) {

    if (viewState.isLoading()) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        if (viewState.dataList().size() > 0) {
          showFaqs(viewState.dataList());
          elw.setVisibility(View.VISIBLE);
          llMessage.setVisibility(View.GONE);
        } else {
          elw.setVisibility(View.GONE);
          llMessage.setVisibility(View.VISIBLE);
          if (viewState.message().equals("1")) {
            tvMessage.setText(R.string.faq_empty);
            tvMessageInfo.setVisibility(View.GONE);
          } else {
            tvMessage.setText(
                String.format(getString(R.string.faq_not_found), edtSearch.getText().toString()));
            tvMessageInfo.setVisibility(View.VISIBLE);
          }
        }
      } else {

      }
    }
  }

  void showFaqs(List<FaqItem> items) {
    List<String> headerList = new ArrayList<>();
    HashMap<String, List<String>> childMap = new HashMap<>();
    for (FaqItem faqItem : items) {
      headerList.add(faqItem.getTitle());

      List<String> childList = new ArrayList<>();
      childList.add(faqItem.getContent());
      childMap.put(faqItem.getTitle(), childList);
    }
    FaqExpandableAdapter faqExpandableAdapter =
        new FaqExpandableAdapter(this, headerList, childMap);
    elw.setAdapter(faqExpandableAdapter);
  }

  @OnClick(R.id.iv_cart)
  void onCartClicked() {
    ShoppingCartActivity.start(this);
  }
}
