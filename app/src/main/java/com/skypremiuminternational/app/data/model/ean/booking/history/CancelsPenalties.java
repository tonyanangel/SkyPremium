package com.skypremiuminternational.app.data.model.ean.booking.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CancelsPenalties implements Serializable {

    @SerializedName("currency")
    private String currency;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    @SerializedName("nights")
    private String nights;

    @SerializedName("amount")
    private String amount;

    @SerializedName("percent")
    private String percent;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return end;
    }

    public void setNights(String nights) {
        this.nights = nights;
    }

    public String getNights() {
        return nights;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getPercent() {
        return percent;
    }


}
