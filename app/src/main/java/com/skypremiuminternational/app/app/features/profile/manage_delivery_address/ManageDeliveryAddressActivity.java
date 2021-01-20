package com.skypremiuminternational.app.app.features.profile.manage_delivery_address;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import dagger.android.AndroidInjection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.ADD_ADDRESS;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.EDIT_ADDRESS;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.EXTRA_KEY_COUNTRY;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.EXTRA_KEY_DIALOG_TYPE;

/**
 * Created by aeindraaung on 2/19/18.
 */

public class ManageDeliveryAddressActivity extends BaseActivity<ManageDeliveryAddressPresenter>
    implements ManageDeliveryAddressView<ManageDeliveryAddressPresenter> {

  private static final String TAG_ADDRESS_DIALOG = "AddressDialog";

  @BindView(R.id.tvTitle_toolbar)
  TextView txtTitleToolbar;
  @BindView(R.id.rvDeliveryAddress)
  RecyclerView rvDeliveryAddress;
  @BindView(R.id.layout_no_delivery_address)
  RelativeLayout layoutNoAddress;
  @Inject
  ErrorMessageFactory errorMessageFactory;

  private ManageDeliveryAddressAdapter adapter;
  private ProgressDialog progressDialog;
  private UserDetailResponse userDetailResponse;
  private List<CountryCode> countryCodes = new ArrayList<>();

  private List<ISOCountry> countryCodesISO = new ArrayList<>();

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, ManageDeliveryAddressActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_manage_delivery_address);
    ButterKnife.bind(this);
    txtTitleToolbar.setText(getText(R.string.title_manage_delivery_address));
    rvDeliveryAddress.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    adapter = new ManageDeliveryAddressAdapter();

    setupRecyclerAdapter();
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);

    presenter.getCountryCodes();
  }

  private void setupRecyclerAdapter() {
    adapter.setItemEditClickListener(item -> {
      Bundle bundle = new Bundle();
      bundle.putInt(EXTRA_KEY_DIALOG_TYPE, EDIT_ADDRESS);
      bundle.putSerializable(EXTRA_KEY_COUNTRY, (Serializable) countryCodes);
      final AddOrEditAddressDialog dialog = AddOrEditAddressDialog.newInstance(item,countryCodes);
      dialog.setArguments(bundle);
      dialog.setActionListener(new AddOrEditAddressDialog.ActionListener() {
        @Override
        public void onSaveClicked(Address address) {
          if (address != null) {
            if (dialog.isDefault()) {
              for (Address address1 : userDetailResponse.getAddresses()) {
                if (address.getId() == address1.getId()) {
                  address1.setDefaultBilling(true);
                  address1.setDefaultShipping(true);
                } else {
                  address1.setDefaultBilling(false);
                  address1.setDefaultShipping(false);
                }
              }
            } else {
              address.setDefaultBilling(false);
              address.setDefaultShipping(false);
            }
            presenter.updateAddress(address, false,countryCodesISO,countryCodes);
          }
        }

        @Override
        public void onDeleteClicked(Address address) {
          presenter.deleteAddress(address);
        }
      });

      dialog.show(getSupportFragmentManager(), TAG_ADDRESS_DIALOG);
    });
    rvDeliveryAddress.setAdapter(adapter);
  }

  @Override
  public void showLoading(String message) {
    if (!this.isDestroyed()) {
      progressDialog.setMessage(message);
      progressDialog.show();
    }
  }

  @Override
  public void hideLoading() {
    if (!this.isDestroyed()) {
      progressDialog.dismiss();
    }
  }

  @Override
  public void render(UserDetailResponse response) {
    if (response != null) {
      userDetailResponse = response;
      if (userDetailResponse.getAddresses().size() > 0) {
        layoutNoAddress.setVisibility(View.GONE);
        rvDeliveryAddress.setVisibility(View.VISIBLE);
        adapter.setData(userDetailResponse.getAddresses());
      } else {
        layoutNoAddress.setVisibility(View.VISIBLE);
        rvDeliveryAddress.setVisibility(View.GONE);
      }
    } else {
      layoutNoAddress.setVisibility(View.VISIBLE);
      rvDeliveryAddress.setVisibility(View.GONE);
    }
  }

  @Override
  public void render(Throwable error) {
    if (isDestroyed()) return;

    new AlertDialog.Builder(this)
        .setMessage(errorMessageFactory.getErrorMessage(error))
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss())
        .setCancelable(false)
        .show();
  }

  @Override
  public void render(String error) {
    if (isDestroyed()) return;

    new AlertDialog.Builder(this)
            .setMessage(error)
            .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss())
            .setCancelable(false)
            .show();
  }

  @Override
  public void notifyStatusChanged() {
    adapter.notifyDataSetChanged();
  }

  @Override
  public void hideAddressDialog() {
    AddOrEditAddressDialog fragment =
        (AddOrEditAddressDialog) getSupportFragmentManager().findFragmentByTag(
            TAG_ADDRESS_DIALOG);
    if (fragment != null && fragment.getDialog().isShowing()) {
      fragment.dismiss();
    }
  }

  @Override
  public void showAddAddressDialog(Address address) {
    Bundle bundle = new Bundle();
    bundle.putInt(EXTRA_KEY_DIALOG_TYPE, ADD_ADDRESS);
    bundle.putSerializable(EXTRA_KEY_COUNTRY, (Serializable) countryCodes);
    final AddOrEditAddressDialog addOrEditAddressDialog =
        AddOrEditAddressDialog.newInstance(address,countryCodes);
    addOrEditAddressDialog.setArguments(bundle);
    addOrEditAddressDialog.setActionListener(new AddOrEditAddressDialog.ActionListener() {
      @Override
      public void onSaveClicked(Address address) {
        if (address != null) {
          if (addOrEditAddressDialog.isDefault()) {
            address.setDefaultBilling(true);
            address.setDefaultShipping(true);
            for (Address address1 : userDetailResponse.getAddresses()) {
              address1.setDefaultBilling(false);
              address1.setDefaultShipping(false);
            }
          } else {
            address.setDefaultBilling(false);
            address.setDefaultShipping(false);
          }
          presenter.updateAddress(address, true,countryCodesISO,countryCodes);
        }
      }

      @Override
      public void onDeleteClicked(Address address) {
        presenter.deleteAddress(address);
      }
    });
    addOrEditAddressDialog.show(this.getSupportFragmentManager(), TAG_ADDRESS_DIALOG);
  }

  @Override
  public void render(List<CountryCode> value,List<ISOCountry> value1) {
    countryCodes = value;
    countryCodesISO = value1;
    if(value != null ){
      adapter.setCountryCodes(value);
    }
    presenter.getDeliveryAddresses();
  }

  @OnClick(R.id.ivNavigation_toolbar)
  public void onClickBack() {
    finish();
  }

  @OnClick(R.id.img_add)
  public void onClickAdd() {
    presenter.addAddress();
  }

  @Inject
  @Override
  public void injectPresenter(ManageDeliveryAddressPresenter presenter) {
    super.injectPresenter(presenter);
  }
}
