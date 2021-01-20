package com.skypremiuminternational.app.app.features.profile.billingaddress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;

import dagger.android.AndroidInjection;

import java.util.List;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.ADD_ADDRESS;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.EDIT_ADDRESS;

public class ManageBillingAddressActivity extends BaseActivity<ManageBillingAddressPresenter>
    implements ManageBillingAddressView<ManageBillingAddressPresenter> {

  private static final String TAG_ADDRESS_DIALOG = "AddressDialog";

  @BindView(R.id.tvTitle_toolbar)
  TextView txtTitleToolbar;
  @BindView(R.id.rv_billing_address)
  RecyclerView rvBillingAddress;
  @BindView(R.id.layout_no_billing_address)
  RelativeLayout layoutNoAddress;
  @Inject
  ErrorMessageFactory errorMessageFactory;

  private BillingAddressAdapter adapter;
  private ProgressDialog progressDialog;

  private Unbinder unbinder;
  private AddOrEditBillingAddressDialog addOrEditAddressDialog;

  public static void start(Context context) {
    Intent starter = new Intent(context, ManageBillingAddressActivity.class);
    context.startActivity(starter);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_manage_billing_address);
    unbinder = ButterKnife.bind(this);
    txtTitleToolbar.setText(R.string.billing_address_title);

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.loading));

    setUpRecyclerView();
    presenter.getCountryCodes();
  }

  private void setUpRecyclerView() {
    adapter = new BillingAddressAdapter(false);
    adapter.setActionListener(new BillingAddressAdapter.ActionListener() {
      @Override
      public void onEditClicked(BillingAddress address) {
        presenter.collectCountryCodesAndProceedToEdit(address);
      }

      @Override
      public void onItemClicked(BillingAddress address) {
        //do nothing
      }
    });

    rvBillingAddress.setAdapter(adapter);
    rvBillingAddress.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  @Inject
  @Override
  public void injectPresenter(ManageBillingAddressPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void render(Throwable error) {
    if (isDestroyed()) return;

    new AlertDialog.Builder(this)
        .setCancelable(false)
        .setMessage(errorMessageFactory.getErrorMessage(error))
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss())
        .show();
  }

  @Override
  public void render(List<BillingAddress> billingAddresses) {
    if (addOrEditAddressDialog != null) {
      addOrEditAddressDialog.dismiss();
    }
    if (billingAddresses != null && billingAddresses.size() > 0) {
      layoutNoAddress.setVisibility(View.GONE);
      rvBillingAddress.setVisibility(View.VISIBLE);
      adapter.setBillingAddresses(billingAddresses);
    } else {
      layoutNoAddress.setVisibility(View.VISIBLE);
      rvBillingAddress.setVisibility(View.GONE);
    }
  }

  @Override
  public void setCountryCodes(List<CountryCode> countryCodes) {
    adapter.setCountryCodes(countryCodes);
  }

  @Override
  public void showAddAddressDialog(List<CountryCode> countryCodes) {
    showAddOrEditAddressDialog(countryCodes, ADD_ADDRESS, null);
  }

  @Override
  public void showEditAddressDialog(List<CountryCode> countryCodes,
                                    BillingAddress address) {
    showAddOrEditAddressDialog(countryCodes, EDIT_ADDRESS, address);
  }

  @Override
  public void showLoading() {
    if (isDestroyed()) return;
    progressDialog.show();
  }

  @Override
  public void hideLoading() {
    if (isDestroyed()) return;
    progressDialog.dismiss();
  }

  @OnClick(R.id.ivNavigation_toolbar)
  public void onClickBack() {
    finish();
  }

  @OnClick(R.id.img_add)
  public void onClickAdd() {
    presenter.collectCountryCodesAndProceedToAdd();
  }

  private void showAddOrEditAddressDialog(List<CountryCode> countryCodes, int dialogType,
                                          BillingAddress address) {
    addOrEditAddressDialog =
        AddOrEditBillingAddressDialog.newInstance(address, countryCodes, dialogType);
    addOrEditAddressDialog.setActionListener(new AddOrEditBillingAddressDialog.ActionListener() {
      @Override
      public void onSaveClicked(BillingAddress address) {
        if (dialogType == ADD_ADDRESS) {
          presenter.addAddress(address);
        } else if (dialogType == EDIT_ADDRESS) {
          presenter.editAddress(address);
        }
      }

      @Override
      public void onDeleteClicked(BillingAddress address) {
        presenter.deleteAddress(address);
      }
    });
    addOrEditAddressDialog.show(this.getSupportFragmentManager(), TAG_ADDRESS_DIALOG);
  }
}
