package com.skypremiuminternational.app.data.mapper;

import android.text.TextUtils;

import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.data.model.ean.availability.FeesItem;
import com.skypremiuminternational.app.data.model.ean.availability.NightlyItem;
import com.skypremiuminternational.app.data.model.ean.availability.Occupancy;
import com.skypremiuminternational.app.data.model.ean.availability.Totals;
import com.skypremiuminternational.app.data.model.ean.pricecheck.PriceCheckResponse;
import com.skypremiuminternational.app.data.utl.OccupancyArranger;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckResult;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckSummary;
import com.skypremiuminternational.app.domain.models.booking.PriceNTaxes;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;
import com.skypremiuminternational.app.domain.models.ean.Child;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class PriceCheckMapper {

  private final OccupancyArranger occupancyArranger;
  private final TourismFeeAggregator tourismFeeAggregator;

  @Inject
  public PriceCheckMapper(OccupancyArranger occupancyArranger, TourismFeeAggregator tourismFeeAggregator) {
    this.occupancyArranger = occupancyArranger;
    this.tourismFeeAggregator = tourismFeeAggregator;
  }

  public PriceCheckResult map(PriceCheckResponse response, int roomCount, int adultCount,
                              List<Child> children) {

    double subTotal = 0;
    double grandTotal = 0;

    double mandatoryTax = 0;
    String currencymandatoryTax = "";

    double tourismFee = 0;
    String bookingLink;
    String tourismFeeCurrency = "";
    String skyDollar = "";
    Map<String, PriceNTaxes> priceNTaxesMap = new HashMap<>();
    List<TourismFee> tourismFees = new ArrayList<>();
    Map<String, Occupancy> occupancyMap = response.getOccupancies();
    if (occupancyMap != null) {

      List<String> groupedOccupancies =
          occupancyArranger.groupOccupancies(roomCount, adultCount, children);
      for (String group : groupedOccupancies) {
        Occupancy occupancy = occupancyMap.get(group);
        if (occupancy != null) {
          if (occupancy.getTotals() != null) {
            if (occupancy.getTotals().getInclusive() != null) {
              final double value = parseDouble(
                  occupancy.getTotals().getInclusive().getBillableCurrency().getValue());
              subTotal += value;
              grandTotal += value;

             // List<FeesItem> fees = (List<FeesItem>) occupancy.getFees(); //cover
              if (occupancy.getFees() != null && occupancy.getFees().getMandatory_tax() != null ) {

                //<<START>> WIKI Toan Tran - pick up  Mandatory tax
                mandatoryTax += parseDouble(
                        occupancy.getFees().getMandatory_tax().getBillable_currency().getValue());
                currencymandatoryTax = occupancy.getFees().getMandatory_tax().getBillable_currency().getCurrency();
                //<<END>> WIKI Toan Tran - pick up  Mandatory tax
                TourismFee tf = TourismFee.builder()
                      .currency(occupancy.getFees().getMandatory_tax().getBillable_currency().getCurrency())
                      .price(occupancy.getFees().getMandatory_tax().getBillable_currency().getValue())
                      .feesType("mandatory_tax")//fee.getType().replace("_", " "))
                      .build();

                TourismFee tf2 = TourismFee.builder()
                          .currency(occupancy.getFees().getMandatory_tax().getRequest_currency().getCurrency())
                          .price(occupancy.getFees().getMandatory_tax().getRequest_currency().getValue())
                          .feesType("")//fee.getType().replace("_", " "))
                          .build();

                tourismFees.add(tf);
                //20200327 - WIKI Toan Tran - Disable Request_currency
                // tourismFees.add(tf2);

                tourismFee += parseDouble(occupancy.getFees().getMandatory_tax().getBillable_currency().getValue());

                //20200327 - WIKI Toan Tran - Disable Request_currency
                //tourismFee += parseDouble(occupancy.getFees().getMandatory_tax().getRequest_currency().getValue());
                if (TextUtils.isEmpty(tourismFeeCurrency)) {
                    tourismFeeCurrency = occupancy.getFees().getMandatory_tax().getBillable_currency().getCurrency();
                  }

              }
            }
            if (occupancy.getTotals().getMarketingFee() != null) {
              skyDollar = String.valueOf(parseDouble(occupancy.getTotals().getMarketingFee()
                  .getRequestCurrency().getValue()) - (0.0005 * grandTotal));
            }
          }
        }
      }

      for (Map.Entry<String, Occupancy> entry : occupancyMap.entrySet()) {
        Totals totals = entry.getValue().getTotals();
        double roomPrice = 0;
        double feeNTaxes = 0;

        if (totals != null) {
          final double inclusivePrice =
              parseDouble(totals.getInclusive().getBillableCurrency().getValue());

          roomPrice = parseDouble(totals.getExclusive().getBillableCurrency().getValue());
          feeNTaxes = inclusivePrice - roomPrice;
        }

        PriceNTaxes priceNTaxes =
            new PriceNTaxes(roomPrice, feeNTaxes);
        priceNTaxesMap.put(entry.getKey(), priceNTaxes);
      }
    }

    bookingLink = response.getLinks().getBook().getHref();

    tourismFees = tourismFeeAggregator.aggregate(tourismFees);

    PriceCheckSummary summary = PriceCheckSummary.builder()
        .subTotal(subTotal)
        .tourismFee(tourismFee)
        .grandTotal(grandTotal)
        .bookingLink(bookingLink)
        .fees(tourismFees)
        .tourismFeeCurrency(tourismFeeCurrency)
        .skyDollar(skyDollar)
        .mandatoryTax(mandatoryTax)
        .currencymandatoryTax(currencymandatoryTax)
        .build();
    return new PriceCheckResult(summary, priceNTaxesMap);
  }

  private double parseDouble(String value) {
    try {
      return Double.parseDouble(value);
    } catch (Exception e) {
      return 0;
    }
  }
}
