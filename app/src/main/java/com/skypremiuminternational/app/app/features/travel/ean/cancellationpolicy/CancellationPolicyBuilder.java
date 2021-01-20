package com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy;

import android.content.Context;
import android.util.Log;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

public class CancellationPolicyBuilder {

  private final Context context;

  @Inject
  public CancellationPolicyBuilder(Context context) {
    this.context = context;
  }
  /**
   * 20200310 WIKI Nhat Nguyen - Format penalty dates and set cancellation policy label
   * @param cancelPenalty
   * @param propertyName
   * @param cancelsPenaltiesList
   * @param cancelPenaltyList
   */
  public String build(CancelPenalty cancelPenalty, String propertyName,List<CancelsPenalties> cancelsPenaltiesList,List<CancelPenalty> cancelPenaltyList) {
    String policy="";



    

    if(cancelsPenaltiesList != null && cancelsPenaltiesList.size() > 0){

      if(cancelsPenaltiesList.size() >1){

        String startTime = ConverDay(cancelsPenaltiesList.get(0).getStart(),cancelsPenaltiesList.get(0).getEnd());

        policy = "We understand that sometimes your travel plans change.\n\n";

        policy += "This booking is refundable. ";








        if (Validator.isTextValid(cancelsPenaltiesList.get(0).getAmount()) && Validator.isTextValid(
                cancelsPenaltiesList.get(0).getPercent())) {
          policy += "The property ("
                  + propertyName
                  + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                  + startTime
                  + " are subjected to the amount of "
                  + cancelsPenaltiesList.get(0).getCurrency()
                  + " "
                  + cancelsPenaltiesList.get(0).getAmount()
                  + ", followed by "
                  + cancelsPenaltiesList.get(0).getPercent()
                  + "% of the remaining total booking amount and tax penalty.";
        } else if (Validator.isTextValid(cancelsPenaltiesList.get(0).getNights()) && Validator.isTextValid(
                cancelsPenaltiesList.get(0).getAmount())) {
          policy += "The property ("
                  + propertyName
                  + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                  + startTime
                  + " are subjected to the amount of "
                  + cancelsPenaltiesList.get(0).getCurrency()
                  + " "
                  + cancelsPenaltiesList.get(0).getAmount()
                  + ", followed by "
                  + cancelsPenaltiesList.get(0).getNights()
                  + " room night and tax penalty.";
        } else if (Validator.isTextValid(cancelsPenaltiesList.get(0).getNights())) {

          policy += "Free cancellation until "
                  +startTime
                  +"."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelsPenaltiesList.get(0).getNights()
                  + " night penalty plus taxes and fees in "
                  + cancelsPenaltiesList.get(0).getCurrency()+"."
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else if (Validator.isTextValid(cancelsPenaltiesList.get(0).getAmount())) {
          policy += "Free cancellation until "
                  +startTime
                  +"."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelsPenaltiesList.get(0).getAmount()
                  + cancelsPenaltiesList.get(0).getCurrency()
                  + " fee.\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else if (Validator.isTextValid(cancelsPenaltiesList.get(0).getPercent())) {


          String percent="";
          if(cancelsPenaltiesList.get(0).getPercent().equalsIgnoreCase("1.0000%")) {

            percent = cancelsPenaltiesList.get(0).getPercent().replace("1.0000%", "100");
          }else{
            percent = cancelsPenaltiesList.get(0).getPercent().replace("%", "");
          }
          policy += "Free cancellation until "
                  + startTime
                  + "."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + Math.round(Float.parseFloat(percent)) + "%"
                  + " penalty of the stay charges and fees in "
                  + cancelsPenaltiesList.get(0).getCurrency()+"."
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else {
          policy += "";
        }
        }else{
        String startTime = ConverDay(cancelsPenaltiesList.get(0).getStart(),cancelsPenaltiesList.get(0).getEnd());

        policy = "We understand that sometimes your travel plans change.\n\n";

        policy += "This booking is refundable. ";








        if (Validator.isTextValid(cancelsPenaltiesList.get(0).getAmount()) && Validator.isTextValid(
                cancelsPenaltiesList.get(0).getPercent())) {
          policy += "The property ("
                  + propertyName
                  + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                  + startTime
                  + " are subjected to the amount of "
                  + cancelsPenaltiesList.get(0).getCurrency()
                  + " "
                  + cancelsPenaltiesList.get(0).getAmount()
                  + ", followed by "
                  + cancelsPenaltiesList.get(0).getPercent()
                  + "% of the remaining total booking amount and tax penalty.";
        } else if (Validator.isTextValid(cancelsPenaltiesList.get(0).getNights()) && Validator.isTextValid(
                cancelsPenaltiesList.get(0).getAmount())) {
          policy += "The property ("
                  + propertyName
                  + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                  + startTime
                  + " are subjected to the amount of "
                  + cancelsPenaltiesList.get(0).getCurrency()
                  + " "
                  + cancelsPenaltiesList.get(0).getAmount()
                  + ", followed by "
                  + cancelsPenaltiesList.get(0).getNights()
                  + " room night and tax penalty.";
        } else if (Validator.isTextValid(cancelsPenaltiesList.get(0).getNights())) {

          policy += "Free cancellation until "
                  +startTime
                  +"."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelsPenaltiesList.get(0).getNights()
                  + " night penalty plus taxes and fees in "
                  + cancelsPenaltiesList.get(0).getCurrency()+"."
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else if (Validator.isTextValid(cancelsPenaltiesList.get(0).getAmount())) {
          policy += "Free cancellation until "
                  +startTime
                  +"."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelsPenaltiesList.get(0).getAmount()
                  + cancelsPenaltiesList.get(0).getCurrency()
                  + " fee.\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else if (Validator.isTextValid(cancelsPenaltiesList.get(0).getPercent())) {
          String percent="";
          if(cancelsPenaltiesList.get(0).getPercent().equalsIgnoreCase("1.0000%")) {

            percent = cancelsPenaltiesList.get(0).getPercent().replace("1.0000%", "100");
          }else{
            percent = cancelsPenaltiesList.get(0).getPercent().replace("%", "");
          }
          policy += "Free cancellation until "
                  + startTime
                  +"."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + Math.round(Float.parseFloat(percent)) + "%"
                  + " penalty of the stay charges and fees in "
                  + cancelsPenaltiesList.get(0).getCurrency()+"."
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else {
          policy += "";
        }
        }
      }else if(cancelPenaltyList != null && cancelPenaltyList.size()>0){


      if(cancelPenaltyList.size() >1){

        String startTime = ConverDay(cancelPenaltyList.get(0).start(),cancelPenaltyList.get(0).end());

        policy = "We understand that sometimes your travel plans change.\n\n";

        policy += "This booking is refundable. ";








        if (Validator.isTextValid(cancelPenaltyList.get(0).amount()) && Validator.isTextValid(
                cancelPenaltyList.get(0).percent())) {
          policy += "The property ("
                  + propertyName
                  + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                  + startTime
                  + " are subjected to the amount of "
                  + cancelPenaltyList.get(0).currency()
                  + " "
                  + cancelPenaltyList.get(0).amount()
                  + ", followed by "
                  + cancelPenaltyList.get(0).percent()
                  + "% of the remaining total booking amount and tax penalty.";
        } else if (Validator.isTextValid(cancelPenaltyList.get(0).nights()) && Validator.isTextValid(
                cancelPenaltyList.get(0).amount())) {
          policy += "The property ("
                  + propertyName
                  + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                  + startTime
                  + " are subjected to the amount of "
                  + cancelPenaltyList.get(0).currency()
                  + " "
                  + cancelPenaltyList.get(0).amount()
                  + ", followed by "
                  + cancelPenaltyList.get(0).nights()
                  + " room night and tax penalty.";
        } else if (Validator.isTextValid(cancelPenaltyList.get(0).nights())) {

          policy += "Free cancellation until "
                  +startTime
                  +"."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelPenaltyList.get(0).nights()
                  + " night penalty plus taxes and fees in "
                  + cancelPenaltyList.get(0).currency()+"."
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else if (Validator.isTextValid(cancelPenaltyList.get(0).amount())) {
          policy += "Free cancellation until "
                  +startTime
                  +"."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelPenaltyList.get(0).amount()
                  + cancelPenaltyList.get(0).currency()
                  + " fee.\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else if (Validator.isTextValid(cancelPenaltyList.get(0).percent())) {

          String percent="";
          if(cancelPenaltyList.get(0).percent().equalsIgnoreCase("1.0000%")) {

            percent = cancelPenaltyList.get(0).percent().replace("1.0000%", "100");
          }else{
            percent = cancelPenaltyList.get(0).percent().replace("%", "");
          }
          policy += "Free cancellation until "
                  + startTime
                  + "."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + Math.round(Float.parseFloat(percent)) + "%"
                  + " penalty of the stay charges and fees in "
                  + cancelPenaltyList.get(0).currency()+"."
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else {
          policy += "";
        }
      }else{
        String startTime = ConverDay(cancelPenaltyList.get(0).start(),cancelPenaltyList.get(0).end());

        policy = "We understand that sometimes your travel plans change.\n\n";

        policy += "This booking is refundable. ";








        if (Validator.isTextValid(cancelPenaltyList.get(0).amount()) && Validator.isTextValid(
                cancelPenaltyList.get(0).percent())) {
          policy += "The property ("
                  + propertyName
                  + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                  + startTime
                  + " are subjected to the amount of "
                  + cancelPenaltyList.get(0).currency()
                  + " "
                  + cancelPenaltyList.get(0).amount()
                  + ", followed by "
                  + cancelPenaltyList.get(0).percent()
                  + "% of the remaining total booking amount and tax penalty.";
        } else if (Validator.isTextValid(cancelPenaltyList.get(0).nights()) && Validator.isTextValid(
                cancelPenaltyList.get(0).amount())) {
          policy += "The property ("
                  + propertyName
                  + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                  + startTime
                  + " are subjected to the amount of "
                  + cancelPenaltyList.get(0).currency()
                  + " "
                  + cancelPenaltyList.get(0).amount()
                  + ", followed by "
                  + cancelPenaltyList.get(0).nights()
                  + " room night and tax penalty.";
        } else if (Validator.isTextValid(cancelPenaltyList.get(0).nights())) {

          policy += "Free cancellation until "
                  +startTime
                  +"."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelPenaltyList.get(0).nights()
                  + " night penalty plus taxes and fees in "
                  + cancelPenaltyList.get(0).currency()+"."
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else if (Validator.isTextValid(cancelPenaltyList.get(0).amount())) {
          policy += "Free cancellation until "
                  +startTime
                  +"."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelPenaltyList.get(0).amount()
                  + cancelPenaltyList.get(0).currency()
                  + " fee.\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else if (Validator.isTextValid(cancelPenaltyList.get(0).percent())) {


          String percent="";
          if(cancelPenaltyList.get(0).percent().equalsIgnoreCase("1.0000%")) {

            percent = cancelPenaltyList.get(0).percent().replace("1.0000%", "100");
          }else{
            percent = cancelPenaltyList.get(0).percent().replace("%", "");
          }
          policy += "Free cancellation until "
                  + startTime
                  + "."
                  + "\n\n"
                  + "The property "
                  + propertyName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + Math.round(Float.parseFloat(percent)) + "%"
                  + " penalty of the stay charges and fees in "
                  + cancelPenaltyList.get(0).currency()+"."
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

        } else {
          policy += "";
        }
      }
    }else if (cancelPenalty != null) {

      if(cancelPenalty.cancelPenaltyList() != null && cancelPenalty.cancelPenaltyList().size()>0) {


        List<CancelPenalty> cancellist = cancelPenalty.cancelPenaltyList();
        if (cancellist.size() > 1) {

          String startTime = ConverDay(cancellist.get(0).start(),cancellist.get(0).end());

          policy = "We understand that sometimes your travel plans change.\n\n";

          policy += "This booking is refundable. ";


          if (Validator.isTextValid(cancellist.get(0).amount()) && Validator.isTextValid(
                  cancellist.get(0).percent())) {
            policy += "The property ("
                    + propertyName
                    + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                    + startTime
                    + " are subjected to the amount of "
                    + cancellist.get(0).currency()
                    + " "
                    + cancellist.get(0).amount()
                    + ", followed by "
                    + cancellist.get(0).percent()
                    + "% of the remaining total booking amount and tax penalty.";
          } else if (Validator.isTextValid(cancellist.get(0).nights()) && Validator.isTextValid(
                  cancellist.get(0).amount())) {
            policy += "The property ("
                    + propertyName
                    + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                    + startTime
                    + " are subjected to the amount of "
                    + cancellist.get(0).currency()
                    + " "
                    + cancellist.get(0).amount()
                    + ", followed by "
                    + cancellist.get(0).nights()
                    + " room night and tax penalty.";
          } else if (Validator.isTextValid(cancellist.get(0).nights())) {

            policy += "Free cancellation until "
                    + startTime
                    + "."
                    + "\n\n"
                    + "The property "
                    + propertyName
                    + " imposes a penalty to its customers that we are required to pass on: "
                    + "any cancellations made after "
                    + startTime
                    + " will result in a "
                    + cancellist.get(0).nights()
                    + " night penalty plus taxes and fees in "
                    + cancellist.get(0).currency()+"."
                    + "\n\n"
                    + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

          } else if (Validator.isTextValid(cancellist.get(0).amount())) {
            policy += "Free cancellation until "
                    + startTime
                    + "."
                    + "\n\n"
                    + "The property "
                    + propertyName
                    + " imposes a penalty to its customers that we are required to pass on: any cancellations made after "
                    + startTime
                    + " will result in a "
                    + cancellist.get(0).amount()
                    + cancellist.get(0).currency()
                    + " fee.\n\n"
                    + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

          } else if (Validator.isTextValid(cancellist.get(0).percent())) {

            String percent="";
            if(cancellist.get(0).percent().equalsIgnoreCase("1.0000%")) {

              percent = cancellist.get(0).percent().replace("1.0000%", "100");
            }else{
              percent = cancellist.get(0).percent().replace("%", "");
            }
            policy += "Free cancellation until "
                    + startTime
                    + "."
                    + "\n\n"
                    + "The property "
                    + propertyName
                    + " imposes a penalty to its customers that we are required to pass on: "
                    + "any cancellations made after "
                    + startTime
                    + " will result in a "
                    + Math.round(Float.parseFloat(percent)) + "%"
                    + " penalty of the stay charges and fees in "
                    + cancellist.get(0).currency()+"."
                    + "\n\n"
                    + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

          } else {
            policy += "";
          }
        } else {
          String startTime = ConverDay(cancellist.get(0).start(),cancellist.get(0).end());

          policy = "We understand that sometimes your travel plans change.\n\n";

          policy += "This booking is refundable. ";


          if (Validator.isTextValid(cancellist.get(0).amount()) && Validator.isTextValid(
                  cancellist.get(0).percent())) {
            policy += "The property ("
                    + propertyName
                    + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                    + startTime
                    + " are subjected to the amount of "
                    + cancellist.get(0).currency()
                    + " "
                    + cancellist.get(0).amount()
                    + ", followed by "
                    + cancellist.get(0).percent()
                    + "% of the remaining total booking amount and tax penalty.";
          } else if (Validator.isTextValid(cancellist.get(0).nights()) && Validator.isTextValid(
                  cancellist.get(0).amount())) {
            policy += "The property ("
                    + propertyName
                    + ") imposes a penalty to its customers that we are required to pass on any cancellations or changes made after "
                    + startTime
                    + " are subjected to the amount of "
                    + cancellist.get(0).currency()
                    + " "
                    + cancellist.get(0).amount()
                    + ", followed by "
                    + cancellist.get(0).nights()
                    + " room night and tax penalty.";
          } else if (Validator.isTextValid(cancellist.get(0).nights())) {

            policy += "Free cancellation until "
                    + startTime
                    + "."
                    + "\n\n"
                    + "The property "
                    + propertyName
                    + " imposes a penalty to its customers that we are required to pass on: "
                    + "any cancellations made after "
                    + startTime
                    + " will result in a "
                    + cancellist.get(0).nights()
                    + " night penalty plus taxes and fees in "
                    + cancellist.get(0).currency()+"."
                    + "\n\n"
                    + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

          } else if (Validator.isTextValid(cancellist.get(0).amount())) {
            policy += "Free cancellation until "
                    + startTime
                    + "."
                    + "\n\n"
                    + "The property "
                    + propertyName
                    + " imposes a penalty to its customers that we are required to pass on: any cancellations made after "
                    + startTime
                    + " will result in a "
                    + cancellist.get(0).amount()
                    + cancellist.get(0).currency()
                    + " fee.\n\n"
                    + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

          } else if (Validator.isTextValid(cancellist.get(0).percent())) {

            String percent="";
            if(cancellist.get(0).percent().equalsIgnoreCase("1.0000%")) {

               percent = cancellist.get(0).percent().replace("1.0000%", "100");
            }else{
               percent = cancellist.get(0).percent().replace("%", "");
            }
            policy += "Free cancellation until "
                    + startTime
                    + "."
                    + "\n\n"
                    + "The property "
                    + propertyName
                    + " imposes a penalty to its customers that we are required to pass on: "
                    + "any cancellations made after "
                    + startTime
                    + " will result in a "
                    + Math.round(Float.parseFloat(percent)) + "%"
                    + " penalty of the stay charges and fees in "
                    + cancellist.get(0).currency()+"."
                    + "\n\n"
                    + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.\n";

          } else {
            policy += "";
          }
        }
      }else{
        policy = context.getString(R.string.cancellation_policy_non_refundable);
      }
    }



    else{
        policy = context.getString(R.string.cancellation_policy_non_refundable);
      }




    return policy;
  }

  public String ConverDay(String startday,String endday){
    String daytime = "";
    String startTimes = "";
    String formattedDate = "";
    String value = "";
    try {
      SimpleDateFormat dateFormat =
              new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

      SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm a (z)", Locale.US);
      df.setTimeZone(TimeZone.getTimeZone("GMT+8"));

      SimpleDateFormat dfShort = new SimpleDateFormat("dd MMM yyyy", Locale.US);
      dfShort.setTimeZone(TimeZone.getTimeZone("GMT+8"));

      String startTime = startday;
      String startTimeShort = startday;
      String endTime = endday;
      Date start = dateFormat.parse(startTime);

      startTime = df.format(start).replace("GMT+08:00", "GMT+8").replace(",","");
      startTimeShort = dfShort.format(start);
      startTimes = startTime;

      daytime = startTimeShort;


      String s = startday;
      s = s.substring(s.lastIndexOf("+") + 1);


      int iend = s.indexOf(":");
      s= s.substring(0 , iend);

      int iend2 = s.indexOf(s);
      if(s.substring(0,iend2).equalsIgnoreCase("0")){
        s = s.substring(0,0);
      }

      if(s.equalsIgnoreCase("01")){
        s = "1";
      }
      if(s.equalsIgnoreCase("02")){
        s = "2";
      }
      if(s.equalsIgnoreCase("03")){
        s = "3";
      }
      if(s.equalsIgnoreCase("04")){
        s = "4";
      }
      if(s.equalsIgnoreCase("05")){
        s = "5";
      }
      if(s.equalsIgnoreCase("06")){
        s = "6";
      }
      if(s.equalsIgnoreCase("07")){
        s = "7";
      }
      if(s.equalsIgnoreCase("08")){
        s = "8";
      }
      if(s.equalsIgnoreCase("09")){
        s = "9";
      }


      try {
        String dateStr1 = startTimes.replace("(GMT+8)","");
        SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH);
        df1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date1 = df1.parse(dateStr1);
        df1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String formattedDate1 = df1.format(date1);
        formattedDate = df1.format(date1);
        value = df1.format(date1);
      } catch (ParseException e) {
        e.printStackTrace();
      }




      Date end = dateFormat.parse(endday);
      if(endTime.equalsIgnoreCase("2020-03-25T23:59:00.000Z")){
        endTime = "26 March 2020 07:59 AM(GMT+8)";
      }else {
        endTime = df.format(end).replace("GMT+08:00", "GMT+8");
      }
    } catch (ParseException e) {
      e.printStackTrace();

      try {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm a (z)", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat dfShort = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        dfShort.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        String startTime = startday;
        String startTimeShort = startday;
        String endTime = endday;

        Date start = dateFormat.parse(startTime);

        start.setTime(start.getTime());

        startTime = df.format(start).replace("GMT+08:00", "GMT+8").replace(",","");
        startTimeShort = dfShort.format(start);
        daytime = startTimeShort;
        startTimes = startTime;


        String s = startday;
        s = s.substring(s.lastIndexOf("+") + 1);


        int iend = s.indexOf(":");
        s= s.substring(0 , iend);

        int iend2 = s.indexOf(s);
        if(s.substring(0,iend2).equalsIgnoreCase("0")){
          s = s.substring(0,0);
        }

        try {
          String dateStr1 = startTimes.replace("(GMT+8)","");
          SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH);
          df1.setTimeZone(TimeZone.getTimeZone("GMT+0"));
          Date date1 = df1.parse(dateStr1);
          df1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
          String formattedDate1 = df1.format(date1);
          formattedDate = df1.format(date1);
          value = df1.format(date1);
        } catch (ParseException eq) {
          eq.printStackTrace();
        }

        Date end = dateFormat.parse(endday);
        if(endTime.equalsIgnoreCase("2020-03-25T23:59:00.000Z")){
          endTime = "26 March 2020 07:59 AM(GMT+8)";
        }else {
          endTime = df.format(end).replace("GMT+08:00", "GMT+8");
        }


      } catch (ParseException eq) {
        eq.printStackTrace();
      }
    }






    return value+"(GMT+8)";
  }
}

