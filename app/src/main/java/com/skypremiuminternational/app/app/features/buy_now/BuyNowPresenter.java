package com.skypremiuminternational.app.app.features.buy_now;

import android.text.TextUtils;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.exception.cart.CartLimitException;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.ApplyPromoCode;
import com.skypremiuminternational.app.domain.interactor.cart.ApplyRoyaltyPoints;
import com.skypremiuminternational.app.domain.interactor.cart.CheckLimit;
import com.skypremiuminternational.app.domain.interactor.cart.DeleteCartItem;
import com.skypremiuminternational.app.domain.interactor.cart.DeletePromoCode;
import com.skypremiuminternational.app.domain.interactor.cart.DeleteRoyaltyPoints;
import com.skypremiuminternational.app.domain.interactor.cart.GetAllCost;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartDetail;
import com.skypremiuminternational.app.domain.interactor.cart.UpdateCartItemCount;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetailFromApi;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.cart.CartDetailResponse;
import com.skypremiuminternational.app.domain.models.cart.CheckLimitResponse;
import com.skypremiuminternational.app.domain.models.cart.RoyaltyResponse;
import com.skypremiuminternational.app.domain.models.cart.UpdateItemCountRequest;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class BuyNowPresenter extends BasePresenter<BuyNowView> {

  public static final int CHECKOUT_TYPE_RENEWAL_WITH_POINTS = 1;
  public static final int CHECKOUT_TYPE_RENEWAL_WITH_CREDIT = 2;
  public static final int CHECKOUT_TYPE_ESTORE = 3;

  private final ApplyPromoCode applyPromoCode;
  private final GetAllCost getAllCost;
  private final DeletePromoCode deletePromoCode;
  private final ApplyRoyaltyPoints applyRoyaltyPoints;
  private final DeleteRoyaltyPoints deleteRoyaltyPoints;
  private final GetCartDetail getCartDetail;
  private final UpdateCartItemCount updateCartItemCount;
  private final DeleteCartItem deleteCartItem;
  private final GetUserDetailFromApi getUserDetailFromApi;
  private final CompositeSubscription compositeSubscription = new CompositeSubscription();
  private final CheckLimit checkLimit;
  private CartDetailResponse cartDetail;
  private String loyaltyPoints;
  private CartAllInformationResponse allCost;
  boolean limited;

  @Inject
  BuyNowPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                        CheckLimit checkLimit, DeleteRoyaltyPoints deleteRoyaltyPoints,
                        ApplyRoyaltyPoints applyRoyaltyPoints, DeletePromoCode deletePromoCode,
                        GetAllCost getAllCost, ApplyPromoCode applyPromoCode,
                        GetCartDetail getCartDetail, GetUserDetailFromApi getUserDetailFromApi,
                        DeleteCartItem deleteCartItem, UpdateCartItemCount updateCartItemCount) {
    super(getAppVersion, internalStorageManager);
    this.getCartDetail = getCartDetail;
    this.checkLimit = checkLimit;
    this.getUserDetailFromApi = getUserDetailFromApi;
    this.applyRoyaltyPoints = applyRoyaltyPoints;
    this.deleteRoyaltyPoints = deleteRoyaltyPoints;
    this.deletePromoCode = deletePromoCode;
    this.getAllCost = getAllCost;
    this.applyPromoCode = applyPromoCode;
    this.deleteCartItem = deleteCartItem;
    this.updateCartItemCount = updateCartItemCount;
  }

  void getLoyaltyPoints() {
    getView().showLoading("Loading ...");
    add(getUserDetailFromApi.asObservable()
        .flatMapIterable(UserDetailResponse::getCustomAttributes)
        .filter(customAttribute -> customAttribute.getAttributeCode()
            .equalsIgnoreCase("member_loyalty_point"))
        .map(CustomAttribute::getValue)
        .subscribe(royaltyPoints -> {
          BuyNowPresenter.this.loyaltyPoints = royaltyPoints;
          Timber.e("loyalty");
          //getCartDetail();
        }, throwable -> {
          getView().hideLoading();
          getView().render(new Exception("Failed to load sky dollar"));
        }));
  }

  void getCartDetail() {
    getView().showLoading("Get cart ...");
    add(getCartDetail.execute(new SingleSubscriber<CartDetailResponse>() {
      @Override
      public void onSuccess(CartDetailResponse response) {
        BuyNowPresenter.this.cartDetail = response;
        if (response.getItems() != null && response.getItems().size() > 0) {
          getAllCost();
        } else {
          getView().hideLoading();
          getView().render(cartDetail);

        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Fail to load Card Detail"));
      }
     }));
  }

  void getCartDetailAfterChangeQty() {
    getView().showLoading("Get cart ...");
    add(getCartDetail.execute(new SingleSubscriber<CartDetailResponse>() {
      @Override
      public void onSuccess(CartDetailResponse response) {
        BuyNowPresenter.this.cartDetail = response;
        if (response.getItems() != null && response.getItems().size() > 0) {
          getAllCost();
        } else {
          getView().hideLoading();
          getView().render(cartDetail);
          checkCartLimitForAdapter();
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Fail to load Card Detail"));
      }
    } ));
  }

  public void deleteShoppingCart(String cartItemId) {
    getView().showLoading("Deleting item ...");
    DeleteCartItem.Params params = new DeleteCartItem.Params(cartItemId);
    add(deleteCartItem.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean value) {
        if (value) {
          getCartDetail();
        } else {
          getView().render(new Exception("Failed to remove this item"));
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to remove this item"));
      }
    }, params));
  }

  private void getAllCost() {
    add(getAllCost.execute(new SingleSubscriber<CartAllInformationResponse>() {
      @Override
      public void onSuccess(CartAllInformationResponse value) {
        BuyNowPresenter.this.allCost = value;
        getView().render(loyaltyPoints, value.getGrandTotal());
        getView().render(cartDetail);
        getView().render(value, cartDetail.getVirtual());
        getView().hideLoading();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load costs"));
      }
    } ));
  }

  void updateItemCount(CartDetailItem item, int qty) {
    getView().showLoading("Checking...");
    UpdateItemCountRequest request = buildRequest(item, qty);
    UpdateCartItemCount.Params params =
        new UpdateCartItemCount.Params(item, request);
    add(updateCartItemCount.execute(new SingleSubscriber<CartDetailItem>() {
      @Override
      public void onSuccess(CartDetailItem value) {
        getView().hideLoading();
        getCartDetail();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        if (error instanceof CartLimitException) {
          CartLimitException cartLimitException = (CartLimitException) error;
          handleError(cartLimitException);
        } else {
          getView().render(new Exception("Failed to update item count"));
        }
      }
    }, params));

  }

  void checkLimitCountAterChangeQty(CartDetailItem item, int qty){
    getView().showLoading("Checking ....");
    add(checkLimit.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean success) {
        getView().hideLoading();
        //updateItemCountAfterChangeQty(item,qty);
      }
      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        if (error instanceof CartLimitException) {
          CartLimitException cartLimitException = (CartLimitException) error;
          handleError(cartLimitException);
        } else {
          getView().render(error);
        }
      }
    } ));
  }

  void updateItemCountAfterChangeQty(CartDetailItem item, int qty) {
    getView().showLoading("Checking...");
    UpdateItemCountRequest request = buildRequest(item, qty);
    UpdateCartItemCount.Params params =
        new UpdateCartItemCount.Params(item, request);
    add(updateCartItemCount.execute(new SingleSubscriber<CartDetailItem>() {
      @Override
      public void onSuccess(CartDetailItem value) {
        getView().hideLoading();
        //checkCartLimitForAdapter();
        getCartDetailAfterChangeQty();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        if (error instanceof CartLimitException) {

          CartLimitException cartLimitException = (CartLimitException) error;
          handleError(cartLimitException);
        } else {
          getView().render(new Exception("Failed to update item count"));
        }
      }
    }, params));

  }


  private void handleError(CartLimitException exception) {
    if (cartDetail == null) return;
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
      for (CartDetailItem cartDetailItem : cartDetail.getItems()) {
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

  private UpdateItemCountRequest buildRequest(CartDetailItem item, int qty) {
    UpdateItemCountRequest.Cart cart =
        new UpdateItemCountRequest.Cart(item.getSku(), qty, item.getName(),
            item.getPrice(),
            item.getProductType(), item.getQuoteId());
    return new UpdateItemCountRequest(cart);
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  void applyPromoCode(String promoCode) {
    getView().showLoading("Applying promo code...");
    add(applyPromoCode.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean success) {
        getView().hideLoading();
        if (success) {
          getAllCost();
        } else {
          getView().render(new Exception("Failed to apply promo code!"));
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    }, new ApplyPromoCode.Params(promoCode)));
  }

  void deletePromoCode() {
    getView().showLoading("Deleting promo code...");
    add(deletePromoCode.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean success) {
        getView().hideLoading();
        if (success) {
          getAllCost();
        } else {
          getView().render(new Exception("Failed to delete promo code"));
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to delete promo code"));
      }
    } ));
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  void deleteRoyaltyPoints() {
    getView().showLoading("Deleting sky dollar...");
    add(deleteRoyaltyPoints.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean success) {
        getView().hideLoading();
        if (success) {
          getAllCost();
        } else {
          getView().render(new Exception("Failed to delete sky dollar"));
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    } ));
  }

  void applyRoyaltyPoints(float royaltyPoints) {
    getView().showLoading("Applying sky dollar..");
    add(applyRoyaltyPoints.execute(new SingleSubscriber<RoyaltyResponse>() {
      @Override
      public void onSuccess(RoyaltyResponse response) {
        getView().hideLoading();
        if (response.success) {
          getAllCost();
        } else {
          getView().render(new Exception(response.message));
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    }, new ApplyRoyaltyPoints.Params("" + royaltyPoints)));
  }

  void checkCartLimit() {
    getView().showLoading("Checking limit...");
    add(checkLimit.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean success) {
        getView().hideLoading();
        getView().proceedToCheckout(checkCheckoutType(), checkPaymentType());
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        if (error instanceof CartLimitException) {
          CartLimitException cartLimitException = (CartLimitException) error;
          handleError(cartLimitException);
        } else {
          getView().render(error);
        }
      }
    } ));
  }

  void checkCartLimitForAdapter() {
    getView().showLoading("Checking limit...");

    add(checkLimit.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean success) {
        getView().hideLoading();

        //  getCartDetail();


      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        if (error instanceof CartLimitException) {
          CartLimitException cartLimitException = (CartLimitException) error;
          handleError(cartLimitException);
        } else {
          getView().render(error);
        }
      }
    } ));

  }

  private int checkCheckoutType() {
    if (cartDetail.getVirtual()
        && allCost.getRedeemedSkyDollars() != null
        && !allCost.getRedeemedSkyDollars().equalsIgnoreCase("0")
        && !allCost.getRedeemedSkyDollars().equalsIgnoreCase("")) {
      return CHECKOUT_TYPE_RENEWAL_WITH_POINTS;
    } else if (cartDetail.getVirtual()) {
      return CHECKOUT_TYPE_RENEWAL_WITH_CREDIT;
    }
    return CHECKOUT_TYPE_ESTORE;
  }

  public String checkPaymentType() {
    if (allCost == null || cartDetail == null) return "";

    String redeemedDollars = allCost.getRedeemedSkyDollars();
    if (!cartDetail.getVirtual() && redeemedDollars != null
        && !redeemedDollars.equalsIgnoreCase("0")
        && !redeemedDollars.equalsIgnoreCase("") && (allCost.getGrandTotal() == null
        || TextUtils.isEmpty(allCost.getGrandTotal().trim()) || allCost.getGrandTotal()
        .trim()
        .equalsIgnoreCase("0"))) {
      return "free";
    } else {
      return "sky_stripe_cards";
    }
  }
}
