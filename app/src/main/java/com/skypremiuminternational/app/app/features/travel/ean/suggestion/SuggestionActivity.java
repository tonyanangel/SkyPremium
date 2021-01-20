package com.skypremiuminternational.app.app.features.travel.ean.suggestion;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.view.RxEditText;
import com.skypremiuminternational.app.domain.models.ean.Suggestion;

import dagger.android.AndroidInjection;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Subscription;

public class SuggestionActivity extends BaseActivity<SuggestionPresenter>
    implements SuggestionView<SuggestionPresenter> {

  @BindView(R.id.ly_error)
  ViewGroup lyError;
  @BindView(R.id.tv_error_message)
  TextView tvErrorMessage;
  @BindView(R.id.edt_search)
  AppCompatEditText edtSearch;
  @BindView(R.id.rv_suggestion)
  RecyclerView rvSuggestion;
  @BindView(R.id.iv_clear)
  ImageView ivClear;

  private SuggestionAdapter adapter;

  private Subscription subscription;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  public static void startForResult(Fragment context, int requestCode) {
    Intent starter = new Intent(context.getContext(), SuggestionActivity.class);
    context.startActivityForResult(starter, requestCode);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auto_suggest);
    ButterKnife.bind(this);
    setUpRecyclerView();

    subscription = new RxEditText(edtSearch)
        .textChanges()
        .filter(charSequence -> charSequence.length() >= 3)
        .debounce(500, TimeUnit.MILLISECONDS)
        .map(CharSequence::toString)
        .subscribe(presenter::getSuggestions);
  }

  private void setUpRecyclerView() {
    adapter = new SuggestionAdapter();
    adapter.setActionListener(suggestion -> {
      Intent intent = new Intent();
      intent.putExtra("suggestion", suggestion.suggestion);
      setResult(RESULT_OK, intent);
      hideKeyboard();
      finish();
    });
    rvSuggestion.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    rvSuggestion.setAdapter(adapter);
  }

  @Inject
  @Override
  public void injectPresenter(SuggestionPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.iv_clear)
  public void onClearClicked() {
    edtSearch.setText("");
  }

  @OnClick(R.id.tv_ok)
  public void onCancelClicked() {
    Intent intent = new Intent();
    intent.putExtra("suggestion", edtSearch.getText().toString());
    setResult(RESULT_OK, intent);
    hideKeyboard();
    finish();
  }

  @Override
  public void render(List<Suggestion> suggestions) {
    if (suggestions != null && suggestions.size() > 0) {
      //ivClear.setVisibility(View.VISIBLE);
      rvSuggestion.setVisibility(View.VISIBLE);
      lyError.setVisibility(View.GONE);
      adapter.setSuggestions(suggestions);
    } else {
      showError("");
    }
  }

  private void showError(String message) {
    rvSuggestion.setVisibility(View.GONE);
    lyError.setVisibility(View.VISIBLE);
    tvErrorMessage.setText(message);
  }

  @Override
  public void render(Throwable error) {
    showError(errorMessageFactory.getErrorMessage(error));
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (subscription != null) subscription.unsubscribe();
  }
}
