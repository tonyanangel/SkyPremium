package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class FeesItem {

    @SerializedName("mandatory_tax")
    private Mandatory_tax mandatory_tax;




    public Mandatory_tax getMandatory_tax() {
        return mandatory_tax;
    }

    public void setMandatory_tax(Mandatory_tax mandatory_tax) {
        this.mandatory_tax = mandatory_tax;
    }


}
