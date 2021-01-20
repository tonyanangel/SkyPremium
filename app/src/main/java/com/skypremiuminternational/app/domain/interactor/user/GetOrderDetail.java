package com.skypremiuminternational.app.domain.interactor.user;

import android.util.Log;

import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.myOrder.ExtraFee;
import com.skypremiuminternational.app.domain.models.myOrder.detail.ItemsItem;
import com.skypremiuminternational.app.domain.models.myOrder.detail.OrderDetailResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 3/6/18.
 */

public class GetOrderDetail extends UseCase<OrderDetailResponse, GetOrderDetail.Params> {

  @Inject
  protected GetOrderDetail(DataManager dataManager,
                           ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<OrderDetailResponse> provideObservable(Params params) {
   /* return getDataManager().getOrderDetail(String.valueOf(params.orderId))
        .flatMap(orderDetail -> Observable.from(orderDetail.getItems())
            .concatMap(itemsItem -> getDataManager().getEstoreItemBySku(itemsItem.getSku())
                .map(estoreItem -> {
                  if (estoreItem.getSku().equals(itemsItem.getSku())) {
                    itemsItem.setImageUrl(estoreItem.getImageUrl());
                    itemsItem.setCategory(estoreItem.getCategory());
                  } else {
                    throw new IllegalStateException("Skus are different");
                  }
                  return itemsItem;
                })
                .toObservable())
            .toList()
            .map(itemsItems -> {
              orderDetail.setItems(itemsItems);
              return orderDetail;
            })
            .zipWith(getDataManager().getExtraOrderDetail(params.orderId + ""),
                (orderDetailResponse, extraOrderDetail) -> {
                  if (extraOrderDetail.getExtraFee().size() > 0) {
                    ExtraFee extraFee = extraOrderDetail.getExtraFee().get(0);
                    orderDetailResponse.setShippingFee(extraFee.getShippingFee());
                    orderDetailResponse.setDeliveryFee(extraFee.getDeliveryFee());
                    orderDetailResponse.setTotalLoyaltyValueRedeemed(
                        extraFee.getTotalLoyaltyValueRedeemed());
                    orderDetailResponse.setTotalLoyaltyRenewProductRedeemed(
                        extraFee.getTotalLoyaltyRenewProductRedeemed());
                  }
                  return orderDetailResponse;
                }));*/

    /*String  letters[] = {"a"};
    Observable lettersObservable = Observable.from(letters);*/
    return getDataManager().getOrderDetail(String.valueOf(params.orderId)).flatMap(orderDetail -> Observable.from(orderDetail.getItems())
        .concatMap(itemsItem -> getDataManager()
            .getReviewDetail(new ReviewDetailRequest(getDataManager().getUserDetail().getId().toString(),itemsItem.getProductId(),itemsItem.getOrderId()))
            .map(reviewDetailResponse -> {
              Log.d("ORDER-MAP",""+reviewDetailResponse.getProductId());
              if(reviewDetailResponse.getReviewId()!=null){
                if(reviewDetailResponse.getProductId().equalsIgnoreCase(itemsItem.getProductId())){
                  itemsItem.setStatus(reviewDetailResponse.getStatus());
                  itemsItem.setReviewId(reviewDetailResponse.getReviewId());
                }else {
                  itemsItem.setStatus("0");
                }
              }else {
                itemsItem.setStatus("0");
              }
              return itemsItem;
            })).toList().map(itemsItems -> {
          orderDetail.setItems(itemsItems);
          return orderDetail;
        }).zipWith(getDataManager().getExtraOrderDetail(params.orderId + ""),
            (orderDetailResponse, extraOrderDetail) -> {
              if (extraOrderDetail.getExtraFee().size() > 0) {
                ExtraFee extraFee = extraOrderDetail.getExtraFee().get(0);
                orderDetailResponse.setShippingFee(extraFee.getShippingFee());
                orderDetailResponse.setDeliveryFee(extraFee.getDeliveryFee());
                orderDetailResponse.setTotalLoyaltyValueRedeemed(
                    extraFee.getTotalLoyaltyValueRedeemed());
                orderDetailResponse.setTotalLoyaltyRenewProductRedeemed(
                    extraFee.getTotalLoyaltyRenewProductRedeemed());
              }
              return orderDetailResponse;
            }));
        /*
            .map(review -> {
              if(review.getReviewId()!=null){
                itemsItem.setStatus("1");
                itemsItem.setReviewId(review.getReviewId());
              }else {
                itemsItem.setStatus("0");
              }
              return orderDetail;
            })));*/
            /*
        .toList()
        .map(itemsItems -> {
          orderDetail.setItems(itemsItems);
          return orderDetail;
        }).zipWith(getDataManager().getExtraOrderDetail(params.orderId + ""),
                (orderDetailResponse, extraOrderDetail) -> {
                  if (extraOrderDetail.getExtraFee().size() > 0) {
                    ExtraFee extraFee = extraOrderDetail.getExtraFee().get(0);
                    orderDetailResponse.setShippingFee(extraFee.getShippingFee());
                    orderDetailResponse.setDeliveryFee(extraFee.getDeliveryFee());
                    orderDetailResponse.setTotalLoyaltyValueRedeemed(
                        extraFee.getTotalLoyaltyValueRedeemed());
                    orderDetailResponse.setTotalLoyaltyRenewProductRedeemed(
                        extraFee.getTotalLoyaltyRenewProductRedeemed());
                  }
                  return orderDetailResponse;
                })));*/

  }

  public static class Params {
    public final int orderId;

    public Params(int orderId) {
      this.orderId = orderId;
    }
  }
}
