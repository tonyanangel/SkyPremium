package com.skypremiuminternational.app.app.features.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.search.result_products.SearchProductsFragment;
import com.skypremiuminternational.app.app.internal.mvp.LocationAwareActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.UIUtils;
import com.skypremiuminternational.app.domain.models.search.SearchKeyword;

import dagger.android.AndroidInjection;

import javax.inject.Inject;

public class SearchActivity extends LocationAwareActivity<SearchPresenter>
    implements SearchView<SearchPresenter> {

  @BindView(R.id.edtSearch)
  EditText edtSearch;
  @BindView(R.id.nsvSuggested)
  NestedScrollView nsvSuggested;
  @BindView(R.id.llLast)
  LinearLayout llLast;
  @BindView(R.id.rvLast)
  RecyclerView rvLast;
  @BindView(R.id.rvPopular)
  RecyclerView rvPopular;
  @BindView(R.id.flResult)
  FrameLayout flResult;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  String categoryId;
  String keyword;
  String sortBy;
  private boolean isEstore;

  private ProgressDialog progressDialog;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, SearchActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  public static void startMeWithKey(Context context, String keyword,String sortBy) {
    Intent intent = new Intent(context, SearchActivity.class);
    intent.putExtra("Keyword", keyword);
    intent.putExtra("sortBy", sortBy);
    context.startActivity(intent);
  }

  public static void startMe(Context context, String categoryId) {
    Intent intent = new Intent(context, SearchActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    intent.putExtra("Category", categoryId);
    context.startActivity(intent);
  }

  public static void startMe(Context context, String categoryId, boolean isEstore) {
    Intent intent = new Intent(context, SearchActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    intent.putExtra("Category", categoryId);
    intent.putExtra("isEstore", isEstore);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);

    categoryId = getIntent().getStringExtra("Category");
    keyword = getIntent().getStringExtra("Keyword");
    sortBy = getIntent().getStringExtra("sortBy");
    isEstore = getIntent().getBooleanExtra("isEstore", false);
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);

    edtSearch.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        if (!TextUtils.isEmpty(edtSearch.getText().toString().trim())) {
          onClickSearchKeyword(new SearchKeyword(edtSearch.getText().toString()));
        }
        return true;
      }
      return false;
    });
    if (keyword != null) {
      if (!TextUtils.isEmpty(keyword.toString().trim())) {
        edtSearch.setText(keyword);
        onClickSearchKeyword(new SearchKeyword(edtSearch.getText().toString(),sortBy));
        edtSearch.setFocusable(false);
      }
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Inject
  @Override
  public void injectPresenter(SearchPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivClear)
  void onClickClear() {
    edtSearch.setText("");
  }

  @OnClick(R.id.tvCancel)
  void onClickCancel() {
    finish();
  }

  @OnClick(R.id.tvLastClear)
  void onClickLastClear() {
    llLast.setVisibility(View.GONE);
  }

  @Subscribe
  public void onClickSearchKeyword(SearchKeyword searchKeyword) {
    nsvSuggested.setVisibility(View.GONE);
    flResult.setVisibility(View.VISIBLE);
    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
    trans.replace(R.id.flResult,
        SearchProductsFragment.newInstance(searchKeyword.getText(),getSelectedSortingIxdex(searchKeyword.getSortBy()), categoryId, isEstore,!TextUtils.isEmpty(searchKeyword.getSortBy())));
    trans.commit();
    UIUtils.hideKeyboard(this);
  }

  @Override
  public void render(SearchViewState viewState) {
    {
      if (viewState.isLoading()) {
        progressDialog.show();
      } else {
        /*progressDialog.dismiss();
        if (viewState.isSuccess()) {
          finish();
          LandingActivity.startMe(this);
        } else {
          if (viewState.error() instanceof InvalidEmailException) {
            tilStaffID.getEditText().setError(viewState.error().getLocalizedMessage());
          } else if (viewState.error() instanceof InvalidPasswordException) {
            tilPassword.getEditText().setError(viewState.error().getLocalizedMessage());
          } else {
            Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
                Toast.LENGTH_SHORT).show();
          }
        }*/
      }
    }
  }
  private int getSelectedSortingIxdex(String  sortBy) {
    if(sortBy!=null){
      switch (sortBy){
        case "relevance_order" :{
          return 0;
        }
        case "popularity_order" :{
          return 1;
        }
        case "nameaz" :{
          return 2;
        }
        case "nameza" :{
          return 3;
        }
        case "latest" :{
          return 4;
        }
        case "earliest" :{
          return 5;
        }
        default:{
          return 0;
        }
      }
    }
    return 0;
  }
}
