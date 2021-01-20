package com.skypremiuminternational.app.app.features.profile.manage_credit_card;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditCcFirstTimeDialog;
import com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditCreditCardDialog;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.phone_code.ContactCountryCode;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

import dagger.android.AndroidInjection;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.EXTRA_KEY_COUNTRY;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditCreditCardDialog.ADD_CARD;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditCreditCardDialog.EDIT_CARD;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditCreditCardDialog.EXTRA_KEY;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditCreditCardDialog.KEY_DIALOG_TYPE;

public class ManageCreditCardActivity extends BaseActivity<ManageCreditCardPresenter>
    implements ManageCreditCardView<ManageCreditCardPresenter> {

  private static final String TAG_CREDIT_CARD_DIALOG = "CreditCardDialog";
  private static final String TAG_CREDIT_CARD_FT_DIALOG = "CreditCardFTDialog";

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.rvCreditCards)
  RecyclerView rv;
  @BindView(R.id.layout_no_credit)
  RelativeLayout layoutNoCreditCards;
  @Inject
  ErrorMessageFactory errorMessageFactory;

  private CreditCardAdapter adapter;
  private ProgressDialog progressDialog;
  private List<CountryCodeCC> countryCodes;

  public static void startMe(Context context) {
    Intent intent = new Intent(context, ManageCreditCardActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_manage_credit_cards);
    ButterKnife.bind(this);
    tvTitle_toolbar.setText(getResources().getString(R.string.manage_credit_cards));
    setCreditCards();

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);

    presenter.getCreditCardsV2();
  }

  @Inject
  @Override
  public void injectPresenter(ManageCreditCardPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    finish();
  }

  void setCreditCards() {
    rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    adapter = new CreditCardAdapter();
    adapter.setItemEditClickListener(item -> {
      Bundle bundle = new Bundle();
      bundle.putInt(KEY_DIALOG_TYPE, EDIT_CARD);
      bundle.putSerializable(EXTRA_KEY, item);
      AddOrEditCreditCardDialog addOrEditCreditCardDialog = new AddOrEditCreditCardDialog();
      addOrEditCreditCardDialog.setArguments(bundle);
      addOrEditCreditCardDialog.setActionListener(new AddOrEditCreditCardDialog.ActionListener() {
        @Override
        public void onSaveClicked(CreditCardResponse creditCard) {
          if (creditCard != null) {
            presenter.updateCreditCardV2(creditCard, "edit_credit_card", creditCard.isSetAsDefault());
          }
        }

        @Override
        public void onDeleteClicked(CreditCardResponse creditCard) {
          presenter.deleteCreditCard(creditCard);
        }
      });
      addOrEditCreditCardDialog.show(getSupportFragmentManager(),
          TAG_CREDIT_CARD_DIALOG);
    });
    rv.setAdapter(adapter);
  }

  @OnClick(R.id.img_add)
  public void onClickAdd() {
    presenter.checkCardCounts();
  }

  @Override
  public void render(List<CreditCardResponse> response) {
    if (response.size() > 0) {
      rv.setVisibility(View.VISIBLE);
      layoutNoCreditCards.setVisibility(View.GONE);
      adapter.setDataList(response);
    } else {
      rv.setVisibility(View.GONE);
      layoutNoCreditCards.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void showLoading(String message) {
    if (!isDestroyed()) {
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
  public void render(Throwable error) {
    if (isDestroyed()) return;

    new AlertDialog.Builder(this)
        .setMessage(errorMessageFactory.getErrorMessage(error))
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss()).setCancelable(false)
        .show();
  }

  @Override
  public void showWarning(String message) {
    if (!this.isDestroyed()) {
      new AlertDialog.Builder(this).setMessage(message)
          .setCancelable(false)
          .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss())
          .show();
    }
  }

  @Override
  public void showAddCreditDialog(CreditCardResponse creditCard) {
    Bundle bundle = new Bundle();
    bundle.putInt(KEY_DIALOG_TYPE, ADD_CARD);
    bundle.putSerializable(EXTRA_KEY, creditCard);
    AddOrEditCreditCardDialog addOrEditCreditCardDialog = new AddOrEditCreditCardDialog();
    addOrEditCreditCardDialog.setArguments(bundle);
    addOrEditCreditCardDialog.setActionListener(new AddOrEditCreditCardDialog.ActionListener() {
      @Override
      public void onSaveClicked(CreditCardResponse creditCard) {
        if (creditCard != null) {
          presenter.updateCreditCard(creditCard, "add_credit_card", false);
        }
      }

      @Override
      public void onDeleteClicked(CreditCardResponse creditCard) {
        presenter.deleteCreditCard(creditCard);
      }
    });
    addOrEditCreditCardDialog.show(getSupportFragmentManager(),
        TAG_CREDIT_CARD_DIALOG);
  }

  @Override
  public void showAddCreditDialogV2(CreditCardResponse creditCard) {
    Bundle bundle = new Bundle();
    bundle.putInt(KEY_DIALOG_TYPE, ADD_CARD);
    bundle.putSerializable(EXTRA_KEY, creditCard);
    AddOrEditCreditCardDialog addOrEditCreditCardDialog = new AddOrEditCreditCardDialog();
    addOrEditCreditCardDialog.setArguments(bundle);
    addOrEditCreditCardDialog.setActionListener(new AddOrEditCreditCardDialog.ActionListener() {
      @Override
      public void onSaveClicked(CreditCardResponse creditCard) {
        if (creditCard != null) {
          presenter.updateCreditCardV2(creditCard, "add_credit_card", false);
        }
      }

      @Override
      public void onDeleteClicked(CreditCardResponse creditCard) {
        presenter.deleteCreditCard(creditCard);
      }
    });
    addOrEditCreditCardDialog.show(getSupportFragmentManager(),
        TAG_CREDIT_CARD_DIALOG);
  }

  @Override
  public void showAddCreditDialogFirstTime( ) {
    Bundle bundle = new Bundle();
    bundle.putInt(KEY_DIALOG_TYPE, ADD_CARD);
    bundle.putSerializable(EXTRA_KEY_COUNTRY, (Serializable) countryCodes);
    AddOrEditCcFirstTimeDialog addOrEditCcFirstTimeDialog = new AddOrEditCcFirstTimeDialog();
    addOrEditCcFirstTimeDialog.setArguments(bundle);

    addOrEditCcFirstTimeDialog.setActionListener(new AddOrEditCcFirstTimeDialog.ActionListener() {
      @Override
      public void onSaveClicked(CreditCardResponse creditCard) {
        if (creditCard != null) {
          presenter.updateCreditCardV2(creditCard, "add_credit_card", false);
        }
      }

      @Override
      public void onDeleteClicked(CreditCardResponse creditCard) {
        presenter.deleteCreditCard(creditCard);
      }
    });
    addOrEditCcFirstTimeDialog.show(getSupportFragmentManager(),
        TAG_CREDIT_CARD_FT_DIALOG);
  }

  @Override
  public void hideAddressDialog() {
    AddOrEditCreditCardDialog fragmentCreditCard =
        (AddOrEditCreditCardDialog) getSupportFragmentManager().findFragmentByTag(
            TAG_CREDIT_CARD_DIALOG);
    if (fragmentCreditCard != null && fragmentCreditCard.getDialog().isShowing()) {
      fragmentCreditCard.dismiss();
    }

    AddOrEditCcFirstTimeDialog fragmentCreditCardFT =
        (AddOrEditCcFirstTimeDialog) getSupportFragmentManager().findFragmentByTag(
            TAG_CREDIT_CARD_FT_DIALOG);
    if (fragmentCreditCardFT != null && fragmentCreditCardFT.getDialog().isShowing()) {
      fragmentCreditCardFT.dismiss();
    }
  }

  @Override
  public void notifyStatusChanged() {
    adapter.notifyDataSetChanged();
  }

  @Override
  public void initCountryCode(List<CountryCodeCC> countryCodeCCS) {
    countryCodes = countryCodeCCS;
  }
}
