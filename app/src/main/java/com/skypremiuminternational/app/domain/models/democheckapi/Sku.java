package com.skypremiuminternational.app.domain.models.democheckapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sku {
    @SerializedName("id")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("name")
    @Expose
    private String attribute;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
