package com.skypremiuminternational.app.domain.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryFilterItem implements Serializable {


    @SerializedName("filter_label")
    @Expose
    private Integer id;
    @SerializedName("tree")
    @Expose
    private Integer tree;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("product_count")
    @Expose
    private Integer productCount;


}
