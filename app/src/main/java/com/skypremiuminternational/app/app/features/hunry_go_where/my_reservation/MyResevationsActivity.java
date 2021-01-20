package com.skypremiuminternational.app.app.features.hunry_go_where.my_reservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.hunry_go_where.detail.DetailsResevationActivity;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryRespone;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class MyResevationsActivity extends BaseActivity<MyReservationsPresenter>
    implements MyReservationsView<MyReservationsPresenter> {

  @BindView(R.id.rv_reservation)
  RecyclerView rvReservation;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitleToolbar;
  @BindView(R.id.tvCategory_filter)
  TextView tvCategoryFilter;
  @BindView(R.id.tvSort_filter)
  TextView tvSortFilter;
  @BindView(R.id.tv_num)
  TextView tvNum;


  ProgressDialog progressDialog;
  MyReservationApdapter apdapter;

  int refineSelectedPos = 0;
  int sortSelectedPos = 0;


  public static void startMe(Context context) {
    Intent intent = new Intent(context, MyResevationsActivity.class);
    context.startActivity(intent);
  }

  @Inject
  @Override
  public void injectPresenter(MyReservationsPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_reservations);
    ButterKnife.bind(this);

    setRecyclerView();
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);

    initRefineSort();

  }

  private void initRefineSort() {
    tvCategoryFilter.setText("REFINE: " +"All" );
    tvSortFilter.setText("SORT BY: " +"LATEST FIRST" );
    tvCategoryFilter.setText("REFINE: "+Constants.refineArrReservation[refineSelectedPos]);
    tvSortFilter.setText("SORT BY: "+Constants.sortArrReservation[sortSelectedPos]);

  }

  @Override
  protected void onResume() {
    super.onResume();
    loadHistoryList();
  }

  private void loadHistoryList(){
    if(refineSelectedPos==0){
      presenter.getHistoryAll(Constants.sortDirectionReservation[sortSelectedPos]);
    }else {
      presenter.getHistoryFilter(Constants.refineDirectionReservation[refineSelectedPos],
          Constants.sortDirectionReservation[sortSelectedPos]);
    }
  }

  private void setRecyclerView() {
    apdapter = new MyReservationApdapter();
    rvReservation.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    rvReservation.setAdapter(apdapter);

    apdapter.setActionListener(bookingId -> {
      presenter.getBookingDetail(bookingId);
    });

  }

  @Override
  public void renderError(String msg) {
    new AlertDialog.Builder(this).setMessage(msg).show();
  }

  @Override
  public void renderHistoryReservation(ReserveHistoryRespone reserveHistoryRespone) {
    apdapter.setData(reserveHistoryRespone.getReserveHistoryItemList());

    tvTitleToolbar.setText(getResources().getString(R.string.my_reservation));
    tvNum.setText(String.format("(%d)",reserveHistoryRespone.getReserveHistoryItemList().size()));

  }


  @Override
  public void renderGotoBookingDetail(ReserveHistoryItem reserveHistoryItem) {
    DetailsResevationActivity.startMe(this, reserveHistoryItem);
  }

  @Override
  public void showLoading() {
    if(progressDialog!=null && !progressDialog.isShowing()){
      progressDialog.setMessage(getResources().getString(R.string.loading));
      progressDialog.show();
    }
  }


  @Override
  public void hideLoading() {
    if(progressDialog != null && progressDialog.isShowing()){
      progressDialog.dismiss();
    }
  }


  @OnClick(R.id.ivNavigation_toolbar)
  void onClickBack(){
    onBackPressed();
  }
  @OnClick(R.id.tvCategory_filter)
  void onClickCategory(){
    new AlertDialog.Builder(this).setTitle("REFINE:")
        .setItems(Constants.refineArrReservation, (dialog, position) -> {
          refineSelectedPos =  position;
          tvCategoryFilter.setText("REFINE: "+Constants.refineArrReservation[refineSelectedPos]);
          loadHistoryList();
        })
        .setNegativeButton("Cancel", null)
        .show();
  }
  @OnClick(R.id.tvSort_filter)
  void onClickSort(){
    new AlertDialog.Builder(this).setTitle("SORT BY:")
        .setItems(Constants.sortArrReservation, (dialog, position) -> {
          sortSelectedPos =  position;
          tvSortFilter.setText("SORT BY: "+Constants.sortArrReservation[sortSelectedPos]);
          loadHistoryList();
        })
        .setNegativeButton("Cancel", null)
        .show();
  }


}
