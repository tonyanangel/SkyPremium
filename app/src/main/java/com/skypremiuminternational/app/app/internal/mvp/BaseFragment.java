package com.skypremiuminternational.app.app.internal.mvp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.app.utils.RxBus;

/**
 * Created by khunzohn on 5/22/17.
 */
public abstract class BaseFragment<T extends Presentable> extends Fragment implements Viewable<T> {

  private Unbinder unbinder;
  protected T presenter;

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(@StringRes int resource) {
    setTitle(getString(resource));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(@NonNull CharSequence msg) {
    getActivity().setTitle(msg);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onStart() {
    super.onStart();
    getPresenter().onStart();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    RxBus.get().register(this);
    setRetainInstance(true);
  }

  /**
   * {@inheritDoc}
   */
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutId(), container, false);
    unbinder = ButterKnife.bind(this, view);
    //noinspection unchecked
    getPresenter().attachView(this);
    return view;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getPresenter().onViewCreated();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onDestroyView() {
    getPresenter().detachView();
    unbinder.unbind();
    super.onDestroyView();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onStop() {
    getPresenter().onStop();
    super.onStop();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onDestroy() {
    presenter = null;
    RxBus.get().unregister(this);
    super.onDestroy();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void displayError(String message) {
    View rootContent = ButterKnife.findById(getActivity(), android.R.id.content);
    Snackbar.make(rootContent, message, Snackbar.LENGTH_LONG).show();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void displayError(int messageId) {
    displayError(getString(messageId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showLoading() {
    // no-op by default
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hideLoading() {
    // no-op by default
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T getPresenter() {
    return presenter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void injectPresenter(T presenter) {
    this.presenter = presenter;
  }

  protected abstract int getLayoutId();

  @Override
  public void showForceUpdateNotice(String forceUpdateMessage, String url) {
    //nothing to do with fragemnt
  }

  @Override
  public void showUpdateNotice(String updateMessage, String url) {
    //nothing to do with fragemnt
  }
}
