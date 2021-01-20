package com.skypremiuminternational.app.app.features.checkout.steptwo;

import android.text.TextUtils;

import com.skypremiuminternational.app.app.features.cart.ShoppingCartPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.exception.cart.CartLimitException;
import com.skypremiuminternational.app.domain.interactor.cart.CheckLimit;
import com.skypremiuminternational.app.domain.interactor.cart.CreateCart;
import com.skypremiuminternational.app.domain.interactor.cart.GetAllCost;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartDetail;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartDetailBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.GetCostBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceOrder;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceOrderBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceRenewalOrder;
import com.skypremiuminternational.app.domain.interactor.cart.SetShippingAndBillingAddress;
import com.skypremiuminternational.app.domain.interactor.cart.UpdateBuyNowCart;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodes;
import com.skypremiuminternational.app.domain.interactor.ean.GetISOCountryCodes;
import com.skypremiuminternational.app.domain.interactor.user.GetCreditCards;
import com.skypremiuminternational.app.domain.interactor.user.GetDeliveryAddress;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.GetBillingAddresses;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.cart.CheckLimitResponse;
import com.skypremiuminternational.app.domain.models.cart.SetShippingAndBillingResponse;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aeindraaung on 2/4/18.
 */

public class CheckoutReviewPresenter extends BaseFragmentPresenter<CheckoutReviewView> {
  public static final int CHECKOUT_TYPE_RENEWAL_WITH_POINTS = 1;
  public static final int CHECKOUT_TYPE_RENEWAL_WITH_CREDIT = 2;
  public static final int CHECKOUT_TYPE_ESTORE = 3;
  public static final int CHECKOUT_TYPE_BUY_NOW = 4;


  private final GetCartDetail getCartDetail;
  private final GetCartDetailBuyNow getCartDetailBuyNow;
  private final GetAllCost getAllCost;
  private final GetCostBuyNow getCostBuyNow;
  private final CompositeSubscription compositeSubscription;
  private final GetCreditCards getCreditCards;
  private final PlaceOrder placeOrder;
  private final PlaceOrderBuyNow placeOrderBuyNow;
  private final CheckLimit checkLimit;
  private final SetShippingAndBillingAddress setShippingAndBillingAddress;
  private final PlaceRenewalOrder placeRenewalOrder;
  private final GetBillingAddresses getBillingAddresses;
  private final GetISOCountryCodes getISOCountryCodes;
  private final GetCountryCodes getCountryCodes;
  private final GetDeliveryAddress getDeliveryAddress;
  private final UpdateBuyNowCart updateBuyNowCart;
  private final CreateCart createCart;
  private List<CartDetailItem> cartDetailItems;
  private List<ISOCountry> countryCodeList;
  private List<CountryCode> countryCodes;
  private BillingAddress defaultBillingAddress;
  private Address defaultDeliveryAddress;
  private CreditCardResponse defaultCreditCard;
  private String email;

  @Inject
  public CheckoutReviewPresenter(GetDeliveryAddress getDeliveryAddress,
                                 PlaceRenewalOrder placeRenewalOrder,
                                 SetShippingAndBillingAddress setShippingAndBillingAddress, CheckLimit checkLimit,
                                 GetCreditCards getCreditCards, PlaceOrder placeOrder, GetCartDetail getCartDetail,
                                 GetAllCost getAllCost, GetBillingAddresses getBillingAddresses,
                                 GetISOCountryCodes getISOCountryCodes, GetCountryCodes getCountryCodes,
                                 GetCostBuyNow getCostBuyNow, PlaceOrderBuyNow placeOrderBuyNow,
                                 GetCartDetailBuyNow getCartDetailBuyNow, CreateCart createCart,
                                 UpdateBuyNowCart updateBuyNowCart) {
    this.getISOCountryCodes = getISOCountryCodes;
    this.checkLimit = checkLimit;
    this.getDeliveryAddress = getDeliveryAddress;
    this.getBillingAddresses = getBillingAddresses;
    this.placeRenewalOrder = placeRenewalOrder;
    this.setShippingAndBillingAddress = setShippingAndBillingAddress;
    this.placeOrder = placeOrder;
    this.getCreditCards = getCreditCards;
    this.getCartDetail = getCartDetail;
    this.getAllCost = getAllCost;
    this.getCountryCodes = getCountryCodes;
    this.getCostBuyNow = getCostBuyNow;
    this.placeOrderBuyNow = placeOrderBuyNow;
    this.getCartDetailBuyNow = getCartDetailBuyNow;
    this.createCart = createCart;
    this.updateBuyNowCart = updateBuyNowCart;
    compositeSubscription = new CompositeSubscription();
  }



  void getCartDetail() {
    getView().showLoading("Loading...");

    add(getCartDetail.asObservable().flatMapIterable(cartDetailResponse -> {
      CheckoutReviewPresenter.this.email = cartDetailResponse.getCustomer().getEmail();
      return cartDetailResponse.getItems();
    }).map(detailItem -> {
      detailItem.setQtyEditable(false);
      return detailItem;
    }).toList().subscribe(cartDetailItems -> {
      CheckoutReviewPresenter.this.cartDetailItems = cartDetailItems;
      getView().renderCountryCode(countryCodes);
      getView().render(cartDetailItems);

/*  20201707 - WIKI Viet Nguyen - fix bug set default delivery address
      getDefaultBillingAddress();
*/
      getAllCost();
    }, throwable -> {
      getView().render(new Exception("Failed to load cart detail"));
      getView().hideLoading();
    }));
  }
  void getCartDetailBuyNow() {
    getView().showLoading("Loading...");

    add(getCartDetailBuyNow.asObservable().flatMapIterable(cartDetailResponse -> {
      CheckoutReviewPresenter.this.email = cartDetailResponse.getCustomer().getEmail();
      return cartDetailResponse.getItems();
    }).map(detailItem -> {
      detailItem.setQtyEditable(false);
      return detailItem;
    }).toList().subscribe(cartDetailItems -> {
      CheckoutReviewPresenter.this.cartDetailItems = cartDetailItems;
      getView().renderCountryCode(countryCodes);
      getView().render(cartDetailItems);

/*  20201707 - WIKI Viet Nguyen - fix bug set default delivery address
      getDefaultBillingAddress();
*/
      getCostBuyNow();
    }, throwable -> {
      getView().render(new Exception("Failed to load cart detail"));
      getView().hideLoading();
    }));
  }

  private void getDefaultBillingAddress() {
    add(getBillingAddresses.asObservable()
        .flatMapIterable(addresses -> addresses)
        .filter(address -> address != null && address.isDefaultBilling())
        .first()
        .subscribe(address -> {
          CheckoutReviewPresenter.this.defaultBillingAddress = address;
          getDefaultDeliveryAddress();
        }, throwable -> {
          getView().hideLoading();
          getView().render(new Exception("Failed to load default address"));
        }));
  }

  private void getDefaultDeliveryAddress() {
    add(getDeliveryAddress.asObservable()
        .flatMapIterable(UserDetailResponse::getAddresses)
        .filter(address -> address.getDefaultShipping() != null && address.getDefaultShipping())
        .subscribe(address -> {
          CheckoutReviewPresenter.this.defaultDeliveryAddress = address;
/*    20201707 - WIKI Viet Nguyen - fix bug set default delivery address
        getView().render(address);
   */
          getDefaultCreditCard();
        }, throwable -> {
          getView().hideLoading();
          getView().render(new Exception("Failed to load default address"));
        }));
  }

  private void getDefaultCreditCard() {
    add(getCreditCards.asObservable()
        .flatMapIterable(creditCardResponses -> creditCardResponses)
        .filter(CreditCardResponse::getCartDefault)
        .subscribe(creditCardResponse -> {
          CheckoutReviewPresenter.this.defaultCreditCard = creditCardResponse;
          getView().render(creditCardResponse);
          getAllCost();
        }, throwable -> {
          getView().hideLoading();
          getView().render(new Exception("Failed to load default payment method"));
        }));
  }

  private void getAllCost() {
    add(getAllCost.execute(new SingleSubscriber<CartAllInformationResponse>() {
      @Override public void onSuccess(CartAllInformationResponse value) {
        getView().hideLoading();
        getView().render(value);
      }

      @Override public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load costs"));
      }
    }));
  }
  private void getCostBuyNow() {
    add(getCostBuyNow.execute(new SingleSubscriber<CartAllInformationResponse>() {
      @Override public void onSuccess(CartAllInformationResponse value) {
        getView().hideLoading();
        getView().render(value);
      }

      @Override public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load costs"));
      }
    }));
  }

  /*20201707 - WIKI Viet Nguyen - fix bug set default address*/
  void checkLimit(int checkoutType, String paymentType, Address deliveryAddress, BillingAddress billingAddress,
                  CreditCardResponse defaultCreditCard) {
    getView().showLoading("Loading...");
    add(checkLimit.execute(new SingleSubscriber<Boolean>() {
      @Override public void onSuccess(Boolean value) {
        getView().hideLoading();
        if (checkoutType == ShoppingCartPresenter.CHECKOUT_TYPE_ESTORE) {
          setShippingAndBillingAddress(paymentType, deliveryAddress, billingAddress, defaultCreditCard);
        } else {
          placeRenewalOrder(checkoutType, paymentType, billingAddress, defaultCreditCard);
        }
      }

      @Override public void onError(Throwable error) {
        getView().hideLoading();
        if (error instanceof CartLimitException) {
          CartLimitException exception = (CartLimitException) error;
          handleError(exception);
        } else {
          getView().render(new Exception("Failed to check limit"));
        }
      }
    } ));
  }

  void checkLimitBuyNow(int checkoutType, String paymentType, Address deliveryAddress, BillingAddress billingAddress,
                  CreditCardResponse defaultCreditCard) {
    getView().showLoading("Loading...");
    add(checkLimit.execute(new SingleSubscriber<Boolean>() {
      @Override public void onSuccess(Boolean value) {
        getView().hideLoading();
        if (checkoutType == CHECKOUT_TYPE_BUY_NOW) {
          setShippingAndBillingAddressBuyNow(paymentType, deliveryAddress, billingAddress, defaultCreditCard);
        } else {
          placeRenewalOrder(checkoutType, paymentType, billingAddress, defaultCreditCard);
        }
      }

      @Override public void onError(Throwable error) {
        getView().hideLoading();
        if (error instanceof CartLimitException) {
          CartLimitException exception = (CartLimitException) error;
          handleError(exception);
        } else {
          getView().render(new Exception("Failed to check limit"));
        }
      }
    }));
  }

  /*20201707 - WIKI Viet Nguyen - fix bug set default address*/
  private void placeRenewalOrder(int checkoutType, String paymentType, BillingAddress defaultBillingAddress,
                                 CreditCardResponse defaultCreditCard) {
    getView().showProcessingOrder();
    add(placeRenewalOrder.execute(new SingleSubscriber<Integer>() {
      @Override public void onSuccess(Integer orderId) {
        getView().hideProcessingOrder();
        getView().proceedToNextStep(orderId);
      }

      @Override public void onError(Throwable error) {
        getView().showProcessingOrderError();
      }
    }, new PlaceRenewalOrder.Params(checkoutType, email, defaultBillingAddress,
        defaultCreditCard, paymentType)));
  }

  /*20201707 - WIKI Viet Nguyen - fix bug set default delivery address*/
  private void setShippingAndBillingAddress(String paymentType, Address defaultDeliveryAddress,
                                            BillingAddress defaultBillingAddress,
                                            CreditCardResponse defaultCreditCard) {
    getView().showLoading("Loading...");
    SetShippingAndBillingAddress.Params params =
        new SetShippingAndBillingAddress.Params(defaultBillingAddress, defaultDeliveryAddress,
            Constants.SHIPPING_CARRIER_CODE, Constants.SHIPPING_METHOD_CODE, email);
    add(setShippingAndBillingAddress.execute(new SingleSubscriber<SetShippingAndBillingResponse>() {
      @Override public void onSuccess(SetShippingAndBillingResponse value) {
        getView().hideLoading();
        placeOrder(paymentType, defaultBillingAddress, defaultCreditCard);
      }

      @Override public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to set address"));
      }
    }, params));
  }
  private void setShippingAndBillingAddressBuyNow(String paymentType, Address defaultDeliveryAddress,
                                            BillingAddress defaultBillingAddress,
                                            CreditCardResponse defaultCreditCard) {
    getView().showLoading("Loading...");
    SetShippingAndBillingAddress.Params params =
        new SetShippingAndBillingAddress.Params(defaultBillingAddress, defaultDeliveryAddress,
            Constants.SHIPPING_CARRIER_CODE, Constants.SHIPPING_METHOD_CODE, email);
    add(setShippingAndBillingAddress.execute(new SingleSubscriber<SetShippingAndBillingResponse>() {
      @Override public void onSuccess(SetShippingAndBillingResponse value) {
        getView().hideLoading();
        placeOrderBuyNow(paymentType, defaultBillingAddress, defaultCreditCard);

      }

      @Override public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to set address buy now"));
      }
    }, params));
  }

  public void placeOrder(String paymentType, BillingAddress defaultBillingAddress,
                         CreditCardResponse defaultCreditCard) {

    if(!TextUtils.isEmpty(paymentType)&&paymentType.equalsIgnoreCase("free")){
      if(defaultCreditCard==null){
        defaultCreditCard = new CreditCardResponse();
      }
    }else {
      if (defaultCreditCard == null || defaultBillingAddress == null) return;
    }

    getView().showProcessingOrder();
    add(placeOrder.execute(new SingleSubscriber<Integer>() {
      @Override public void onSuccess(Integer orderId) {
        getView().hideProcessingOrder();
        getView().proceedToNextStep(orderId);
      }

      @Override public void onError(Throwable error) {
        getView().showProcessingOrderError();
      }
    }, new PlaceOrder.Params(email, defaultBillingAddress, defaultCreditCard, paymentType)));
  }
  public void placeOrderBuyNow(String paymentType, BillingAddress defaultBillingAddress,
                         CreditCardResponse defaultCreditCard) {
    if (defaultCreditCard == null || defaultBillingAddress == null) return;

    getView().showProcessingOrder();
    add(placeOrderBuyNow.execute(new SingleSubscriber<String>() {
      @Override public void onSuccess(String orderId) {
        createCart(Integer.parseInt(orderId.trim()));
      }

      @Override public void onError(Throwable error) {
        getView().showProcessingOrderError();
      }
    }, new PlaceOrderBuyNow.Params(email, defaultBillingAddress, defaultCreditCard, paymentType)));
  }

  void updateBuyNowCart(int orderId) {
    getView().showLoading("Get cart ...");
    add(updateBuyNowCart.execute(new SingleSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody responseBody) {
        getView().hideProcessingOrder();
        getView().hideLoading();
        getView().proceedToNextStep(orderId);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Fail to load Card Detail"));
      }
    } ));
  }
  public void createCart(Integer orderId) {
    getView().showLoading();
    add(createCart.execute(new SingleSubscriber<String>() {
      @Override
      public void onSuccess(String value) {
        updateBuyNowCart(orderId);
      }
      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
      }
    } ));
  }

  private void handleError(CartLimitException exception) {
    if (cartDetailItems == null) return;
    CheckLimitResponse.LimitError limitError;
    String errorMessage = "";
    List<CheckLimitResponse.IdMessageErrorPair> idErrorPairs = new ArrayList<>();
    for (int i = 0; i < exception.getLimitErrors().size(); i++) {
      limitError = exception.getLimitErrors().get(i);
      if (limitError.item != null && limitError.item.size() > 0) {
        for (CheckLimitResponse.AffectedId affectedId : limitError.item) {
          idErrorPairs.add(
              new CheckLimitResponse.IdMessageErrorPair(affectedId.item_id, limitError.message));
        }
      } else {
        errorMessage = limitError.message;
      }
    }
    if (idErrorPairs.size() > 0) {
      for (CartDetailItem cartDetailItem : cartDetailItems) {
        setAsAffectedIfNeeded(cartDetailItem, idErrorPairs);
      }

      getView().notifyCartItemStatusChanged();
    }
    if (errorMessage != null && !TextUtils.isEmpty(errorMessage)) {
      getView().renderErrorMessage(errorMessage);
    }
  }

  private void setAsAffectedIfNeeded(CartDetailItem detailItem,
      List<CheckLimitResponse.IdMessageErrorPair> idErrorPairs) {
    StringBuilder errorBuilder = new StringBuilder();
    for (CheckLimitResponse.IdMessageErrorPair pair : idErrorPairs) {
      if (String.valueOf(detailItem.getItemId()).equalsIgnoreCase(pair.id)) {
        errorBuilder.append(pair.errorMessage);
        errorBuilder.append(" ");
      }
    }
    if (errorBuilder.length() > 0) {
      detailItem.setErrorMessage(errorBuilder.toString());
    }
  }

  private void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  @Override public void onStop() {
    super.onStop();
    compositeSubscription.clear();
  }

  public void getISOCountryCodes() {
    add(getISOCountryCodes.execute(new SingleSubscriber<List<ISOCountry>>() {
      @Override public void onSuccess(List<ISOCountry> value) {
        CheckoutReviewPresenter.this.countryCodeList = value;
        getCartDetail();
      }

      @Override public void onError(Throwable error) {
        getView().render(new Exception("Failed to load country codes"));
      }
    } ));
  }
  public void getCountryCodesBuyNow() {
    add(getCountryCodes.execute(new SingleSubscriber<List<CountryCode>>() {
      @Override public void onSuccess(List<CountryCode> value) {
        CheckoutReviewPresenter.this.countryCodes = value;
        getCartDetailBuyNow();
      }

      @Override public void onError(Throwable error) {
        getView().render(new Exception("Failed to load country codes"));
      }
    } ));
  }

  public void getCountryCodes() {
    getView().showLoading();
    add(getCountryCodes.execute(new SingleSubscriber<List<CountryCode>>() {
      @Override
      public void onSuccess(List<CountryCode> countryCodes) {
        CheckoutReviewPresenter.this.countryCodes = countryCodes;
        getCartDetail();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    } ));
  }
}
