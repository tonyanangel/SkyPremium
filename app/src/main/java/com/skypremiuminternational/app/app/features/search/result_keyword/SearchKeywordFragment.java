package com.skypremiuminternational.app.app.features.search.result_keyword;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;

import dagger.android.support.AndroidSupportInjection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by sandi on 7/27/17.
 */

public class SearchKeywordFragment extends BaseFragment<SearchKeywordPresenter>
    implements SearchKeywordView<SearchKeywordPresenter> {

  @BindView(R.id.tvCategory)
  TextView tvCategory;
  @BindView(R.id.rvKeyword)
  RecyclerView rv;
  /*@BindView(R.id.llLoading) LinearLayout llLoading;
  @BindView(R.id.pbLoading) ProgressBar progressBar;
  @BindView(R.id.tvLoading) TextView tvLoading;
  @BindView(R.id.llMessage) LinearLayout llMessage;
  @BindView(R.id.imgMessage) ImageView imgMessage;
  @BindView(R.id.tvMessage) TextView tvMessage;
  @BindView(R.id.btnMessage) Button btnMessage;*/

  @Inject
  ErrorMessageFactory errorMessageFactory;

  SearchKeywordAdapter adapter;
  String category = "";
  String keyword = "";

  public static SearchKeywordFragment newInstance(String category, String keyword) {
    SearchKeywordFragment fragment = new SearchKeywordFragment();
    fragment.category = category;
    fragment.keyword = keyword;
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);
    tvCategory.setText(category);
    setUpRecyclerView();
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_search_keyword;
  }

  @Inject
  @Override
  public void injectPresenter(SearchKeywordPresenter presenter) {
    super.injectPresenter(presenter);
  }

  public void setUpRecyclerView() {

    //rv.setNestedScrollingEnabled(false);
    //frv.setHasFixedSize(true);
    //rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new SearchKeywordAdapter();
    rv.setAdapter(adapter);

    List<String> resultList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      resultList.add(keyword + " result " + String.valueOf(i + 1));
    }

    adapter.setDataList(resultList);
  }

  @Override
  public void render(SearchKeywordViewState viewState) {

  }
}
