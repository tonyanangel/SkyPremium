package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class Mandatory_tax {

    @SerializedName("billable_currency")
    private NightlyItem billable_currency;

    @SerializedName("request_currency")
    private NightlyItem request_currency;




    public NightlyItem getBillable_currency() {
        return billable_currency;
    }

    public void setBillable_currency(NightlyItem billable_currency) {
        this.billable_currency = billable_currency;
    }


    public NightlyItem getRequest_currency() {
        return request_currency;
    }

    public void setRequest_currency(NightlyItem request_currency) {
        this.request_currency = request_currency;
    }


}
