package com.skypremiuminternational.app.domain.models.democheckapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.demo.Item;

import java.util.List;

public class DemoRes {
    @SerializedName("items")
    @Expose
    private List<Item> itemList;




    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
