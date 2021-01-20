package com.skypremiuminternational.app.app.features.profile.my_orders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.profile.order.detail.OrderDetailsActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.CustomTypefaceSpan;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.listener.ItemClickListener;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderItem;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderResponse;

import dagger.android.AndroidInjection;
import timber.log.Timber;

import javax.inject.Inject;

public class MyOrderActivity extends BaseActivity<MyOrdersPresenter>
    implements MyOrdersView<MyOrdersPresenter> {

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.rvMyOrders)
  RecyclerView rv;
  /*@BindView(R.id.spnCategory_filter) AppCompatSpinner spnCategory;
  @BindView(R.id.spnSort_filter) AppCompatSpinner spnSort;*/
  @BindView(R.id.tvTitle_toolbar_amount)
  TextView txtToolbarAmount;
  @BindView(R.id.layout_no_order)
  RelativeLayout layoutNoOrder;
  @BindView(R.id.tvCategory_filter)
  TextView tvCategoryFilter;
  @BindView(R.id.tvSort_filter)
  TextView tvSortFilter;
  @Inject
  ErrorMessageFactory errorMessageFactory;

  private ProgressDialog progressDialog;
  private MyOrderAdapter orderAdapter;
  private int selectedSorting = 0;
  private int selectedCategory = 0;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, MyOrderActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_orders);
    ButterKnife.bind(this);
    tvTitle_toolbar.setText(getResources().getString(R.string.my_orders));

    tvSortFilter.setText(String.format("Sort By: %s", Constants.sortingArrOrder[selectedSorting]));
    tvCategoryFilter.setText(
        String.format("Refine: %s", Constants.categoryArrOrder[selectedCategory]));

    setupRecyclerAdapter();

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);

    fetchOrderHistory();
  }

  private void setupRecyclerAdapter() {
    rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    orderAdapter = new MyOrderAdapter();
    orderAdapter.setItemClickListener(new ItemClickListener<MyOrderItem>() {
      @Override
      public void onItemClicked(MyOrderItem item) {
        Timber.i("OrderItem : " + item);
        OrderDetailsActivity.startMe(MyOrderActivity.this, item);
      }
    });
    rv.setAdapter(orderAdapter);
  }

  @Inject
  @Override
  public void injectPresenter(MyOrdersPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    //NavigationDialogFragment.newInstance().show(getSupportFragmentManager(), "Navigation");
    finish();
  }

  @Override
  public void showLoading(String message) {
    if (!isDestroyed()) {
      progressDialog.setMessage(message);
      progressDialog.show();
    }
  }

  @Override
  public void render(MyOrderResponse value) {
    Timber.i("total : " + value.getTotalCount());
    txtToolbarAmount.setText("(" + String.valueOf(value.getTotalCount()) + ")");
    if (value.getItems().size() > 0) {
      rv.setVisibility(View.VISIBLE);
      layoutNoOrder.setVisibility(View.GONE);
      orderAdapter.setDataList(value.getItems());
    } else {
      rv.setVisibility(View.GONE);
      layoutNoOrder.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void render(Throwable error) {
    Toast.makeText(getApplicationContext(), errorMessageFactory.getErrorMessage(error),
        Toast.LENGTH_LONG).show();
  }

  @Override
  public void hideLoading() {
    if (!isDestroyed()) {
      progressDialog.dismiss();
    }
  }

  @OnClick(R.id.tvSort_filter)
  public void onClickSort() {
    new AlertDialog.Builder(this).setTitle("SORT BY:")
        .setItems(Constants.sortingArrOrder, (dialog, item) -> {
          selectedSorting = item;
          tvSortFilter.setText(String.format("Sort By: %s", Constants.sortingArrOrder[item]));
          fetchOrderHistory();
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  @OnClick(R.id.tvCategory_filter)
  public void onClickCategory() {
    new AlertDialog.Builder(this).setTitle("REFINE: ")
        .setItems(Constants.categoryArrOrder, (dialog, item) -> {
          selectedCategory = item;
          tvCategoryFilter.setText(String.format("Refine: %s", Constants.categoryArrOrder[item]));
          fetchOrderHistory();
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  private void fetchOrderHistory() {
    getPresenter().setRequest(Constants.sortingArrOrder[selectedSorting],
        Constants.categoryArrOrder[selectedCategory]);
    getPresenter().getOrderHistory();
  }

  private void changeBoldStyle(String tilte, String data) {
    String boldText = tilte;
    String normalText = data;
    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf");
    SpannableStringBuilder str = new SpannableStringBuilder(boldText + normalText);
    str.setSpan(new CustomTypefaceSpan("", font), 0, boldText.length(),
        Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    tvCategoryFilter.setText(str);
  }
}
