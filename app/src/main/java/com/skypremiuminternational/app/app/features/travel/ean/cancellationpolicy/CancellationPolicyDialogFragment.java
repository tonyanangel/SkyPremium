package com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;

import java.util.List;

import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

public class CancellationPolicyDialogFragment extends DialogFragment {

  @BindView(R.id.tv_name)
  TextView tvName;
  @BindView(R.id.tv_policy)
  TextView tvPolicy;

  private Unbinder unbinder;

  private CancelPenalty cancelPenalty;
  private String roomAndBed;

  @Inject
  CancellationPolicyBuilder cancellationPolicyBuilder;
  private String propertyName;
  private List<CancelsPenalties> cancelsPenaltiesList;
  private List<CancelPenalty> cancelPenaltyList;

  public CancellationPolicyDialogFragment() {

  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  public static CancellationPolicyDialogFragment newInstance(
          CancelPenalty cancelPenalty, String roomAndBed, String propertyName,List<CancelsPenalties> cancelsPenaltiesList,List<CancelPenalty> cancelPenaltyList) {
    CancellationPolicyDialogFragment fragment = new CancellationPolicyDialogFragment();
    String refined = roomAndBed;
    if (roomAndBed.endsWith(")")) {
      refined = roomAndBed.substring(0, roomAndBed.lastIndexOf("("));
    }
    fragment.roomAndBed = refined;
    fragment.cancelsPenaltiesList = cancelsPenaltiesList;
    fragment.cancelPenaltyList = cancelPenaltyList;
    fragment.propertyName = propertyName;
    fragment.cancelPenalty = cancelPenalty;
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
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

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_cancellation_policy, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (roomAndBed != null) {
      tvName.setText(roomAndBed);
    }

    tvPolicy.setText(cancellationPolicyBuilder.build(cancelPenalty, propertyName,cancelsPenaltiesList,cancelPenaltyList));
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCancelable(true);
  }

  @Override
  public void onDestroyView() {
    unbinder.unbind();
    super.onDestroyView();
  }

  @OnClick(R.id.iv_close)
  void onCloseClicked() {
    dismiss();
  }
}
