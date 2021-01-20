package com.skypremiuminternational.app.app.features.travel.ean.occupancy;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.travel.booking.ChildAdapter;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.SpacesItemDecoration;
import com.skypremiuminternational.app.domain.models.ean.Child;

import dagger.android.support.AndroidSupportInjection;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by codigo on 20/4/18.
 */

public class OccupancyPickerDialogFragment extends BaseDialogFragment<OccupancyPickerPresenter>
    implements OccupancyPickerView<OccupancyPickerPresenter> {

  @BindView(R.id.layout_main)
  LinearLayout layoutMain;
  @BindView(R.id.tv_room_count)
  TextView tvRoomCount;
  @BindView(R.id.tv_adult_label)
  TextView tvAdultLabel;
  @BindView(R.id.tv_adult_count)
  TextView tvAdultCount;
  @BindView(R.id.tv_children_label)
  TextView tvChildrenLabel;
  @BindView(R.id.tv_children_count)
  TextView tvChildrenCount;
  @BindView(R.id.child_section_separator)
  View childSectionSeparator;
  @BindView(R.id.rv_children)
  RecyclerView rvChildren;
  @BindView(R.id.txtClear)
  TextView txtClear;
  private Unbinder unbinder;

  private ChildAdapter childAdapter;
  private int roomCount;
  private List<Child> children;
  private int adultCount;
  private boolean isChildClick = true;

  private OnDoneClickListener onDoneClickListener;

  public OccupancyPickerDialogFragment() {

  }

  public static OccupancyPickerDialogFragment newInstance(int roomCount, int adultCount,
                                                          List<Child> children) {
    OccupancyPickerDialogFragment fragment = new OccupancyPickerDialogFragment();
    fragment.roomCount = roomCount;
    fragment.adultCount = adultCount;
    fragment.children = children;
    return fragment;
  }

  public void setOnDoneClickListener(OnDoneClickListener onDoneClickListener) {
    this.onDoneClickListener = onDoneClickListener;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCancelable(true);
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

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    childAdapter = new ChildAdapter();
    childAdapter.setActionListener((child, position) -> presenter.editChildAge(child, position));
    rvChildren.setHasFixedSize(true);
    rvChildren.setNestedScrollingEnabled(false);

    rvChildren.addItemDecoration(
        new SpacesItemDecoration(2, MetricsUtils.convertDpToPixel(16, getContext()), true));

    rvChildren.setLayoutManager(new GridLayoutManager(getContext(), 2));
    rvChildren.setAdapter(childAdapter);

    presenter.initValues(roomCount, adultCount, children);

    render(roomCount, adultCount, children);
  }

  @Override
  public void render(int roomCount, int adultCount, List<Child> children) {
    tvRoomCount.setText(String.format(Locale.getDefault(), "%d", roomCount));
    tvAdultCount.setText(String.format(Locale.getDefault(), "%d", adultCount));
    tvChildrenCount.setText(String.format(Locale.getDefault(), "%d", children.size()));
    if (children.size() > 0) {
      childSectionSeparator.setVisibility(View.VISIBLE);
      rvChildren.setVisibility(View.VISIBLE);
    } else {
      childSectionSeparator.setVisibility(View.GONE);
      rvChildren.setVisibility(View.GONE);
    }
    childAdapter.setChildren(children);
  }

  @Inject
  @Override
  public void injectPresenter(OccupancyPickerPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_occupancy_picker;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    unbinder = ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (unbinder != null) unbinder.unbind();
  }

  @OnClick(R.id.iv_back)
  public void onBackClicked() {
    dismiss();
  }

  @OnClick(R.id.btn_decrease_room)
  public void onDecreaseRoomClicked() {
    presenter.decreaseRoomCount();
  }

  @OnClick(R.id.btn_increase_room)
  public void onIncreaseRoomClicked() {
    presenter.increaseRoomCount();
  }

  @OnClick(R.id.btn_decrease_adult)
  public void onDecreaseAdultClicked() {
    presenter.decreaseAdultCount();
  }

  @OnClick(R.id.btn_increase_adult)
  public void onIncreaseAdultClicked() {
    presenter.increaseAdultCount();
  }

  @OnClick(R.id.btn_decrease_children)
  public void onDecreaseChildrenClicked() {
    presenter.decreaseChildCount();
  }

  @OnClick(R.id.btn_increase_children)
  public void onIncreaseChildrenClicked() {
    if (isChildClick) {
      isChildClick = !isChildClick;
      presenter.increaseChildCount();
    }
  }

  @OnClick(R.id.txtClear)
  public void onClearClicked() {
    presenter.reset();
  }

  @OnClick(R.id.tv_done)
  public void onDoneClicked() {
    presenter.collectValues();
  }

  @Override
  public void showChildAgePicker(String title, String[] childrenAges, int checkedItem,
                                 Child editingChild, int editingChildPosition) {
    new AlertDialog.Builder(getContext()).setTitle(title)
        .setSingleChoiceItems(childrenAges, editingChildPosition, null)
        .setPositiveButton(R.string.btn_done, (dialog, which) -> {
          isChildClick = true;
          dialog.dismiss();
          int selectedAgePosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
          if (editingChild != null) {
            presenter.editChild(selectedAgePosition, editingChild, editingChildPosition);
          } else {
            if (((AlertDialog) dialog).getListView().getCheckedItemPosition() >= 0) {
              presenter.addChild(selectedAgePosition);
            }
          }
        })
        .setNegativeButton(R.string.btn_label_cancel, (dialog, which) -> {
          isChildClick = true;
          dialog.dismiss();
        })
        .show();
  }

  @Override
  public void notifyChildCountChanged(int size) {
    tvChildrenCount.setText(String.format(Locale.getDefault(), "%d", size));
    if (size == 0) {
      childSectionSeparator.setVisibility(View.GONE);
      rvChildren.setVisibility(View.GONE);
    }
    childAdapter.notifyDataSetChanged();
  }

  @Override
  public void notifyChildStatusChange(int position) {
    childAdapter.notifyItemChanged(position);
  }

  @Override
  public void done(int roomCount, int adultCount, List<Child> children) {
    if (onDoneClickListener != null) {
      onDoneClickListener.onDone(roomCount, adultCount, children);
    }
    dismiss();
  }

  @Override
  public void renderError(String errText) {
    new AlertDialog.Builder(getContext())
        .setMessage(errText)
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> {
          isChildClick = true;
          dialog.dismiss();
        })
        .show();
  }

  public interface OnDoneClickListener {
    void onDone(int roomCount, int adultCount, List<Child> children);
  }
}
