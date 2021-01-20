package com.skypremiuminternational.app.data.model.ean.booking.itinerary;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.data.model.ean.availability.FeesItem;
import com.skypremiuminternational.app.data.model.ean.availability.NightlyItem;
import com.skypremiuminternational.app.data.model.ean.availability.Totals;
import com.skypremiuminternational.app.domain.models.myOrder.Total;

import java.util.List;

public class Pricing {


    @SerializedName("totals")
    private Totals totals;

    @SerializedName("nightly")
    private List<List<NightlyItem>> nightly;

    @SerializedName("fees")
    private FeesItem fees;




    public void setTotals(Totals totals) {
        this.totals = totals;
    }

    public Totals getTotals() {
        return totals;
    }


    public void setNightly(List<List<NightlyItem>> nightly) {
        this.nightly = nightly;
    }

    public List<List<NightlyItem>> getNightly() {
        return nightly;
    }

    public FeesItem getFees() {
        return fees;
    }

    public void setFees(FeesItem fees) {
        this.fees = fees;
    }
}
