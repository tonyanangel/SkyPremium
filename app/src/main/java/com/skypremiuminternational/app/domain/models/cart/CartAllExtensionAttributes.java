package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aeindraaung on 2/22/18.
 */

public class CartAllExtensionAttributes {
  @SerializedName("reward_points_balance")
  @Expose
  private int rewardPointsBalance;
  @SerializedName("reward_currency_amount")
  @Expose
  private int rewardCurrencyAmount;
  @SerializedName("base_reward_currency_amount")
  @Expose
  private int baseRewardCurrencyAmount;

  @SerializedName("total_sky_dollar_earn")
  @Expose
  private int totalSkyDollarEarn;

  public int getRewardPointsBalance() {
    return rewardPointsBalance;
  }

  public void setRewardPointsBalance(int rewardPointsBalance) {
    this.rewardPointsBalance = rewardPointsBalance;
  }

  public int getRewardCurrencyAmount() {
    return rewardCurrencyAmount;
  }

  public void setRewardCurrencyAmount(int rewardCurrencyAmount) {
    this.rewardCurrencyAmount = rewardCurrencyAmount;
  }

  public int getBaseRewardCurrencyAmount() {
    return baseRewardCurrencyAmount;
  }

  public void setBaseRewardCurrencyAmount(int baseRewardCurrencyAmount) {
    this.baseRewardCurrencyAmount = baseRewardCurrencyAmount;
  }

  public int getTotalSkyDollarEarn() {
    return totalSkyDollarEarn;
  }

  public void setTotalSkyDollarEarn(int totalSkyDollarEarn) {
    this.totalSkyDollarEarn = totalSkyDollarEarn;
  }
}
