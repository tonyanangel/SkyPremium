package com.skypremiuminternational.app.data.mapper;

import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

public class TourismFeeAggregator {

  @Inject
  public TourismFeeAggregator() {

  }

  public List<TourismFee> aggregate(List<TourismFee> rawTourismFees) {
    List<TourismFee> refinedTourismFees = new ArrayList<>();
    Observable.from(rawTourismFees)
        .groupBy(TourismFee::feesType)
        .concatMap(
            (Func1<GroupedObservable<String, TourismFee>, Observable<TourismFee>>)
                (GroupedObservable<String, TourismFee> groupedObservable) ->
                    groupedObservable
                        .reduce((oldValue, newValue) -> {
                          double price = 0;
                          if (oldValue != null && Validator.isTextValid(oldValue.price())) {
                            price += Double.parseDouble(oldValue.price());
                          }
                          String currency = "";
                          if (newValue.currency() != null) {
                            currency = newValue.currency();
                          }

                          //20200327 - WIKI Toan Tran - Disable
                          //price += Double.parseDouble(newValue.price())

                            // 20200327 - WIKI Toan Tran - Disable newValue ->oldValue
                          return TourismFee.builder()
                              .currency(currency)
                              .feesType(oldValue.feesType())
                              .price(String.valueOf(price))
                              .build();
                        }))
        .toBlocking()
        .subscribe(refinedTourismFees::add);
    return refinedTourismFees;
  }
}
