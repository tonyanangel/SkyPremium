package com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy;

import android.content.Context;
import android.util.Log;

import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;

import java.util.List;

import javax.inject.Inject;

public class CancellationPolicyfordetailBuilder {

    private final Context context;

    @Inject
    public CancellationPolicyfordetailBuilder(Context context) {
        this.context = context;
    }

    public String build(CancelPenalty cancelPenalty, String propertyName, List<CancelsPenalties> cancelsPenaltiesList) {
        String policy="";


   // Log.d("testttttok",String.valueOf(cancelsPenaltiesList.size()));


  /*  if (cancelPenalty != null && cancelPenalty.isRefundable()) {
      //2018-06-01T00:01:00.000+08:00
      SimpleDateFormat dateFormat =
          new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

      SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy, hh:mm a (z)", Locale.US);
      df.setTimeZone(TimeZone.getTimeZone("GMT+8"));

      String startTime = cancelPenalty.start();
      String endTime = cancelPenalty.end();
      try {
        Date start = dateFormat.parse(cancelPenalty.start());
        startTime = df.format(start);

        Date end = dateFormat.parse(cancelPenalty.end());
        endTime = df.format(end);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      policy = "We understand that sometimes your travel plans change.\n";

      policy += "This booking is refundable. ";








      if (Validator.isTextValid(cancelPenalty.amount()) && Validator.isTextValid(
          cancelPenalty.percent())) {
        policy += "The property ("
            + propertyName
            + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
            + startTime
            + " are subjected to the amount of "
            + cancelPenalty.currency()
            + " "
            + cancelPenalty.amount()
            + ", followed by "
            + cancelPenalty.percent()
            + "% of the remaining total booking amount and tax penalty.";
      } else if (Validator.isTextValid(cancelPenalty.nights()) && Validator.isTextValid(
          cancelPenalty.amount())) {
        policy += "The property ("
            + propertyName
            + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
            + startTime
            + " are subjected to the amount of "
            + cancelPenalty.currency()
            + " "
            + cancelPenalty.amount()
            + ", followed by "
            + cancelPenalty.nights()
            + " room night and tax penalty.";
      } else if (Validator.isTextValid(cancelPenalty.nights())) {

        policy += "Free cancellation until "
                +startTime
                +"."
                + "\n"
                + "The property "
                + propertyName
                + " imposes a penalty to its customers that we are required to pass on:” "
                + "any cancellations made after "
                + endTime
                + " will result in a "
                + cancelPenalty.nights()
                + " night penalty plus taxes and fees in "
                + cancelPenalty.currency()
                + "\n"
                + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

      } else if (Validator.isTextValid(cancelPenalty.amount())) {
          policy += "Free cancellation until "
                  +startTime
                  +"."
                  + "\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: any cancellations made after "
                  + endTime
                  + " will result in a "
                  + cancelPenalty.amount()
                  + cancelPenalty.currency()
                  + " fee.\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

      } else if (Validator.isTextValid(cancelPenalty.percent())) {


        policy += "Free cancellation until "
                + startTime
                + "\n"
                + "The property "
                + propertyName
                + " imposes a penalty to its customers that we are required to pass on:” "
                + "any cancellations made after "
                + endTime
                + " will result in a "
                + cancelPenalty.percent()
                + " penalty of the stay charges and fees in "
                + cancelPenalty.currency()
                + "\n"
                + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

      } else {
        policy += "";
      }

    }
    else{
        policy = context.getString(R.string.cancellation_policy_non_refundable);
      }

   */


        return policy;
    }
}
