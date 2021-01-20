package com.skypremiuminternational.app.app.features.checkout.stepone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartPresenter;
import com.skypremiuminternational.app.app.features.checkout.CheckoutActivity;
import com.skypremiuminternational.app.app.features.profile.billingaddress.AddOrEditBillingAddressDialog;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DataUtils;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.view.SkyTextInputEditEventLayout;
import com.skypremiuminternational.app.app.view.SkyTextInputWithouLineLayout;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import dagger.android.support.AndroidSupportInjection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.features.checkout.CheckoutActivity.STEP_TWO;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.ADD_ADDRESS;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.EDIT_ADDRESS;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.EXTRA_KEY_COUNTRY;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditCreditCardDialog.ADD_CARD;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditCreditCardDialog.KEY_DIALOG_TYPE;

/**
 * Created by aeindraaung on 2/2/18.
 */

public class CheckoutDeliveryFragment extends BaseFragment<CheckoutDeliveryPresenter>
    implements CheckoutDeliveryView<CheckoutDeliveryPresenter> {
  private static final String TAG_ADDRESS_DIALOG = "AddressDialog";
  private static final String TAG_CREDIT_CARD_DIALOG = "CreditCardDialog";
  private static final String TAG_CREDIT_CARD_FT_DIALOG = "CreditCardFTDialog";

  @BindView(R.id.layout_no_billing_address)
  ViewGroup layoutNoBillingAddress;
  @BindView(R.id.img_add_visa)
  ImageView ivAddVisa;
  @BindView(R.id.scrollView)
  NestedScrollView scrollView;
  @BindView(R.id.layout_choose_credit_card)
  ViewGroup layoutCreditCard;
  @BindView(R.id.recycler_address)
  RecyclerView recyclerAddress;
  @BindView(R.id.recycler_credit)
  RecyclerView recyclerCredit;
  @BindView(R.id.layout_no_address)
  RelativeLayout layoutNoAddress;
  @BindView(R.id.layout_no_credit_card)
  RelativeLayout layoutNoCreditCards;
  @BindView(R.id.recycler_billing_address)
  RecyclerView recyclerBillingAddress;
  @BindView(R.id.ll_selection_not_required)
  LinearLayout llSelectionNotRequired;
  @BindView(R.id.layout_choose_delivery_address)
  FrameLayout layoutchosedeliveryaddress;
  @BindView(R.id.tv_next)
  TextView tvNext;
  @Inject
  ErrorMessageFactory errorMessageFactory;

  private CheckoutVisaCardAdapter visaAdapter;
  private CheckoutDeliveryAddressAdapter addressAdapter;
  private BillingAddressAdapter billingAddressAdapter;
  private UserDetailResponse userDetailResponse;
  private ProgressDialog progressDialog;
  private List<CountryCode> countryCodes = new ArrayList<>();
  private List<CountryCodeCC> countryCodesCc = new ArrayList<>();
  private boolean scrollToEditCreditCard;
  private boolean isFullRedemption = false;
  private boolean isMembership = false;
  private boolean userHasRoyaltyPoints = false;
  private int checkoutType;
  private AddOrEditBillingAddressDialog addOrEditBillingAddressDialog;
  private List<ISOCountry> countryCodesISO = new ArrayList<>();
  private Address deliveryAddress;
  private BillingAddress billingAddress;
  private List<BillingAddress> billingAddressList = new ArrayList<>();
  private CreditCardResponse creditCard;
  private List<CreditCardResponse> cardResponseList = new ArrayList<>();


/*  20201707 - WIKI Viet Nguyen - fix bug set default delivery address
    public static CheckoutDeliveryFragment newInstance(boolean scrollToEditCreditCard,
                                                     int checkoutType, boolean isFullRedemption,boolean isMembership) {
    CheckoutDeliveryFragment fragment = new CheckoutDeliveryFragment();
    fragment.scrollToEditCreditCard = scrollToEditCreditCard;
    fragment.checkoutType = checkoutType;
    fragment.isFullRedemption = isFullRedemption;
    fragment.isMembership = isMembership;
    return fragment;
  }*/

  public static CheckoutDeliveryFragment newInstance(boolean scrollToEditCreditCard,
                                                     int checkoutType, boolean isFullRedemption,boolean isMembership) {
    CheckoutDeliveryFragment fragment = new CheckoutDeliveryFragment();
    fragment.scrollToEditCreditCard = scrollToEditCreditCard;
    fragment.checkoutType = checkoutType;
    fragment.isFullRedemption = isFullRedemption;
    fragment.isMembership = isMembership;
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
    presenter.getCountry();
    billingAddressList.clear();
    setupRecyclerView();

    progressDialog = new ProgressDialog(getActivity());
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);

    if (checkoutType == ShoppingCartPresenter.CHECKOUT_TYPE_RENEWAL_WITH_POINTS) {
      recyclerCredit.setVisibility(View.GONE);
      ivAddVisa.setVisibility(View.GONE);
    }else if(checkoutType == ShoppingCartPresenter.CHECKOUT_TYPE_BUY_NOW){
      recyclerCredit.setVisibility(View.VISIBLE);
      ivAddVisa.setVisibility(View.VISIBLE);
    } else {
      recyclerCredit.setVisibility(View.VISIBLE);
      ivAddVisa.setVisibility(View.VISIBLE);
    }

    // Hide credit card selection if total cost if fully offset by SKY$
    if (isFullRedemption) {
      recyclerCredit.setVisibility(View.GONE);
      llSelectionNotRequired.setVisibility(View.VISIBLE);
      layoutCreditCard.setVisibility(View.GONE);
    }
    presenter.getCountryCodes();
    presenter.getBillingAddress();


  }


  void setupRecyclerView() {
    billingAddressAdapter = new BillingAddressAdapter(true);
    recyclerBillingAddress.setNestedScrollingEnabled(false);
    billingAddressAdapter.setActionListener(new BillingAddressAdapter.ActionListener() {
      @Override
      public void onEditClicked(BillingAddress address) {
        presenter.collectCountryCodesAndProceedToEdit(address);
      }

      @Override
      public void onItemClicked(BillingAddress address) {
/*  20201707 - WIKI Viet Nguyen - fix bug set default billing address
        if (!address.isDefaultBilling()) {
          presenter.setDefaultBillingAddress(address);
        }*/
        billingAddress = address;
        billingAddressAdapter.notifyDataSetChanged();
      }
    });
    recyclerBillingAddress.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    recyclerBillingAddress.setAdapter(billingAddressAdapter);

    recyclerAddress.setNestedScrollingEnabled(false);
    recyclerAddress.setLayoutManager(new LinearLayoutManager(getActivity()));
    addressAdapter = new CheckoutDeliveryAddressAdapter();
    addressAdapter.setItemClickListener(address -> {

/*  20201707 - WIKI Viet Nguyen - fix bug set default delivery address
      if (!address.isDefault()) {
        changeAddDefaultDialog(address);
      }*/
      deliveryAddress = address;
      for(CustomAttribute customAttribute : deliveryAddress.getCustomAttributes()){
        if(customAttribute.getAttributeCode().equalsIgnoreCase("unit_number")){
          deliveryAddress.setUnitNumber(customAttribute.getValue());
        }
      }
    });
    addressAdapter.setItemEditClickListener(item -> {
      Bundle bundle = new Bundle();
      bundle.putInt(
          AddOrEditAddressDialog.EXTRA_KEY_DIALOG_TYPE, AddOrEditAddressDialog.EDIT_ADDRESS);
      bundle.putSerializable(AddOrEditAddressDialog.EXTRA_KEY_COUNTRY, (Serializable) countryCodes);
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

      dialog.show(getActivity().getSupportFragmentManager(), TAG_ADDRESS_DIALOG);
    });
    recyclerAddress.setAdapter(addressAdapter);
    recyclerAddress.setHasFixedSize(true);

    recyclerCredit.setNestedScrollingEnabled(false);
    recyclerCredit.setLayoutManager(new LinearLayoutManager(getActivity()));
    visaAdapter = new CheckoutVisaCardAdapter();
    visaAdapter.setItemClickListener(item -> {
      if (!item.getCartDefault()) {
/*  20201707 - WIKI Viet Nguyen - fix bug set default delivery address
        editDefaultCreditCardDialog(item.getId());
*/
        creditCard = item;
      }
    });
    visaAdapter.setItemEditClickListener(item -> {
      Bundle bundle = new Bundle();
      bundle.putInt(AddOrEditCreditCardDialog.KEY_DIALOG_TYPE, AddOrEditCreditCardDialog.EDIT_CARD);
      bundle.putSerializable(AddOrEditCreditCardDialog.EXTRA_KEY, item);
      AddOrEditCreditCardDialog addOrEditCreditCardDialog = new AddOrEditCreditCardDialog();
      addOrEditCreditCardDialog.setArguments(bundle);
      addOrEditCreditCardDialog.setActionListener(new AddOrEditCreditCardDialog.ActionListener() {
        @Override
        public void onSaveClicked(CreditCardResponse creditCard) {
          if (creditCard != null) {
            presenter.updateCreditCardV2(creditCard, "edit_credit_card",creditCard.isSetAsDefault());
          }
        }

        @Override
        public void onDeleteClicked(CreditCardResponse creditCard) {
          presenter.deleteCreditCard(creditCard);
        }
      });
      addOrEditCreditCardDialog.show(getActivity().getSupportFragmentManager(),
          TAG_CREDIT_CARD_DIALOG);
    });
    recyclerCredit.setAdapter(visaAdapter);
    recyclerCredit.setHasFixedSize(true);
  }

  private void changeAddDefaultDialog(final Address address) {
    if (address != null) {
      for (Address address1 : userDetailResponse.getAddresses()) {
        if (address.getId() == address1.getId()) {
          address1.setDefaultBilling(true);
          address1.setDefaultShipping(true);
        } else {
          address1.setDefaultBilling(false);
          address1.setDefaultShipping(false);
        }
      }
      presenter.updateAddress(address, false,countryCodesISO,countryCodes);
    }
    /*AlertDialog.Builder builder;
    builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(getString(R.string.que_default_address))
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            if (address != null) {
              for (Address address1 : userDetailResponse.getAddresses()) {
                if (address.getId() == address1.getId()) {
                  address1.setDefaultBilling(true);
                  address1.setDefaultShipping(true);
                } else {
                  address1.setDefaultBilling(false);
                  address1.setDefaultShipping(false);
                }
              }
              presenter.updateAddress(address);
            }
            dialog.cancel();
          }
        })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        })
        .show();*/
  }

  private void editDefaultCreditCardDialog(final String id) {
    if (id != null) {
      if (!TextUtils.isEmpty(id)) {
        presenter.setDefaultCreditCard(id);
      }
    }
    /*AlertDialog.Builder builder;
    builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(getString(R.string.que_default_credit_card))
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            if (id != null) {
              if (!TextUtils.isEmpty(id)) {
                presenter.setDefaultCreditCard(id);
              }
            }
            dialog.cancel();
          }
        })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        })
        .show();*/
  }

  @Override
  public void render(UserDetailResponse response) {
    if (response != null) {
      userDetailResponse = response;
      if (userDetailResponse.getAddresses().size() > 0) {
        recyclerAddress.setVisibility(View.VISIBLE);
        layoutNoAddress.setVisibility(View.GONE);
        for (int i = 0; i < userDetailResponse.getAddresses().size(); i++){
          if (App.idDeliveryAddress == null){
            if (userDetailResponse.getAddresses().get(i).isDefault()){
              setItemDeliveryAddressSelected(i);
            }
          } else {
            int idTemp = -1;
            try {
              idTemp = Integer.parseInt(App.idDeliveryAddress);
            } catch (Exception ex){}
            if (userDetailResponse.getAddresses().get(i).getId() == idTemp){
              setItemDeliveryAddressSelected(i);
            }
          }
        }
        addressAdapter.setDeliveryAddress(userDetailResponse.getAddresses());
        tvNext.setVisibility(View.VISIBLE);
      } else {
        recyclerAddress.setVisibility(View.GONE);
        layoutNoAddress.setVisibility(View.VISIBLE);
        tvNext.setVisibility(View.VISIBLE);
      }

    } else {
      recyclerAddress.setVisibility(View.GONE);
      layoutNoAddress.setVisibility(View.VISIBLE);
      tvNext.setVisibility(View.VISIBLE);
    }
  }

  private void setItemDeliveryAddressSelected(int position){
    userDetailResponse.getAddresses().get(position).setDeliveryAddressSelected(true);
    deliveryAddress = userDetailResponse.getAddresses().get(position);
    for(CustomAttribute customAttribute : deliveryAddress.getCustomAttributes()){
      if(customAttribute.getAttributeCode().equalsIgnoreCase("unit_number")){
        deliveryAddress.setUnitNumber(customAttribute.getValue());
      }
    }
    App.idDeliveryAddress = String.valueOf(userDetailResponse.getAddresses().get(position).getId());
    addressAdapter.setIdTemp(position);
  }

  @Override
  public void render(Throwable error) {
    Toast.makeText(getActivity(), errorMessageFactory.getErrorMessage(error), Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public void render(List<ISOCountry> value, boolean success) {
    countryCodesISO = value;
  }

  @Override
  public void render(String error) {
    Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT)
            .show();
  }

  @Override
  public void render(List<CreditCardResponse> response) {
    if (checkoutType == ShoppingCartPresenter.CHECKOUT_TYPE_RENEWAL_WITH_POINTS) {
      return;
    }
    // If there are cards
    cardResponseList = response;
    if (cardResponseList.size() > 0) {
      // If price is not fully offset
      if (!isFullRedemption) {
        layoutNoCreditCards.setVisibility(View.GONE);
        recyclerCredit.setVisibility(View.VISIBLE);
        for (int i = 0; i < cardResponseList.size(); i++){
          if (App.idCard == null){
            if (cardResponseList.get(i).getCartDefault()){
              setItemCardSelected(i);
            }
          } else {
            if (App.idCard.equals(cardResponseList.get(i).getId())){
              setItemCardSelected(i);
            }
          }
        }
        visaAdapter.setVisaList(cardResponseList);
        if (scrollToEditCreditCard) {
          new Handler().postDelayed(() -> {
            if (!getActivity().isDestroyed()) {
              scrollView.scrollTo(0, layoutCreditCard.getTop());
            }
          }, 1000);
        }
      } else {
        for (int i = 0; i < cardResponseList.size(); i++){
          if (App.idCard == null){
            if (cardResponseList.get(i).getCartDefault()){
              setItemCardSelected(i);
            }
          } else {
            if (App.idCard.equals(cardResponseList.get(i).getId())){
              setItemCardSelected(i);
            }
          }
        }
        recyclerCredit.setVisibility(View.GONE);
        llSelectionNotRequired.setVisibility(View.VISIBLE);
      }
    } else {
      layoutNoCreditCards.setVisibility(View.VISIBLE);
      recyclerCredit.setVisibility(View.GONE);
    }
  }

  private void setItemCardSelected(int i) {
    cardResponseList.get(i).setVisaSelected(true);
    creditCard = cardResponseList.get(i);
    visaAdapter.setItemVisaTemp(i);
    App.idCard = cardResponseList.get(i).getId();
  }

  @Override
  public void showLoading(String message) {
    if (!getActivity().isDestroyed()) {
      progressDialog.setMessage(message);
      progressDialog.show();
    }
  }

  @Override
  public void hideLoading() {
    if (!getActivity().isDestroyed()) {
      progressDialog.dismiss();
    }
  }

  @Override
  public void notifyStatusChanged() {
    addressAdapter.notifyDataSetChanged();
    visaAdapter.notifyDataSetChanged();
  }

  @Override
  public void hideAddressDialog() {
    AddOrEditAddressDialog fragment =
        (AddOrEditAddressDialog) getActivity().getSupportFragmentManager().findFragmentByTag(
            TAG_ADDRESS_DIALOG);
    if (fragment != null && fragment.getDialog().isShowing()) {
      fragment.dismiss();
    }

    AddOrEditCreditCardDialog fragmentCreditCard =
        (AddOrEditCreditCardDialog) getActivity().getSupportFragmentManager().findFragmentByTag(
            TAG_CREDIT_CARD_DIALOG);
    if (fragmentCreditCard != null && fragmentCreditCard.getDialog().isShowing()) {
      fragmentCreditCard.dismiss();
    }

    AddOrEditCcFirstTimeDialog fragmentCreditCardFt =
        (AddOrEditCcFirstTimeDialog) getActivity().getSupportFragmentManager().findFragmentByTag(
            TAG_CREDIT_CARD_FT_DIALOG);
    if (fragmentCreditCardFt != null && fragmentCreditCardFt.getDialog().isShowing()) {
      fragmentCreditCardFt.dismiss();
    }
  }

  @Override
  public void showAddAddressDialog(Address address) {
    Bundle bundle = new Bundle();
    bundle.putInt(AddOrEditAddressDialog.EXTRA_KEY_DIALOG_TYPE, AddOrEditAddressDialog.ADD_ADDRESS);
    bundle.putSerializable(AddOrEditAddressDialog.EXTRA_KEY_COUNTRY, (Serializable) countryCodes);
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
    addOrEditAddressDialog.show(getActivity().getSupportFragmentManager(), TAG_ADDRESS_DIALOG);
  }

  @Override
  public void showAddCreditDialog(CreditCardResponse creditCardResponse) {
    Bundle bundle = new Bundle();
    bundle.putInt(AddOrEditCreditCardDialog.KEY_DIALOG_TYPE, AddOrEditCreditCardDialog.ADD_CARD);
    bundle.putSerializable(AddOrEditCreditCardDialog.EXTRA_KEY, creditCardResponse);
    AddOrEditCreditCardDialog addOrEditCreditCardDialog = new AddOrEditCreditCardDialog();
    addOrEditCreditCardDialog.setArguments(bundle);
    addOrEditCreditCardDialog.setActionListener(new AddOrEditCreditCardDialog.ActionListener() {
      @Override
      public void onSaveClicked(CreditCardResponse creditCard) {
        if (creditCard != null) {
          presenter.updateCreditCardV2(creditCard, "add_credit_card",true);
        }
      }

      @Override
      public void onDeleteClicked(CreditCardResponse creditCard) {
        presenter.deleteCreditCard(creditCard);
      }
    });
    addOrEditCreditCardDialog.show(getActivity().getSupportFragmentManager(),
        TAG_CREDIT_CARD_DIALOG);
  }

  @Override
  public void showAddCreditDialogFirstTime() {
    Bundle bundle = new Bundle();
    bundle.putInt(KEY_DIALOG_TYPE, ADD_CARD);
    bundle.putSerializable(EXTRA_KEY_COUNTRY, (Serializable) countryCodesCc);
    AddOrEditCcFirstTimeDialog addOrEditCcFirstTimeDialog = new AddOrEditCcFirstTimeDialog();
    addOrEditCcFirstTimeDialog.setArguments(bundle);

    addOrEditCcFirstTimeDialog.setActionListener(new AddOrEditCcFirstTimeDialog.ActionListener() {
      @Override
      public void onSaveClicked(CreditCardResponse creditCard) {
        if (creditCard != null) {
          presenter.updateCreditCardV2(creditCard, "add_credit_card", true);
        }
      }

      @Override
      public void onDeleteClicked(CreditCardResponse creditCard) {
        presenter.deleteCreditCard(creditCard);
      }
    });
    addOrEditCcFirstTimeDialog.show(getActivity().getSupportFragmentManager(),
        TAG_CREDIT_CARD_FT_DIALOG);
  }

  @Override
  public void showUpdateFail(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showWarning(String message) {
    if (!getActivity().isDestroyed()) {
      new AlertDialog.Builder(getContext()).setMessage(message)
          .setCancelable(false)
          .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss())
          .show();
    }
  }

  @Override
  public void proceedToNextStep() {
    ((CheckoutActivity) getActivity()).gotoStep(STEP_TWO, deliveryAddress, billingAddress, creditCard);
  }

  @Override
  public void renderCountryCode(List<CountryCode> countryCodeList) {
    countryCodes = countryCodeList;
    addressAdapter.setCountryCodes(countryCodes);
  }

  @Override
  public void renderBillingAddress(List<BillingAddress> value) {
    if (addOrEditBillingAddressDialog != null) addOrEditBillingAddressDialog.dismiss();
    billingAddressList = value;
    if (billingAddressList != null && billingAddressList.size() > 0) {
      layoutNoBillingAddress.setVisibility(View.GONE);
      recyclerBillingAddress.setVisibility(View.VISIBLE);
      billingAddressAdapter.setCountryCodes(new Gson().fromJson(new DataUtils(getContext()).readObject("CountryCode"),
              new TypeToken<List<CountryCode>>() {
              }.getType()));
/*  20201707 - WIKI Viet Nguyen - fix bug set default billing address*/
      for (int i = 0; i < billingAddressList.size(); i++){
        if (App.idBillingAddress == null){
          if (billingAddressList.get(i).isDefaultBilling()){
            setItemBillingAddressSelected(i);
          }
        } else {
          if (billingAddressList.get(i).getId().equals(App.idBillingAddress)){
            setItemBillingAddressSelected(i);
          }
        }
      }
      billingAddressAdapter.setBillingAddresses(billingAddressList);
    } else {
      layoutNoBillingAddress.setVisibility(View.VISIBLE);
      recyclerBillingAddress.setVisibility(View.GONE);
    }
  }

  private void setItemBillingAddressSelected(int position){
    billingAddressList.get(position).setBillingAddressSelected(true);
    billingAddressAdapter.setPositionSelected(position);
    billingAddress = billingAddressList.get(position);
    App.idBillingAddress = billingAddressList.get(position).getId();
  }

  @Override
  public void showEditBillingAddressDialog(List<CountryCode> countryCodes, BillingAddress address) {
    showAddOrEditAddressDialog(countryCodes, EDIT_ADDRESS, address);
  }

  @Override
  public void showAddBillingAddressDialog(List<CountryCode> countryCodes) {
    showAddOrEditAddressDialog(countryCodes, ADD_ADDRESS, null);
  }

  @Override
  public void requestBillingAddressSuccess() {
    presenter.validate(checkoutType,addressAdapter.isSelectedDefault(),billingAddressAdapter.isSelectedDefault(),visaAdapter.getVisaCartList(),isFullRedemption);
  }

  @Override
  public void requestBillingAddressError(String error) {
    CommonUtils.showToast(getActivity(),error,Toast.LENGTH_SHORT);
  }

  @Override
  public void initCountryCodeCc(List<CountryCodeCC> countryCodesCc) {
    this.countryCodesCc = countryCodesCc;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_checkout_delivery;
  }

  @Inject
  @Override
  public void injectPresenter(CheckoutDeliveryPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.img_add_billing_address)
  public void onClickAddBillingAddress() {
    presenter.collectCountryCodesAndProceedToAdd();
  }

  @OnClick(R.id.img_add_delivery_address)
  public void onClickAddAddress() {
    presenter.addAddress();
  }

  @OnClick(R.id.img_add_visa)
  public void onClickAddVisa() {
    presenter.checkCardsCount();
  }


  @OnClick(R.id.tv_next)
  public void onClickNext() {
    Map<Object, Object> params = new HashMap<>();
    if(billingAddress!=null){
      params.put(Constants.BILLING_ADDRESS_ID, billingAddress.getId());
      presenter.requestBillingAddressPayment(CommonUtils.requestBody(params));
    }else {
      showWarning("Please select a billing address to proceed");
    }
  }

  private void showAddOrEditAddressDialog(List<CountryCode> countryCodes, int dialogType,
                                          BillingAddress address) {
    addOrEditBillingAddressDialog =
        AddOrEditBillingAddressDialog.newInstance(address, countryCodes, dialogType);
    addOrEditBillingAddressDialog.setActionListener(
        new AddOrEditBillingAddressDialog.ActionListener() {
          @Override
          public void onSaveClicked(BillingAddress address) {
            if (dialogType == ADD_ADDRESS) {
              presenter.addBillingAddress(address);
            } else if (dialogType == EDIT_ADDRESS) {
              presenter.editBillingAddress(address);
            }
          }

          @Override
          public void onDeleteClicked(BillingAddress address) {
            presenter.deleteBillingAddress(address);
          }
        });
    addOrEditBillingAddressDialog.show(getChildFragmentManager(), TAG_ADDRESS_DIALOG);
  }


}
