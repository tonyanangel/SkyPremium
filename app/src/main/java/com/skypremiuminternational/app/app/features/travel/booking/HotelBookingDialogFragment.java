package com.skypremiuminternational.app.app.features.travel.booking;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerDialogFragment;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.SpacesItemDecoration;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.travijuu.numberpicker.library.NumberPicker;

import dagger.android.support.AndroidSupportInjection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by johnsonmaung on 10/6/17.
 */

public class HotelBookingDialogFragment extends BaseDialogFragment<HotelBookingPresenter>
    implements HotelBookingView<HotelBookingPresenter> {

  @BindView(R.id.tilPackage)
  TextInputLayout tilPackage;
  @BindView(R.id.tilDate)
  TextInputLayout tilDate;
  @BindView(R.id.npAdult)
  NumberPicker npAdult;
  @BindView(R.id.npChild)
  NumberPicker npChild;
  @BindView(R.id.rvChildren)
  RecyclerView rvChildren;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  ChildAdapter childAdapter;

  public static HotelBookingDialogFragment newInstance() {
    HotelBookingDialogFragment fragment = new HotelBookingDialogFragment();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCancelable(false);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    //return super.onCreateDialog(savedInstanceState);
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    //dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    return dialog;
  }

  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      dialog.getWindow()
          .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);
    final String[] packages = {
        "Family Packages", "Fun & Entertainment Packages", "Romance Packages", "Shopping Packages"
    };
    tilPackage.getEditText().setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
          new AlertDialog.Builder(getContext()).setTitle("Choose Package Type")
              .setItems(packages, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int itemPosition) {
                  tilPackage.getEditText().setText(packages[itemPosition]);
                }
              })
              .show();
        }
        return false;
      }
    });
    tilDate.getEditText().setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
          DateRangePickerDialogFragment.newInstance()
              .show(getChildFragmentManager(), "DateRangePicker");
        }
        return false;
      }
    });

    String[] ageArr = {
        "<1yo", "1yo", "2yo", "3yo", "4yo", "5yo", "6yo", "7yo", "8yo", "9yo", "10yo", "11yo",
        "12yo", "13yo", "14yo", "15yo", "16yo", "17yo"
    };
    int spanCount = 2;
    int spacing = 16;
    boolean includeEdge = true;
    rvChildren.addItemDecoration(
        new SpacesItemDecoration(spanCount, MetricsUtils.convertDpToPixel(spacing, getContext()),
            includeEdge));
    rvChildren.setHasFixedSize(true);
    rvChildren.setNestedScrollingEnabled(false);
    rvChildren.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
    childAdapter = new ChildAdapter();
    rvChildren.setAdapter(childAdapter);

    List<Child> children = new ArrayList<>();
    /*children.add(new Child("10"));
    children.add(new Child("50"));
    children.add(new Child("23"));*/

    childAdapter.setChildren(children);
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
  }

  @OnClick(R.id.imgClose)
  void onClickClose() {
    dismiss();
  }

  @OnClick(R.id.tvContinue)
  void onClickContinue() {
    dismiss();
    //ResetPasswordActivity.startMe(getActivity());
  }

  @Inject
  @Override
  public void injectPresenter(HotelBookingPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_fragment_hotel_booking;
  }

  @Override
  public void render(HotelBookingViewState viewState) {
    if (viewState.error() != null) {
      Toast.makeText(getActivity(), errorMessageFactory.getErrorMessage(viewState.error()),
          Toast.LENGTH_SHORT).show();
      //edtScan.setText("");
    }
  }
}
