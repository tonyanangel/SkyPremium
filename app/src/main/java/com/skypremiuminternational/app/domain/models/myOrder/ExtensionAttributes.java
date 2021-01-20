package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class ExtensionAttributes implements Serializable {
  @SerializedName("shipping_assignments")
  @Expose
  private List<ShippingAssignment> shippingAssignments = null;
  @SerializedName("totalloyalty")
  @Expose
  private String  totalloyalty;
  @SerializedName("total_sky_dollar_earn")
  @Expose
  private double  totalSkyDollarEarn;

  public String getTotalloyalty() {
    return totalloyalty;
  }
  public void setTotalloyalty(String totalloyalty) {
    this.totalloyalty = totalloyalty;
  }

  public List<ShippingAssignment> getShippingAssignments() {
    return shippingAssignments;
  }

  public void setShippingAssignments(List<ShippingAssignment> shippingAssignments) {
    this.shippingAssignments = shippingAssignments;
  }

  public double getTotalSkyDollarEarn() {
    return totalSkyDollarEarn;
  }

  public void setTotalSkyDollarEarn(double totalSkyDollarEarn) {
    this.totalSkyDollarEarn = totalSkyDollarEarn;
  }
}
