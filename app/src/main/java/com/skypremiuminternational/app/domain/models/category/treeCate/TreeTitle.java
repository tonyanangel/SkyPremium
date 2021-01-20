package com.skypremiuminternational.app.domain.models.category.treeCate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TreeTitle {

    @SerializedName("filter_label")
    @Expose
    private String filterLabel;
    @SerializedName("filter_type")
    @Expose
    private String filterType;
    @SerializedName("tree")
    @Expose
    private TreeCategory tree;
}
